package com.tinkerpop.bench.operationFactory;

import com.tinkerpop.bench.operationFactory.factories.WithOpCount;

/**
 * @author Alex Averbuch (alex.averbuch@gmail.com)
 */
public class OperationFactoryGeneric extends OperationFactoryBase implements WithOpCount {

	private Class<?> operationType = null;
	private int opCount = -1;
	private int remainingCount = -1;
	private Object[] args = null;
	private String tag = null;

	public OperationFactoryGeneric(Class<?> operationType) {
		this(operationType, -1, new Object[] {});
	}

	public OperationFactoryGeneric(Class<?> operationType, int opCount) {
		this(operationType, opCount, new Object[] {});
	}

	public OperationFactoryGeneric(Class<?> operationType, int opCount,
			Object[] args) {
		this(operationType, opCount, args, "");
	}

	public OperationFactoryGeneric(Class<?> operationType, int opCount,
			Object[] args, String tag) {
		this.operationType = operationType;
		this.opCount = opCount;
		this.remainingCount = opCount;
		this.args = args;
		this.tag = tag;
	}

	@Override
	public void onInitialize() {
	}

	@Override
	public boolean hasNext() {
		return (-1 == remainingCount) ? true : (remainingCount > 0);
	}

	@Override
	protected OperationArgs onCreateOperation() throws Exception {
		if (-1 != remainingCount)
			remainingCount--;
		return new OperationArgs(args, operationType, tag);
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
		return opCount - remainingCount;
	}
}
