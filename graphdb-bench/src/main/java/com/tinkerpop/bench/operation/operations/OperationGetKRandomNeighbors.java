package com.tinkerpop.bench.operation.operations;

import java.util.ArrayList;
import java.util.Random;

import com.tinkerpop.bench.operation.Operation;
import com.tinkerpop.blueprints.pgm.Edge;
import com.tinkerpop.blueprints.pgm.Vertex;

public class OperationGetKRandomNeighbors extends Operation {

	private Vertex startVertex;
	private final int k = 2;
	
	@Override
	protected void onInitialize(Object[] args) {
		startVertex = (Vertex) args[0];
	}
	
	@Override
	protected void onExecute() throws Exception {
		try {
			Vertex curr = startVertex;
			final ArrayList<Edge> next = new ArrayList<Edge>();
			final ArrayList<Vertex> result = new ArrayList<Vertex>();
						
			for(int i = 0; i < k; i++) {
				for (Edge e : curr.getOutEdges())
					next.add(e);
				if (next.size() > 0) {
					curr = next.get((new Random()).nextInt(next.size())).getInVertex();
					next.clear();
					result.add(curr);
				} else
					break;
			}
		
			setResult(result.size());	
		} catch (Exception e) {
			throw e;
		}
	}

}
