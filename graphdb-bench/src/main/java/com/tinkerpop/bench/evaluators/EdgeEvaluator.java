package com.tinkerpop.bench.evaluators;

import com.tinkerpop.blueprints.pgm.Edge;
import com.tinkerpop.blueprints.pgm.Graph;

/**
 * @author Alex Averbuch (alex.averbuch@gmail.com)
 * @author Martin Neumann (m.neumann.1980@gmail.com)
 */
public abstract class EdgeEvaluator {

	private double total = -1;

	public double evaluateTotal(Graph db) {
		if (total != -1)
			return total;

		total = 0d;
		for (Edge edge : db.getEdges())
			total += evaluate(edge);

		return total;
	}

	public abstract double evaluate(Edge edge);
}
