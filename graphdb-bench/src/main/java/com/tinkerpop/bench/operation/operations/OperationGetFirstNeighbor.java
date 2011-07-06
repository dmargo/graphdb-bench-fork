package com.tinkerpop.bench.operation.operations;

import com.tinkerpop.bench.operation.Operation;
import com.tinkerpop.blueprints.pgm.Vertex;

public class OperationGetFirstNeighbor extends Operation {

	private Vertex startVertex;
	
	@Override
	protected void onInitialize(Object[] args) {
		startVertex = (Vertex) args[0];
	}
	
	@Override
	protected void onExecute() throws Exception {
		try {
			Vertex firstNeighbor = startVertex.getOutEdges().iterator().next().getInVertex();
			setResult(firstNeighbor);
		} catch (Exception e) {
			throw e;
		}
	}

}
