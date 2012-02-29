package com.tinkerpop.bench.evaluators;

import com.tinkerpop.bench.cache.Cache;

/**
 * @author Alex Averbuch (alex.averbuch@gmail.com)
 */
public class EvaluatorProperty extends Evaluator {

	private String property = null;

	public EvaluatorProperty(String property) {
		this.property = property;
	}

	@Override
	public double evaluate(Cache cache, int index) {
		return (Double) cache.getVertex(index).getProperty(property);
	}
}