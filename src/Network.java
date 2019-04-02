import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import java.util.stream.Collectors;

/**
 * The {@code Network} is a general purpose weighted graph.
 * 
 * @param <T> vertex type
 * 
 * @author Jimmy Y.
 * @see WeightedGraph
 * @see AbstractWeightedGraph
 * @version 1.0 (3/16/2019)
 */
public class Network<T> extends AbstractWeightedGraph<T> implements WeightedGraph<T> {
	/**
	 * {@code boolean} flag that indicates type of edge
	 */
	private boolean isDirected;
	
	/**
	 * Creates a new {@code Network} object with undirected edges.
	 */
	public Network() {
		this(false);
	}
	
	/**
	 * Creates a new {@code Network} object with edges whose type specified by client.
	 * 
	 * @param isDirected
	 *        is the edge directed?
	 */
	public Network(boolean isDirected) {
		super();
		
		this.isDirected = isDirected;
	}
	
	/**
	 * Uses Kruskal's algorithm to calculates a spanning tree of the graph based on its edges' comparator.
	 * 
	 * @return a spanning tree of the graph, returns {@code null} if the edges empty or the graph is directed (It's hard to implement a good algorithm for directed graph spanning tree)
	 */
	public Network<T> spanningTree() {
		if (edges.isEmpty() || isDirected)
			return null;
		
		@SuppressWarnings("unchecked")
		List<WeightedEdge<T>> graph = (List<WeightedEdge<T>>) ((ArrayList<WeightedEdge<T>>) edges).clone();
		
		Network<T> spanningTree = new Network<>();
		DisjointSetUnion<T> dsu = new DisjointSetUnion<>();
		
		Collections.sort(graph);
		
		for (int i = 0, size = graph.size(), count = 0, vertexes = vertexCount(); i < size && count < vertexes - 1; ++i) {//spanning tree have V - 1 edges
			T u = graph.get(i).getU();
			T v = graph.get(i).getV();
			
			int weight = graph.get(i).getWeight();
			
			if (dsu.union(u, v)) {
				spanningTree.addEdge(u, v, weight);
				
				++count;
			}
		}
		
		return spanningTree;
	}
	
	/**
	 * Sets edges' comparator.
	 * 
	 * @param comparator
	 *        edge comparator
	 */
	public void setComparator(Comparator<WeightedEdge<T>> comparator) {
		check(comparator);
		
		edges.forEach(e -> e.setComparator(comparator));
	}
	
	/**
	 * Finds maximum possible flow we can send from source to sink.
	 * 
	 * @param source
	 *        vertex with no incoming edges
	 * @param sink
	 *        vertex with no outgoing edges
	 * 
	 * @return max flow we can send, -1 if it does not exist
	 */
	public int maxFlow(T source, T sink) {
		check(source, sink);
		
		return new EdmondsKarp(this).maxFlow(source, sink);
	}
	
	/**
	 * Adds an edge in the graph, it can be directed or undirected depends.
	 * 
	 * @param edge
	 *        {@code WeightedEdge} object
	 */
	public void addEdge(WeightedEdge<T> edge) {
		check(edge);
		
		T u = edge.getU();
		T v = edge.getV();
		
		int weight = edge.getWeight();
		
		if (!edges.contains(edge)) {
			edges.add(edge);
			
			if (!isDirected)
				edges.add(new WeightedEdge<>(v, u, weight));
		} else
			return;
		
		vertexes.add(u);
		vertexes.add(v);
		
		if (!adj.containsKey(u))
			adj.put(u, new HashSet<>());
		
		if (!adj.containsKey(v))
			adj.put(v, new HashSet<>());
		
		adj.get(u).add(new Pair<>(v, weight));
		
		if (!isDirected)
			adj.get(v).add(new Pair<>(u, weight));
	}
	
	/**
	 * Counts and returns number of edges in the graph.
	 * 
	 * @return number of edges
	 */
	@Override
	public int edgeCount() {
		if (isDirected)
			return edges.size();
		
		Set<WeightedEdge<T>> set = new HashSet<>();//make sure no edges are counted twice in undirected graph
		
		int total = 0;
		
		for (WeightedEdge<T> edge : edges) {
			T u = edge.getU();
			T v = edge.getV();
			
			int weight = edge.getWeight();
			
			WeightedEdge<T> reverse = new WeightedEdge<>(v, u, weight);
			
			if (!set.contains(reverse)) {
				total++;
				set.add(edge);
			}
		}
		
		return total;
	}
	
	/**
	 * Returns {@code boolean} flag that tells if the graph is directed or not.
	 * 
	 * @return {@code true} if the graph is directed {@code false} otherwise
	 */
	@Override
	public boolean isDirected() {
		return isDirected;
	}
	
	/**
	 * Adds an edge in the graph, it can be directed or undirected depends.
	 * 
	 * @param u
	 *        "from" vertex
	 * @param v
	 *        "to" vertex
	 * @param weight
	 *        edge weight
	 */
	@Override
	public void addEdge(T u, T v, int weight) {
		check(u, v);
		
		addEdge(new WeightedEdge<T>(u, v, weight));
	}
	
