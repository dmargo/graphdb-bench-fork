package com.tinkerpop.bench.operation.operations;

import com.tinkerpop.bench.generator.GraphGenerator;
import com.tinkerpop.bench.operation.Operation;

/**
 * Operator for graph generation
 * 
 * @author Peter Macko <pmacko@eecs.harvard.edu>
 */
public class OperationGenerateGraph extends Operation {
	
	protected GraphGenerator generator;
	
	@Override
	protected void onInitialize(Object[] args) {
		if (args.length != 1) 
			throw new IllegalArgumentException("Invalid arguments - usage: { generator }");
		
		generator = (GraphGenerator) args[0];
	}

	@Override
	protected void onExecute() throws Exception {
		generator.generate(getGraph());
		setResult("COMPLETE");
	}
}
