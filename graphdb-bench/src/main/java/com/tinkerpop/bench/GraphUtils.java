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
	 * @return the local clustering coefficient
	 */
	public static double localClusteringCoefficient(Vertex v) {
		
		HashSet<Vertex> neighbors = new HashSet<Vertex>();
		
		for (Edge e : v.getOutEdges()) {
			Vertex w = e.getInVertex();
			neighbors.add(w);
		}
		
		int triangles = 0;
		for (Vertex w : neighbors) {
			for (Edge e : w.getOutEdges()) {
				if (neighbors.contains(e.getInVertex())) {
					triangles++;
				}
			}
		}
		
		int K = neighbors.size();
		return K <= 1 ? 0 : (triangles / (K * (K-1)));
	}
}
