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

import joptsimple.OptionParser;
import joptsimple.OptionSet;

/**
 * @author Alex Averbuch (alex.averbuch@gmail.com)
 */
public class BenchmarkMicro extends Benchmark {

	/*
	 * Static Code
	 */

	public static void run(String[] args) throws Exception {		
		String dirResults = Bench.benchProperties.getProperty(Bench.RESULTS_DIRECTORY) + "Micro/";
		
		String dirGraphML = Bench.benchProperties.getProperty(Bench.DATASETS_DIRECTORY);
		String[] graphmlFiles = new String[] {
				//dirGraphML + "barabasi_1000_5000.graphml"};
				//dirGraphML + "barabasi_10000_50000.graphml"};
				//dirGraphML + "barabasi_100000_500000.graphml"};
				dirGraphML + "barabasi_1000000_5000000.graphml"};
		
		OptionParser parser = new OptionParser();
		parser.accepts("bdb");
		parser.accepts("dex");
		parser.accepts("dup");
		parser.accepts("neo");
		parser.accepts("rdf");
		parser.accepts("sql");
		
		parser.accepts("ingest");
		parser.accepts("dijkstra");
		parser.accepts("add");
		OptionSet options = parser.parse(args);
		
		Benchmark benchmark = new BenchmarkMicro(dirResults + "benchmark_micro.csv", graphmlFiles, options);
		
		
		GraphDescriptor graphDescriptor = null;
		
		StringBuilder sb = new StringBuilder();
		for (String s : args) {
			sb.append(s.substring(2));
			sb.append('-');
		}
		sb.append(Runtime.getRuntime().maxMemory());
		String argString = sb.toString();
		
		LinkedHashMap<String, String> resultFiles = new LinkedHashMap<String, String>();
		
		
		//XXX dmargo: Load operation logs with Bdb
		if (options.has("bdb")) {
			graphDescriptor = new GraphDescriptor(BdbGraph.class,
					dirResults + "bdb/warmup/", dirResults + "bdb/warmup/");
			benchmark.loadOperationLogs(graphDescriptor,
					dirResults + "bdb/bdb-warmup-" + argString + ".csv");
			resultFiles.put("Bdb-Warmup", dirResults + "bdb/bdb-warmup-" + argString + ".csv");	
			
			graphDescriptor = new GraphDescriptor(BdbGraph.class,
					dirResults + "bdb/db", dirResults + "bdb/db");
			benchmark.loadOperationLogs(graphDescriptor,
					dirResults + "bdb/bdb-" + argString + ".csv");
			resultFiles.put("Bdb", dirResults + "bdb/bdb-" + argString + ".csv");
			
			// Create file with summarized results from all databases and operations
			LogUtils.makeResultsSummary(
					dirResults + "bdb/summary-" + argString + ".csv", resultFiles);
		}
		
        //XXX dmargo: Load operation logs with Dex
		else if (options.has("dex")) {
	        graphDescriptor = new GraphDescriptor(DexGraph.class,
	        		dirResults + "dex/", dirResults + "dex/warmup.dex");
	        benchmark.loadOperationLogs(graphDescriptor,
	        		dirResults + "dex/dex-warmup-" + argString + ".csv");
	        resultFiles.put("Dex-Warmup", dirResults + "dex/dex-warmup-" + argString + ".csv");
			
	        graphDescriptor = new GraphDescriptor(DexGraph.class,
	        		dirResults + "dex/", dirResults + "dex/db.dex");
	        benchmark.loadOperationLogs(graphDescriptor,
	        		dirResults + "dex/dex-" + argString + ".csv");
	        resultFiles.put("Dex", dirResults + "dex/dex-" + argString + ".csv");
	        
			// Create file with summarized results from all databases and operations
			LogUtils.makeResultsSummary(
					dirResults + "dex/summary-" + argString + ".csv", resultFiles);
		}
		
        //XXX dmargo: Load operation logs with Dup
		else if (options.has("dup")) {
			graphDescriptor = new GraphDescriptor(DupGraph.class,
					dirResults + "dup/warmup/", dirResults + "dup/warmup/");
			benchmark.loadOperationLogs(graphDescriptor,
					dirResults + "dup/dup-warmup-" + argString + ".csv");
			resultFiles.put("Dup-Warmup", dirResults + "dup/dup-warmup-" + argString + ".csv");
			
			graphDescriptor = new GraphDescriptor(DupGraph.class,
					dirResults + "dup/db/", dirResults + "dup/db/");
			benchmark.loadOperationLogs(graphDescriptor,
					dirResults + "dup/dup-" + argString + ".csv");
			resultFiles.put("Dup", dirResults + "dup/dup-" + argString + ".csv");
			
			// Create file with summarized results from all databases and operations
			LogUtils.makeResultsSummary(
					dirResults + "dup/summary-" + argString + ".csv", resultFiles);
		}
		
		// Load operation logs with Neo4j
		else if (options.has("neo")) {
	        graphDescriptor = new GraphDescriptor(Neo4jGraph.class,
					dirResults + "neo4j/warmup/", dirResults + "neo4j/warmup/");
			benchmark.loadOperationLogs(graphDescriptor,
					dirResults + "neo4j/neo4j-warmup-" + argString + ".csv");
			resultFiles.put("Neo4j-Warmup", dirResults + "neo4j/neo4j-warmup-" + argString + ".csv");
			
	        graphDescriptor = new GraphDescriptor(Neo4jGraph.class,
					dirResults + "neo4j/db", dirResults + "neo4j/db");
			benchmark.loadOperationLogs(graphDescriptor,
					dirResults + "neo4j/neo4j-" + argString + ".csv");
			resultFiles.put("Neo4j", dirResults + "neo4j/neo4j-" + argString + ".csv");
			
			// Create file with summarized results from all databases and operations
			LogUtils.makeResultsSummary(
					dirResults + "neo4j/summary-" + argString + ".csv", resultFiles);
		}
		
		//XXX dmargo: Load operation logs with RDF
		else if (options.has("rdf")) {
			graphDescriptor = new GraphDescriptor(NativeStoreRdfGraph.class,
					dirResults + "rdf/warmup/", dirResults + "rdf/warmup/");
			benchmark.loadOperationLogs(graphDescriptor,
					dirResults + "rdf/rdf-warmup-" + argString + ".csv");
			resultFiles.put("Rdf-Warmup", dirResults + "rdf/rdf-warmup-" + argString + ".csv");
			
			graphDescriptor = new GraphDescriptor(NativeStoreRdfGraph.class,
					dirResults + "rdf/db/", dirResults + "rdf/db/");
			benchmark.loadOperationLogs(graphDescriptor,
					dirResults + "rdf/rdf-" + argString + ".csv");
			resultFiles.put("Rdf", dirResults + "rdf/rdf-" + argString + ".csv");
			
			// Create file with summarized results from all databases and operations
			LogUtils.makeResultsSummary(
					dirResults + "rdf/summary-" + argString + ".csv", resultFiles);
		}
		
		//XXX dmargo: Load operation logs with SQL
		else if (options.has("sql")) {
			graphDescriptor = new GraphDescriptor(SqlGraph.class,
					null, "//localhost/graphdb?user=dmargo&password=kitsune");
			benchmark.loadOperationLogs(graphDescriptor,
					dirResults + "sql/sql-warmup-" + argString + ".csv");
			resultFiles.put("Sql-Warmup", dirResults + "sql/sql-warmup-" + argString + ".csv");
			
			graphDescriptor = new GraphDescriptor(SqlGraph.class,
					null, "//localhost/graphdb?user=dmargo&password=kitsune");
			benchmark.loadOperationLogs(graphDescriptor,
					dirResults + "sql/sql-" + argString + ".csv");
			resultFiles.put("Sql", dirResults + "sql/sql-" + argString + ".csv");
			
			// Create file with summarized results from all databases and operations
			LogUtils.makeResultsSummary(
					dirResults + "sql/summary-" + argString + ".csv", resultFiles);
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
		
		// Load operation logs with TinkerGraph
		//graphDescriptor = new GraphDescriptor(TinkerGraph.class);
		//benchmark.loadOperationLogs(graphDescriptor,
		//		dirResults + "benchmark_micro_tinker.csv");
		//resultFiles.put("TinkerGraph", dirResults + "benchmark_micro_tinker.csv");
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
