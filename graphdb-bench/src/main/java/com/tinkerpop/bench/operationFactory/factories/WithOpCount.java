package com.tinkerpop.bench.operationFactory.factories;


/**
 * @author Peter Macko (pmacko@eecs.harvard.edu)
 */
public interface WithOpCount {

	/**
	 * Return the total number of operations
	 * 
	 * @return the total number of operations
	 */
	public int getOpCount();
	
	/**
	 * Return the number of already executed operations
	 * 
	 * @return the number of already executed operations
	 */
	public int getExecutedOpCount();
}
