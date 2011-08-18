package com.tinkerpop.bench.operation.operations;

import com.tinkerpop.bench.operation.Operation;

public class OperationGetEdge extends Operation {

	private Object id;
	
	@Override
	protected void onInitialize(Object[] args) {
		id = args[0];
	}
	
	@Override
	protected void onExecute() throws Exception {
		try {
			setResult(getGraph().getEdge(id));
		} catch (Exception e) {
			throw e;
		}
	}

}
