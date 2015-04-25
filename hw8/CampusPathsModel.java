/**
 * 
 */
package hw8;

import hw5.Edge;
import hw5.Graph;
import hw5.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * @author Haoming Liu
 * This class serves as a model of the campus paths. It is a singleton.
 */
public class CampusPathsModel {
	
	private static final Map<Position, Building> buildings = new HashMap<Position, Building>();
	// buildings maps the position to the building to help parse the data in Campus_paths.dat
	
	private static final List<Building> listOfBuildingNames = new ArrayList<Building>();
	// listOfBuildingNames stores all the building names.
	
	private static final Graph<Building, Double> graph = buildGraph();
	// graph stores all the paths and related data of the campus path.
	
	public static final String CAMPUS_BUILDINGS = "src/hw8/data/campus_buildings.dat";
	// The data file name of the campus buildings
	
	public static final String CAMPUS_PATHS = "src/hw8/data/campus_paths.dat";
	// The data file name of the campus path.
	
	
	public static final int INITIAL_CAPACITY = 20; // This is the initial size of the queue.
	
	/**
	 * The function is used to find the shortest path between two buildings.
	 * @param start is the start node of the building
	 * @param end is the end node of the building
	 * @param path is a list of edges / small paths 
	 * @return true if the shoretest path is found, false otherwise.
	 */
	public static boolean findPath(Node<Building> start, Node<Building> end, List<Edge<Building, Double>> path) {
		if (start.equals(end)) {
			return true;
		}
		// Dijkstra's Algorithm to find the shortest path:
		Comparator<List<Edge<Building, Double>>> cmp = buildComparator();
		// This builds the comparator that is used in the priority queue.
		Set<Node<Building>> finished = new HashSet<Node<Building>>();
		// This set stores the finished node in the set.
		PriorityQueue<List<Edge<Building, Double>>> active = new PriorityQueue<List<Edge<Building, Double>>>(
				INITIAL_CAPACITY, cmp);
		// This priority queue stores the path to different nodes. The first node that will pop out is the 
		// path with shortest weight.
		active.add(new ArrayList<Edge<Building, Double>>());
		while (!active.isEmpty()) {
			List<Edge<Building, Double>> temp = active.remove();
			Node<Building> currentEnd;
			if (temp.size() != 0) {
				Edge<Building, Double> currentEdge = temp.get(temp.size() - 1);
				currentEnd = currentEdge.getEnd();
			} else {
				currentEnd = start;
			}
			// If the temp size is zero, it means that the current end is start.
			if (currentEnd.equals(end)) {
				for (int i = 0; i < temp.size(); i++) {
					path.add(temp.get(i));
				}
				return true;
			}
			if (finished.contains(currentEnd)) {
				continue;
			}
			List<Edge<Building, Double>> newEnds = graph.listEnds(currentEnd);
			for (int i = 0; i < newEnds.size(); i++) {
				if (!finished.contains(newEnds.get(i).getEnd())) {
					List<Edge<Building, Double>> paths = new ArrayList<Edge<Building, Double>>(temp);
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
	private static Comparator<List<Edge<Building, Double>>> buildComparator() {
		Comparator<List<Edge<Building, Double>>> cmp = new Comparator<List<Edge<Building, Double>>>() {
			@Override
			public int compare(List<Edge<Building, Double>> o1,
					List<Edge<Building, Double>> o2) {
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
	
	/**
	 * This function parses the building from the given file and construct a list of buildings
	 * @param filename is the String of the file name that the user gives to parse
	 * @param listOfBuildings is the result of the list of buildings
	 * @param result is the map which maps the position to the corresponding buildings.
	 * @throws MalformedDataException if the file is not formated in the right way.
	 */
	public static void parseBuildings(String filename, List<Building> listOfBuildings, 
    		Map<Position, Building> result) throws MalformedDataException {
	    BufferedReader reader = null;
	    try {
	        reader = new BufferedReader(new FileReader(filename));

	        // Construct the collections
	        String inputLine;
	        while ((inputLine = reader.readLine()) != null) {
	            String[] tokens = inputLine.split("\t");
	            if (tokens.length < 4) {
	            	throw new MalformedDataException();
	            }
	            String shortName = tokens[0];
	            String longName = tokens[1];
	            
	            double xCor = Double.parseDouble(tokens[2]);
	            double yCor = Double.parseDouble(tokens[3]);
	            Building newBuilding = new Building(shortName, longName, xCor, yCor);
	            listOfBuildings.add(newBuilding);
	            result.put(new Position(xCor, yCor), newBuilding);
	            // Add the parsed data to the Buildings collections.
	        }
	    } catch (MalformedDataException e) {
	    	System.err.println(e.toString());
	    	e.printStackTrace(System.err);
	    } catch (IOException e) {
	        System.err.println(e.toString());
	        e.printStackTrace(System.err);
	    } finally {
	        if (reader != null) {
	            try {
	                reader.close();
	            } catch (IOException e) {
	                System.err.println(e.toString());
	                e.printStackTrace(System.err);
	            }
	        }
	    }
    }
	
	/**
	 * This function builds the graph from the given file.
	 * @return the graph of the building as nodes and double numbers as weight
	 * @throws MalformedDataException if the given file is not organized in the correct format.
	 */
	public static Graph<Building, Double> buildGraph() {
		if (graph != null) {
			return graph;
		}
    	BufferedReader reader = null;
		Graph<Building, Double> result = new Graph<Building, Double>();
		try {
			CampusPathsModel.parseBuildings(CAMPUS_BUILDINGS, listOfBuildingNames, buildings);
	        reader = new BufferedReader(new FileReader(CAMPUS_PATHS));

	        // Construct the collections
	        String inputLine = reader.readLine();
	        while (inputLine != null) {
	            String[] tokens = inputLine.split(",");
	            if (tokens.length < 2) {
	            	throw new MalformedDataException();
	            } // if the file is not formated in the right way
	            Position start = new Position(Double.parseDouble(tokens[0]), Double.parseDouble(tokens[1]));
	            Node<Building> startBuilding;
	            if (buildings.get(start) != null) {
	            	startBuilding = new Node<Building>(buildings.get(start));
	            } else {
	            	startBuilding = new Node<Building>(new Building("", "", start.getXCor(), start.getYCor()));
	            } // if there is not a building at that position, construct a building with empty string as
	            // short name and long name.
	            inputLine = reader.readLine();
	            tokens = inputLine.split("[\t: ,]+");
	            boolean finished = false;
	            while (!finished && tokens.length == 4) {
	            	Position end = new Position(Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2]));
	            	Node<Building> endBuilding;
	            	if (buildings.get(end) != null) {
	            		endBuilding = new Node<Building>(buildings.get(end));
	            	} else {
	            		endBuilding = new Node<Building>(new Building("", "", end.getXCor(), end.getYCor()));
	            	}
	            	Edge<Building, Double> path = new Edge<Building, Double>(startBuilding, endBuilding, 
	            			Double.parseDouble(tokens[3]));
	            	result.addEdge(path);
	            	inputLine = reader.readLine();
	            	if (inputLine != null) {
	            		tokens = inputLine.split("[\t: ,]+");
	            	} else {
	            		finished = true;
	            	}
 	            }
	            // Add the parsed data to the Buildings collections.
	        }
	    } catch (MalformedDataException e) {
			System.err.println(e.toString());
			e.printStackTrace();
		} catch (IOException e) {
	        System.err.println(e.toString());
	        e.printStackTrace(System.err);
	    } finally {
	        if (reader != null) {
	            try {
	                reader.close();
	            } catch (IOException e) {
	                System.err.println(e.toString());
	                e.printStackTrace(System.err);
	            }
	        }
	    }
    	return result;  	
    }
	
	/**
	 * This function helps to find whether the building is in the list of buildings.
	 * @param building is the name of the building to be found in the list
	 * @return null if the building is not found in the model, otherwise return 
	 * 			the node of that building.
	 */
	public static Node<Building> findBuilding(String building) {
		Node<Building> buildingNode = null;
		for (int i = 0; i < listOfBuildingNames.size(); i++) {
			String shortName = listOfBuildingNames.get(i).getShortName();
			if (shortName.equals(building)) {
				buildingNode = new Node<Building>(listOfBuildingNames.get(i));
			} 
		}
		return buildingNode;
	}
	
	/**
     * A checked exception class for bad data files
     */
    @SuppressWarnings("serial")
    public static class MalformedDataException extends Exception {
        public MalformedDataException() { }

        public MalformedDataException(String message) {
            super(message);
        }

        public MalformedDataException(Throwable cause) {
            super(cause);
        }

        public MalformedDataException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    
    /**
     * This function return a list of building in sorted order.
     * @return a list of node of building in sorted order to be used.
     */
	public static List<Node<Building>> buildings() {
		List<Node<Building>> result = new ArrayList<Node<Building>>();
		for (int i = 0; i < listOfBuildingNames.size(); i++) {
			result.add(new Node<Building>(listOfBuildingNames.get(i)));
		}
		Collections.sort(result);
		return result;
	}
}
