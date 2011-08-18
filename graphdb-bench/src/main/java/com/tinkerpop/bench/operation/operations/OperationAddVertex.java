package com.tinkerpop.bench.operation.operations;

import com.tinkerpop.bench.operation.Operation;

public class OperationAddVertex extends Operation {
	
	private Object id;
	
	@Override
	protected void onInitialize(Object[] args) {
		id = args.length > 0 ? args[0] : null;
	}
	
	@Override
	protected void onExecute() throws Exception {
		try {
			setResult(getGraph().addVertex(id));
		} catch (Exception e) {
			throw e;
		}
	}

}
