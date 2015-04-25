package hw8.test;

import static org.junit.Assert.*;  
import hw5.Edge;
import hw5.Graph;
import hw5.Node;
import hw8.Building;
import hw8.CampusPathsModel;
import hw8.CampusPathsModel.MalformedDataException;
import hw8.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class TestCampusPathsModel {
	
	public static final String CAMPUS_BUILDINGS = "src/hw8/data/campus_buildings.dat";
	// The data file path
	
	public static final int EXPECTED_PATH_SIZE = 25; // expected size of the path
	public static final int EXPECTED_EMPTY_PATH_SIZE = 0; // expected size of empty path
	
	public static final int EXPECTED_SIZE_OF_BUILDINGS = 51; // expected num of buildings
	
	public static final int CSE_INDEX = 3; // index of cse building
	public static final int PAB_INDEX = 26; // index of pab building

	Map<Position, Building> result;
	List<Building> listOfBuildings;
	Graph<Building, Double> graph;
	
	@Before
	public void setUp() throws Exception {
		result = new HashMap<Position, Building>();
		listOfBuildings = new ArrayList<Building>();
	}
	
	@Test
	public void testFindPath() throws MalformedDataException {
		CampusPathsModel.parseBuildings("src/hw8/data/campus_buildings.dat", listOfBuildings, result);
		graph = CampusPathsModel.buildGraph();
		List<Edge<Building, Double>> path = new ArrayList<Edge<Building,Double>>();
		Node<Building> start = new Node<Building>(listOfBuildings.get(CSE_INDEX));
		Node<Building> end = new Node<Building>(listOfBuildings.get(PAB_INDEX));
		CampusPathsModel.findPath(start, end, path);
		assertEquals(EXPECTED_PATH_SIZE, path.size());
		path.clear();
		CampusPathsModel.findPath(start, start, path);
		assertEquals(EXPECTED_EMPTY_PATH_SIZE, path.size());
	}

	@Test
	public void testParseBuildings() {
		try {
			CampusPathsModel.parseBuildings(CAMPUS_BUILDINGS, listOfBuildings, result);
		} catch (MalformedDataException e) {
			System.err.println(e.toString());
			e.printStackTrace();
			fail("Should be well formed data");
		}
		for (int i = 0; i < listOfBuildings.size(); i++) {
			Building b = listOfBuildings.get(i);
			assertTrue(result.keySet().contains(new Position(b.getXCor(), b.getYCor())));
		}
		assertEquals("BAG", listOfBuildings.get(0).getShortName());
		assertEquals("Bagley Hall (East Entrance)", listOfBuildings.get(0).getLongName());
		assertEquals(new Position(1914.5103, 1709.8816), new Position(listOfBuildings.get(0).getXCor(), 
				listOfBuildings.get(0).getYCor()));
		assertEquals("CMU", listOfBuildings.get(listOfBuildings.size() - 1).getShortName());
		assertEquals("Communications Building", listOfBuildings.get(listOfBuildings.size() - 1).getLongName());
		assertEquals(new Position(2344.8512, 1114.6251), new Position(listOfBuildings.get(listOfBuildings.size() - 1).getXCor(), 
				listOfBuildings.get(listOfBuildings.size() - 1).getYCor()));
	}

	@Test
	public void testBuildGraph() throws MalformedDataException {
		graph = CampusPathsModel.buildGraph();
		for (int i = 0; i < listOfBuildings.size(); i++) {
			Building b = listOfBuildings.get(i);
			assertTrue(graph.contains(new Node<Building>(b)));
		}
	}
	
	@Test
	public void testBuildings() {
		List<Node<Building>> list = CampusPathsModel.buildings();
		for (int i = 1; i < list.size(); i++) {
			assertTrue(list.get(i).compareTo(list.get(i - 1)) > 0);
		}
		assertEquals(EXPECTED_SIZE_OF_BUILDINGS, list.size());
	}
	
	@Test
	public void testFindBuilding() {
		assertNotNull(CampusPathsModel.findBuilding("CSE"));
		assertNotNull(CampusPathsModel.findBuilding("PAB"));
		assertNull(CampusPathsModel.findBuilding("ABC"));
		assertNull(CampusPathsModel.findBuilding(""));
	}

}
