/**
 * 
 */
package hw9;

import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import hw5.Edge;
import hw5.Node;
import hw8.Building;
import hw8.CampusPathsModel;

import javax.swing.JButton;


/**
 * @author Haoming Liu
 * This class controls the model and the view to interact with each other.
 */
public class CampusPathsMain {
	public static void main(String[] args) {
		JButton find = new JButton("Find"); // constructs the find button
		JButton reset = new JButton("Reset"); // constructs the reset button
		ButtonActionListener listener = new ButtonActionListener();
		find.addActionListener(listener);
		reset.addActionListener(listener); // add the action listener to the button
		CampusPathsGUI.getGuiInstance(find, reset); // set up the frame
	}
	
	/**
	 * 
	 * @author Haoming Liu
	 * This class implements the ActionListener and customizes the listener to 
	 * the find and reset button.
	 */
	public static class ButtonActionListener implements ActionListener {
		/**
		 * This function selects the function to be performed.
		 */
		public void actionPerformed(ActionEvent e) {
	      if (e.getActionCommand().equals("Find")) {
	    	  findPath();
	      } else if (e.getActionCommand().equals("Reset")) {
	    	  reset();
	      } 
		}
	}

	/**
	 * This function gets the start and end building as well as the path between them from the model.
	 * It also passes the information to the view to show up to the user.
	 */
	public static void findPath() {
		String startBuilding = CampusPathsGUI.getStartBuilding();
		String endBuilding = CampusPathsGUI.getEndBuilding();
		Node<Building> start = CampusPathsModel.findBuilding(startBuilding);
		Node<Building> end = CampusPathsModel.findBuilding(endBuilding);
		
		List<Edge<Building, Double>> path = new ArrayList<Edge<Building, Double>>();
		CampusPathsModel.findPath(start, end, path);
		
		CampusPathsGUI.drawPath(path, start, end);	
	}
	
	/**
	 * This function notifies the view to reset the window.
	 */
	public static void reset() {
		CampusPathsGUI.reset();
	}
}
