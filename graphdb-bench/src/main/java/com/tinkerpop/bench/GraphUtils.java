package com.tinkerpop.bench;

import java.util.HashSet;

import com.tinkerpop.blueprints.pgm.Edge;
import com.tinkerpop.blueprints.pgm.Vertex;

public class GraphUtils {
	
	
	/**
	 * Calculate local clustering coefficient.
	 * 
	 *     C_i = |{e_jk}| / |N_i|(|N_i| - 1)
	 * 
	 * Reference: http://sonivis.org/wiki/index.php/Global_Clustering_Coefficient
	 *
	 * @param v the vector
	 * @param stat the statistics object
	 * @return the local clustering coefficient
	 */
	public static double localClusteringCoefficient(Vertex v, OpStat stat) {
		
		HashSet<Vertex> neighbors = new HashSet<Vertex>();
		
		stat.num_getOutEdges++;
		for (Edge e : v.getOutEdges()) {
			stat.num_getInVertex++;
			stat.num_uniqueVertices++;
			Vertex w = e.getInVertex();
			neighbors.add(w);
		}
		
		int triangles = 0;
		for (Vertex w : neighbors) {
			stat.num_getOutEdges++;
			for (Edge e : w.getOutEdges()) {
				stat.num_getInVertex++;
				if (neighbors.contains(e.getInVertex())) {
					triangles++;
				}
				else {
					stat.num_uniqueVertices++;
				}
			}
		}
		
		int K = neighbors.size();
		return K <= 1 ? 0 : (triangles / (double) (K * (K-1)));
	}
	
	
	/**
	 * A detailed operation breakdown in terms of the number of operations
	 */
	public static class OpStat {
		
		public int num_getInVertex = 0;
		public int num_getOutVertex = 0;
		public int num_getInEdges = 0;
		public int num_getOutEdges = 0;
		public int num_getVertices = 0;
		public int num_getVerticesNext = 0;			/* getting the next vertex from the iterator */
		public int num_uniqueVertices = 0;
		
		public boolean has_uniqueVertices = true;	/* set to false if not, never set this back to true */
		
		/**
		 * Convert to string
		 * 
		 * @return the string
		 */
		@Override
		public String toString() {
			return   "getInVertex=" + num_getInVertex + ":getOutVertex="     + num_getOutVertex +
					":getInEdges="  + num_getInEdges  + ":getOutEdges="      + num_getOutEdges  +
					":getVertices=" + num_getVertices + ":getVerticesNext="  + num_getVerticesNext +
					 (has_uniqueVertices ? ":uniqueVertices=" + num_uniqueVertices : "");
		}
	}
}
