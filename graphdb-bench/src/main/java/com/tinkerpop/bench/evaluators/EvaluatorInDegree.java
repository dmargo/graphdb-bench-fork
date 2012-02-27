package com.tinkerpop.bench.evaluators;

import com.tinkerpop.bench.cache.Cache;
import com.tinkerpop.blueprints.pgm.Edge;
import com.tinkerpop.blueprints.pgm.Vertex;

/**
 * @author Alex Averbuch (alex.averbuch@gmail.com)
 * @author Martin Neumann (m.neumann.1980@gmail.com)
 */
public class EvaluatorInDegree extends Evaluator {

	@Override
	@SuppressWarnings("unused")
	public double evaluate(Cache cache, int index) {
		return cache.getInDegree(index);
	}
}