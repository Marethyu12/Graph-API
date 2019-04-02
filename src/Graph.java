import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The {@code Graph} is an interface that wraps methods for unweighted graphs.
 * 
 * @param <T> vertex type
 * 
 * @author Jimmy Y.
 * @see GraphBase
 * @see WeightedGraph
 * @see Forest
 * @see DirectedGraph
 * @version 1.0 (3/16/2019)
 */
public interface Graph<T> extends GraphBase<T> {
	/**
	 * Adds an edge in the graph, it can be directed or undirected depends.
	 * 
	 * @param u
	 *        "from" vertex
	 * @param v
	 *        "to" vertex
	 */
	void addEdge(T u, T v);
	
	/**
	 * Removes an edge in the graph, it can be directed or undirected depends.
	 * 
	 * @param u
	 *        "from" vertex
	 * @param v
	 *        "to" vertex
	 */
	void removeEdge(T u, T v);
	
	/**
	 * Returns an adjacency list representation of the graph.
	 * 
	 * @return adjacency list
	 */
	Map<T, Set<T>> getAdjacencyList();
	
	/**
	 * Returns all vertexes of the graph.
	 * 
	 * @return graph's vertexes
	 */
	Set<T> getVertexes();
	
	/**
	 * Returns all edges of the graph.
	 * 
	 * @return graph's edges
	 */
	List<Edge<T>> getEdges();
}
