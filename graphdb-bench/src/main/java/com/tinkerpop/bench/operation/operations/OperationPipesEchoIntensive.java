package com.tinkerpop.bench.operation.operations;

import java.util.ArrayList;
import java.util.Arrays;

import com.tinkerpop.bench.operation.Operation;
import com.tinkerpop.blueprints.pgm.Element;
import com.tinkerpop.blueprints.pgm.Index;
import com.tinkerpop.blueprints.pgm.IndexableGraph;
import com.tinkerpop.blueprints.pgm.Vertex;
import com.tinkerpop.pipes.AbstractPipe;
import com.tinkerpop.pipes.Pipe;
import com.tinkerpop.pipes.Pipeline;
import com.tinkerpop.pipes.pgm.EdgeVertexPipe;
import com.tinkerpop.pipes.pgm.VertexEdgePipe;

/**
 * @author Alex Averbuch (alex.averbuch@gmail.com)
 */
public class OperationPipesEchoIntensive extends Operation {

	private String startVertexIndexKey = null;
	private String startVertexIndexValue = null;
	private int steps = 1;
	@SuppressWarnings("rawtypes")
	private Pipeline pipeline = null;
	private boolean isIndexableGraph = false;
	Iterable<Vertex> startVertices = null;

	// args
	// -> 0 startVertexIndexKey
	// -> 1 startVertexIndexValue
	// -> 2 steps
	@Override
	protected void onInitialize(Object[] args) {
		this.startVertexIndexKey = (String) args[0];
		this.startVertexIndexValue = (String) args[1];
		this.steps = Integer.parseInt((String) args[2]);
		this.pipeline = createPipeline(steps);

		if (getGraph() instanceof IndexableGraph) {
			this.isIndexableGraph = true;
			Index<Vertex> vIndex = ((IndexableGraph) getGraph()).getIndex(
					Index.VERTICES, Vertex.class);
			this.startVertices = vIndex.get(startVertexIndexKey,
					startVertexIndexValue);
		} else {
			this.isIndexableGraph = false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onExecute() throws Exception {
		try {
			int counter = 0;

			if (isIndexableGraph == false)
				throw new Exception("Graph is not IndexableGraph");

			for (Element startVertex : startVertices) {

				if ((startVertex == null)
						|| (startVertex instanceof Vertex == false))
					throw new Exception("No start Vertex found");

				pipeline.setStarts(Arrays.asList(startVertex).iterator());
				while (pipeline.hasNext()) {
					pipeline.next();
					counter++;
				}

			}

			setResult(Integer.toString(counter));
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Pipeline createPipeline(final Integer steps) {
		final ArrayList<Pipe> pipes = new ArrayList<Pipe>();
		for (int i = 0; i < steps; i++) {
			pipes.add(new VertexEdgePipe(VertexEdgePipe.Step.OUT_EDGES));
			pipes.add(new PropertyReadPipe());
			pipes.add(new EdgeVertexPipe(EdgeVertexPipe.Step.IN_VERTEX));
			pipes.add(new PropertyReadPipe());
		}
		return new Pipeline(pipes);
	}

	public class PropertyReadPipe extends AbstractPipe<Element, Element> {
		public Element processNextStart() {
			Element e = this.starts.next();
			for (String key : e.getPropertyKeys())
				e.getProperty(key);
			return e;
		}
	}

}
