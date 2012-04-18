package com.tinkerpop.bench.operation.operations;

import com.tinkerpop.bench.StatisticsHelper;
import com.tinkerpop.bench.cache.Cache;
import com.tinkerpop.bench.evaluators.EvaluatorUniform;
import com.tinkerpop.bench.operation.Operation;
import com.tinkerpop.blueprints.pgm.Edge;
import com.tinkerpop.blueprints.pgm.Graph;
import com.tinkerpop.blueprints.pgm.Vertex;

import edu.harvard.pass.cpl.CPL;
import edu.harvard.pass.cpl.CPLObject;

public class OperationAddManyEdges extends Operation {

	private int opCount;
	private Vertex[] vertexSamples;
	
	private Object id;
	private String label;
	
	@Override
	protected void onInitialize(Object[] args) {
		opCount = args.length > 0 ? (Integer) args[0] : 1000;
		Object[] vertexIds = StatisticsHelper.getSampleVertexIds(getGraph(), new EvaluatorUniform(), 2 * opCount);
		vertexSamples = new Vertex[2 * opCount];
		for (int i = 0; i < 2 * opCount; i++)
			vertexSamples[i] = getGraph().getVertex(vertexIds[i]);
		
		id = null; //args.length > 1 ? args[1] : null;
		label = ""; //args.length > 2 ? (String) args[2] : "";
	}
	
	@Override
	protected void onExecute() throws Exception {
		try {
			Graph graph = getGraph();
			
			for (int i = 0; i < 2 * opCount;) {
				Edge edge = graph.addEdge(id, vertexSamples[i++], vertexSamples[i++], label);
				Cache.getInstance(getGraph()).addEdge(edge);
			}
			
			setResult(opCount);
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

	@Override
	public boolean isUpdate() {
		return true;
	}
}
