package com.tinkerpop.bench;

import java.lang.reflect.Constructor;

import com.tinkerpop.blueprints.pgm.Graph;
import com.tinkerpop.blueprints.pgm.impls.dex.DexGraph;
import com.tinkerpop.blueprints.pgm.impls.sql.SqlGraph;

import edu.harvard.pass.cpl.CPL;
import edu.harvard.pass.cpl.CPLObject;

import java.io.File;

public class GraphDescriptor {

	private Class<?> graphType = null;
	private String graphDir = null;
	private String graphPath = null;
	private Graph graph = null;
	private CPLObject cplObject = null;

	public GraphDescriptor(Class<?> graphType) {
		this(graphType, null, null);
	}

	public GraphDescriptor(Class<?> graphType, String graphDir, String graphPath) {
		this.graphType = graphType;
		this.graphDir = graphDir;
		this.graphPath = graphPath;
		
		if (CPL.isAttached()) {
			String name = graphType.getSimpleName();
			if (graphPath != null) name += " " + graphPath;
			cplObject = CPLObject.lookupOrCreate(Bench.ORIGINATOR, name, Bench.TYPE_DB);
			if (cplObject.getVersion() == 0) initializeCPLObject();
		}
	}

	//
	// Getter Methods
	// 

	public Class<?> getGraphType() {
		return graphType;
	}

	public Graph getGraph() {
		return graph;
	}

	public boolean getPersistent() {
		return graphPath != null;
	}
	
	public CPLObject getCPLObject() {
		return cplObject;
	}
	

	//
	// Functionality
	//

	public Graph openGraph() throws Exception {
		if (null != graph)
			return graph;
		
		Object[] args = (null == graphPath) ? new Object[] {}
				: new Object[] { graphPath };

		Constructor<?> graphConstructor = (null == graphPath) ? Class.forName(
				graphType.getName()).getConstructor() : Class.forName(
				graphType.getName()).getConstructor(String.class);

		try {
			graph = (Graph) graphConstructor.newInstance(args);
		} catch (Exception e) {
			throw e;
		}

		//if (TransactionalGraph.class.isAssignableFrom(graphType))
		//	((TransactionalGraph) graph).setTransactionMode(TransactionalGraph.Mode.AUTOMATIC);

		return graph;
	}

	public void shutdownGraph() {
		if (null != graph) {
			graph.shutdown();
			graph = null;
		}
	}

	public void deleteGraph() {
		//XXX dmargo: Again, total kludge but the API is just not uniform.
		if (graphType == SqlGraph.class) {
			((SqlGraph) graph).delete();
			graph = null;
		} else {
			shutdownGraph();
			
			if (true == getPersistent()) {
				deleteDir(graphDir);
			}
		}
		
		recreateCPLObject();
	}

	private void deleteDir(String pathStr) {
		LogUtils.deleteDir(pathStr);
	}

	public void recreateCPLObject() {
		if (CPL.isAttached()) {
			String name = graphType.toString();
			if (graphPath != null) name += " " + graphPath;
			cplObject = new CPLObject(Bench.ORIGINATOR, name, Bench.TYPE_DB);
			initializeCPLObject();
		}
	}
	
	private void initializeCPLObject() {
		cplObject.addProperty("CLASS", "" + graphType);
		if (graphDir != null) cplObject.addProperty("DIR", graphDir);
		if (graphPath != null) cplObject.addProperty("PATH", graphPath);
	}
}
