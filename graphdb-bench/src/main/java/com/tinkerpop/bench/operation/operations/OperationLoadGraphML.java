package com.tinkerpop.bench.operation.operations;

import java.io.File;
import java.io.FileInputStream;

import com.tinkerpop.bench.GlobalConfig;
import com.tinkerpop.bench.cache.Cache;
import com.tinkerpop.bench.operation.Operation;
import com.tinkerpop.blueprints.pgm.Graph;
import com.tinkerpop.blueprints.pgm.util.graphml.GraphMLReader;

import edu.harvard.pass.cpl.CPL;
import edu.harvard.pass.cpl.CPLFile;

/**
 * @author Alex Averbuch (alex.averbuch@gmail.com)
 */
public class OperationLoadGraphML extends Operation {

	private String graphmlPath = null;

	// args
	// -> 0 graphmlDir
	@Override
	protected void onInitialize(Object[] args) {
		this.graphmlPath = (String) args[0];
	}

	@Override
	protected void onExecute() throws Exception {
		Graph graph = getGraph();
		try {
			GraphMLReader.inputGraph(graph, new FileInputStream(
					graphmlPath), GlobalConfig.transactionBufferSize, null, null, null);
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

	@Override
	public boolean isUpdate() {
		return true;
	}
	
	@Override
	public boolean isUsingCustomTransactions() {
		return true;
	}
}
