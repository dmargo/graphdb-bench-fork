package com.tinkerpop.bench.benchmark;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.tinkerpop.bench.Bench;
import com.tinkerpop.bench.GraphDescriptor;
import com.tinkerpop.bench.LogUtils;
import com.tinkerpop.bench.operation.OperationDeleteGraph;
import com.tinkerpop.bench.operation.operations.*;
import com.tinkerpop.bench.operationFactory.OperationFactory;
import com.tinkerpop.bench.operationFactory.OperationFactoryGeneric;
import com.tinkerpop.bench.operationFactory.factories.OperationFactoryRandomVertex;
import com.tinkerpop.bench.operationFactory.factories.OperationFactoryRandomVertexPair;
import com.tinkerpop.blueprints.pgm.impls.bdb.BdbGraph;
import com.tinkerpop.blueprints.pgm.impls.dex.DexGraph;
import com.tinkerpop.blueprints.pgm.impls.dup.DupGraph;
import com.tinkerpop.blueprints.pgm.impls.neo4j.Neo4jGraph;
import com.tinkerpop.blueprints.pgm.impls.orientdb.OrientGraph;
import com.tinkerpop.blueprints.pgm.impls.rdf.impls.NativeStoreRdfGraph;
import com.tinkerpop.blueprints.pgm.impls.sql.SqlGraph;
import com.tinkerpop.blueprints.pgm.impls.tg.TinkerGraph;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

/**
 * @author Alex Averbuch (alex.averbuch@gmail.com)
 * @author Daniel Margo
 * @author Peter Macko
 */
public class BenchmarkMicro extends Benchmark {
	
	/// The default file for ingest
	private static final String DEFAULT_INGEST_FILE = "barabasi_1000_5000.graphml";
	
