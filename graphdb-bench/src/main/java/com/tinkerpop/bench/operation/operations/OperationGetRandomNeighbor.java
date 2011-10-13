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
		startVertex = getGraph().getVertex(args[0]);
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
			
			setResult(result != null ? result.toString() : "DNE");
			} catch (Exception e) {
			throw e;
		}
	}

}
