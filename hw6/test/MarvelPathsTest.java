package hw6.test;

import static org.junit.Assert.*; 

import java.util.LinkedList;
import java.util.List;

import hw5.Edge;
import hw5.Graph;
import hw5.Node;
import hw6.MarvelParser.MalformedDataException;
import hw6.MarvelPaths;

import org.junit.Test;

/**
 * 
 * @author Haoming-Liu
 * This class of tests test the MarvelPaths class.
 */
public class MarvelPathsTest {
	MarvelPaths m = new MarvelPaths();

	@Test
	public void testBuildPaths() throws MalformedDataException {
		Graph<String, String> largeGraph = m.buildPaths("src/hw6/data/marvel.tsv");
		Graph<String, String> smallGraph = m.buildPaths("src/hw6/data/people.tsv");
		largeGraph.contains(new Node<String>(""));
		smallGraph.contains(new Node<String>("Haoming-Liu"));
		smallGraph.contains(new Edge<String, String>(new Node<String>("Haoming-Liu"), 
				new Node<String>("Xinyi-Xu"), "CSE331"));
		largeGraph.contains(new Node<String>("HUMAN ROBOT"));
		largeGraph.contains(new Node<String>("CAPTAIN AMERICA"));
		largeGraph.contains(new Edge<String, String>(new Node<String>("VENUS II"), 
				new Node<String>("HAWK"), "AVF 4"));
		largeGraph.contains(new Edge<String, String>(new Node<String>("RETREAD"), 
				new Node<String>("RAZORBACK/"), "S-H2 46"));
	}

	@Test
	public void testFindPaths() throws MalformedDataException {
		Graph<String, String> largeGraph = m.buildPaths("src/hw6/data/marvel.tsv");
		Graph<String, String> smallGraph = m.buildPaths("src/hw6/data/people.tsv");
		List<Edge<String, String>> result = new LinkedList<Edge<String, String>>();
		List<Edge<String, String>> path = new LinkedList<Edge<String, String>>();
		result.add(new Edge<String, String>(new Node<String>("HUMAN ROBOT"), 
				new Node<String>("CAPTAIN AMERICA"), "AVF 4"));
		m.findPaths(new Node<String>("HUMAN ROBOT"), new Node<String>("CAPTAIN AMERICA"), largeGraph, path);
		assertEquals(result, path);
		result.clear();
		result.add(new Edge<String, String>(new Node<String>("CAPTAIN AMERICA"), new Node<String>("HAWK"), 
				"A '00"));
		result.add(new Edge<String, String>(new Node<String>("HAWK"), new Node<String>("OLD SKULL"), 
				"AA2 21"));
		path.clear();
		m.findPaths(new Node<String>("CAPTAIN AMERICA"), new Node<String>("OLD SKULL"), largeGraph, path);
		assertEquals(result, path);
		result.clear();
		path.clear();
		result.add(new Edge<String, String>(new Node<String>("Haoming-Liu"), new Node<String>("Xinyi-Xu")
				, "CSE331"));
		m.findPaths(new Node<String>("Haoming-Liu"), new Node<String>("Xinyi-Xu"), smallGraph, path);
		assertEquals(result, path);
	}

}
