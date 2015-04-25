/**
 * 
 */
package hw7;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

import hw5.Edge;
import hw5.Graph;
import hw5.Node;
import hw6.MarvelPaths;
import hw6.MarvelParser.MalformedDataException;

/**
 * @author Haoming Liu
 * This class is used to load graph from a given file with correct format.
 * It can be also used to find the shortest path from one node to the other. 
 * The label/weight of the edges should be double and the node of the data should be string.
 */
public class MarvelPaths2 {
	
	public static final int INITIAL_CAPACITY = 20; // This is the initial capacity of the priority queue.
	
	// This is not an ADT
	
	public static void main(String[] args) throws MalformedDataException {
		MarvelPaths2 m = new MarvelPaths2();
		@SuppressWarnings("resource")
		Scanner console = new Scanner(System.in);
		System.out.println("The graph you wish to use: ");
		System.out.println("please give me the simple path. ");
		String graphName = console.nextLine();
		Graph<String, Double> graph = m.buildPaths("src/hw7/data/" + graphName);
		System.out.println("Graph loaded");
		System.out.println("Start Node:");
		String s = console.nextLine();
		Node<String> start = new Node<String>(s);
		System.out.println("End Node:");
		s = console.nextLine();
		Node<String> end = new Node<String>(s);
		List<Edge<String, Double>> path = new ArrayList<Edge<String, Double>>();
		boolean find = m.findPath(graph, start, end, path);
		System.out.println(find);
		for (int i = 0; i < path.size(); i++) {
			System.out.println(path.get(i));
		}
	}
	
	/**
	 * This function build a graph of characters and weight between them.
	 * The weight is the multiplicative inverse of the number of books between two characters.
	 * @param fileName is the file given to build the graph
	 * @return the graph constructed
	 * @throws MalformedDataException if the given file is not in the correct format.
	 */
	public Graph<String, Double> buildPaths(String fileName) throws MalformedDataException {
		Graph<String, String> comics = new Graph<String, String>();
		Graph<String, Double> result = new Graph<String, Double>();
		MarvelPaths m = new MarvelPaths();
		comics = m.buildPaths(fileName);
		// First construct a graph of books and characters in hw6
		for (Node<String> n: comics.listNodes()) {
			List<Edge<String, String>> list = comics.listEnds(n);
			for (int i = 0; i < list.size(); i++) {
				Edge<String, String> e1 = list.get(i);
				double count = 1.0;
				for (int j = i + 1; j < list.size(); j++) {
					Edge<String, String> e2 = list.get(j);
					if (e1.getEnd().equals(e2.getEnd())) {
						count = count + 1;
						list.remove(j);
						// remove the second appearance of the end node to prevent
						// multiple times of counting the same end node.
						j--;
					}
				}
				result.addEdge(new Edge<String, Double>(n, e1.getEnd(), 1.0 / count));
			}
		}
		// Go through each character to find how many books between each of them and each of their children
		// to determine the weight.
		
		// The weight between two characters must be greater than 0 and less than or equal to 1. 
		return result;
	}
	
	/**
	 * The function finds the shortest path between two characters. The shortest path is determined by 
	 * the smallest weight between two nodes.
	 * @param graph is given graph to be used to find the path.
	 * @param start is the start node of the path.
	 * @param end is the end node of the path.
	 * @param path is the shortest path found connecting the start node with the end node.
	 * @return true if there is a shortest path found, false otherwise.
	 */
	public boolean findPath(Graph<String, Double> graph, Node<String> start, Node<String> end,
				List<Edge<String, Double>> path) {
		if (!graph.contains(start) || !graph.contains(end)) {
			return false;
		}
		if (start.equals(end)) {
			return true;
		}
		// Dijkstra's Algorithm:
		Comparator<List<Edge<String, Double>>> cmp = buildComparator();
		Set<Node<String>> finished = new HashSet<Node<String>>();
		PriorityQueue<List<Edge<String, Double>>> active = new PriorityQueue<List<Edge<String, Double>>>(
				INITIAL_CAPACITY, cmp);
		active.add(new ArrayList<Edge<String, Double>>());
		while (!active.isEmpty()) {
			List<Edge<String, Double>> temp = active.remove();
			Node<String> currentEnd;
			if (temp.size() != 0) {
				Edge<String, Double> currentEdge = temp.get(temp.size() - 1);
				currentEnd = currentEdge.getEnd();
			} else {
				currentEnd = start;
			}
			if (currentEnd.equals(end)) {
				for (int i = 0; i < temp.size(); i++) {
					path.add(temp.get(i));
				}
				return true;
			}
			if (finished.contains(currentEnd)) {
				continue;
			}
			List<Edge<String, Double>> newEnds = graph.listEnds(currentEnd);
			for (int i = 0; i < newEnds.size(); i++) {
				if (!finished.contains(newEnds.get(i).getEnd())) {
					List<Edge<String, Double>> paths = new ArrayList<Edge<String, Double>>(temp);
					paths.add(newEnds.get(i));
					active.add(paths);
				}
			}
			finished.add(currentEnd);
		}
		return false;
	}
	
	/**
	 * This private function returns a comparator used to compare weights of two paths (List of edges).
	 * The smaller one has the smaller sum of all the weight of edges.
	 * @return the comparator used to compare weights of two paths.
	 */
	private Comparator<List<Edge<String, Double>>> buildComparator() {
		Comparator<List<Edge<String, Double>>> cmp = new Comparator<List<Edge<String, Double>>>() {
			@Override
			public int compare(List<Edge<String, Double>> o1,
					List<Edge<String, Double>> o2) {
				double path1 = 0.0;
				double path2 = 0.0;
				for (int i = 0; i < o1.size(); i++) {
					path1 = path1 + o1.get(i).getLabel();
				}
				for (int i = 0; i < o2.size(); i++) {
					path2 = path2 + o2.get(i).getLabel();
				}
				if (path1 - path2 < 0) {
					return -1;
				} else if (path1 - path2 == 0) {
					return 0;
				} else {
					return 1;
				}
			}
			
		}; 
		return cmp;
	}
}
