/**
 * 
 */
package hw7.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import hw5.Edge;
import hw5.Graph;
import hw5.Node;
import hw6.MarvelParser.MalformedDataException;
import hw7.MarvelPaths2;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Haoming-Liu
 *
 */
public class MarvelPaths2Test {
	MarvelPaths2 m;
	Graph<String, Double> graph;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		m = new MarvelPaths2();
	}

	/**
	 * Test method for {@link hw7.MarvelPaths2#buildPaths(java.lang.String)}.
	 * @throws MalformedDataException 
	 */
	@Test
	public void testBuildPaths() throws MalformedDataException {
		graph = m.buildPaths("src/hw7/data/people.tsv");
		Node<String> n1 = new Node<String>("Haoming-Liu");
		Node<String> n2 = new Node<String>("Xinyi-Xu");
		Edge<String, Double> e1 = new Edge<String, Double>(n1, n2, 0.5);
		assertTrue(graph.contains(e1));
	}

	/**
	 * Test method for {@link hw7.MarvelPaths2#findPath(hw5.Graph, hw5.Node, hw5.Node, java.util.List)}.
	 * @throws MalformedDataException 
	 */
	@Test
	public void testFindPath() throws MalformedDataException {
		graph = m.buildPaths("src/hw7/data/people.tsv");
		Node<String> n1 = new Node<String>("Haoming-Liu");
		Node<String> n2 = new Node<String>("Xinyi-Xu");
		Edge<String, Double> e1 = new Edge<String, Double>(n1, n2, 0.5);
		List<Edge<String, Double>> expected = new ArrayList<Edge<String, Double>>();
		List<Edge<String, Double>> actual = new ArrayList<Edge<String, Double>>();
		expected.add(e1);
		assertTrue(m.findPath(graph, n1, n2, actual));
		assertEquals(expected, actual);
	}

}
