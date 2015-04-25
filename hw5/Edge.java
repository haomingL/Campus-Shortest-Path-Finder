package hw5;
/** <b>Edge</b> represents an <b>immutable</b> edge which connects
 * two nodes and has its own label.
 * @param <E> must implement Comparable interface to compare two edges.
 * 
*/

public class Edge<E extends Comparable<E>, T extends Comparable<T>> implements Comparable<Edge<E, T>> {
	public static final int PRIME_FACTOR = 31;
	
	private final Node<E> start;
	private final Node<E> end;
	private final T label;
	
	// Abstract Function:
	// The start represents the start node of the edge
	// The end represents the end node of the edge
	// The label represents label the edge bears
	
	// Representation Invariants:
	// The start and the end node cannot be null. The label
	// cannot be null too.
	
	/**
	 * constructs a new edge.
	 * @require start, end should be Nodes and label should be a string
	 * @param start is the start of the node
	 * @param end is the end of the node
	 * @param label is the label of the edge
	 */
	public Edge (Node<E> newStart, Node<E> newEnd, T newLabel) {
		this.start = newStart;
		this.end = newEnd;
		this.label = newLabel;
		checkRep();
	}
	
	/**
	 * @return the start node of the edge
	 */
	public Node<E> getStart() {
		return start;
	}
	
	/**
	 * @return the end node of the edge
	 */
	public Node<E> getEnd() {
		return end;
	}
	
	/**
	 * @return the label of the edge
	 */
	public T getLabel() {
		return label;
	}
	
	@Override
	/**
	 * This function determines whether two edges are equal. If two edges are equal, they
	 * contain the same start node and the end node.
	 * @param the object given to be compared.
	 * @return true if two edges are equal and false otherwise.
	 * @throws IllegalArgumentException if the object given is not an instance of Edge
	 */
	public boolean equals(Object o) {
		if (!(o instanceof Edge)) {
			return false;
		}
		return this.start.equals(((Edge<?, ?>)o).getStart()) && this.end.equals(((Edge<?, ?>)o).getEnd())
				&& this.label.equals(((Edge<?, ?>)o).label);
	}
	
	@Override
	/**
	 * @return the hash code of the Edge
	 */
	public int hashCode() {
		return start.getData().hashCode() * PRIME_FACTOR + label.hashCode() * PRIME_FACTOR * PRIME_FACTOR +  
				end.getData().hashCode() * PRIME_FACTOR * PRIME_FACTOR * PRIME_FACTOR;
	}
	
	@Override
	public int compareTo(Edge<E, T> other) {
		if (!this.getEnd().equals(other.getEnd())) {
			return this.getEnd().compareTo(other.getEnd());
		} else if (!this.label.equals(other.label)) {
			return this.label.compareTo(other.label);
		} else {
			return this.getStart().compareTo(other.getStart());
		}
	}
	
	@Override
	public String toString() {
		return this.getStart().getData().toString() + " to " + this.getEnd().getData() + " via " + 
				this.getLabel().toString();
	}
	
	/**
	 * check whether the representation invariant is violated.
	 */
	private void checkRep() {
		assert (start != null);
		assert (end != null);
		assert (label != null);
	}
}
