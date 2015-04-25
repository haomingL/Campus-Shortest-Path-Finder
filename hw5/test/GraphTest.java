/**
 * 
 */
package hw5.test;

import static org.junit.Assert.*;   

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hw5.Edge;
import hw5.Graph;
import hw5.Node;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Haoming Liu
 *
 */
public class GraphTest {
	
	private Graph<String, String> empty_graph;
	private Graph<String, String> single_node_graph;
	private Graph<String, String> single_edge_graph;
	private Graph<String, String> multi_nodes_multi_edges_graph;
	private Node<String> n1;
	private Node<String> n2;
	private Node<String> n3;
	private Node<String> n4;
	private Edge<String, String> e1;
	private Edge<String, String> e2;
	private Edge<String, String> e3;
	private Edge<String, String> e4;
	private Edge<String, String> e5;
	private Edge<String, String> e6;
	

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		n1 = new Node<String>("a");
		n2 = new Node<String>("b");
		n3 = new Node<String>("c");
		n4 = new Node<String>("d");
		e1 = new Edge<String, String>(n1, n2, "ab");
		e2 = new Edge<String, String>(n1, n3, "ac");
		e3 = new Edge<String, String>(n1, n4, "ad");
		e4 = new Edge<String, String>(n2, n3, "bc");
		e5 = new Edge<String, String>(n2, n4, "bd");
		e6 = new Edge<String, String>(n3, n4, "cd");
		empty_graph = new Graph<String, String>();
		single_node_graph = new Graph<String, String>();
		single_node_graph.addNode(n1);
		single_edge_graph = new Graph<String, String>();
		single_edge_graph.addEdge(e1);
		multi_nodes_multi_edges_graph = new Graph<String, String>();
		multi_nodes_multi_edges_graph.addEdge(e1);
		multi_nodes_multi_edges_graph.addEdge(e2);
		multi_nodes_multi_edges_graph.addEdge(e3);
		multi_nodes_multi_edges_graph.addEdge(e4);
		multi_nodes_multi_edges_graph.addEdge(e5);
	}

	@Test
	public void testConstructor() {
		new Graph<String, String>();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddEdge() {
		empty_graph.addEdge(e1);
		single_node_graph.addEdge(e2);
		multi_nodes_multi_edges_graph.addEdge(e6);
		assertTrue(single_node_graph.contains(e2));
		assertTrue(empty_graph.contains(e1));
		assertTrue(multi_nodes_multi_edges_graph.contains(e6));
		single_node_graph.addEdge(n2, e1);
		empty_graph.removeEdge(e1);
		single_node_graph.removeEdge(e2);
		multi_nodes_multi_edges_graph.removeEdge(e6);
		empty_graph.addEdge(n1,e1);
		single_node_graph.addEdge(n1, e2);
		multi_nodes_multi_edges_graph.addEdge(n3, e6);
		assertTrue(single_node_graph.contains(e2));
		assertTrue(empty_graph.contains(e1));
		assertTrue(multi_nodes_multi_edges_graph.contains(e6));
	}
	
	@Test
	public void testAdd() {
		empty_graph.addNode(n1);
		assertFalse(empty_graph.addNode(n1));
		empty_graph.addNode(n2);
		assertFalse(empty_graph.addNode(n2));
		assertTrue(empty_graph.contains(n1));
		assertTrue(empty_graph.contains(n2));
		empty_graph.removeNode(n1);
		empty_graph.removeNode(n2);
		empty_graph.addNode("a");
		empty_graph.addNode("b");
		assertTrue(empty_graph.contains(n1));
		assertTrue(empty_graph.contains(n2));
	}
	
	@Test
	public void testRemove() {
		single_node_graph.removeNode(n1);
		assertFalse(single_node_graph.contains(n1));
		multi_nodes_multi_edges_graph.removeNode(n1);
		assertFalse(multi_nodes_multi_edges_graph.contains(e1));
		assertFalse(multi_nodes_multi_edges_graph.contains(e2));
		assertFalse(multi_nodes_multi_edges_graph.contains(e3));
		assertFalse(multi_nodes_multi_edges_graph.contains(n1));
		multi_nodes_multi_edges_graph.removeEdge(e4);
		assertFalse(multi_nodes_multi_edges_graph.contains(e4));
	}
	
	@Test
	public void testContains() {
		empty_graph.addNode(n1);
		assertTrue(empty_graph.contains(n1));
		assertTrue(single_node_graph.contains(n1));
		assertTrue(single_edge_graph.contains(n1));
		assertTrue(multi_nodes_multi_edges_graph.contains(n2));
		assertTrue(single_edge_graph.contains(e1));
		assertTrue(multi_nodes_multi_edges_graph.contains(e4));
	}
	
	@Test
	public void testListNodes() {
		assertEquals(empty_graph.listNodes(), new ArrayList<Node<String>>());
		List<Node<String>> temp = multi_nodes_multi_edges_graph.listNodes();
		Iterator<Node<String>> itr = temp.iterator();
		while (itr.hasNext()) {
			assertTrue(multi_nodes_multi_edges_graph.contains(itr.next()));
		}
	}
	
	@Test 
	public void testListEnds() {
		List<Node<String>> temp = new ArrayList<Node<String>>();
		temp.add(n2);
		temp.add(n3);
		temp.add(n4);
		List<Edge<String, String>> edges = multi_nodes_multi_edges_graph.listEnds(n1);
		List<Node<String>> ends = new ArrayList<Node<String>>();
		for (Edge<String, String> e : edges) {
			ends.add(e.getEnd());
		}
		assertEquals(temp, ends);
		assertTrue(single_node_graph.listEnds(n2).size() == 0);
	}
	
	@Test
	public void testToString() {
		multi_nodes_multi_edges_graph.toString();
		assertEquals("", empty_graph.toString());
	}
}
