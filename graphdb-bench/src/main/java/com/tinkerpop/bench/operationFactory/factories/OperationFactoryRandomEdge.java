package com.tinkerpop.bench.operationFactory.factories;

import java.util.ArrayList;
import java.util.Arrays;

import com.tinkerpop.bench.StatisticsHelper;
import com.tinkerpop.bench.evaluators.EdgeEvaluatorUniform;
import com.tinkerpop.bench.operationFactory.OperationArgs;
import com.tinkerpop.bench.operationFactory.OperationFactoryBase;

public class OperationFactoryRandomEdge extends OperationFactoryBase {
	
	private Class<?> opType = null;
	private int opCount;
	private Object[] args;
	private ArrayList<Object> edgeSamples = null;
	
	public OperationFactoryRandomEdge(Class<?> opType, int opCount) {
		this(opType, opCount, new Object[] {});
	}
	
	public OperationFactoryRandomEdge(Class<?> opType, int opCount, Object[] args) {
		super();
		this.opType = opType;
		this.opCount = opCount;
		this.args = args;
	}

	@Override
	protected void onInitialize() {
		edgeSamples = new ArrayList<Object>(Arrays.asList(StatisticsHelper
				.getSampleEdgeIds(getGraph(), new EdgeEvaluatorUniform(), opCount)));		
	}

	@Override
	public boolean hasNext() {
		return edgeSamples.isEmpty() == false;
	}
	
	@Override
	protected OperationArgs onCreateOperation() throws Exception {
		Object[] myArgs = new Object[1 + args.length];
		myArgs[0] = edgeSamples.remove(0);
		System.arraycopy(args, 0, myArgs, 1, args.length);
		return new OperationArgs(myArgs, opType);
	}

}
