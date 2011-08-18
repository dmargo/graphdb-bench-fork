package com.tinkerpop.bench;

import com.tinkerpop.bench.benchmark.BenchmarkEchoVersusDepth;
import com.tinkerpop.bench.benchmark.BenchmarkMicro;
import com.tinkerpop.bench.benchmark.BenchmarkRandomTraversals;
import com.tinkerpop.bench.benchmark.BenchmarkReadWriteVersusSize;

public class BenchmarkSuite {
	public static void main(String[] args) throws Exception {
		//BenchmarkEchoVersusDepth.run();
		BenchmarkMicro.run();
		//BenchmarkRandomTraversals.run();
		//BenchmarkReadWriteVersusSize.run();
	}
}
