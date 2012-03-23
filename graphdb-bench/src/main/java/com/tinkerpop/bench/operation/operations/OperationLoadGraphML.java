package com.tinkerpop.bench.operation.operations;

import java.io.File;
import java.io.FileInputStream;

import com.tinkerpop.bench.cache.Cache;
import com.tinkerpop.bench.operation.Operation;
import com.tinkerpop.blueprints.pgm.util.graphml.GraphMLReader;

import edu.harvard.pass.cpl.CPL;
import edu.harvard.pass.cpl.CPLFile;

/**
 * @author Alex Averbuch (alex.averbuch@gmail.com)
 */
public class OperationLoadGraphML extends Operation {

	private String graphmlPath = null;
	private final int TRANSACTION_BUFFER = 1000;

	// args
	// -> 0 graphmlDir
	@Override
	protected void onInitialize(Object[] args) {
		this.graphmlPath = (String) args[0];
	}

	@Override
	protected void onExecute() throws Exception {
		try {
			GraphMLReader.inputGraph(getGraph(), new FileInputStream(
					graphmlPath), TRANSACTION_BUFFER, null, null, null);
			Cache.getInstance(getGraph()).invalidate();
			setResult("DONE");
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	protected void onFinalize() throws Exception {
		if (CPL.isAttached()) {
			getCPLObject().dataFlowFrom(CPLFile.lookupOrCreate(new File(graphmlPath)));
			getGraphDescriptor().getCPLObject().dataFlowFrom(getCPLObject());
		}
	}
}
