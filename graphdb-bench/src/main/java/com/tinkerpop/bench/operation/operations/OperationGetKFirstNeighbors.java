package com.tinkerpop.bench.operation.operations;

import java.util.ArrayList;
import java.util.Iterator;

import com.tinkerpop.bench.operation.Operation;
import com.tinkerpop.blueprints.pgm.Edge;
import com.tinkerpop.blueprints.pgm.Vertex;

public class OperationGetKFirstNeighbors extends Operation {

	private Vertex startVertex;
	private final int k = 2;
	
	@Override
	protected void onInitialize(Object[] args) {
		startVertex = getGraph().getVertex(args[0]);
	}
	
	@Override
	protected void onExecute() throws Exception {
		try {
			Vertex curr = startVertex;
			final ArrayList<Vertex> result = new ArrayList<Vertex>();
			
			for(int i = 0; i < k; i++) {
				Iterator<Edge> iter = curr.getOutEdges().iterator();
				if (iter.hasNext()) {
					curr = iter.next().getInVertex();
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
