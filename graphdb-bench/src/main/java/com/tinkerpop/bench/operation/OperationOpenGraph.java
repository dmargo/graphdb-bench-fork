package com.tinkerpop.bench.operation;

/**
 * @author Alex Averbuch (alex.averbuch@gmail.com)
 */
public class OperationOpenGraph extends Operation {

	@Override
	protected void onInitialize(Object[] args) {
	}

	@Override
	protected void onExecute() throws Exception {
		try {
			getGraphDescriptor().openGraph();
			setResult("DONE");
		} catch (Exception e) {
			throw e;
		}
	}

}
