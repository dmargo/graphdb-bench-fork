package com.tinkerpop.bench;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Vector;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import com.tinkerpop.bench.benchmark.Benchmark;
import com.tinkerpop.bench.log.OperationLogWriter;
import com.tinkerpop.bench.operation.Operation;
import com.tinkerpop.bench.operation.OperationDeleteGraph;
import com.tinkerpop.bench.operation.OperationDoGC;
import com.tinkerpop.bench.operation.OperationOpenGraph;
import com.tinkerpop.bench.operation.OperationShutdownGraph;
import com.tinkerpop.bench.operationFactory.OperationFactory;
import com.tinkerpop.bench.operationFactory.OperationFactoryGeneric;
import com.tinkerpop.bench.operationFactory.OperationFactoryLog;
import com.tinkerpop.bench.operationFactory.factories.WithOpCount;

import edu.harvard.pass.cpl.CPL;


/**
 * Benchmark runner
 * 
 * @author Alex Averbuch (alex.averbuch@gmail.com)
 * @author Martin Neumann (m.neumann.1980@gmail.com)
 * @author Peter Macko (pmacko@eecs.harvard.edu)
 */
public class BenchRunner {
	
	/// Log writer
	private OperationLogWriter logWriter = null;

	/// Graph descriptor
	private GraphDescriptor graphDescriptor = null;

	/// The benchmark to run
	private Benchmark benchmark = null;
	
	/// The number of threads
	private int numThreads;
	
	/// Whether the graph is open
	private boolean graphOpen = false;
	
	/// Shared operation factories
	private OperationFactory openFactory;
	private OperationFactory shutdownFactory;
	private OperationFactory gcFactory;
	
	/// The worker threads
	private Collection<Worker> workers;
	
	/// The operation ID of the last completed operation
	private AtomicInteger operationId;
	
	/// The benchmark semaphore - do not allow more than one benchmark at the time
	private Semaphore benchmarkSemaphore;
	
	/// The barrier for synchronizing tasks
	private CyclicBarrier barrier = null;
	
	
	/**
	 * Create an instance of BenchRunner
	 * 
	 * @param graphDescriptor the graph descriptor
	 * @param logFile the log file
	 * @param benchmark the benchmark to run
	 * @param threads the number of threads
	 * @throws IOException if the log writer fails
	 */
	public BenchRunner(GraphDescriptor graphDescriptor, File logFile,
			Benchmark benchmark, int numThreads) throws IOException {
		
		this.graphDescriptor = graphDescriptor;
		this.benchmark = benchmark;
		this.numThreads = numThreads;

		logWriter = LogUtils.getOperationLogWriter(logFile);

		openFactory = new OperationFactoryGeneric(OperationOpenGraph.class);
		shutdownFactory = new OperationFactoryGeneric(OperationShutdownGraph.class);
		gcFactory = new OperationFactoryGeneric(OperationDoGC.class);
		
		benchmarkSemaphore = new Semaphore(1);
	}
	
	
	/**
	 * Open the graph
	 * 
	 * @throws Exception 
	 */
	protected synchronized void openGraph() throws Exception {
		
		if (graphOpen) return;
		graphOpen = true;
		
		// Flush cache: open/close before/after each factory
		Operation openOperation = openFactory.next();
		openOperation.setLogWriter(logWriter);
		openOperation.initialize(graphDescriptor);
		openOperation.execute();
		logWriter.logOperation(openOperation);
	}
	
	
	/**
	 * Close the graph
	 * 
	 * @throws Exception 
	 */
	protected synchronized void closeGraph() throws Exception {
		
		if (!graphOpen) return;
		graphOpen = false;
		
		// Flush cache: open/close before/after each factory
		Operation shutdownOperation = shutdownFactory.next();
		shutdownOperation.setLogWriter(logWriter);
		shutdownOperation.initialize(graphDescriptor);
		shutdownOperation.execute();
		logWriter.logOperation(shutdownOperation);

		// Try to Garbage Collect
		Operation gcOperation = gcFactory.next();
		gcOperation.setLogWriter(logWriter);
		gcOperation.initialize(graphDescriptor);
		gcOperation.execute();
		logWriter.logOperation(gcOperation);
	}


