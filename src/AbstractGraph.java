import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

import java.util.stream.Collectors;

/**
 * The {@code AbstractGraph} is a skeleton class for other graph classes that implements {@code Graph} interface.
 * It's a drag to implement all required methods in {@code Graph} interface, this class will
 * save lots of effort implementing them since this class provides bodies for some interface methods.
 * Inspired from Abstract classes in java.util package.
 * 
 * @param <T> vertex type
 * 
 * @author Jimmy Y.
 * @see Graph
 * @see AbstractWeightedGraph
 * @see Forest
 * @see DirectedGraph
 * @version 1.0 (3/16/2019)
 */
public abstract class AbstractGraph<T> implements Graph<T>, IterableGraph<T> {
	/**
	 * Adjacency list for the graph
	 */
	protected Map<T, Set<T>> adj;
	
	/**
	 * Set of graph's vertexes
	 */
	protected Set<T> vertexes;
	
	/**
	 * Collection of graph's edges
	 */
	protected List<Edge<T>> edges;
	
	/**
	 * This constructor initializes this class's
	 * fields, it can only be called by child classes.
	 */
	protected AbstractGraph() {
		adj = new HashMap<>();
		vertexes = new HashSet<>();
		edges = new ArrayList<>();
	}
	
	protected void check(Object... objs) {
		for (Object obj : objs)
			Objects.requireNonNull(obj);
	}
	
	/**
	 * Returns number of vertexes in a graph.
	 * 
	 * @return number of vertexes
	 */
	@Override
	public int vertexCount() {
		return vertexes.size();
	}
	
	/**
	 * Computes a shortest path between vertexes u and v.
	 * A shortest path between vertexes is a path that has smallest number of edges separating vertexes.
	 * 
	 * @param u
	 *        source vertex
	 * @param v
	 *        destination vertex
	 * 
	 * @return shortest path between vertexes u and v if exists else -1
	 */
	@Override
	public int shortestPath(T u, T v) {
		check(u, v);
		
		Set<T> visited = new HashSet<>();
		Map<T, Integer> dist = new HashMap<>();
		Queue<T> Q = new ArrayDeque<>();//it might not work with earlier versions of Java, use java.util.LinkedList if necessary
		
		visited.add(u);
		dist.put(u, 0);
		Q.add(u);
		
		while (!Q.isEmpty()) {
			T vertex = Q.poll();
			
			if (vertex.equals(v))
				return dist.get(vertex);
			
			if (adj.get(vertex) != null)
				for (T successor : adj.get(vertex))
					if (!visited.contains(successor)) {
						visited.add(successor);
						dist.put(successor, dist.get(vertex) + 1);//dist[v] = dist[u] + 1;
						Q.add(successor);
					}
		}
		
		return -1;
	}
	
	/**
	 * Returns a degree of vertex (number of successors he have).
	 * 
	 * @param vertex
	 *       a query vertex
	 * 
	 * @return {@code int} number of vertex's successors
	 * 
	 * @throw java.lang.IllegalArgumentException if the vertex DNE
	 */
	@Override
	public int degree(T vertex) {
		check(vertex);
		
		if (!contains(vertex))
			throw new IllegalArgumentException("The vertex does not exist!");
		
		return adj.get(vertex).size();
	}
	
	/**
	 * Tests if the vertex exists in the graph or not.
	 * 
	 * @param vertex
	 *        vertex to be tested
	 * 
	 * @return {@code true} if the vertex exists {@code false} otherwise
	 */
	@Override
	public boolean contains(T vertex) {
		check(vertex);
		
		return vertexes.contains(vertex);
	}
	
	/**
	 * Adds a vertex in the graph.
	 * 
	 * @param vertex
	 *        vertex to be added
	 */
	@Override
	public void addVertex(T vertex) {
		check(vertex);
		
		vertexes.add(vertex);
	}
	
	/**
	 * Removes a vertex in the graph.
	 * 
	 * @param vertex
	 *        vertex to be removed
	 * 
	 * @throw java.lang.IllegalArgumentException if the vertex does not exist
	 */
	@Override
	public void removeVertex(T vertex) {
		check(vertex);
		
		if (!contains(vertex))
			throw new IllegalArgumentException();
		
		//Case 1: remove from set of vertexes
		vertexes.remove(vertex);
		
		//Case 2: remove from collection of edges
		edges = edges.parallelStream().filter(e -> !e.getU().equals(vertex) && !e.getV().equals(vertex)).collect(Collectors.toList());
		
		//Case 3: remove from adjacency list, very complicated...
		//steps:
		//1.change the map to its entry set
		//2.filter out the set by removing entries with key equals vertex
		//3.for each entry, convert it to new map entry with its value modified so that each instance of vertex's removed in the set
		//4.convert it to entry set
		//5.convert it to map
		//TODO: test with corner cases
		adj = adj.entrySet().parallelStream().filter(e -> !e.getKey().equals(vertex)).map(e -> new AbstractMap.SimpleEntry<T, Set<T>>(e.getKey(), e.getValue().parallelStream().filter(v -> !v.equals(vertex)).collect(Collectors.toSet()))).collect(Collectors.toSet()).parallelStream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}
	
	/**
	 * Returns an adjacency list representation of the graph.
	 * 
	 * @return adjacency list
	 */
	@Override
	public Map<T, Set<T>> getAdjacencyList() {
		return adj;
	}
	
	/**
	 * Returns all vertexes of the graph.
	 * 
	 * @return graph's vertexes
	 */
	@Override
	public Set<T> getVertexes() {
		return vertexes;
	}
	
	/**
	 * Returns all edges of the graph.
	 * 
	 * @return graph's edges
	 */
	@Override
	public List<Edge<T>> getEdges() {
		return edges;
	}
	
	/**
	 * Returns an {@code Iterator} of the graph in breadth first fashion.
	 * Note: It does not explore all vertexes of the graph since it might be disconnected.
	 * 
	 * @param source
	 *        source vertex
	 * 
	 * @return {@code Iterator} object
	 */
	@Override
	public Iterator<T> breadthFirstIterator(T source) {
		check(source);
		
		return new GraphIterator<T>(this, source, false);
	}
	
	/**
	 * Returns an {@code Iterator} of the graph in depth first fashion.
	 * Note: It does not explore all vertexes of the graph since it might be disconnected.
	 * 
	 * @param source
	 *        source vertex
	 * 
	 * @return {@code Iterator} object
	 */
	@Override
	public Iterator<T> depthFirstIterator(T source) {
		check(source);
		
		return new GraphIterator<T>(this, source, true);
	}
	
	/**
	 * Returns unique hash value of the current object.
	 * 
	 * @return hash value
	 */
	@Override
	public int hashCode() {
		return adj.hashCode() ^ edges.hashCode() ^ vertexes.hashCode();
	}
	
	/**
	 * Test if the current object matches other.
	 * 
	 * @param obj
	 *        object to be tested with
	 * 
	 * @return {@code true} if match {@code false} otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (this == null || obj == null)
			return false;
		
		if (this.getClass() != obj.getClass())
			return false;
		
		AbstractGraph<?> graph = (AbstractGraph<?>) obj;
		
		return vertexes.equals(graph.getVertexes()) && /* It's not necessary to compare everything */
			   edges.equals(graph.getEdges());
	}
	
	/**
	 * Returns formatted {@code String} representation of {@code Graph} object.
	 * 
	 * @return {@code String} representation of the object
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(getClass().getName()).append("[");
		
		edges.forEach(e -> sb.append(e).append(", "));
		
		sb.setLength(sb.length() - 2);//remove last ", "
		sb.append("]");
		
		return sb.toString();
	}
}
