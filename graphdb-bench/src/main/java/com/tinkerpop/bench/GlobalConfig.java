package com.tinkerpop.bench;

/**
 * @author Peter Macko <pmacko@eecs.harvard.edu>
 */
public class GlobalConfig {

	/// The size of the transaction buffer
	public static int transactionBufferSize = 100 * 1000;
	
	/// Whether to open one database connection per thread
	public static boolean oneDbConnectionPerThread = true;
}
