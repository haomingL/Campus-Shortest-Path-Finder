package hw5;

import java.util.ArrayList; 
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/** <b>Graph</b> represents a <b>mutable</b> graph. 
 *  Graph represents a graph which consists of vertex of nodes and edges.
 */
public class Graph<E extends Comparable<E>,T extends Comparable<T>> {
	private final Map<Node<E>, Set<Edge<E, T>>> vertices;
	
	// Abstract Function:
	// The vertices store all the vertices and edges in the graph.
	// Representation Invariant:
	// All the vertices stored are unique. In other words, the start node of
	// the vertices is unique. Edges are all unique. All the edges starts with
	// the start node which is the key.
	
	public Graph() {
		this.vertices = new HashMap<Node<E>, Set<Edge<E, T>>>();
		checkRep();
	}
	
	/**
	 * This function add a node with given s to the graph
	 * @param s the string that the node has.
	 * @return true if the node is added successfully, false otherwise.
	 * @modifies create a node and add it to the graph.
	 */
	public boolean addNode(E s) {
		return addNode(new Node<E>(s));
	}
	
	/**
	 * This function adds a node with given s to the graph
	 * @param node to be added to the graph
	 * @return true if the node is added successfully, false if the node
	 * already exists.
	 * @modifies add the node into the graph.
	 */
	public boolean addNode(Node<E> node) {
		for (Node<E> n: vertices.keySet()) {
			if (n.equals(node)) {
				return false;
			}
		}
		vertices.put(node, new HashSet<Edge<E, T>>());
		checkRep();
		return true;
	}
	
	/**
	 * This function adds a start node and an corresponding edge to the graph
	 * @param start is the start node of the vertex.
	 * @param edge is the edge corresponding to the start node.
	 * @return true if the node and the edge are added successfully, false if the edge
	 * 		    already exists.
	 * @throws IllegalArgumentException if the edge does not start with the start node
	 * @modifies add the given edge to the graph.
	 */
	public boolean addEdge(Node<E> start, Edge<E, T> edge) {
		if (!edge.getStart().equals(start)) {
			throw new IllegalArgumentException();
		}
		Node<E> end = edge.getEnd();
		if (!vertices.containsKey(start)) {
			vertices.put(start, new HashSet<Edge<E, T>>());
		}
		Set<Edge<E, T>> edges = vertices.get(start);
		if (edges.contains(edge)) {
			return false;
		}
		edges.add(edge);
		if (!vertices.containsKey(end)) {
			vertices.put(end, new HashSet<Edge<E, T>>());
		}
		checkRep();
		return true;
	}
	
	/**
	 * This function adds a start node and an corresponding edge to the graph
	 * @param start is the start node of the vertex.
	 * @param edge is the edge corresponding to the start node.
	 * @return true if the node and the edge are added successfully, false if the edge
	 * 		    already exists.
	 * @throws IllegalArgumentException if the edge does not start with the start node
	 * @modifies add the edge to the graph.
	 */
	public boolean addEdge(Edge<E, T> edge) {
		return addEdge(edge.getStart(), edge);
	}
	
	/**
	 * This function remove the node from the graph.
	 * @param v is the given vertex to be removed.
	 * @return true if the vertex is successfully removed from the graph.
	 * 	       false otherwise.
	 * @modifies remove the node from the graph.
	 */
	public boolean removeNode(Node<E> target) {
		boolean success = false;
		Iterator<Node<E>> i = vertices.keySet().iterator();
		while (i.hasNext()) {
			Node<E> start = i.next();
			if (start.equals(target)) {
				i.remove();
				success = true;
			} else {
				Set<Edge<E, T>> edges = vertices.get(start);
				Iterator<Edge<E, T>> j = edges.iterator();
				while (j.hasNext()) {
					Edge<E, T> edge = j.next();
					if (edge.getEnd().equals(target)) {
						j.remove();
						success = true;
					}
				}
			}
		}
		checkRep();
		return success;
	}
	
	/**
	 * This function removes the edge from the graph.
	 * @param target is the given target to be removed.
	 * @return true if the edge is removed, false otherwise.
	 * @modifies remove the edge from the graph.
	 */
	public boolean removeEdge(Edge<E, T> target) {
		Node<E> start = target.getStart();
		if (!vertices.keySet().contains(start)) {
			return false;
		}
		checkRep();
		return vertices.get(start).remove(target);
	}
	
	/**
	 * This function is called to tell whether the graph contains
	 * the given node.
	 * @param node to be determined whether it is in the graph
	 * @return true if the node is in the graph, false otherwise.
	 */
	public boolean contains(Node<E> node) {
		return vertices.containsKey(node);
	}
	
	/**
	 * This function is called to tell whether the graph contains
	 * the given edge.
	 * @param edge to be determined whether it is in the graph
	 * @return true if the edge is in the graph, false otherwise.
	 */
	public boolean contains(Edge<E, T> edge) {
		Node<E> start = edge.getStart();
		if (vertices.containsKey(start)) {
			if (vertices.get(start).contains(edge)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	/**
	 * This function returns a string representation of the graph.
	 * Each start node followed by edges is in one line.
	 * e.g. a a---ab-->b
	 * @return the string representation of the graph
	 */
	public String toString() {
		StringBuffer temp = new StringBuffer();
		for (Node<E> start: vertices.keySet()) {
			temp.append(start.getData());
			temp.append(" ");
			for (Edge<E, T> e: vertices.get(start)) {
				temp.append(e.getStart().getData());
				temp.append("---");
				temp.append(e.getLabel());
				temp.append("-->");
				temp.append(e.getEnd().getData());
				temp.append(" ");
			}
			temp.append("\n");
		}
		return temp.toString();
	}
	
	/**
	 * This function returns a set of nodes in the graph.
	 * @return a set of nodes in the graph in sorted order
	 */
	public List<Node<E>> listNodes() {
		List<Node<E>> result = new ArrayList<Node<E>>();
		result.addAll(vertices.keySet());
		Collections.sort(result);
		return result;
	}
	
	/**
	 * This function returns all the children of the parent node
	 * @requires the start node is in the graph and it is not null.
	 * @param start is the parent node in the graph
	 * @return a set of children node of the given parent node in sorted order.
	 */
	public List<Edge<E, T>> listEnds(Node<E> start) {
		List<Edge<E, T>> result = new ArrayList<Edge<E, T>>();
		if (!vertices.containsKey(start)) {
			return result;
		}
		for (Edge<E, T> e: vertices.get(start)) {
			result.add(e);
		}
		Collections.sort(result);
		return result;
	}
	
	/**
	 * This function checks the representation invariant in the class.
	 */
	private void checkRep() {
		assert(!vertices.keySet().contains(null));
	}
}
