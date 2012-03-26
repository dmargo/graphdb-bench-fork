package com.tinkerpop.bench.operation.operations;

import java.util.ArrayList;
import java.util.HashSet;

import com.tinkerpop.bench.operation.Operation;
import com.tinkerpop.blueprints.pgm.Edge;
import com.tinkerpop.blueprints.pgm.Vertex;

public class OperationGetKHopNeighbors extends Operation {

	private Vertex startVertex;
	private int k;
	
	@Override
	protected void onInitialize(Object[] args) {
		startVertex = getGraph().getVertex(args[0]);
		k = args.length > 1 ? (Integer) args[1] : 2;
	}
	
	@Override
	protected void onExecute() throws Exception {
		try {
			int real_hops, get_ops = 0, get_vertex = 0;
			
			ArrayList<Vertex> curr = new ArrayList<Vertex>();
			ArrayList<Vertex> next = new ArrayList<Vertex>();
			final HashSet<Vertex> result = new HashSet<Vertex>();
			
			curr.add(startVertex);
			
			for(real_hops = 0; real_hops < k; real_hops++) {
				
				for (Vertex u : curr) {
					
					get_ops++;
					for (Edge e : u.getOutEdges()) {
						
						get_vertex++;
						Vertex v = e.getInVertex();
						
						if (result.add(v)) {
							next.add(v);
						}
					}
				}
				
				if(next.size() == 0)
					break;
				
				ArrayList<Vertex> tmp = curr;
				curr = next;
				tmp.clear();
				next = tmp;
			}
			
			setResult(result.size() + ":" + real_hops + ":" + get_ops + ":" + get_vertex);
		} catch (Exception e) {
			throw e;
		}
	}

}
