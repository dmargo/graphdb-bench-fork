package com.tinkerpop.bench.operationFactory.factories;

import java.util.ArrayList;
import java.util.Arrays;

import com.tinkerpop.bench.StatisticsHelper;
import com.tinkerpop.bench.evaluators.EvaluatorUniform;
import com.tinkerpop.bench.operationFactory.OperationArgs;
import com.tinkerpop.bench.operationFactory.OperationFactoryBase;

public class OperationFactoryRandomVertex extends OperationFactoryBase implements WithOpCount {
	
	private Class<?> opType = null;
	private int opCount;
	private Object[] args;
	private ArrayList<Object> vertexSamples = null;
	
	public OperationFactoryRandomVertex(Class<?> opType, int opCount) {
		this(opType, opCount, new Object[] {});
	}
	
	public OperationFactoryRandomVertex(Class<?> opType, int opCount, Object[] args) {
		super();
		this.opType = opType;
		this.opCount = opCount;
		this.args = args;
	}

	@Override
	protected void onInitialize() {
		vertexSamples = new ArrayList<Object>(Arrays.asList(StatisticsHelper
				.getSampleVertexIds(getGraph(), new EvaluatorUniform(), opCount)));		
	}

	@Override
	public boolean hasNext() {
		return vertexSamples.isEmpty() == false;
	}
	
	@Override
	protected OperationArgs onCreateOperation() throws Exception {
		Object[] myArgs = new Object[1 + args.length];
		myArgs[0] = vertexSamples.remove(0);
		System.arraycopy(args, 0, myArgs, 1, args.length);
		return new OperationArgs(myArgs, opType);
	}
	
	/**
	 * Return the total number of operations
	 * 
	 * @return the total number of operations
	 */
	@Override
	public int getOpCount() {
		return opCount;
	}
	
	/**
	 * Return the number of already executed operations
	 * 
	 * @return the number of already executed operations
	 */
	@Override
	public int getExecutedOpCount() {
		return opCount - vertexSamples.size();
	}
}