	/**
	 * Removes an edge in the graph, it can be directed or undirected depends.
	 * 
	 * @param u
	 *        "from" vertex
	 * @param v
	 *        "to" vertex
	 */
	@Override
	public void removeEdge(T u, T v, int weight) {
		check(u, v);
		
		WeightedEdge<T> lookfor = new WeightedEdge<>(u, v, weight);
		WeightedEdge<T> other = !isDirected ? new WeightedEdge<>(v, u, weight) : lookfor;
		
		edges = edges.parallelStream().filter(e -> !e.equals(lookfor) && !e.equals(other)).collect(Collectors.toList());
	}
	
	/**
	 * Returns sum of edge weights in the graph.
	 * 
	 * @return sum of edge weights
	 */
	@Override
	public int edgeSum() {
		Set<WeightedEdge<T>> set = new HashSet<>();//make sure no edges are counted twice
		
		int total = 0;
		
		for (WeightedEdge<T> edge : edges) {
			T u = edge.getU();
			T v = edge.getV();
			
			int weight = edge.getWeight();
			
			WeightedEdge<T> other = !isDirected ? new WeightedEdge<>(v, u, weight) : edge;
			
			if (!set.contains(other)) {
				total += weight;
				set.add(edge);
			}
		}
		
		return total;
	}
	
	/**
	 * Returns formatted {@code String} representation of {@code Network} object.
	 * 
	 * @return {@code String} representation of the object
	 */
	@Override
	public String toString() {
		if (isDirected)
			return super.toString();
		
		StringBuilder sb = new StringBuilder();
		
		Set<WeightedEdge<T>> set = new HashSet<>();//we don't want reverse edges in string representation
		
		sb.append("Network[");
		
		edges.forEach(e -> {
			T u = e.getU();
			T v = e.getV();
			
			int weight = e.getWeight();
			
			WeightedEdge<T> reverse = new WeightedEdge<>(v, u, weight);
			
			if (!set.contains(reverse)) {
				sb.append(e).append(", ");
				set.add(e);
			}
		});
		
		sb.setLength(sb.length() - 2);//remove last ", "
		sb.append("]");
		
		return sb.toString();
	}
	
	/*
	 * A nested class to support Edmonds Karp max flow algorithm
	 */
	private class EdmondsKarp {
		Map<T, List<EdmondsKarp.Edge>> adj;
		Map<T, EdmondsKarp.Edge> parent;
		
		EdmondsKarp(Network<T> flowNetwork) {
			this.adj = new HashMap<>();
			
			Map<T, Set<Pair<T, Integer>>> adj = flowNetwork.getAdjacencyList();
			Set<T> vertexes = flowNetwork.getVertexes();
			Set<Map.Entry<T, Set<Pair<T, Integer>>>> set = adj.entrySet();
			
			for (T vertex : vertexes)
				this.adj.put(vertex, new ArrayList<>());
			
			for (Map.Entry<T, Set<Pair<T, Integer>>> entry : set) {
				T u = entry.getKey();
				Set<Pair<T, Integer>> second = entry.getValue();
				
				for (Pair<T, Integer> pair : second) {
					T v = pair.first();
					int cap = pair.second();
					
					EdmondsKarp.Edge forwardEdge = new EdmondsKarp.Edge(u, v, 0, cap, this.adj.get(v).size());//0 flow and cap capacity
					EdmondsKarp.Edge backEdge = new EdmondsKarp.Edge(v, u, 0, 0, this.adj.get(u).size());//0 flow and 0 capacity
					
					this.adj.get(u).add(forwardEdge);
					this.adj.get(v).add(backEdge);
				}
			}
		}
		
		int maxFlow(T source, T sink) {
			if (source.equals(sink))
				return -1;
			
			int maxFlow = 0;
			
			//while it's possible to send flow from source to sink
			while (haveArgumentedPath(source, sink)) {
				int flow = INF;
				
				//find minimum flow (residual capacity) in an argumented path
				for (T u = sink; u != source; u = parent.get(u).u)
					flow = Math.min(flow, parent.get(u).cap - parent.get(u).flow);
				
				for (T u = sink; u != source; u = parent.get(u).u) {
					parent.get(u).flow += flow;//increase the edge's flow
					adj.get(parent.get(u).v).get(parent.get(u).rev).flow -= flow;//decrease the flow of edge's reverse
				}
				
				maxFlow += flow;
			}
			
			return maxFlow;
		}
		
		boolean haveArgumentedPath(T source, T sink) {
			parent = new HashMap<>();
			Queue<T> Q = new ArrayDeque<>();//might not work with earlier versions of java, use java.util.LinkedList if necessary
			
			Q.add(source);
			
			while (!Q.isEmpty()) {
				T u = Q.poll();
				
				for (EdmondsKarp.Edge edge : adj.get(u)) {
					T v = edge.v;
					int flow = edge.flow;
					int cap = edge.cap;
					
					if (!parent.containsKey(v) && flow < cap) {
						parent.put(v, edge);
						Q.add(v);
					}
				}
			}
			
			return parent.containsKey(sink);
		}
		
		private class Edge {
			T u;
			T v;
			
			int flow;//current edge flow
			int cap;//capacity of the edge
			int rev;//reverse edge index
			
			Edge(T u, T v, int flow, int cap, int rev) {
				this.u = u;
				this.v = v;
				this.flow = flow;
				this.cap = cap;
				this.rev = rev;
			}
		}
	}
}
