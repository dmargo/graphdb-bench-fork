package com.tinkerpop.bench.generator;

import com.tinkerpop.blueprints.pgm.Graph;

/**
 * Abstract, stateless graph generator
 * 
 * @author Peter Macko <pmacko@eecs.harvard.edu>
 */
public abstract class GraphGenerator {
	
	/**
	 * Constructor for the objects of type Graph
	 * 
	 * @param graph the graph
	 */
	public GraphGenerator() {
	}
	
	
	/**
	 * Generate the graph
	 * 
	 * @param graph the graph
	 */
	public abstract void generate(Graph graph);
}
