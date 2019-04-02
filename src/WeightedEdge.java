import java.util.Comparator;
import java.util.Objects;

/**
 * The {@code WeightedEdge} is an extension of the regular {@code Edge} where the
 * connection between vertexes u and v have a specified cost represented in integer.
 * 
 * @param <T> type of vertex
 * 
 * @author Jimmy Y.
 * @see Edge
 * @version 1.0 (3/16/2019)
 */
public class WeightedEdge<T> extends Edge<T> implements Comparable<WeightedEdge<T>> {
	/**
	 * Graph's integer weight
	 * 
	 * TODO: difference between weight and label?
	 */
	private int weight;
	
	/**
	 * A custom edge comparator.
	 * It's useful for sorting edges in
	 * Kruskal's algorithm.
	 */
	private Comparator<WeightedEdge<T>> comparator;
	
	/**
	 * Default constructor that makes a new {@code WeightedEdge} object out
	 * of from, to vertexes and a weight.
	 * The comparator is defaulted to compare edges based on increasing order of weights.
	 * 
	 * @param u
	 *        "from" vertex
	 * @param v
	 *        "to" vertex
	 * @param weight
	 *        edge weight
	 */
	public WeightedEdge(T u, T v, int weight) {
		this(u, v, weight, (a, b) -> a.getWeight() - b.getWeight());//the default comparator compares edges based on increasing order of weights
	}
	
	/**
	 * A constructor that makes a new {@code WeightedEdge} object out
	 * of from, to vertexes and a weight; also it accepts an edge comparator.
	 * 
	 * @param u
	 *        from vertex
	 * @param v
	 *        to vertex
	 * @param weight
	 *        edge weight
	 * @param comparator
	 *        edge comparator
	 */
	public WeightedEdge(T u, T v, int weight, Comparator<WeightedEdge<T>> comparator) {
		super(u, v);
		
		Objects.requireNonNull(comparator);
		
		this.weight = weight;
		this.comparator = comparator;
	}
	
	/**
	 * Sets edge's weight.
	 * 
	 * @param weight
	 *        edge weight
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	/**
	 * Sets edge's comparator.
	 * 
	 * @param comparator
	 *        edge comparator
	 */
	public void setComparator(Comparator<WeightedEdge<T>> comparator) {
		Objects.requireNonNull(comparator);
		
		this.comparator = comparator;
	}
	
	/**
	 * Returns weight of the edge.
	 * 
	 * @return edge weight
	 */
	public int getWeight() {
		return weight;
	}
	
	/**
	 * Returns edge's comparator
	 * 
	 * @return edge comparator
	 */
	public Comparator<WeightedEdge<T>> getComparator() {
		return comparator;
	}
	
	/**
	 * Compares the current {@code WeightedEdge} object with others.
	 * 
	 * @param that
	 *        another {@code WeightedEdge} object to be compared against
	 * 
	 * @return integer less than 0 if the current object must be placed before {@code that} object,
	 *         integer equal to 0 if the current object matches {@code that} object,
	 *         integer greater than 0 if the current object must be placed after {@code that} object
	 */
	@Override
	public int compareTo(WeightedEdge<T> that) {
		Objects.requireNonNull(comparator);
		
		return comparator.compare(this, that);
	}
	
	/**
	 * Returns the current object's hash value.
	 * 
	 * @return integer hash value of the current object
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ weight;
	}
	
	/**
	 * Tests if the current object matches with other object.
	 * 
	 * @param obj
	 *        object to be tested
	 * 
	 * @return {@code true} if they match {@code false} otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (this == null || obj == null)
			return false;
		
		if (this.getClass() != obj.getClass())
			return false;
		
		WeightedEdge<?> edge = (WeightedEdge<?>) obj;
		
		return super.equals((Edge<?>) edge) &&
			   weight == edge.getWeight();
	}
	
	/**
	 * Returns a formatted {@code String} representation of the current object.
	 * 
	 * @return {@code String} representation of the object
	 */
	@Override
	public String toString() {
		return "WeightedEdge[u=" + getU() + ", v=" + getV() + ", weight=" + weight + "]";
	}
}
