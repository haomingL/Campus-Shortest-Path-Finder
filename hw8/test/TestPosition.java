package hw8.test;

import static org.junit.Assert.*;
import hw8.Position;

import org.junit.Before;
import org.junit.Test;

public class TestPosition {
	
	public static final double DELTA = 0.0001;
	// This delta is used to compare two double without round off error
	
	Position p1;
	Position p2;
	Position p3;
	Position p4;
	
	@Before
	public void setUp() throws Exception {
		p1 = new Position(0.0, 0.0); 
		p2 = new Position(1.1, 1.1);
		p3 = new Position(3.3, 3.3);
		p4 = new Position(3.3, 3.3);
	}

	@Test
	public void testHashCode() {
		assertEquals(p3.hashCode(), p4.hashCode());
		assertNotEquals(p1.hashCode(), p2.hashCode());
		assertNotEquals(p2.hashCode(), p3.hashCode());
		assertNotEquals(p1.hashCode(), p3.hashCode());
	}

	@Test
	public void testPosition() {
		new Position(3242.432, 8091.34);
		new Position(1231.231, 1231.89);
	}

	@Test
	public void testGetXCor() {
		assertEquals(0.0, p1.getXCor(), DELTA);
		assertEquals(1.1, p2.getXCor(), DELTA);
		assertEquals(3.3, p3.getXCor(), DELTA);
		assertEquals(3.3, p4.getXCor(), DELTA);
	}

	@Test
	public void testGetYCor() {
		assertEquals(0.0, p1.getYCor(), DELTA);
		assertEquals(1.1, p2.getYCor(), DELTA);
		assertEquals(3.3, p3.getYCor(), DELTA);
		assertEquals(3.3, p4.getYCor(), DELTA);
	}

	@Test
	public void testEqualsObject() {
		assertNotEquals(p1, p2);
		assertEquals(p3, p4);
	}

	@Test
	public void testFindDirection() {
		Position p5 = new Position(1.0, 0.0);
		Position p6 = new Position(0.0, 1.0);
		Position p7 = new Position(3.0, 0.0);
		assertEquals("SE", p1.findDirection(p2));
		assertEquals("NW", p2.findDirection(p1));
		assertEquals("E", p1.findDirection(p5));
		assertEquals("W", p5.findDirection(p1));
		assertEquals("S", p1.findDirection(p6));
		assertEquals("N", p6.findDirection(p1));
		assertEquals("NE", p2.findDirection(p7));
		assertEquals("SW", p7.findDirection(p2));
	}

}
