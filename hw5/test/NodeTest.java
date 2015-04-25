/**
 * 
 */
package hw5.test;

import static org.junit.Assert.*;
import hw5.Node;

import org.junit.Test;

/**
 * @author Haoming Liu
 * This class contains a set of test cases that can be used to test the
 * implementation of the Node<String> class.
 */
public class NodeTest {
	private Node<String> test_case_single_char1 = new Node<String>("a");
	private Node<String> test_case_single_char2 = new Node<String>("a");
	private Node<String> test_case_empty1 = new Node<String>("");
	private Node<String> test_case_empty2 = new Node<String>("");
	private Node<String> test_case_multi_chars1 = new Node<String>("abcdefghi");
	private Node<String> test_case_multi_chars2 = new Node<String>("abcdefghi");
	
	@Test
	// This test tests the constructor
	public void testConstructor() {
		new Node<String>("a");
		new Node<String>("");
		new Node<String>("abcdefghi");
	}
	
	@Test
	// This function tests the getData function
	public void testGetData() {
		assertEquals(test_case_single_char1.getData(), "a");
		assertEquals(test_case_empty1.getData(), "");
		assertEquals(test_case_multi_chars1.getData(), "abcdefghi");
	}
	
	@Test
	// This test tests the equal function.
	public void testEquals() {
		assertTrue(test_case_single_char1.equals(test_case_single_char2));
		assertTrue(test_case_empty1.equals(test_case_empty2));
		assertTrue(test_case_multi_chars1.equals(test_case_multi_chars2));
	}
	
	@Test
	// This test tests whether the hashcode is generated properly.
	public void testHashCode() {
		assertEquals(test_case_single_char1.hashCode(), test_case_single_char2.hashCode());
		assertEquals(test_case_empty1.hashCode(), test_case_empty2.hashCode());
		assertEquals(test_case_multi_chars1.hashCode(), test_case_multi_chars2.hashCode());
	}
	
	@Test
	// This test tests the compareTo function
	public void testCompareTo() {
		assertTrue(test_case_single_char1.compareTo(test_case_single_char2) == 0);
		assertTrue(test_case_single_char1.compareTo(test_case_multi_chars1) < 0);
	}
}
