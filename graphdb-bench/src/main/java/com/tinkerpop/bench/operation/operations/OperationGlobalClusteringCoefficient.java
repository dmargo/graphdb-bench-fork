package com.tinkerpop.bench.operation.operations;

import java.util.HashSet;

import com.tinkerpop.bench.GraphUtils;
import com.tinkerpop.bench.operation.Operation;
import com.tinkerpop.blueprints.pgm.Edge;
import com.tinkerpop.blueprints.pgm.Graph;
import com.tinkerpop.blueprints.pgm.Vertex;

public class OperationGlobalClusteringCoefficient extends Operation {
	
	@Override
	protected void onInitialize(Object[] args) {
	}
	
	@Override
	protected void onExecute() throws Exception {
		try {
			Graph graph = getGraph();
			GraphUtils.OpStat stat = new GraphUtils.OpStat();
			
			int closedTriplets = 0;
			int totalTriplets = 0;
			HashSet<Vertex> neighbors = new HashSet<Vertex>();
			
			stat.num_getVertices++;
			for (Vertex v : graph.getVertices()) {
				stat.num_getVerticesNext++;
				stat.num_uniqueVertices++;
				neighbors.clear();
				
				// Let all edges be undirected
				
				stat.num_getOutEdges++;
				for (Edge e : v.getOutEdges()) {
					stat.num_getInVertex++;
					Vertex w = e.getInVertex();
					neighbors.add(w);
				}
				
				stat.num_getInEdges++;
				for (Edge e : v.getInEdges()) {
					stat.num_getOutVertex++;
					Vertex w = e.getOutVertex();
					neighbors.add(w);
				}
				
				int k = neighbors.size();
				totalTriplets += k * (k - 1);
				if (k <= 1) continue;
				
				for (Vertex w : neighbors) {
					stat.num_getOutEdges++;
					for (Edge e : w.getOutEdges()) {
						stat.num_getInVertex++;
						if (neighbors.contains(e.getInVertex())) {
							closedTriplets++;
						}
					}
				}
			}
			
			double C = totalTriplets == 0 ? 0 : (closedTriplets / (double) totalTriplets);
			setResult("" + C + ":" + stat);
			
		} catch (Exception e) {
			throw e;
		}
	}
}
