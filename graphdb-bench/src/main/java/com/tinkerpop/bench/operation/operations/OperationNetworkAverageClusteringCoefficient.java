package com.tinkerpop.bench.operation.operations;

import com.tinkerpop.bench.GraphUtils;
import com.tinkerpop.bench.operation.Operation;
import com.tinkerpop.blueprints.pgm.Graph;
import com.tinkerpop.blueprints.pgm.Vertex;

public class OperationNetworkAverageClusteringCoefficient extends Operation {
	
	@Override
	protected void onInitialize(Object[] args) {
	}
	
	@Override
	protected void onExecute() throws Exception {
		try {
			Graph graph = getGraph();
			
			double C = 0;
			int N = 0;
			
			for (Vertex v : graph.getVertices()) {
				C += GraphUtils.localClusteringCoefficient(v);
				N++;
			}
			
			if (N > 0) C /= N;
			setResult("" + C);
			
		} catch (Exception e) {
			throw e;
		}
	}
}
