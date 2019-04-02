import java.util.Objects;

/**
 * The {@code Edge} represents an undirected edge of the graph.
 * 
 * @param <T> type of vertex
 * 
 * @author Jimmy Y.
 * @see WeightedEdge
 * @version 1.0 (3/16/2019)
 */
public class Edge<T> {
	/**
	 * "from" vertex
	 */
	private T from;
	
	/**
	 * "to" vertex
	 */
	private T to;
	
	/**
	 * Creates a new {@code Edge} object out of from and to vertexes.
	 * 
	 * @param from
	 *        "from" vertex
	 * @param to
	 *        "to" vertex
	 */
	public Edge(T from, T to) {
		Objects.requireNonNull(from);
		Objects.requireNonNull(to);
		
		this.from = from;
		this.to = to;
	}
	
	/**
	 * Sets "from" vertex of the edge.
	 * 
	 * @param from
	 *        "from" vertex
	 */
	public void setU(T from) {
		Objects.requireNonNull(from);
		
		this.from = from;
	}
	
	/**
	 * Sets "to" vertex of the edge.
	 * 
	 * @param to
	 *        "to" vertex
	 */
	public void setV(T to) {
		Objects.requireNonNull(to);
		
		this.to = to;
	}
	
	/**
	 * Returns "from" vertex of the edge.
	 * 
	 * @return "from" vertex
	 */
	public T getU() {
		return from;
	}
	
	/**
	 * Returns "to" vertex of the edge.
	 * 
	 * @return "to" vertex
	 */
	public T getV() {
		return to;
	}
	
	/**
	 * Returns the current object's hash value.
	 * 
	 * @return hash value
	 */
	@Override
	public int hashCode() {
		return from.hashCode() ^ to.hashCode();
	}
	
	/**
	 * Tests equality of the current object with other.
	 * 
	 * @param obj
	 *        an object to be tested against
	 * 
	 * @return {@code true} if the current object matches with other {@code false} otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (this == null || obj == null)
			return false;
		
		if (this.getClass() != obj.getClass())
			return false;
		
		Edge<?> edge = (Edge<?>) obj;
		
		return from.equals(edge.getU()) &&
			   to.equals(edge.getV());
	}
	
	/**
	 * Returns formatted {@code String} representation of the current {@code Edge} object.
	 * 
	 * @return {@code String} representation of the object
	 */
	@Override
	public String toString() {
		return "Edge[u=" + from + ", v=" + to + "]";
	}
}
