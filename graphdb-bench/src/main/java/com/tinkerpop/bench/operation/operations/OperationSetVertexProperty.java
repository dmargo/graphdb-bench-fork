package com.tinkerpop.bench.operation.operations;

import java.util.UUID;

import com.tinkerpop.bench.operation.Operation;
import com.tinkerpop.blueprints.pgm.Vertex;

public class OperationSetVertexProperty extends Operation {

	private Vertex vertex;
	private String property_key;
	private String property_value;
	
	@Override
	protected void onInitialize(Object[] args) {
		vertex = getGraph().getVertex(args[0]);
		property_key = (String) args[1];
		property_value = args.length > 2 ? (String) args[2] : UUID.randomUUID().toString();
	}
	
	@Override
	protected void onExecute() throws Exception {
		try {
			vertex.setProperty(property_key, property_value);
			setResult("DONE");
		} catch (Exception e) {
			throw e;
		}
	}

}
