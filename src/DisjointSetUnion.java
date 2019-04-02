import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The {@code DisjointSetUnion} is a utility class used to implement some graph algorithms
 * like detecting a cycle in an undirected graph and act as an efficient data structure for Kruskal's MST algorithm.
 * This implementation of union find uses path compression and union by rank heuristics.
 * 
 * @param <T> element type
 * 
 * @author Jimmy Y.
 * @version 1.0 (3/16/2019)
 */
public class DisjointSetUnion<T> {
	/**
	 * Stores element's parent set
	 */
	private Map<T, T> parent;
	
	/**
	 * Stores set's ranks
	 */
	private Map<T, Integer> rank;
	
	/**
	 * Creates a new {@code DisjointSetUnion} object without any sets.
	 */
	public DisjointSetUnion() {
		parent = new HashMap<>();
		rank = new HashMap<>();
	}
	
	/**
	 * Creates a new set with a new element.
	 * 
	 * @param x
	 *        new element
	 */
	public void makeSet(T x) {
		Objects.requireNonNull(x);
		
		parent.put(x, x);//parent of a new set is itself
		rank.put(x, 0);
	}
	
	/**
	 * Returns element's parent set.
	 * 
	 * @param x
	 *        element
	 * 
	 * @return parent representative of element x
	 */
	public T find(T x) {
		if (!parent.containsKey(x))
			makeSet(x);
		
		return findUtil(x);
	}
	
	private T findUtil(T x) {
		if (!x.equals(parent.get(x)))//if (x != parent[x])
			parent.put(parent.get(x), find(parent.get(x)));//parent[x] = find(parent[x]);
		
		return parent.get(x);//return parent[x];
	}
	
	/**
	 * Unites elements' parent sets. The union happens
	 * only if both elements are from different sets otherwise
	 * it won't happen.
	 * 
	 * @param x
	 *        first element
	 * @param y
	 *        second element
	 * 
	 * @return {@code true} if union's success {@code false} otherwise
	 */
	public boolean union(T x, T y) {
		if (!parent.containsKey(x))
			makeSet(x);
		
		if (!parent.containsKey(y))
			makeSet(y);
		
		x = find(x);
		y = find(y);
		
		if (parent.get(x).equals(parent.get(y)))//if (parent[x] == parent[y])// means x and y are on same set, don't unite
			return false;
		
		if (rank.get(x) > rank.get(y))//if (rank[x] > rank[y])
			parent.put(y, x);//parent[y] = x;
		else if (rank.get(x) < rank.get(y))
			parent.put(x, y);//parent[x] = y;
		else {//same rank
			parent.put(x, y);
			rank.put(y, rank.get(y) + 1);//++rank[y];
		}
		
		return true;//success
	}
}