	/// The list of supported databases
	private static final String[] DATABASE_SHORT_NAMES = { "bdb", "dex", "dup", "neo", "rdf", "sql" };
	
	
	/**
	 * Print the help
	 */
	protected static void help() {
		
		System.err.println("Usage: runBenchmarkSuite.sh OPTIONS");
		System.err.println("");
		System.err.println("General options:");
		System.err.println("  --help            Print this help message");
		System.err.println("  --no-warmup       Disable the initial warmup run");
		System.err.println("");
		System.err.println("Options to select a database (select one):");
		System.err.println("  --bdb             Berkeley DB, using massive indexing");
		System.err.println("  --dex             DEX");
		System.err.println("  --dup             Berkeley DB with duplicates on edge lookups and properties");
		System.err.println("  --neo             neo4j");
		System.err.println("  --rdf             Sesame RDF");
		System.err.println("  --sql             MySQL");
		System.err.println("");
		System.err.println("Options to select a workload (select multiple):");
		System.err.println("  --add             Adding nodes and edges to the database");
		System.err.println("  --dijkstra        Dijkstra's shortest path algorithm");
		System.err.println("  --ingest          Ingest a file to the database");
		System.err.println("  --get             \"Get\" microbenchmarks");
		System.err.println("");
		System.err.println("Ingest options:");
		System.err.println("  -f, --file FILE   Select the file to ingest");
	}

	
	/**
	 * Run the benchmarking program
	 * 
	 * @param args the command-line arguments
	 * @throws Exception on error
	 */
	public static void run(String[] args) throws Exception {		

		/*
		 * Parse the command-line arguments
		 */
		
		OptionParser parser = new OptionParser();
		
		parser.accepts("help");
		parser.accepts("no-warmup");
		
		
		// Databases
		
		for (int i = 0; i < DATABASE_SHORT_NAMES.length; i++) {
			parser.accepts(DATABASE_SHORT_NAMES[i]);
		}
		
		
		// Workloads
		
		parser.accepts("ingest");
		parser.accepts("dijkstra");
		parser.accepts("add");
		parser.accepts("get");
		
		
		// Modifiers
		
		parser.accepts("f").withRequiredArg().ofType(String.class);
		parser.accepts("file").withRequiredArg().ofType(String.class);
		
		
		// Parse the options
		
		OptionSet options;
		
		try {
			options = parser.parse(args);
		}
		catch (Exception e) {
			System.err.println("Invalid options (please use --help for a list): " + e.getMessage());
			return;
		}
		
		
		// Handle the options
		
		if (options.has("help") || !options.hasOptions()) {
			help();
			return;
		}
		
		String dbShortName = null;
		for (int i = 0; i < DATABASE_SHORT_NAMES.length; i++) {
			if (options.has(DATABASE_SHORT_NAMES[i])) {
				if (dbShortName != null) {
					System.err.println("Error: Multiple databases selected.");
					return;
				}
				dbShortName = DATABASE_SHORT_NAMES[i];
			}
		}
		if (dbShortName == null) {
			System.err.println("Error: No database is selected (please use --help for a list of options).");
			return;
		}
		
		String ingestFile = DEFAULT_INGEST_FILE;		
		if (options.has("f") || options.has("file")) {
			ingestFile = options.valueOf(options.has("f") ? "f" : "file").toString();
		}
		
		boolean warmup = true;
		if (options.has("no-warmup")) {
			warmup = false;
		}
		
		
		/*
		 * Setup the benchmark
		 */
		
		String propDirResults = Bench.benchProperties.getProperty(Bench.RESULTS_DIRECTORY);
		if (propDirResults == null) {
			System.err.println("Error: Property \"" + Bench.RESULTS_DIRECTORY + "\" is not set.");
			return;
		}
		if (!propDirResults.endsWith("/")) propDirResults += "/";
		String dirResults = propDirResults + "Micro/";
		
		if (!(new File(ingestFile)).exists()) {
			String dirGraphML = Bench.benchProperties.getProperty(Bench.DATASETS_DIRECTORY);
			if (dirGraphML == null) {
				System.err.println("Warning: Property \"" + Bench.DATASETS_DIRECTORY + "\" is not set.");
				System.err.println("Error: File \"" + ingestFile + "\" does not exist.");
				return;
			}
			if (!dirGraphML.endsWith("/")) dirGraphML += "/";
			if (!(new File(dirGraphML + ingestFile)).exists()) {
				System.err.println("Error: File \"" + ingestFile + "\" does not exist.");
				return;
			}
			else {
				ingestFile = dirGraphML + ingestFile;
			}
		}
		String[] graphmlFiles = new String[] { ingestFile };
		
		Benchmark benchmark = new BenchmarkMicro(dirResults + "benchmark_micro.csv", graphmlFiles, options);
		
		
		/*
		 * Setup the database
		 */
		
		GraphDescriptor graphDescriptor = null;
		
		StringBuilder sb = new StringBuilder();
		for (String s : args) {
			sb.append(s.substring(2));
			sb.append('-');
		}
		sb.append(Runtime.getRuntime().maxMemory());
		String argString = sb.toString();
		
		LinkedHashMap<String, String> resultFiles = new LinkedHashMap<String, String>();
		
		
		// Load operation logs
		
		if (warmup) {
			graphDescriptor = new GraphDescriptor(BdbGraph.class,
					dirResults + dbShortName + "/warmup/", dirResults + dbShortName + "/warmup/");
			benchmark.loadOperationLogs(graphDescriptor,
					dirResults + dbShortName + "/" + dbShortName + "-warmup-" + argString + ".csv");
			resultFiles.put(dbShortName + "-warmup", dirResults + dbShortName + "/bdb-warmup-" + argString + ".csv");
		}
			
		graphDescriptor = new GraphDescriptor(BdbGraph.class,
				dirResults + dbShortName + "/db", dirResults + dbShortName + "/db");
		benchmark.loadOperationLogs(graphDescriptor,
				dirResults + dbShortName + "/" + dbShortName + "-" + argString + ".csv");
		resultFiles.put(dbShortName, dirResults + dbShortName + "/" + dbShortName + "-" + argString + ".csv");
		
		
		// Create file with summarized results from all databases and operations
		
		LogUtils.makeResultsSummary(
				dirResults + dbShortName + "/summary-" + argString + ".csv", resultFiles);
	}

	
	/*
	 * Instance Code
	 */
	
	private final int OP_COUNT = 1000;
	private final String PROPERTY_KEY = "_id";
	private final int K_HOPS = 2;

	private String[] graphmlFilenames = null;
	private OptionSet options = null;

	public BenchmarkMicro(String log, String[] graphmlFilenames, OptionSet options) {
		super(log);
		this.graphmlFilenames = graphmlFilenames;
		this.options = options;
	}

