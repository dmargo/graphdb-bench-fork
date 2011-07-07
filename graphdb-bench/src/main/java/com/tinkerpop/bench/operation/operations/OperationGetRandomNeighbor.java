package com.tinkerpop.bench.operation.operations;

import java.util.ArrayList;
import java.util.Random;

import com.tinkerpop.bench.operation.Operation;
import com.tinkerpop.blueprints.pgm.Edge;
import com.tinkerpop.blueprints.pgm.Vertex;

public class OperationGetRandomNeighbor extends Operation {

	private Vertex startVertex;
	
	@Override
	protected void onInitialize(Object[] args) {
		startVertex = (Vertex) args[0];
	}
	
	@Override
	protected void onExecute() throws Exception {
		try {
			Vertex result = null;
			
			final ArrayList<Edge> edges = new ArrayList<Edge>();
			for (Edge e : startVertex.getOutEdges())
				edges.add(e);
			if (edges.size() > 0)
				result = edges.get((new Random()).nextInt(edges.size())).getInVertex();
			
			setResult(result == null ? 0 : 1);
			} catch (Exception e) {
			throw e;
		}
	}

}
