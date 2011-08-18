package com.tinkerpop.bench.operation.operations;

import com.tinkerpop.bench.operation.Operation;

public class OperationGetVertex extends Operation {

	private Object id;
	
	@Override
	protected void onInitialize(Object[] args) {
		id = args[0];
	}
	
	@Override
	protected void onExecute() throws Exception {
		try {
			setResult(getGraph().getVertex(id));
		} catch (Exception e) {
			throw e;
		}
	}

}
