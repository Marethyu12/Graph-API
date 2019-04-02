import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import java.util.stream.Collectors;

/**
 * The {@code DirectedGraph} class wraps commom operations on directed graphs.
 * It's a graph that have special kind of edges (directed edges).
 * 
 * @param <T> type of vertex
 * 
 * @author Jimmy Y.
 * @see Graph
 * @see AbstractGraph
 * @see Forest
 * @version 1.0 (3/16/2019)
 */
public class DirectedGraph<T> extends AbstractGraph<T> implements Graph<T>, IterableGraph<T> {
	/**
	 * An enumeration of colours used for cycle detecting in directed graph.
	 * 
	 * {@code Colour.WHITE} means vertex's not visited yet
	 * {@code Colour.GRAY} means vertex's visited but his successors are not visited yet
	 * {@code Colour.BLACK} means vertex's visited including his successors
	 */
	private enum Colour{WHITE, GRAY, BLACK};
	
	/**
	 * Creates new and empty {@code DirectedGraph} object.
	 */
	public DirectedGraph() {
		super();
	}
	
	/**
	 * Returns {@code boolean} value to indicate if the directed graph have a cycle.
	 * The directed graph have a cycle if it have back edges.
	 * 
	 * @return {@code true} if the graph have a cycle else {@code} false
	 */
	public boolean isCyclic() {
		Map<T, Colour> colour = new HashMap<>();
		
		for (T vertex : vertexes)
			colour.put(vertex, Colour.WHITE);
		
		boolean cycle = false;
		
		for (T vertex : vertexes)
			if (colour.get(vertex) == Colour.WHITE && DFS1(vertex, colour)) {
				cycle = true;
				break;
			}
		
		return cycle;
	}
	
	private boolean DFS1(T u, Map<T, Colour> colour) {
		colour.put(u, Colour.GRAY);
		
		if (adj.get(u) != null)
			for (T v : adj.get(u))
				if (colour.get(v) == Colour.WHITE) {
					if (DFS1(v, colour))
						return true;//cycle found!
				} else if (colour.get(v) == Colour.GRAY)//if already visited vertex's colour is gray which means a back edge (u to ancestor v) is found
					return true;
		
		colour.put(u, Colour.BLACK);
		
		return false;
	}
	
	/**
	 * Returns topological ordering of directed acyclic graph (DAG) edges.
	 * Topological sorting means to organize the directed edges in such way like "from" vertexes
	 * comes first before "to" vertexes. There's no topological ordering for directed cyclic graphs.
	 * 
	 * @return topological ordering of DAG edges, {@code null} if the directed graph have a cycle
	 */
	public List<T> topologicalSort() {
		if (isCyclic())
			return null;
		
		Set<T> visited = new HashSet<>();
		List<T> order = new ArrayList<>();
		
		for (T vertex : vertexes)
			if (!visited.contains(vertex))
				DFS2(vertex, visited, order);
		
		Collections.reverse(order);
		
		return order;
	}
	
	private void DFS2(T u, Set<T> visited, List<T> order) {
		visited.add(u);
		
		if (adj.get(u) != null)
			for (T v : adj.get(u))
				if (!visited.contains(v))
					DFS2(v, visited, order);
		
		order.add(u);
	}
	
	/**
	 * Computes all the graph's strongly connected components (SCC).
	 * The SCC is a component of the directed graph where all of vertxes of the component are reachable to each other.
	 * 
	 * @return list of graph's SCCs
	 */
	public List<List<T>> getStronglyConnectedComponents() {
		return new Kosaraju(this).sccs();
	}
	
	/**
	 * Returns transpose of the graph (all of its edges are reversed).
	 * 
	 * @return transposed {@code DirectedGraph} object
	 */
	public DirectedGraph<T> transpose() {
		DirectedGraph<T> digraph = new DirectedGraph<>();
		
		for (T u : vertexes) {
			if (adj.get(u) == null)
				continue;
			
			for (T v : adj.get(u))
				digraph.addEdge(v, u);//addEdge(gT, v, u);
		}
		
		return digraph;
	}
	
	/**
	 * Counts and returns number of directed edges in the graph.
	 * 
	 * @return number of edges
	 */
	@Override
	public int edgeCount() {
		return edges.size();
	}
	
	/**
	 * This method returns {@code true} if the graph is directed or not.
	 * It's obvious the {@code DirectedGraph} is directed so {@code true} always returned!
	 * 
	 * @return {@code true}
	 */
	@Override
	public boolean isDirected() {
		return true;
	}
	
	/**
	 * Adds a directed edge to the graph.
	 * 
	 * @param u "from" vertex
	 * @param v "to" vertex
	 */
	@Override
	public void addEdge(T u, T v) {
		check(u, v);
		
		Edge<T> edge = new Edge<>(u, v);
		
		if (!edges.contains(edge))
			edges.add(edge);
		else
			return;//the edge already exists, so do nothing
		
		vertexes.add(u);
		vertexes.add(v);
		
		if (!adj.containsKey(u))
			adj.put(u, new HashSet<>());
		
		adj.get(u).add(v);
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
		
		edges = edges.parallelStream().filter(e -> !e.equals(lookfor)).collect(Collectors.toList());
	}
	
	/*
	 * A nested class to hold methods of Kosaraju's
	 * SCC algorithm, it's a bit complex.
	 */
	private class Kosaraju {
		DirectedGraph<T> digraph;
		
		Set<T> vertexes;
		Map<T, Set<T>> adj;
		
		Kosaraju(DirectedGraph<T> digraph) {
			this.digraph = digraph;
			vertexes = digraph.getVertexes();
			adj = digraph.getAdjacencyList();
		}
		
		List<List<T>> sccs() {
			Set<T> visited = new HashSet<>();
			Stack<T> stack = new Stack<>();
			
			for (T vertex : vertexes)
				if (!visited.contains(vertex))
					DFS1(vertex, visited, stack);
			
			visited.clear();
			adj = digraph.transpose().getAdjacencyList();
			
			List<List<T>> sccs = new ArrayList<>();
			
			while (!stack.isEmpty()) {
				T vertex = stack.pop();
				
				if (!visited.contains(vertex)) {
					List<T> scc = new ArrayList<>();
					
					DFS2(vertex, visited, scc);
					
					sccs.add(scc);
				}
			}
			
			return sccs;
		}
		
		void DFS1(T u, Set<T> visited, Stack<T> stack) {
			visited.add(u);
			
			if (adj.get(u) != null)
				for (T v : adj.get(u))
					if (!visited.contains(v))
						DFS1(v, visited, stack);
			
			stack.push(u);
		}
		
		void DFS2(T u, Set<T> visited, List<T> scc) {
			visited.add(u);
			scc.add(u);
			
			if (adj.get(u) != null)
				for (T v : adj.get(u))
					if (!visited.contains(v))
						DFS2(v, visited, scc);
		}
	}
}
