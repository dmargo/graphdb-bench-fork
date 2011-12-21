package com.tinkerpop.bench.benchmark;

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

/**
 * @author Alex Averbuch (alex.averbuch@gmail.com)
 */
public class BenchmarkMicro extends Benchmark {

	/*
	 * Static Code
	 */

	public static void run(String[] args) throws Exception {
		String dirResults = Bench.benchProperties
				.getProperty(Bench.RESULTS_DIRECTORY) + "Micro-" + args[0] + "/";
		LogUtils.deleteDir(dirResults);

		String dirGraphML = Bench.benchProperties
				.getProperty(Bench.DATASETS_DIRECTORY);
		String[] graphmlFiles = new String[] {
				dirGraphML + "barabasi_1000_5000.graphml"};
				//dirGraphML + "barabasi_10000_50000.graphml"};
				//dirGraphML + "barabasi_100000_500000.graphml"};
				//dirGraphML + "barabasi_1000000_5000000.graphml"};
		Benchmark benchmark = new BenchmarkMicro(
				dirResults + "benchmark_micro.csv", graphmlFiles);
		
		GraphDescriptor graphDescriptor = null;
		LinkedHashMap<String, String> resultFiles = new LinkedHashMap<String, String>();
		
		// Load operation logs with TinkerGraph
		//graphDescriptor = new GraphDescriptor(TinkerGraph.class);
		//benchmark.loadOperationLogs(graphDescriptor,
		//		dirResults + "benchmark_micro_tinker.csv");
		//resultFiles.put("TinkerGraph", dirResults + "benchmark_micro_tinker.csv");
		
		//XXX dmargo: Load operation logs with SQL
		if (args[0].equals("sql")) {
			graphDescriptor = new GraphDescriptor(SqlGraph.class,
					null, "//localhost/graphdb?user=dmargo&password=kitsune");
			benchmark.loadOperationLogs(graphDescriptor,
					dirResults + "benchmark_micro_sql_warmup.csv");
			resultFiles.put("Warmup", dirResults + "benchmark_micro_sql_warmup.csv");
			
			graphDescriptor = new GraphDescriptor(SqlGraph.class,
					null, "//localhost/graphdb?user=dmargo&password=kitsune");
			benchmark.loadOperationLogs(graphDescriptor,
					dirResults + "benchmark_micro_sql.csv");
			resultFiles.put("Sql", dirResults + "benchmark_micro_sql.csv");
		}

		//XXX dmargo: Load operation logs with RDF
		if (args[0].equals("rdf")) {
			graphDescriptor = new GraphDescriptor(NativeStoreRdfGraph.class,
					dirResults + "rdf_warmup/", dirResults + "rdf_warmup/nativestore");
			benchmark.loadOperationLogs(graphDescriptor,
					dirResults + "benchmark_micro_rdf_warmup.csv");
			resultFiles.put("Warmup", dirResults + "benchmark_micro_rdf_warmup.csv");
			
			graphDescriptor = new GraphDescriptor(NativeStoreRdfGraph.class,
					dirResults + "rdf/", dirResults + "rdf/nativestore");
			benchmark.loadOperationLogs(graphDescriptor,
					dirResults + "benchmark_micro_rdf.csv");
			resultFiles.put("Rdf", dirResults + "benchmark_micro_rdf.csv");
		}
		
		// Load operation logs with Neo4j
		if (args[0].equals("neo")) {
	        graphDescriptor = new GraphDescriptor(Neo4jGraph.class,
					dirResults + "neo4j_warmup/", dirResults + "neo4j_warmup/");
			benchmark.loadOperationLogs(graphDescriptor,
					dirResults + "benchmark_micro_neo4j_warmup.csv");
			resultFiles.put("Warmup", dirResults + "benchmark_micro_neo4j_warmup.csv");
			
	        graphDescriptor = new GraphDescriptor(Neo4jGraph.class,
					dirResults + "neo4j/", dirResults + "neo4j/");
			benchmark.loadOperationLogs(graphDescriptor,
					dirResults + "benchmark_micro_neo4j.csv");
			resultFiles.put("Neo4j", dirResults + "benchmark_micro_neo4j.csv");
		}
		
        //XXX dmargo: Load operation logs with Dup
		if (args[0].equals("dup")) {
			graphDescriptor = new GraphDescriptor(DupGraph.class,
					dirResults + "dup_warmup/", dirResults + "dup_warmup/");
			benchmark.loadOperationLogs(graphDescriptor,
					dirResults + "benchmark_micro_dup_warmup.csv");
			resultFiles.put("Warmup", dirResults + "benchmark_micro_dup_warmup.csv");
			
			graphDescriptor = new GraphDescriptor(DupGraph.class,
					dirResults + "dup/", dirResults + "dup/");
			benchmark.loadOperationLogs(graphDescriptor,
					dirResults + "benchmark_micro_dup.csv");
			resultFiles.put("Dup", dirResults + "benchmark_micro_dup.csv");
		}
		
        //XXX dmargo: Load operation logs with Dex
		if (args[0].equals("dex")) {
	        graphDescriptor = new GraphDescriptor(DexGraph.class, dirResults
	        		+ "dex_warmup/", dirResults + "dex_warmup/graph.dex");
	        benchmark.loadOperationLogs(graphDescriptor, dirResults
	              + "benchmark_micro_dex_warmup.csv");
	        resultFiles.put("Warmup", dirResults + "benchmark_micro_dex_warmup.csv");
			
	        graphDescriptor = new GraphDescriptor(DexGraph.class, dirResults
	        		+ "dex/", dirResults + "dex/graph.dex");
	        benchmark.loadOperationLogs(graphDescriptor, dirResults
	              + "benchmark_micro_dex.csv");
	        resultFiles.put("Dex", dirResults + "benchmark_micro_dex.csv");
		}
		
		//XXX dmargo: Load operation logs with Bdb
		if (args[0].equals("bdb")) {
			graphDescriptor = new GraphDescriptor(BdbGraph.class,
					dirResults + "bdb_warmup/", dirResults + "bdb_warmup/");
			benchmark.loadOperationLogs(graphDescriptor,
					dirResults + "benchmark_micro_bdb_warmup.csv");
			resultFiles.put("Warmup", dirResults + "benchmark_micro_bdb_warmup.csv");	
			
			graphDescriptor = new GraphDescriptor(BdbGraph.class,
					dirResults + "bdb/", dirResults + "bdb/");
			benchmark.loadOperationLogs(graphDescriptor,
					dirResults + "benchmark_micro_bdb.csv");
			resultFiles.put("Bdb", dirResults + "benchmark_micro_bdb.csv");
		}

		// Load operation logs with Orient
        //graphDescriptor = new GraphDescriptor(OrientGraph.class, dirResults
		//        + "orient/", "local:" + dirResults + "orient/");
		//benchmark.loadOperationLogs(graphDescriptor, dirResults
		//        + "benchmark_micro_orient.csv");
		//resultFiles.put("OrientDB", dirResults + "benchmark_micro_orient.csv");

        //XXX dmargo: Load operation logs with Sail
        //graphDescriptor = new GraphDescriptor(NativeStoreSailGraph.class, dirResults
        //		+ "sail/", dirResults + "sail/nativestore");
        //benchmark.loadOperationLogs(graphDescriptor, dirResults
        //        + "benchmark_micro_sail.csv");
        //resultFiles.put("Sail", dirResults + "benchmark_micro_sail.csv");

		// Create file with summarized results from all databases and operations
		LogUtils.makeResultsSummary(
				dirResults + "benchmark_micro_summary.csv", resultFiles);
	}

	/*
	 * Instance Code
	 */
	
	private final int OP_COUNT = 1000;
	private final String PROPERTY_KEY = "_id";
	private final int K_HOPS = 2;

	private String[] graphmlFilenames = null;	

	public BenchmarkMicro(String log, String[] graphmlFilenames) {
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
					new String[] { graphmlFilename }, LogUtils.pathToName(graphmlFilename)));
			
			/*
			// GET microbenchmarks
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
			
			
			// SHORTEST PATH (Djikstra's algorithm)
			operationFactories.add(new OperationFactoryRandomVertexPair(
					OperationGetShortestPath.class, OP_COUNT / 2));
			
			operationFactories.add(new OperationFactoryRandomVertexPair(
					OperationGetShortestPathProperty.class, OP_COUNT / 2));
			*/
			
			// ADD/SET microbenchmarks
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

		return operationFactories;
	}

}
