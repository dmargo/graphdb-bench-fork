package com.tinkerpop.bench.operation.operations;

import java.util.ArrayList;

import com.tinkerpop.bench.operation.Operation;
import com.tinkerpop.blueprints.pgm.Edge;
import com.tinkerpop.blueprints.pgm.Vertex;

public class OperationGetAllNeighbors extends Operation {

	private Vertex startVertex;
	
	@Override
	protected void onInitialize(Object[] args) {
		startVertex = (Vertex) args[0];
	}
	
	@Override
	protected void onExecute() throws Exception {
		try {
			final ArrayList<Vertex> neighbors = new ArrayList<Vertex>();
			for (Edge e : startVertex.getOutEdges())
				neighbors.add(e.getInVertex());
			setResult(neighbors.size());
		} catch (Exception e) {
			throw e;
		}
	}

}
