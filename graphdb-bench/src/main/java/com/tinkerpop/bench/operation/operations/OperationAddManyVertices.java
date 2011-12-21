package com.tinkerpop.bench.operation.operations;

import com.tinkerpop.bench.operation.Operation;
import com.tinkerpop.blueprints.pgm.Graph;

public class OperationAddManyVertices extends Operation {
	
	private int opCount;
	
	private Object id;
	
	@Override
	protected void onInitialize(Object[] args) {
		opCount = args.length > 0 ? (Integer) args[0] : 1000;
		
		id = null; //args.length > 1 ? args[1] : null;
	}
	
	@Override
	protected void onExecute() throws Exception {
		try {
			Graph graph = getGraph();
			
			for (int i = 0; i < opCount; i++)
				graph.addVertex(id);
			
			setResult(opCount);
		} catch (Exception e) {
			throw e;
		}
	}

}
