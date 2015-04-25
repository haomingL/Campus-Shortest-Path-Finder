package hw5;

/** <b>Node</b> represents an <b>immutable</b> node with given data of some type.
	The given type must implements comparable interface.
*/

public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
	private final T data;
	
	// Abstract Function:
	// The data represents the given information stored in the node.
	//
	// Representation Invariant:
	// data is not null.
	//
	
	/**
	 * @param s is the given string stored in the data.
	 * Initialize the node object with the given data.
	 */
	public Node(T s) {
		data = s;
		checkRep();
	}
	
	/**
	 * 
	 * @return the String representation of data stored in the Node class.
	 */
	public T getData() {
		return data;
	}
	
	@Override
	/**
	 * This function decides whether two nodes are equal depending on their 
	 * data store in them.
	 * @requires the object given is an instance of node class.
	 * @param a other node object to be compared
	 * @return true if two nodes have the same value or false otherwise.
	 * @throws IllegalArgumentException if the parameter given is not a node.
	 */
	public boolean equals(Object o) {
		if (!(o instanceof Node)) {
			return false;
		}
		return this.data.equals(((Node<?>)o).data);
	}
	
	@Override
	/**
	 * THis function returns the hash code of the node
	 * @return the hash code of the node
	 */
	public int hashCode() {
		return data.hashCode();
	}
	
	private void checkRep() {
		assert(data != null);
		assert(data instanceof Comparable<?>);
	}

	@Override
	/**
	 * @require the data implements the Comparable interface.
	 */
	public int compareTo(Node<T> other) {
		return this.data.compareTo(other.data);
	}
	
	@Override
	public String toString() {
		return data.toString();
	}
}