	@Override
	protected ArrayList<OperationFactory> getOperationFactories() {
		ArrayList<OperationFactory> operationFactories = new ArrayList<OperationFactory>();

		for (String graphmlFilename : graphmlFilenames) {
			// INGEST benchmarks
			if (options.has("ingest")) {
				operationFactories.add(new OperationFactoryGeneric(
						OperationDeleteGraph.class, 1));
	
				operationFactories.add(new OperationFactoryGeneric(
						OperationLoadGraphML.class, 1,
						new String[] { graphmlFilename }, LogUtils.pathToName(graphmlFilename)));
			}

			// GET microbenchmarks
			if (options.has("get")) {
				operationFactories.add(new OperationFactoryGeneric(
						OperationGetManyVertices.class, 1,
						new Integer[] { OP_COUNT }));
				//operationFactories.add(new OperationFactoryRandomVertex(
				//		OperationGetVertex.class, OP_COUNT));
				
				operationFactories.add(new OperationFactoryGeneric(
						OperationGetManyVertexProperties.class, 1,
						new Object[] { PROPERTY_KEY, OP_COUNT }));
				//operationFactories.add(new OperationFactoryRandomVertex(
				//		OperationGetVertexProperty.class, OP_COUNT, new String[] { PROPERTY_KEY }));
				
				operationFactories.add(new OperationFactoryGeneric(
						OperationGetManyEdges.class, 1,
						new Integer[] { OP_COUNT }));
				//operationFactories.add(new OperationFactoryRandomEdge(
				//		OperationGetEdge.class, OP_COUNT));
				
				operationFactories.add(new OperationFactoryGeneric(
						OperationGetManyEdgeProperties.class, 1,
						new Object[] { PROPERTY_KEY, OP_COUNT }));
				//operationFactories.add(new OperationFactoryRandomEdge(
				//		OperationGetEdgeProperty.class, OP_COUNT, new String[] { PROPERTY_KEY }));
	
				// GET_NEIGHBORS ops and variants
				operationFactories.add(new OperationFactoryRandomVertex(
						OperationGetFirstNeighbor.class, OP_COUNT, new Integer[] { K_HOPS }));
				
				operationFactories.add(new OperationFactoryRandomVertex(
						OperationGetRandomNeighbor.class, OP_COUNT, new Integer[] { K_HOPS }));
				
				operationFactories.add(new OperationFactoryRandomVertex(
						OperationGetAllNeighbors.class, OP_COUNT, new Integer[] { K_HOPS }));
				
				// GET_K_NEIGHBORS ops and variants
				operationFactories.add(new OperationFactoryRandomVertex(
						OperationGetKFirstNeighbors.class, OP_COUNT));
				
				operationFactories.add(new OperationFactoryRandomVertex(
						OperationGetKRandomNeighbors.class, OP_COUNT));
				
				operationFactories.add(new OperationFactoryRandomVertex(
						OperationGetKHopNeighbors.class, OP_COUNT));
			}
			
			// SHORTEST PATH (Djikstra's algorithm)
			if (options.has("dijkstra")) {
				operationFactories.add(new OperationFactoryRandomVertexPair(
						OperationGetShortestPath.class, OP_COUNT / 2));
				
				operationFactories.add(new OperationFactoryRandomVertexPair(
						OperationGetShortestPathProperty.class, OP_COUNT / 2));
			}
			
			// ADD/SET microbenchmarks
			if (options.has("add")) {
				operationFactories.add(new OperationFactoryGeneric(
						OperationAddManyVertices.class, 1,
						new Integer[] { OP_COUNT }));
				//operationFactories.add(new OperationFactoryGeneric(
				//		OperationAddVertex.class, OP_COUNT));
				
				operationFactories.add(new OperationFactoryGeneric(
						OperationSetManyVertexProperties.class, 1,
						new Object[] { PROPERTY_KEY, OP_COUNT }));
				//operationFactories.add(new OperationFactoryRandomVertex(
				//		OperationSetVertexProperty.class, OP_COUNT, new String[] { PROPERTY_KEY }));
				
				operationFactories.add(new OperationFactoryGeneric(
						OperationAddManyEdges.class, 1,
						new Integer[] { OP_COUNT }));
				//operationFactories.add(new OperationFactoryRandomVertexPair(
				//		OperationAddEdge.class, OP_COUNT));
				
				operationFactories.add(new OperationFactoryGeneric(
						OperationSetManyEdgeProperties.class, 1,
						new Object[] { PROPERTY_KEY, OP_COUNT }));
				//operationFactories.add(new OperationFactoryRandomEdge(
				//		OperationSetEdgeProperty.class, OP_COUNT, new String[] { PROPERTY_KEY}));
			}
					
		}

		return operationFactories;
	}

}
