package com.tinkerpop.bench.operation.operations;

import com.tinkerpop.bench.StatisticsHelper;
import com.tinkerpop.bench.evaluators.EvaluatorUniform;
import com.tinkerpop.bench.operation.Operation;
import com.tinkerpop.blueprints.pgm.Vertex;

public class OperationGetManyVertexProperties extends Operation {

	private String property_key;

	private int opCount;
	private Vertex[] vertexSamples;
	
	@Override
	protected void onInitialize(Object[] args) {
		property_key = (String) args[0];
		
		opCount = args.length > 1 ? (Integer) args[1] : 1000;
		Object[] vertexIds = StatisticsHelper.getSampleVertexIds(getGraph(), new EvaluatorUniform(), opCount);
		vertexSamples = new Vertex[opCount];
		for (int i = 0; i < opCount; i++)
			vertexSamples[i] = getGraph().getVertex(vertexIds[i]);
	}
	
	@Override
	protected void onExecute() throws Exception {
		try {
			for (int i = 0; i < opCount; i++)
				vertexSamples[i].getProperty(property_key);
			
			setResult(opCount);
		} catch (Exception e) {
			throw e;
		}
	}

}
