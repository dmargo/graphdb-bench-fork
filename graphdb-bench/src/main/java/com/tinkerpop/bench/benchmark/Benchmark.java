package com.tinkerpop.bench.benchmark;

import java.io.File;
import java.util.ArrayList;

import com.tinkerpop.bench.BenchRunner;
import com.tinkerpop.bench.GraphDescriptor;
import com.tinkerpop.bench.operationFactory.OperationFactory;

/**
 * @author Alex Averbuch (alex.averbuch@gmail.com)
 */
public abstract class Benchmark {

	public Benchmark() {
	}

	public abstract ArrayList<OperationFactory> getOperationFactories();

	public final void runBenchmark(GraphDescriptor graphDescriptor, String logOut) throws Exception {
		try {
			BenchRunner benchRunner = new BenchRunner(graphDescriptor, new File(logOut), this);
			benchRunner.runBenchmark();
		} catch (Exception e) {
			throw e;
		}
	}
}