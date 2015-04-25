/**
 * 
 */
package hw6; 
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import hw5.*;
import hw6.MarvelParser.MalformedDataException;

/**
 * @author Haoming-Liu
 * This class use the input the user gives to build a graph and find the shortest path between two nodes.
 * The input file format must be two strings separated by exactly one tab.
 */
public class MarvelPaths {
	
	/**
	 * This main method gets input from the user, build the graph and find the shortest path between
	 * two nodes the user gives.
	 * 
	 * @throws MalformedDataException when the file given is not in the right format. 
	 */
	
	// Abstract Function:
	// This class represents a method that can be used to find the shortest path.
	//
	// Representation Invariant:
	// 
	public static void main(String[] args) {
		try {
			MarvelPaths paths = new MarvelPaths();
			@SuppressWarnings("resource")
			Scanner console = new Scanner(System.in);
			System.out.println("The graph of the file you want:");
			String filePath = "src/hw6/data/" + console.nextLine();
			String fileName = filePath;
		    Graph<String, String> graph = paths.buildPaths(fileName);
			System.out.println("Graph is built");
			System.out.println("Start Node:");
			String s = console.nextLine();
			Node<String> start = new Node<String>(s);
			System.out.println("End Node:");
			s = console.nextLine();
			Node<String> end = new Node<String>(s);
			List<Edge<String, String>> path = new LinkedList<Edge<String, String>>();
			boolean find = paths.findPaths(start, end, graph, path);
			if (find) {
				printPath(path);
			} else {
				System.out.println("not found");
			}
		} catch (MalformedDataException e) {
			System.out.println("Given file is not well formed");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Unexpected exception" + e.toString());
			e.printStackTrace();
		}
	}
	
	/**
	 * This function builds a graph with the given file from the user.
	 * @param fileName is the file that is used to construct the graph
	 * @return the constructed graph with the given file
	 * @throws MalformedDataException if the file is not in the right format.
	 */
	public Graph<String, String> buildPaths(String fileName) throws MalformedDataException{
		Set<Node<String>> characters = new HashSet<Node<String>>();
		Map<Node<String>, List<Node<String>>> books = new HashMap<Node<String>, List<Node<String>>>();
		MarvelParser.parseData(fileName, characters, books);
		Graph<String, String> comics = new Graph<String, String>();
		for (Node<String> label : books.keySet()) {
			List<Node<String>> listOfChars = books.get(label);
			for (int i = 0; i < listOfChars.size(); i++) {
				Node<String> n1 = listOfChars.get(i);
				for (int j = 0; j < listOfChars.size(); j++) {
					if (i != j) {
						Node<String> n2 = listOfChars.get(j);
						Edge<String, String> e = new Edge<String, String>(n1, n2, label.toString());
						comics.addEdge(e);
					}
				}
			}
		}
		return comics;
	}
	
	/**
	 * This method finds the shortest path that starts with the given start node
	 * and ends with the given end node.
	 * @param start is the given start node to find the path.
	 * @param end is the given end node to find the path
	 * @param graph is the graph within which to find the path
	 * @param path is the given list to store the info of the shortest path
	 * @return true if the shortest path is found, false otherwise.
	 */
	public boolean findPaths(Node<String> start, Node<String> end, Graph<String, String> graph, 
			List<Edge<String, String>> path) {
		boolean find = false;
		if (!graph.contains(start) || !graph.contains(end)) {
			return false;
		}
		if (start.equals(end)) {
			return true;
		}
		Queue<Node<String>> q = new LinkedList<Node<String>>();
		q.add(start);
		Map<Node<String>, Edge<String, String>> allPaths = 
				new HashMap<Node<String>, Edge<String, String>>();
		while (!q.isEmpty() && !find) {
			Node<String> temp = q.remove();
			List<Edge<String, String>> edges = graph.listEnds(temp);
			for (Edge<String, String> e : edges) {
				Node<String> n = e.getEnd();
				if (!allPaths.containsKey(n) && !find) {
					q.add(n);
					allPaths.put(n, e);
					if (n.equals(end)) {
						find = true;
					}
				}
			}
		}
		if (find) {
			Node<String> temp = end;
			Edge<String, String> e = allPaths.get(temp);
			while (!e.getStart().equals(start)) {
				temp = e.getStart();
				path.add(0, e);
				e = allPaths.get(temp);
			}
			path.add(0, e);
		}
		return find;
	}
	
	/**
	 * This method prints out the path in a proper way.
	 * @param path is the given path to be printed.
	 */
	public static void printPath(List<Edge<String, String>> path) {
		for (int i = 0; i < path.size(); i++) {
			System.out.println(path.get(i));
		}
	}
}
