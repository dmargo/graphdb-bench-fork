package com.tinkerpop.bench.evaluators;

import com.tinkerpop.bench.cache.Cache;


/**
 * @author Alex Averbuch (alex.averbuch@gmail.com)
 * @author Martin Neumann (m.neumann.1980@gmail.com)
 */
public class EvaluatorOutDegree extends Evaluator {

	@Override
	public double evaluate(Cache cache, int index) {
		return cache.getOutDegree(index);
	}
}