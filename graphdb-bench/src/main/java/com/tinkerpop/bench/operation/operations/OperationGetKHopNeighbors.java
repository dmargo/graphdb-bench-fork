package com.tinkerpop.bench.operation.operations;

import java.util.ArrayList;

import com.tinkerpop.bench.operation.Operation;
import com.tinkerpop.blueprints.pgm.Edge;
import com.tinkerpop.blueprints.pgm.Vertex;

public class OperationGetKHopNeighbors extends Operation {

	private Vertex startVertex;
	private final int k = 2;
	
	@Override
	protected void onInitialize(Object[] args) {
		startVertex = (Vertex) args[0];
	}
	
	@Override
	protected void onExecute() throws Exception {
		try {
			ArrayList<Vertex> curr = new ArrayList<Vertex>();
			ArrayList<Vertex> next = new ArrayList<Vertex>();
			ArrayList<Vertex> result = new ArrayList<Vertex>();
			
			curr.add(startVertex);
			
			for(int i = 0; i < k; i++) {
				for (Vertex u : curr) {
					for (Edge e : u.getOutEdges()) {
						Vertex v = e.getInVertex();
						next.add(v);
						result.add(v);
					}
				}
				ArrayList<Vertex> tmp = curr;
				curr = next;
				tmp.clear();
				next = tmp;
			}
			
			setResult(result);
		} catch (Exception e) {
			throw e;
		}
	}

}
