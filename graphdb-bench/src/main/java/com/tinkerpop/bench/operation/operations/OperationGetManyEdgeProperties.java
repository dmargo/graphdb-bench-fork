package com.tinkerpop.bench.operation.operations;

import com.tinkerpop.bench.StatisticsHelper;
import com.tinkerpop.bench.evaluators.EdgeEvaluatorUniform;
import com.tinkerpop.bench.operation.Operation;
import com.tinkerpop.blueprints.pgm.Edge;

public class OperationGetManyEdgeProperties extends Operation {

	private String property_key;
	
	private int opCount;
	private Edge[] edgeSamples;
	
	@Override
	protected void onInitialize(Object[] args) {
		property_key = (String) args[0];
		
		opCount = args.length > 1 ? (Integer) args[1] : 1000;
		Object[] edgeIds = StatisticsHelper.getSampleEdgeIds(getGraph(), new EdgeEvaluatorUniform(), opCount);
		edgeSamples = new Edge[opCount];
		for (int i = 0; i < opCount; i++)
			edgeSamples[i] = getGraph().getEdge(edgeIds[i]);
	}
	
	@Override
	protected void onExecute() throws Exception {
		try {
			for (int i = 0; i < opCount; i++)
				edgeSamples[i].getProperty(property_key);
			
			setResult(opCount);
		} catch (Exception e) {
			throw e;
		}
	}

}
