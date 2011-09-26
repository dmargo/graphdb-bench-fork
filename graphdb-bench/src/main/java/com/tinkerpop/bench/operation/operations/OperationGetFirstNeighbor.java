package com.tinkerpop.bench.operation.operations;

import java.util.Iterator;

import com.tinkerpop.bench.operation.Operation;
import com.tinkerpop.blueprints.pgm.Edge;
import com.tinkerpop.blueprints.pgm.Vertex;

public class OperationGetFirstNeighbor extends Operation {

	private Vertex startVertex;
	
	@Override
	protected void onInitialize(Object[] args) {
		startVertex = getGraph().getVertex(args[0]);
	}
	
	@Override
	protected void onExecute() throws Exception {
		try {
			Vertex result = null;
			
			Iterator<Edge> iter = startVertex.getOutEdges().iterator();
			if (iter.hasNext())
				result = iter.next().getInVertex();
			
			setResult(result != null ? 1 : 0);
		} catch (Exception e) {
			throw e;
		}
	}

}
