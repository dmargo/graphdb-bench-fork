package com.tinkerpop.bench.operation.operations;

import java.util.HashSet;

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
			
			int closedTriplets = 0;
			int totalTriplets = 0;
			HashSet<Vertex> neighbors = new HashSet<Vertex>();
			
			for (Vertex v : graph.getVertices()) {
				neighbors.clear();
				
				// Let all edges be undirected
				
				for (Edge e : v.getOutEdges()) {
					Vertex w = e.getInVertex();
					neighbors.add(w);
				}
				
				for (Edge e : v.getInEdges()) {
					Vertex w = e.getOutVertex();
					neighbors.add(w);
				}
				
				int k = neighbors.size();
				totalTriplets += k * (k - 1);
				if (k <= 1) continue;
				
				for (Vertex w : neighbors) {
					for (Edge e : w.getOutEdges()) {
						if (neighbors.contains(e.getInVertex())) {
							closedTriplets++;
						}
					}
				}
			}
			
			double C = totalTriplets == 0 ? 0 : (closedTriplets / totalTriplets);
			setResult("" + C);
			
		} catch (Exception e) {
			throw e;
		}
	}
}
