
/**
 * The {@code GraphBase} is an interface that provides
 * basic methods for graph classes.
 * 
 * @param <T> vertex type
 * 
 * @author Jimmy Y.
 * @see Graph
 * @see WeightedGraph
 * @version 1.0 (3/16/2019)
 */
public interface GraphBase<T> {
	/**
	 * Returns number of vertexes in a graph.
	 * 
	 * @return number of vertexes
	 */
	int vertexCount();
	
	/**
	 * Returns number of edges in a graph.
	 * 
	 * @return number of edges
	 */
	int edgeCount();
	
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
	int shortestPath(T u, T v);
	
	/**
	 * Returns number of neighbors vertex have.
	 * 
	 * @param vertex
	 *        query vertex
	 * 
	 * @return number of neighbors
	 */
	int degree(T vertex);
	
	/**
	 * Tests if the vertex exists in the graph or not.
	 * 
	 * @param vertex
	 *        vertex to be tested
	 * 
	 * @return {@code true} if the vertex exists {@code false} otherwise
	 */
	boolean contains(T vertex);
	
	/**
	 * Adds a vertex in the graph.
	 * 
	 * @param vertex
	 *        vertex to be added
	 */
	void addVertex(T vertex);
	
	/**
	 * Removes a vertex in the graph.
	 * 
	 * @param vertex
	 *        vertex to be removed
	 */
	void removeVertex(T vertex);
	
	/**
	 * Indicates if the graph is directed or not.
	 * 
	 * @return {@code true} if the graph is directed {@code false} otherwise
	 */
	boolean isDirected();
}