	/**
	 * Run the benchmark
	 * 
	 * @throws Exception
	 */
	public void runBenchmark() throws Exception {
		
		if (!benchmarkSemaphore.tryAcquire()) {
			throw new IllegalStateException("Cannot execute the same benchmark "
					+ "multiple times at the same time.");
		}
		
		operationId = new AtomicInteger(-1);
		graphOpen = false;
		
		try {
			
			openFactory.initialize(graphDescriptor, operationId);
			shutdownFactory.initialize(graphDescriptor, operationId);
			gcFactory.initialize(graphDescriptor, operationId);
			
			
			// Initialize the barrier concurrency primitive
			
			barrier = new CyclicBarrier(numThreads, new ResetTask());
			
			
			// Create and start the worker threads
			
			workers = new Vector<Worker>(numThreads);
			int numFactories = 0;
			
			for (int i = 0; i < numThreads; i++) {
				
				Collection<OperationFactory> factories = benchmark.createOperationFactories();
				if (i == 0) {
					numFactories = factories.size();
				}
				else {
					if (numFactories != factories.size()) {
						throw new IllegalStateException("Different worker threads have "
								+ "a different number of operation factories");
					}
				}
				
				Worker w = new Worker(i, factories);
				w.start();
				workers.add(w);
			}
			
			
			// Wait for the threads to complete
			
			for (Worker w : workers) {
				w.join();
			}
			
			
			// Finalize
			
			closeGraph();
			graphDescriptor.shutdownGraph();
			logWriter.close();
		}
		finally {
			benchmarkSemaphore.release();
		}
	}

	
	/**
	 * The benchmark thread
	 */
	private class Worker extends Thread {
		
		/// The worker ID
		private int id;
		
		/// The operation factories
		private Collection<OperationFactory> operationFactories = null;
		
		/// The current factory
		private OperationFactory currentFactory = null;
		
		
		/**
		 * Create an instance of class Worker
		 * 
		 * @param id the worker id
		 * @param operationFactories the operation factories
		 */
		public Worker(int id, Collection<OperationFactory> operationFactories) {
			this.id = id;
			this.operationFactories = operationFactories;
		}
		
		
		/**
		 * Get the worker ID
		 * 
		 * @return the worker ID
		 */
		public int getWorkerID() {
			return id;
		}
		
		
		/**
		 * Get the current operation factory
		 * 
		 * @return the current operation factory
		 */
		public OperationFactory getCurrentOperationFactory() {
			return currentFactory;
		}


		/**
		 * Run the worker
		 */
		@Override
		public void run() {
			boolean main = id == 0;
			
			try {
				String lastOperationFactoryName = "";
				for (OperationFactory operationFactory : operationFactories) {
					currentFactory = operationFactory;

					String factoryName = operationFactory.getClass().getSimpleName();
					if (!factoryName.equals(lastOperationFactoryName)) {
						if (main) ConsoleUtils.header(factoryName);
						lastOperationFactoryName = factoryName;
					}
					
					
					// Barrier + reset/open the graph
					
					barrier.await();
					
					
					// Initialize the operation factory

					operationFactory.initialize(graphDescriptor, operationId);

					
					// Run the operations

					for (Operation operation : operationFactory) {

						operation.setLogWriter(logWriter);
						operation.initialize(graphDescriptor);

						if (main) {
							System.out.printf("\r\tOperation %d, %s",
									operation.getId(),
									operation.getName());
							System.out.flush();
						}
						
						if (CPL.isAttached()) {
							if (!(operation instanceof OperationDeleteGraph)
									&& !(operation instanceof OperationDoGC)) {
								operation.getCPLObject().dataFlowFrom(graphDescriptor.getCPLObject());
							}
						}
						
						
						// Execute the operation
						
						operation.execute();
						
						
						// Finalize the operation

						logWriter.logOperation(operation);
						if (CPL.isAttached()) {
							logWriter.getCPLObject().dataFlowFrom(operation.getCPLObject());
						}
						
						if (main && operationFactory instanceof WithOpCount) {
							WithOpCount w = (WithOpCount) operationFactory;
							if (w.getOpCount() > 1) {
								ConsoleUtils.printProgressIndicator(w.getExecutedOpCount(), w.getOpCount());
							}
						}
					}
					
					
					// Finalize the operation factory
					
					if (main) System.out.println();
				}
			}
			catch (RuntimeException e) {
				throw e;
			}
			catch (Exception e) {
				throw new RuntimeException("Worker " + id + " failed", e);
			}
		}
	}
	
	
	/**
	 * The graph reset / cache flush task
	 */
	private class ResetTask implements Runnable {
		
		/**
		 * Create an instance of the class
		 */
		public ResetTask() {
		}
		
		
		/**
		 * Run the task
		 */
		@Override
		public void run() {
			
			// Get the current operation factory
			
			OperationFactory currentFactory = null;
			for (Worker w : workers) {
				if (w.getWorkerID() == 0) {
					currentFactory = w.getCurrentOperationFactory();
					break;
				}
			}
			if (currentFactory == null) {
				throw new IllegalStateException("Could not find the current "
						+ "operation factory from worker 0");
			}
			
			
			// Check consistency across the different threads
			
			for (Worker w : workers) {
				if (!currentFactory.getClass().equals(w.getCurrentOperationFactory().getClass())) {
					throw new IllegalStateException("Inconsistent operation factories "
							+ "between worker threads 0 and " + w.getWorkerID());
				}
			}
			
			
			// Flush the cache by closing and opening the graph
			
			if (!(currentFactory instanceof OperationFactoryLog)) {
				try {
					closeGraph();
					openGraph();
				}
				catch (RuntimeException e) {
					throw e;
				}
				catch (Exception e) {
					throw new RuntimeException("Graph reset / cache flush failed", e);
				}
			}
		}
	}
}
