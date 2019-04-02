
/**
 * An implementation of C++'s pair type from
 * standard template library (stl).
 * 
 * @param <T1>
 *        first object type
 * @param <T2>
 *        second object type
 * 
 * @author Jimmy Y.
 * @version 1.0 (3/16/2019)
 */
public class Pair<T1, T2> {
	/**
	 * First object
	 */
	private T1 first;
	
	/**
	 * Second object
	 */
	private T2 second;
	
	/**
	 * Creates a new {@code Pair} object with empty first and second objects.
	 */
	public Pair() {
		first = null;
		second = null;
	}
	
	/**
	 * Creates a new {@code Pair} object with specified first and second objects.
	 * 
	 * @param first
	 *        first object
	 * @param second
	 *        second object
	 */
	public Pair(T1 first, T2 second) {
		this.first = first;
		this.second = second;
	}
	
	/**
	 * Sets the first object of the pair.
	 * 
	 * @param first
	 *        first object
	 */
	public void setFirst(T1 first) {
		this.first = first;
	}
	
	/**
	 * Sets the second object of the pair.
	 * 
	 * @param second
	 *        second object
	 */
	public void setSecond(T2 second) {
		this.second = second;
	}
	
	/**
	 * Returns the first object of the pair.
	 * 
	 * @return first object
	 */
	public T1 first() {
		return first;
	}
	
	/**
	 * Returns the second object of the pair.
	 * 
	 * @return second object
	 */
	public T2 second() {
		return second;
	}
	
	/**
	 * Returns combined hash values of two objects of the pair.
	 * 
	 * @return hash value of the current object
	 */
	@Override
	public int hashCode() {
		return first.hashCode() ^ second.hashCode();
	}
	
	/**
	 * Tests equality of the current object with other.
	 * 
	 * @param obj
	 *        object to be compared
	 * 
	 * @return {@code true} if they match, {@code false} otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (this == null || obj == null)
			return false;
		
		if (this.getClass() != obj.getClass())
			return false;
		
		Pair<?, ?> pair = (Pair<?, ?>) obj;
		
		return first.equals(pair.first()) &&
			   second.equals(pair.second());
	}
	
	/**
	 * Returns a formatted {@code String} representation of the current object.
	 * 
	 * @return object's {@code String} representation
	 */
	@Override
	public String toString() {
		return "[" + first + ", " + second + "]";
	}
}
