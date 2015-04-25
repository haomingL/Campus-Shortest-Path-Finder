package hw8.test;

import static org.junit.Assert.*;
import hw8.Building;

import org.junit.Before;
import org.junit.Test;

public class TestBuilding {
	public static final double DELTA = 0.00001;
	// the delta value between two double numbers to avoid round off error
	
	public Building empty_building_1;
	public Building empty_building_2;
	public Building complete_building_1;
	public Building complete_building_2;
	public Building complete_building_3;
	
	@Before
	public void setUp() throws Exception {
		empty_building_1 = new Building("", "", 0.0, 0.0);
		empty_building_2 = new Building("", "", 1.1, 1.1);
		complete_building_1 =  new Building("CSE", "CSE Building", 3.3, 3.3);
		complete_building_2 = new Building("EEB", "EEB Building", 4.4, 4.4);
		complete_building_3 = new Building("ABC", "ABC", 1.1, 1.1);
	}

	@Test
	public void testHashCode() {
		assertEquals(empty_building_2.hashCode(), complete_building_3.hashCode());
		assertNotEquals(complete_building_1.hashCode(), complete_building_2.hashCode());
		assertNotEquals(empty_building_1.hashCode(), empty_building_2.hashCode());
	}

	@Test(expected = java.lang.AssertionError.class)
	public void testBuilding() {
		new Building("", "", 1.3, 4.5);
		new Building("a", "aa", 0.0, -0.9);
		new Building(null, null, 5565.5, 1234.6);
	}

	@Test
	public void testCompareTo() {
		assertEquals(0, empty_building_1.compareTo(empty_building_2));
		assertNotEquals(0, empty_building_2.compareTo(complete_building_3));
		assertNotEquals(0, complete_building_1.compareTo(complete_building_2));
		assertTrue(complete_building_1.compareTo(complete_building_2) < 0);
		assertTrue(complete_building_2.compareTo(complete_building_3) > 0);
	}

	@Test
	public void testToString() {
		assertEquals(": ", empty_building_1.toString());
		assertEquals("EEB: EEB Building", complete_building_2.toString());
	}

	@Test
	public void testEqualsObject() {
		assertTrue(empty_building_2.equals(complete_building_3));
		assertTrue(complete_building_3.equals(empty_building_2));
		assertFalse(empty_building_1.equals(empty_building_2));
	}

	@Test
	public void testGetShortName() {
		assertEquals("", empty_building_1.getShortName());
		assertEquals("CSE", complete_building_1.getShortName());
		assertEquals("EEB", complete_building_2.getShortName());
		assertEquals("ABC", complete_building_3.getShortName());
	}

	@Test
	public void testGetLongName() {
		assertEquals("", empty_building_1.getLongName());
		assertEquals("CSE Building", complete_building_1.getLongName());
		assertEquals("EEB Building", complete_building_2.getLongName());
		assertEquals("ABC", complete_building_3.getLongName());
	}

	@Test
	public void testGetXCor() {
		assertEquals(0.0, empty_building_1.getXCor(), DELTA);
		assertEquals(1.1, empty_building_2.getXCor(), DELTA);
		assertEquals(3.3, complete_building_1.getXCor(), DELTA);
		assertEquals(4.4, complete_building_2.getXCor(), DELTA);
		assertEquals(1.1, complete_building_3.getXCor(), DELTA);
	}

	@Test
	public void testGetYCor() {
		assertEquals(0.0, empty_building_1.getYCor(), DELTA);
		assertEquals(1.1, empty_building_2.getYCor(), DELTA);
		assertEquals(3.3, complete_building_1.getYCor(), DELTA);
		assertEquals(4.4, complete_building_2.getYCor(), DELTA);
		assertEquals(1.1, complete_building_3.getYCor(), DELTA);
	}

}
