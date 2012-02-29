package com.tinkerpop.bench.generator;

import com.tinkerpop.bench.ConsoleUtils;
import com.tinkerpop.bench.StatisticsHelper;
import com.tinkerpop.bench.cache.Cache;
import com.tinkerpop.bench.evaluators.Evaluator;
import com.tinkerpop.bench.evaluators.EvaluatorDegree;
import com.tinkerpop.blueprints.pgm.Edge;
import com.tinkerpop.blueprints.pgm.Graph;
import com.tinkerpop.blueprints.pgm.Vertex;


/**
 * A simple Barabasi graph generator for the common case with power=1
 *
 * @author Peter Macko <pmacko@eecs.harvard.edu>
 */
public class SimpleBarabasiGenerator extends GraphGenerator {

	protected int N;
	protected int M;


	/**
	 * Create an instance of the SimpleBarabasiGenerator
	 *
	 * @param N the number of vertices to generate
	 * @param M the degree of the generated vertices
	 */
	public SimpleBarabasiGenerator(int N, int M) {
		this.N = N;
		this.M = M;
	}


	/**
	 * Generate the graph
	 *
	 * @param graph the graph
	 * @see com.tinkerpop.bench.generator.GraphGenerator#generate()
	 */
	@Override
	public void generate(Graph graph) {
		
		int n = N;
		int m = M;
		boolean optimized = false;
		
		
		// Create a vertex if the graph is empty
		
		int size = 0;
		int edges = 0;
		boolean empty = true;
		
		for (Vertex v : graph.getVertices()) {
			empty = false;
			
			if (!optimized) break;
			
			size++;
			for (@SuppressWarnings("unused") Edge e : v.getOutEdges()) {
				edges++;
			}
			for (@SuppressWarnings("unused") Edge e : v.getInEdges()) {
				edges++;
			}
		}
		
		Cache c = Cache.getInstance(graph);
		
		if (empty) {
			Vertex v = graph.addVertex(optimized ? size++ : null);
			c.addVertex(v);
			n--;
		}

		
		// Create more vertices
		
		Object[] otherVertices = new Object[m];
		for (int i = 0; i < n; i++) {
			
			// Get an array of vertices to connect to
			
			if (optimized) {
				for (int j = 0; j < m; j++) {
					// XXX Need the ability to select a vertex with no edges
					int id = (int) (Math.random() * edges);
					otherVertices[j] = graph.getEdge(id).getInVertex();
				}
			}
			else {
				Evaluator evaluator = new EvaluatorDegree(1, 8);
				otherVertices = StatisticsHelper
						.getSampleVertexIds(graph, evaluator, m);
				for (int j = 0; j < otherVertices.length; j++) {
					otherVertices[j] = graph.getVertex(otherVertices[j]);
				}
			}
			
			
			// Create the new vertex and the appropriate edges
			
			Vertex v = graph.addVertex(optimized ? size++ : null);
			c.addVertex(v);
			
			for (Object o : otherVertices) {
				Edge e = graph.addEdge(optimized ? edges++ : null, v, (Vertex) o, "");
				c.addEdge(e);
			}
			
			
			if ((i & 7) == 0 || i == n-1) ConsoleUtils.printProgressIndicator(i+1, n);
		}
	}
}
