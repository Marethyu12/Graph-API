import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The {@code WeightedGraph} is an interface that wraps methods for weighted graphs.
 * 
 * @param <T> vertex type
 * 
 * @author Jimmy Y.
 * @see GraphBase
 * @see Graph
 * @see Network
 * @version 1.0 (3/16/2019)
 */
public interface WeightedGraph<T> extends GraphBase<T> {
	/**
	 * Infinity constant
	 */
	static final int INF = 0x3F3F3F3F;//don't use Integer.MAX_VALUE, integer overflow might happen
	
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
	void addEdge(T u, T v, int weight);
	
	/**
	 * Removes an edge in the graph, it can be directed or undirected depends.
	 * 
	 * @param u
	 *        "from" vertex
	 * @param v
	 *        "to" vertex
	 */
	void removeEdge(T u, T v, int weight);
	
	/**
	 * Returns sum of edge weights int the graph.
	 * 
	 * @return sum of edge weights
	 */
	int edgeSum();
	
	/**
	 * Returns an adjacency list representation of the graph.
	 * 
	 * @return adjacency list
	 */
	Map<T, Set<Pair<T, Integer>>> getAdjacencyList();
	
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
	List<WeightedEdge<T>> getEdges();
}
