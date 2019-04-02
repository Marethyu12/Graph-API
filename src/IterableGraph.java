import java.util.Iterator;

/**
 * The {@code IterableGraph} is an interface used by other graph classes if they allow
 * clients to traverse its vertexes via iterator. The {@code GraphIterator} different from {@code java.lang.Iterable}
 * because unlike iterating collections in java.util package, there are two ways to traverse the graph,
 * depth first or breadth first so this interface provides two different iterating methods for the graph.
 * 
 * @param <T> vertex type
 * 
 * @author Jimmy Y.
 * @see GraphIterator
 * @version 1.0 (3/16/2019)
 */
public interface IterableGraph<T> {
	/**
	 * The {@code breadthFirstIterator} allows clients to explore graph's vertex
	 * in breadth first fashion (explore in layers or levels).
	 * 
	 * @param source
	 *        source vertex
	 * 
	 * @return iterator object
	 */
	Iterator<T> breadthFirstIterator(T source);
	
	/**
	 * The {@code depthFirstIterator} allows clients to explore graph's vertex
	 * in depth first fashion (explore deepest layer first).
	 * 
	 * @param source
	 *        source vertex
	 * 
	 * @return iterator object
	 */
	Iterator<T> depthFirstIterator(T source);
}
