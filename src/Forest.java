import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import java.util.stream.Collectors;

/**
 * A {@code Forest} represents an undirected graph without cycles.
 * 
 * @param <T> type of vertex
 * 
 * @author Jimmy Y.
 * @see Graph
 * @see AbstractGraph
 * @see DirectedGraph
 * @version 1.0 (3/16/2019)
 */
public class Forest<T> extends AbstractGraph<T> implements Graph<T>, IterableGraph<T> {
	/**
	 * An enumeration of colours used for bipartite graph checking.
	 */
	private enum Colour{UNCOLOURED, WHITE, BLACK};
	
	/**
	 * Since the forest is an acyclic graph,
	 * This data structure will ensure the graph have no cycles.
	 * It's better than checking if the graph have cycles with DFS each time.
	 */
	private DisjointSetUnion<T> dsu;
	
	/**
	 * A single default constructor creates new Forest.
	 */
	public Forest() {
		super();
		
		dsu = new DisjointSetUnion<>();
	}
	
	/**
	 * Bipartite graph is a graph whose vertexes can be divided into two sets where vertexes
	 * in each set cannot have an edge each other, the bipartite graph is also known as "two colourable" graph.
	 * To check if the graph is bipartite, BFS is used.
	 * 
	 * @return {@code true} if the forest is bipartite {@code false} otherwise
	 */
	public boolean isBipartite() {
		Set<T> visited = new HashSet<>();
		Map<T, Colour> colour = new HashMap<>();
		
		Boolean bipartite = true;
		
		for (T vertex : vertexes)
			colour.put(vertex, Colour.UNCOLOURED);
		
		for (T vertex : vertexes)//because the graph might be disconnected, so BFS all
			if (!visited.contains(vertex))
				BFS(vertex, visited, colour, bipartite);
		
		return bipartite;
	}
	
	private void BFS(T src, Set<T> visited, Map<T, Colour> colour, Boolean bipartite) {
		Queue<T> Q = new ArrayDeque<>();//it might not work with earlier versions of Java, use java.util.LinkedList if necessary
		
		visited.add(src);
		colour.put(src, Colour.WHITE);
		Q.add(src);
		
		while (!Q.isEmpty()) {
			T u = Q.poll();
			
			if (adj.get(u) == null)
				continue;
			
			for (T v : adj.get(u)) {
				if (colour.get(u) == colour.get(v)) {//if an edge have vertexes share same colour
					bipartite = false;
					return;
				}
				
				colour.put(v, (colour.get(u) == Colour.WHITE) ? Colour.BLACK : Colour.WHITE);
				
				if (!visited.contains(v)) {
					visited.add(v);
					Q.add(v);
				}
			}
		}
	}
	
	/**
	 * Finds all connected components in a graph.
	 * 
	 * @return List of connected components of the graph
	 */
	public List<List<T>> getConnectedComponents() {
		List<List<T>> connectedComponents = new ArrayList<>();
		
		Set<T> visited = new HashSet<>();
		
		for (T vertex : vertexes)
			if (!visited.contains(vertex)) {
				visited.add(vertex);
				
				List<T> component = new ArrayList<>();
				
				DFS(vertex, visited, component);
				
				connectedComponents.add(component);
			}
		
		return connectedComponents;
	}
	
	private void DFS(T u, Set<T> visited, List<T> component) {
		visited.add(u);
		component.add(u);
		
		if (adj.get(u) == null)
			return;
		
		for (T v : adj.get(u))
			if (!visited.contains(v)) {
				visited.add(v);
				
				DFS(v, visited, component);
			}
	}
	
	/**
	 * Counts and returns number of undirected edges in the graph.
	 * 
	 * @return number of edges
	 */
	@Override
	public int edgeCount() {
		Set<Edge<T>> set = new HashSet<>();//make sure no edges are counted twice
		
		int total = 0;
		
		for (Edge<T> edge : edges) {
			T u = edge.getU();
			T v = edge.getV();
			
			Edge<T> reverse = new Edge<>(v, u);
			
			if (!set.contains(reverse)) {
				total++;
				set.add(edge);
			}
		}
		
		return total;
	}
	
	/**
	 * Returns {@code boolean} value that flags if the graph's edges directed or not.
	 * Since the forest is undirected, so it always return {@code true}.
	 * 
	 * @return {@code true}
	 */
	@Override
	public boolean isDirected() {
		return false;
	}
	
	/**
	 * Adds an undirected edge to the graph.
	 * 
	 * @param u "from" vertex
	 * @param v "to" vertex
	 * 
	 * @throw java.lang.IllegalArgumentException if the edge (u, v) created a cycle
	 */
	@Override
	public void addEdge(T u, T v) {
		check(u, v);
		
		Edge<T> edge = new Edge<>(u, v);
		
		if (!edges.contains(edge)) {
			if (!dsu.union(u, v))
				throw new IllegalArgumentException("A newly added edge created a cycle!");
			
			edges.add(edge);
			edges.add(new Edge<>(v, u));//don't forgot another edge since it's undirected graph!
		} else
			return;//the edge already exists, so do nothing
		
		vertexes.add(u);
		vertexes.add(v);//don't worry, set will not not contain duplicates
		
		if (!adj.containsKey(u))
			adj.put(u, new HashSet<>());
		
		if (!adj.containsKey(v))
			adj.put(v, new HashSet<>());
		
		adj.get(u).add(v);
		adj.get(v).add(u);
	}
	
	/**
	 * Removes an edge in the graph.
	 * 
	 * @param u
	 *        "from" vertex
	 * @param v
	 *        "to" vertex
	 */
	@Override
	public void removeEdge(T u, T v) {
		check(u, v);
		
		Edge<T> lookfor = new Edge<>(u, v);
		Edge<T> reverse = new Edge<>(v, u);
		
		edges = edges.parallelStream().filter(e -> !e.equals(lookfor) && !e.equals(reverse)).collect(Collectors.toList());
	}
	
	/**
	 * Returns formatted {@code String} representation of {@code Forest} object.
	 * 
	 * @return {@code String} representation of the object
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		Set<Edge<T>> set = new HashSet<>();//we don't want reverse edges in string representation
		
		sb.append("Forest[");
		
		edges.forEach(e -> {
			T u = e.getU();
			T v = e.getV();
			
			Edge<T> reverse = new Edge<>(v, u);
			
			if (!set.contains(reverse)) {
				sb.append(e).append(", ");
				set.add(e);
			}
		});
		
		sb.setLength(sb.length() - 2);//remove last ", "
		sb.append("]");
		
		return sb.toString();
	}
}
