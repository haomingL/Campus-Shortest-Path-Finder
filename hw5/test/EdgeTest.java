/**
 * 
 */
package hw5.test;

import static org.junit.Assert.*;
import hw5.Edge;
import hw5.Node;

import org.junit.Test;

/**
 * @author Haoming Liu
 * This class contains a set of test cases that can be used to test the
 * implementation of the Edge<String, String> class.
 */
public class EdgeTest {
	private Edge<String, String> single_char_Edge1 = new Edge<String, String>(new Node<String>("a"), new Node<String>("b"),"ab");
	private Edge<String, String> single_char_Edge2 = new Edge<String, String>(new Node<String>("a"), new Node<String>("b"), "ab");
	private Edge<String, String> multi_chars_Edge = new Edge<String, String>(new Node<String>("abc"), new Node<String>("def"),"cd");
	
	/**
	 * All the tests below test each single function simply and expected exception.
	 */
	
	@Test
	public void testConstructor() {
		new Edge<String, String>(new Node<String>("a"), new Node<String>("b"),"ab");
		new Edge<String, String>(new Node<String>("abc"), new Node<String>("def"),"cd");
	}
	
	@Test
	public void testEquals() {
		Edge<String, String> o1 = new Edge<String, String>(new Node<String>("a"), new Node<String>("b"),"ab");
		Edge<String, String> o2 = new Edge<String, String>(new Node<String>("abc"), new Node<String>("def"),"cd");
		assertTrue((single_char_Edge1.equals(o1)));
		assertTrue(multi_chars_Edge.equals(o2));
		assertTrue(single_char_Edge2.equals(single_char_Edge2));
	}
	
	@Test
	public void testGetStart() {
		assertEquals(new Node<String>("a"), single_char_Edge1.getStart());
		assertEquals(new Node<String>("abc"), multi_chars_Edge.getStart());
	}

	@Test
	public void testGetEnd() {
		assertEquals(new Node<String>("b"), single_char_Edge1.getEnd());
		assertEquals(new Node<String>("def"), multi_chars_Edge.getEnd());
	}
	
	@Test
	public void testGetLabel() {
		assertEquals("ab", single_char_Edge1.getLabel());
		assertEquals("cd", multi_chars_Edge.getLabel());
	}
	
	@Test
	public void testHashCode() {
		assertEquals(single_char_Edge1.hashCode(), single_char_Edge2.hashCode());
		assertNotEquals(single_char_Edge2.hashCode(), multi_chars_Edge.hashCode());
	}
}
