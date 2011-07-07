package com.tinkerpop.bench.benchmark;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.tinkerpop.bench.Bench;
import com.tinkerpop.bench.GraphDescriptor;
import com.tinkerpop.bench.LogUtils;
import com.tinkerpop.bench.operation.OperationDeleteGraph;
import com.tinkerpop.bench.operation.operations.OperationGetAllNeighbors;
import com.tinkerpop.bench.operation.operations.OperationGetFirstNeighbor;
import com.tinkerpop.bench.operation.operations.OperationGetKFirstNeighbors;
import com.tinkerpop.bench.operation.operations.OperationGetKHopNeighbors;
import com.tinkerpop.bench.operation.operations.OperationGetKRandomNeighbors;
import com.tinkerpop.bench.operation.operations.OperationGetRandomNeighbor;
import com.tinkerpop.bench.operation.operations.OperationGetShortestPath;
import com.tinkerpop.bench.operation.operations.OperationLoadGraphML;
import com.tinkerpop.bench.operationFactory.OperationFactory;
import com.tinkerpop.bench.operationFactory.OperationFactoryGeneric;
import com.tinkerpop.bench.operationFactory.factories.OperationFactoryRandomVertex;
import com.tinkerpop.bench.operationFactory.factories.OperationFactoryRandomVertexPair;
import com.tinkerpop.blueprints.pgm.impls.bdb.BdbGraph;
import com.tinkerpop.blueprints.pgm.impls.dex.DexGraph;
import com.tinkerpop.blueprints.pgm.impls.neo4j.Neo4jGraph;
import com.tinkerpop.blueprints.pgm.impls.orientdb.OrientGraph;
import com.tinkerpop.blueprints.pgm.impls.sail.impls.NativeStoreSailGraph;
import com.tinkerpop.blueprints.pgm.impls.tg.TinkerGraph;

/**
 * @author Alex Averbuch (alex.averbuch@gmail.com)
 */
public class BenchmarkRandomTraversals extends Benchmark {

	/*
	 * Static Code
	 */

	public static void run() throws Exception {
		String dirResults = Bench.benchProperties
				.getProperty(Bench.RESULTS_DIRECTORY)
				+ "RandomTraversals/";

		LogUtils.deleteDir(dirResults);

		String dirGraphML = Bench.benchProperties
				.getProperty(Bench.DATASETS_DIRECTORY);

		GraphDescriptor graphDescriptor = null;

		String[] graphmlFiles = new String[] {
				dirGraphML + "barabasi_1000_5000.graphml" };
		//		dirGraphML + "barabasi_10000_50000.graphml",
		//		dirGraphML + "barabasi_100000_500000.graphml",
		//		dirGraphML + "barabasi_1000000_5000000.graphml" };

		Benchmark benchmark = new BenchmarkRandomTraversals(dirResults
				+ "load_graphml.csv", graphmlFiles);
		
		//XXX dmargo: Load operation logs with Bdb
		graphDescriptor = new GraphDescriptor(BdbGraph.class, dirResults
				+ "bdb/", dirResults + "bdb/");
		benchmark.loadOperationLogs(graphDescriptor, dirResults
				+ "load_graphml_bdb.csv");
		
        //XXX dmargo: Load operation logs with Dex
        //graphDescriptor = new GraphDescriptor(DexGraph.class, dirResults
        //		+ "dex/", dirResults + "dex/graph.dex");
        //benchmark.loadOperationLogs(graphDescriptor, dirResults
        //      + "load_graphml_dex.csv");

		// Load operation logs with Neo4j
        graphDescriptor = new GraphDescriptor(Neo4jGraph.class, dirResults
              + "neo4j/", dirResults + "neo4j/");
		benchmark.loadOperationLogs(graphDescriptor, dirResults
				+ "load_graphml_neo4j.csv");

		// Load operation logs with Orient
        //graphDescriptor = new GraphDescriptor(OrientGraph.class, dirResults
		//        + "orient/", "local:" + dirResults + "orient/");
		//benchmark.loadOperationLogs(graphDescriptor, dirResults
		//        + "load_graphml_orient.csv");

        //XXX dmargo: Load operation logs with Sail
        //graphDescriptor = new GraphDescriptor(NativeStoreSailGraph.class, dirResults
        //		+ "sail/", dirResults + "sail/nativestore");
        //benchmark.loadOperationLogs(graphDescriptor, dirResults
        //        + "load_graphml_sail.csv");

		// Load operation logs with TinkerGraph
		graphDescriptor = new GraphDescriptor(TinkerGraph.class);
		benchmark.loadOperationLogs(graphDescriptor, dirResults
				+ "load_graphml_tinker.csv");

		// Create file with summarized results from all databases and operations
		LinkedHashMap<String, String> resultFiles = new LinkedHashMap<String, String>();
		resultFiles.put("Bdb", dirResults + "load_graphml_bdb.csv");
        //resultFiles.put("Dex", dirResults + "load_graphml_dex.csv");
		resultFiles.put("Neo4j", dirResults + "load_graphml_neo4j.csv");
		//resultFiles.put("OrientDB", dirResults + "load_graphml_orient.csv");
        //resultFiles.put("Sail", dirResults + "load_graphml_sail.csv");
		resultFiles.put("TinkerGraph", dirResults + "load_graphml_tinker.csv");
		LogUtils.makeResultsSummary(dirResults + "load_graphml_summary.csv",
				resultFiles);
	}

	/*
	 * Instance Code
	 */

	private String[] graphmlFilenames = null;

	private final int OP_COUNT = 1000;

	public BenchmarkRandomTraversals(String log, String[] graphmlFilenames) {
		super(log);
		this.graphmlFilenames = graphmlFilenames;
	}

	@Override
	protected ArrayList<OperationFactory> getOperationFactories() {
		ArrayList<OperationFactory> operationFactories = new ArrayList<OperationFactory>();

		for (String graphmlFilename : graphmlFilenames) {
			operationFactories.add(new OperationFactoryGeneric(
					OperationDeleteGraph.class, 1));

			operationFactories.add(new OperationFactoryGeneric(
					OperationLoadGraphML.class, 1,
					new String[] { graphmlFilename }, LogUtils
							.pathToName(graphmlFilename)));

			operationFactories.add(new OperationFactoryRandomVertex(
					OperationGetFirstNeighbor.class, OP_COUNT));
			
			operationFactories.add(new OperationFactoryRandomVertex(
					OperationGetRandomNeighbor.class, OP_COUNT));
			
			operationFactories.add(new OperationFactoryRandomVertex(
					OperationGetAllNeighbors.class, OP_COUNT));
			
			operationFactories.add(new OperationFactoryRandomVertex(
					OperationGetKFirstNeighbors.class, OP_COUNT));
			
			operationFactories.add(new OperationFactoryRandomVertex(
					OperationGetKRandomNeighbors.class, OP_COUNT));
			
			operationFactories.add(new OperationFactoryRandomVertex(
					OperationGetKHopNeighbors.class, OP_COUNT));
			
			operationFactories.add(new OperationFactoryRandomVertexPair(
					OperationGetShortestPath.class, OP_COUNT / 2));
		}

		return operationFactories;
	}

}
