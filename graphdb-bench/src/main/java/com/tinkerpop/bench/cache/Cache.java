package com.tinkerpop.bench.cache;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;

import com.tinkerpop.blueprints.pgm.Edge;
import com.tinkerpop.blueprints.pgm.Graph;
import com.tinkerpop.blueprints.pgm.Vertex;
import com.tinkerpop.blueprints.pgm.impls.hollow.HollowGraph;


/**
 * A node ID cache
 * 
 * @author Peter Macko
 */
public class Cache {
	
	// XXX Deletes are not yet handled

	private static Map<String, Cache> caches = new HashMap<String, Cache>();
	private static final int PER_ARRAY = 1000000;
	
	private Graph graph;
	private Random random;
	private boolean hollow;
	private boolean valid;
	
	private ArrayList<Object[]> nodeIDs;
	private ArrayList<int[]> inDegrees;
	private ArrayList<int[]> outDegrees;
	private Map<Object, Integer> nodeIDtoIndex;
	private int nextNodeIndex;
	
	
	/**
	 * Create an instance of class Cache
	 * 
	 * @param graph the graph
	 */
	protected Cache(Graph graph) {
		this.graph = graph;
		this.valid = false;
		this.nodeIDs = new ArrayList<Object[]>();
		this.nodeIDtoIndex = new HashMap<Object, Integer>();
		this.inDegrees = new ArrayList<int[]>();
		this.outDegrees = new ArrayList<int[]>();
		this.nextNodeIndex = 0;
		this.random = new Random();
		this.hollow = graph instanceof HollowGraph;
	}
	
	
	/**
	 * Get or create an instance of the graph
	 * 
	 * @param graph the graph
	 * @return the instance of NodeCache
	 */
	public static synchronized Cache getInstance(Graph graph) {
		String key = graph.toString();
		if (caches.containsKey(key)) {
			Cache c = caches.get(key);
			c.graph = graph;
			return c;
		}
		Cache c = new Cache(graph);
		Cache.caches.put(key, c);
		return c;
	}
	
	
	/**
	 * Invalidate the graph
	 */
	public void invalidate() {
		valid = false;
		nodeIDs.clear();
		inDegrees.clear();
		outDegrees.clear();
		nodeIDtoIndex.clear();
		nextNodeIndex = 0;
	}
	
	
	/**
	 * Drop all caches
	 */
	public static void dropAll() {
		caches.clear();
		System.gc();
	}
	
	
	/**
	 * Get the associated graph
	 * 
	 * @ return the graph
	 */
	public Graph getGraph() {
		return graph;
	}
	
	
	/**
	 * Add a node ID
	 * 
	 * @param id the node ID
	 */
	public synchronized void addVertexID(Object id) {
		if (!valid) return;
		if (id == null) throw new IllegalArgumentException("id cannot be null");
		int arrayID = nextNodeIndex / PER_ARRAY;
		int withinID = nextNodeIndex % PER_ARRAY;
		if (arrayID == nodeIDs.size()) {
			nodeIDs.add(new Object[PER_ARRAY]);
			inDegrees.add(new int[PER_ARRAY]);
			outDegrees.add(new int[PER_ARRAY]);
		}
		nodeIDs.get(arrayID)[withinID] = id;
		inDegrees.get(arrayID)[withinID] = 0;
		outDegrees.get(arrayID)[withinID] = 0;
		nodeIDtoIndex.put(id, nextNodeIndex);
		nextNodeIndex++;
	}
	
	
	/**
	 * Add a node
	 * 
	 * @param vertex the node
	 */
	public void addVertex(Vertex vertex) {
		addVertexID(vertex.getId());
	}
	
	
	/**
	 * Add an edge
	 * 
	 * @param edge the edge
	 */
	public void addEdge(Edge edge) {
		if (!valid) return;
		Vertex in = edge.getInVertex();
		Vertex out = edge.getOutVertex();
		int i = nodeIDtoIndex.get(in.getId());
		int o = nodeIDtoIndex.get(out.getId());
		inDegrees.get(i / PER_ARRAY)[i % PER_ARRAY]++;
		outDegrees.get(o / PER_ARRAY)[o % PER_ARRAY]++;
	}
	
	
	/**
	 * Rebuild the cache
	 */
	public synchronized void rebuild() {
		
		System.err.println("\n(Rebuilding cache for " + graph + ")");
		
		nodeIDs.clear();
		inDegrees.clear();
		outDegrees.clear();
		nodeIDtoIndex.clear();
		nextNodeIndex = 0;
		
		System.gc();
		System.runFinalization();
		System.gc();
		
		valid = true;
		for (Vertex vertex : graph.getVertices()) addVertex(vertex);
		for (Edge edge : graph.getEdges()) addEdge(edge);
	}
	
	
	/**
	 * Get the index range
	 * 
	 * @return 1 + the last value of the index
	 */
	public synchronized int getVertexIndexRange() {
		if (!valid) rebuild();
		return nextNodeIndex;
	}
	
	
	/**
	 * Get a vertex given the ID
	 * 
	 * @param index the index
	 * @return the vertex, or null if the object was deleted
	 */
	public synchronized Vertex getVertex(int index) {
		if (!valid) rebuild();
		Object id = getVertexID(index);
		if (id == null) return null;
		return graph.getVertex(id);
	}
	
	
	/**
	 * Get an ID of the object with the given index
	 * 
	 * @param index the index
	 * @return the ID, or null if the object was deleted
	 */
	public synchronized Object getVertexID(int index) {
		
		if (hollow) return new Long(0);
		if (!valid) rebuild();
		if (index >= nextNodeIndex) throw new NoSuchElementException();
		
		return nodeIDs.get(index / PER_ARRAY)[index % PER_ARRAY];
	}
	
	
	/**
	 * Get an indegree of a vertex with the given index
	 * 
	 * @param index the vertex index
	 * @return the indegree, or 0 if the object was deleted
	 */
	public synchronized int getInDegree(int index) {
		
		if (hollow) return 5;
		if (!valid) rebuild();
		if (index >= nextNodeIndex) throw new NoSuchElementException();
		
		return inDegrees.get(index / PER_ARRAY)[index % PER_ARRAY];
	}
	
	
	/**
	 * Get an outdegree of a vertex with the given index
	 * 
	 * @param index the vertex index
	 * @return the outdegree, or 0 if the object was deleted
	 */
	public synchronized int getOutDegree(int index) {
		
		if (hollow) return 5;
		if (!valid) rebuild();
		if (index >= nextNodeIndex) throw new NoSuchElementException();
		
		return outDegrees.get(index / PER_ARRAY)[index % PER_ARRAY];
	}
	
	
	/**
	 * Get a random node ID using uniform distribution
	 * 
	 * @return random node ID
	 */
	public synchronized Object getRandomVertexID() {
		
		if (hollow) return new Long(0);
		if (!valid) rebuild();
		if (nextNodeIndex == 0) throw new NoSuchElementException();
		
		int retries = 10;
		while (retries --> 0) {
			int i = random.nextInt(nextNodeIndex);
			Object id = nodeIDs.get(i / PER_ARRAY)[i % PER_ARRAY];
			if (id != null) return id;
		}
		
		
		// Will become useful when we start to handle deletes
		
		rebuild();
		int i = random.nextInt(nextNodeIndex);
		return nodeIDs.get(i / PER_ARRAY)[i % PER_ARRAY];
	}
}
