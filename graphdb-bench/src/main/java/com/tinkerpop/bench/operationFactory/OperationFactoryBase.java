package com.tinkerpop.bench.operationFactory;

import com.tinkerpop.bench.operation.Operation;


/**
 * @author Alex Averbuch (alex.averbuch@gmail.com)
 * @author Peter Macko (pmacko@eecs.harvard.edu
 */
public abstract class OperationFactoryBase extends OperationFactory {

	/**
	 * Create arguments for an operation
	 * 
	 * @return operation arguments
	 * @throws Exception
	 */
	abstract protected OperationArgs onCreateOperation() throws Exception;

	
	/**
	 * Instantiate the next operation
	 * 
	 * @return the next operation
	 */
	@Override
	public final Operation next() {
		OperationArgs operationArgs = null;
		try {
			operationArgs = onCreateOperation();
		}
		catch (RuntimeException e) {
			throw e;
		}
		catch (Exception e) {
			throw new RuntimeException("Error in onCreateOperation", e);
		}

		try {
			return createOperation(operationArgs.getType().getName(),
					operationArgs.getArgs(), operationArgs.getName());
		}
		catch (RuntimeException e) {
			throw e;
		}
		catch (Exception e) {
			throw new RuntimeException("Error in loadOperation", e);
		}
	}

}
