package com.tinkerpop.bench.operation.operations;

import com.tinkerpop.bench.cache.Cache;
import com.tinkerpop.bench.operation.Operation;
import com.tinkerpop.blueprints.pgm.Edge;
import com.tinkerpop.blueprints.pgm.Vertex;

import edu.harvard.pass.cpl.CPL;
import edu.harvard.pass.cpl.CPLObject;

public class OperationAddEdge extends Operation {

	private Object id;
	private Vertex out;
	private Vertex in;
	private String label;
	
	@Override
	protected void onInitialize(Object[] args) {
		out = getGraph().getVertex(args[0]);
		in = getGraph().getVertex(args[1]);
		id = args.length > 2 ? args[2] : null;
		label = args.length > 3 ? (String) args[3] : "";
	}
	
	@Override
	protected void onExecute() throws Exception {
		try {
			Edge edge = getGraph().addEdge(id, out, in, label);
			setResult(edge.toString());
			Cache.getInstance(getGraph()).addEdge(edge);
		} catch (Exception e) {
			setResult("DUPLICATE");
		}
	}

	@Override
	protected void onFinalize() throws Exception {
		if (CPL.isAttached()) {
			CPLObject obj = getCPLObject();
			getGraphDescriptor().getCPLObject().dataFlowFrom(obj);
		}
	}
}
