package com.tinkerpop.bench.operationFactory.factories;

import java.util.ArrayList;
import java.util.Arrays;

import com.tinkerpop.bench.StatisticsHelper;
import com.tinkerpop.bench.evaluators.EvaluatorUniform;
import com.tinkerpop.bench.operationFactory.OperationArgs;
import com.tinkerpop.bench.operationFactory.OperationFactoryBase;
import com.tinkerpop.blueprints.pgm.Vertex;

public class OperationFactoryRandomVertexPair extends OperationFactoryBase {
	
	private Class<?> opType = null;
	private int opCount;
	private ArrayList<Object> vertexSamples = null;
	
	public OperationFactoryRandomVertexPair(Class<?> opType, int opCount) {
		super();
		this.opType = opType;
		this.opCount = opCount;
	}

	@Override
	protected void onInitialize() {
		vertexSamples = new ArrayList<Object>(Arrays.asList(StatisticsHelper
				.getSampleVertexIds(getGraph(), new EvaluatorUniform(), 2 * opCount)));		
	}

	@Override
	public boolean hasNext() {
		return vertexSamples.isEmpty() == false;
	}
	
	@Override
	protected OperationArgs onCreateOperation() throws Exception {
		Vertex u = getGraph().getVertex(vertexSamples.remove(0));
		Vertex v = getGraph().getVertex(vertexSamples.remove(0));
		Object[] args = {u, v};
		return new OperationArgs(args, opType);
	}

}
