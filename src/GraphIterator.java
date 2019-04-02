import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * The {@code GraphIterator} is a modified {@code Iterator} for traversing graph.
 * It supports two graph traversal algorithms (BFS and DFS).
 * It's noted that the {@code GraphIterator} only explores vertexes in the disconnected graph
 * only if it's on the same component as the specified source vertex.
 * The other vertexes in the disconnected graph are not be explored by the iterator because they are unreachable.
 * 
 * @param <T> type of vertex
 * 
 * @author Jimmy Y.
 * @see Iterator
 * @version 1.0 (3/16/2019)
 */
public class GraphIterator<T> implements Iterator<T> {
	/**
	 * Dequeue is used to hold next vertexes to explore.
	 * It's an efficient data structure to support both BFS and DFS because
	 * when you implement both BFS and DFS iteratively, you notice that the
	 * new vertexes are added to back of a queue in BFS and
	 * the new vertexes are added to front of a stack.
	 */
	private Deque<T> Q;
	
	/**
	 * Adjacency list representation of the graph
	 */
	private Map<T, Set<T>> adj;
	
	/**
	 * Visited vertexes
	 */
	private Set<T> marked;
	
	/**
	 * {@code boolean} flag that indicates how should we traverse graph, depth first or breadth first.
	 */
	private boolean depthFirst;
	
	/**
	 * Creates a new {@code GraphIterator} object out of
	 * specific graph, source vertex, and traversal style.
	 * 
	 * @param graph
	 *        Graph to explore
	 * @param source
	 *        source vertex
	 * @param depthFirst
	 *        traverse graph in depth first fashion?
	 * 
	 * @throw java.lang.IllegalArgumentException if the source vertex DNE
	 */
	public GraphIterator(AbstractGraph<T> graph, T source, boolean depthFirst) {
		if (!graph.contains(source))
			throw new IllegalArgumentException("The source vertex does not exist!");
		
		this.depthFirst = depthFirst;
		
		adj = graph.getAdjacencyList();
		
		Q = new ArrayDeque<>();
		marked = new HashSet<>();
		
		Q.add(source);
		marked.add(source);
	}
	
	/**
	 * Checks there's more vertexes to explore in the graph.
	 * 
	 * @return {@code true} if there's more vertexes to explore else {@code false}
	 */
	@Override
	public boolean hasNext() {
		return !Q.isEmpty();
	}
	
	/**
	 * Returns next new vertex in the graph.
	 * 
	 * @return next vertex
	 * 
	 * @throw java.lang.IllegalArgumentException if there are no more vertexes to explore
	 */
	@Override
	public T next() {
		if (!hasNext())
			throw new IllegalArgumentException("There are no more vertexes to explore!");
		
		T u = Q.pop();
		
		for (T v : adj.get(u))
			if (!marked.contains(v)) {
				marked.add(v);
				
				if (depthFirst)
					Q.addFirst(v);
				else
					Q.addLast(v);
			}
		
		return u;
	}
}
