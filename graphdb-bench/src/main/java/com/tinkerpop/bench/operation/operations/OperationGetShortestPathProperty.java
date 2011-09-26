package com.tinkerpop.bench.operation.operations;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import com.tinkerpop.bench.operation.Operation;
import com.tinkerpop.blueprints.pgm.Edge;
import com.tinkerpop.blueprints.pgm.Vertex;

public class OperationGetShortestPathProperty extends Operation {

	private Vertex source;
	private Vertex target;
	
	@Override
	protected void onInitialize(Object[] args) {
		source = getGraph().getVertex(args[0]);
		target = getGraph().getVertex(args[1]);
	}
	
	@Override
	protected void onExecute() throws Exception {
		try {	
			final Comparator<Vertex> minDist = new Comparator<Vertex>()
			{
				public int compare(Vertex left, Vertex right) {
					Integer leftDist = (Integer) left.getProperty("dist");
					Integer rightDist = (Integer) right.getProperty("dist");
					if (leftDist == null) leftDist = Integer.MAX_VALUE;
					if (rightDist == null) rightDist = Integer.MAX_VALUE;
					return leftDist.compareTo(rightDist);
				}
			};
			
			//dmargo: 11 is the Java default initial capacity...don't ask me why.
			final PriorityQueue<Vertex> queue = new PriorityQueue<Vertex>(11, minDist);
			
			source.setProperty("dist", 0);
			queue.add(source);
			
			while (!queue.isEmpty()) {
				Vertex u = queue.remove();
				
				if (u.equals(target))
					break;
				
				for (Edge e : u.getOutEdges()) {
					Vertex v = e.getInVertex();
					
					Integer alt = (Integer) u.getProperty("dist") + 1;
					Integer cur = (Integer) v.getProperty("dist");
					if (cur == null) cur = Integer.MAX_VALUE;
				
					if (alt < cur) {
						v.setProperty("prev", u.getId());
						v.setProperty("dist", alt);
						queue.remove(v);
						queue.add(v);
					}
				}
			}
			
			ArrayList<Vertex> result = new ArrayList<Vertex>();
			
			Vertex u = target;
			Object prevId = u.getProperty("prev");
			while (prevId != null) {
				result.add(0, u);
				u = getGraph().getVertex(prevId);
				prevId = u.getProperty("prev");
			}
			
			for (Vertex v: getGraph().getVertices()) {
				v.removeProperty("dist");
				v.removeProperty("prev");
			}

			setResult(result.size());
		} catch (Exception e) {
			throw e;
		}
	}
}
