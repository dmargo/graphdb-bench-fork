package com.tinkerpop.bench.operation.operations;

import com.tinkerpop.bench.cache.Cache;
import com.tinkerpop.bench.operation.Operation;
import com.tinkerpop.blueprints.pgm.Vertex;

public class OperationAddVertex extends Operation {
	
	private Object id;
	
	@Override
	protected void onInitialize(Object[] args) {
		id = args.length > 0 ? args[0] : null;
	}
	
	@Override
	protected void onExecute() throws Exception {
		try {
			Vertex vertex = getGraph().addVertex(id);
			setResult(vertex.toString());
			Cache.getInstance(getGraph()).addVertex(vertex);
		} catch (Exception e) {
			throw e;
		}
	}

}
