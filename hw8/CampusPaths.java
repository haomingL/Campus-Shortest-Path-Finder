/**
 * 
 */
package hw8;

import hw5.Edge;    
import hw5.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Haoming Liu
 * This class facilitates the user to give queries, executes them and gives the info.
 */
public class CampusPaths {
	
	/**
	 * @param args can be nothing
	 * The main method stores all the data of paths and deals with all the queries the user gives.
	 * This method executes the query.
	 * m: show the menu of queries.
	 * b: show the list of buildings
	 * r: find the route from one building to the other
	 * q: quit
	 * Any queries that starts with # and blank lines are considered as comments and are not 
	 * executed by the system.
	 */
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		printMenu();
		String line = printOption(console);
		while (line.startsWith("#") || line.length() < 1) {
			System.out.println(line);
			line = console.nextLine();
		} // skips the comments and blank lines
		while (line.length() != 1 || !line.startsWith("q")) {
			if (line.equals("r")) { // find the route
				System.out.print("Abbreviated name of starting building: ");
				while (line.startsWith("#") || line.length() < 1) {
					System.out.println(line);
					line = console.nextLine();
				} // skips the comments and blank lines
				String startBuilding = console.nextLine();
				System.out.print("Abbreviated name of ending building: ");
				while (line.startsWith("#") || line.length() < 1) {
					System.out.println(line);
					line = console.nextLine();
				} // skips the comments and blank lines
				String endBuilding = console.nextLine();
				printPath(startBuilding, endBuilding);
			} else if (line.equals("b")) { // list the building
				printBuildings();
			} else if (line.equals("m")) { // show the menu
				printMenu();
			} else {
				System.out.println("Unknown option");
				System.out.println();
			}
			line = printOption(console); // continues the query 
			while (line.startsWith("#") || line.length() < 1) {
				System.out.println(line);
				line = console.nextLine();
			}
		}
		// execute the query 
	}

	/**
	 * This function finds the shortest path between the start building and the end building
	 * and print it out in the given format.
	 * If the start or end building is not in the list of the building, it will print that the 
	 * building is found.
	 * @param startBuilding is the start building the user gives
	 * @param endBuilding is the end building the user gives
	 */
	public static void printPath(String startBuilding,String endBuilding) {
		Node<Building> start = CampusPathsModel.findBuilding(startBuilding);
		Node<Building> end = CampusPathsModel.findBuilding(endBuilding);
		// check if the start and end building is in the list of buildings and if they are valid
		if (start != null && end != null) {
			List<Edge<Building, Double>> path = new ArrayList<Edge<Building, Double>>();
			// gives the data to the model
			if (CampusPathsModel.findPath(start, end, path)) {
				System.out.println("Path from " + start.getData().getLongName() + " to " + 
						end.getData().getLongName() + ":");
				printDirection(path); // finds the direction
			} else {
				System.out.println("No path Found");
			}
			
		}
		if (start == null) {
			System.out.println("Unknown building: " + startBuilding);
		}
		if (end == null) {
			System.out.println("Unknown building: " + endBuilding);
		}
		System.out.println();
	}
	
	/**
	 * This function gives direction of each pair of points in the path and print them out.
	 * @param path is the given path between two buildings.
	 */
	public static void printDirection(List<Edge<Building, Double>> path) {
		double total = 0.0; // calculates the total distance
		for (int i = 0; i < path.size(); i++) {
			Edge<Building, Double> edge = path.get(i);
			Node<Building> start = edge.getStart();
			Node<Building> end = edge.getEnd();
			double distance = edge.getLabel();
			Position startPos = new Position(start.getData().getXCor(), start.getData().getYCor());
			Position endPos = new Position(end.getData().getXCor(), end.getData().getYCor());
			String direction = startPos.findDirection(endPos);
			System.out.println("\tWalk " + String.format("%.0f", distance) + " feet " + direction + 
					" to (" + String.format("%.0f", endPos.getXCor()) + ", " + 
					String.format("%.0f", endPos.getYCor()) + ")");
			// use the String format to print the path in the correct way
			total = total + distance;
		}
		System.out.println("Total distance: " + String.format("%.0f", total) + " feet");
	}
	
	/**
	 *  This function prints the menu.
	 */
	public static void printMenu() {
		System.out.println("Menu:");
		System.out.println("\tr to find a route");
		System.out.println("\tb to see a list of all buildings");
		System.out.println("\tq to quit");
		System.out.println();
	}
	
	/**
	 * This function prints the options the user has and return the option the 
	 * user gives.
	 * @param console is the given Scanner to read the query from the user.
	 * @return the given query from the user
	 */
	public static String printOption(Scanner console) {
		System.out.print("Enter an option ('m' to see the menu): ");
		String line = console.nextLine();
		return line;
	}
	
	/**
	 * The function print all the buildings in the graph.
	 */
	public static void printBuildings() {
		System.out.println("Buildings:");
		List<Node<Building>> list = CampusPathsModel.buildings();
		for (int i = 0; i < list.size(); i++) {
			System.out.println("\t" + list.get(i));
		}
		System.out.println();
	}
}
