package com.tinkerpop.bench.operation.operations;

import com.tinkerpop.bench.GraphUtils;
import com.tinkerpop.bench.StatisticsHelper;
import com.tinkerpop.bench.evaluators.EvaluatorUniform;
import com.tinkerpop.bench.operation.Operation;
import com.tinkerpop.blueprints.pgm.Graph;
import com.tinkerpop.blueprints.pgm.Vertex;

public class OperationLocalClusteringCoefficient extends Operation {

	private int opCount;
	private Object[] vertexSamples;
	
	@Override
	protected void onInitialize(Object[] args) {
		opCount = args.length > 0 ? (Integer) args[0] : 1000;
		vertexSamples = StatisticsHelper.getSampleVertexIds(getGraph(), new EvaluatorUniform(), opCount);
	}
	
	@Override
	protected void onExecute() throws Exception {
		try {
			Graph graph = getGraph();
			GraphUtils.OpStat stat = new GraphUtils.OpStat();
			
			for (int i = 0; i < opCount; i++) {
				Vertex v = graph.getVertex(vertexSamples[i]);
				GraphUtils.localClusteringCoefficient(v, stat);
			}
			
			setResult(opCount + ":" + stat);
		} catch (Exception e) {
			throw e;
		}
	}
}
