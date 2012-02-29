package com.tinkerpop.bench.evaluators;

import com.tinkerpop.bench.cache.Cache;
import com.tinkerpop.blueprints.pgm.Edge;
import com.tinkerpop.blueprints.pgm.Vertex;

/**
 * @author Alex Averbuch (alex.averbuch@gmail.com)
 * @author Martin Neumann (m.neumann.1980@gmail.com)
 */
public class EvaluatorDegree extends Evaluator {
	
	private double power;
	private double zeroAppeal;

	public EvaluatorDegree() {
		this(1.0, 0.0);
	}
	
	public EvaluatorDegree(double power, double zeroAppeal) {
		this.power = power;
		this.zeroAppeal = zeroAppeal;
	}

	@Override
	@SuppressWarnings("unused")
	public double evaluate(Cache cache, int index) {
		double degree = cache.getInDegree(index) + cache.getOutDegree(index);
		return Math.pow(degree, power) + zeroAppeal;
	}
}