package com.tinkerpop.bench.util;
import java.io.Serializable;


/**
 * A generic immutable pair
 * 
 * @author Peter Lawrey
 * @author Peter Macko
 *
 * @param <T1> the type of the first object
 * @param <T2> the type of the second object
 */
public final class Pair<T1, T2> implements Comparable<Pair<T1, T2>>, Serializable {
	
	// From: http://stackoverflow.com/questions/156275/what-is-the-equivalent-of-the-c-pairl-r-in-java
	
	private static final long serialVersionUID = 1L;
	
	public final T1 first;
	public final T2 second;

	
	/**
	 * Create an instance of class Pair
	 * 
	 * @param first the first element
	 * @param second the second element
	 */
	public Pair(T1 first, T2 second) {
		this.first = first;
		this.second = second;
	}
	
	
	/**
	 * Return the first element
	 * 
	 * @return the first element
	 */
	public T1 getFirst() {
		return first;
	}
	
	
	/**
	 * Return the second element
	 * 
	 * @return the second element
	 */
	public T2 getSecond() {
		return second;
	}

	
	/**
	 * Compare this pair to another pair of the same type
	 * 
	 * @param o the other pair
	 * @return the result of the comparison
	 */
	@Override
	public int compareTo(Pair<T1, T2> o) {
		int cmp = compare(first, o.first);
		return cmp == 0 ? compare(second, o.second) : cmp;
	}

	
	/**
	 * Compare two objects
	 * 
	 * @param o1 the first object
	 * @param o2 the second object
	 * @return the result of the comparison
	 */
	@SuppressWarnings("unchecked")
	private static <T> int compare(T o1, T o2) {
		return o1 == null ? o2 == null ? 0 : -1 : o2 == null ? +1
				: ((Comparable<T>) o1).compareTo(o2);
	}

	
	/**
	 * Compute the hash code
	 * 
	 * @return the hash code
	 */
	@Override
	public int hashCode() {
		return 31 * hashcode(first) + hashcode(second);
	}

	
	/**
	 * Compute the hash code of the given object
	 * 
	 * @param o the object
	 * @return the hash code
	 */
	private static int hashcode(Object o) {
		return o == null ? 0 : o.hashCode();
	}

	
	/**
	 * Check whether this object is equal to the other object
	 * 
	 * @param obj the other object
	 * @return true if they are equal
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Pair<?, ?>))
			return false;
		if (this == obj)
			return true;
		return equal(first, ((Pair<T1, T2>) obj).first)
				&& equal(second, ((Pair<T1, T2>) obj).second);
	}

	
	/**
	 * Check two objects for equality
	 * 
	 * @param o1 the first object
	 * @param o2 the second object
	 * @return true if they are equal
	 */
	private boolean equal(Object o1, Object o2) {
		return o1 == null ? o2 == null : (o1 == o2 || o1.equals(o2));
	}

	
	/**
	 * Return the string representation of the pair
	 * 
	 * @return the string representation
	 */
	@Override
	public String toString() {
		return "(" + first + ", " + second + ')';
	}
}
