/**
 * 
 */
package hw9;

import hw5.Edge; 
import hw5.Node;
import hw8.Building;
import hw8.CampusPathsModel;

import java.awt.BorderLayout; 
import java.awt.Dimension;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * This class implements the GUI that simulates the campus path and can be used
 * to find the path between two buildings. It is like this below:
 * ------------------------------------------------------------------------------
 * 		Start Building [    ]   Destination Building [    ]  |Find|		|Reset|
 * 		-----------------------------------------------------------------------
 *      |																	  | []
 *      |																	  | []
 *      |					Campus Image should be here						  | []
 *      |																	  | []
 *      |			       BAG ------> ------> ------->CSE					  | []
 *      |																	  | []   <--- This is a 
 *      |																	  |           Scroll Bar
 *      |																	  |
 *      |																	  |
 *      |																	  |
 *      -----------------------------------------------------------------------
 *      [][][][][][][][][][][][[][][][][]     <------ This is a Scroll Bar too
 * 
 * @author Haoming Liu
 *
 */
public class CampusPathsGUI {
	private static final int SCREEN_WIDTH = 1080;
	// This is the screen width of the frame 
	
	private static final int SCREEN_HEIGHT = 720;
	// This is the screen height of the frame
		
	private static JFrame frame = null;
	// This is the frame of the GUI
	
	private static JPanel optionPanel; // The option panel that contains labels, buttons and boxes
	private static ImageScrollPane imagePane; // The scroll pane which contains the image panel
	private static ImagePanel imagePanel; // The image panel that contains the image
	
	private static JComboBox<String> startBuildingBox; // The box of start buildings
	private static JComboBox<String> endBuildingBox; // The box of the end buildings
	
	private static JLabel startLabel; // The label that prompts the user of the start building
	private static JLabel endLabel; // The label that prompts the user of the end building
	
	/**
	 * This function return the instance of the frame and set up the model of the frame.
	 * @param find the find button with actions
	 * @param reset the reset button with actions
	 * @return the frame that is already set up
	 */
	public static JFrame getGuiInstance(JButton find, JButton reset) {
		if (frame == null) {
			frame = new JFrame("Campus Paths Finder  --- By Haoming Liu");
			setUp(find, reset);
		}
		return frame;
	}
	
	/**
	 * This function sets up all the models of the frame
	 * @param findButton is the find button that is set up
	 * @param resetButton is the reset button that is set up
	 */
	private static void setUp(JButton findButton, JButton resetButton) {
		
		List<Node<Building>> buildingsNodes = CampusPathsModel.buildings();
		String[] buildings = new String[buildingsNodes.size()];
		for (int i = 0; i < buildings.length; i++) {
			buildings[i] = buildingsNodes.get(i).getData().getShortName();
		}
		
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		
		setPanel();
				
		setBox(buildings);
		
		setLabel();
		
		setOptionalPanel(findButton, resetButton);
		
		setFrame();
	}
	
	/**
	 * This function set up the image panels and option panels
	 */
	private static void setPanel() {
		imagePanel = new ImagePanel(frame);
		optionPanel = new JPanel();
		imagePane = new ImageScrollPane(imagePanel, frame);
		imagePanel.setPreferredSize(imagePanel.getPreferredSize());
		optionPanel.setPreferredSize(new Dimension(frame.getWidth(), 50));
		imagePane.setPreferredSize(imagePane.getPreferredSize());
		imagePane.getVerticalScrollBar().setUnitIncrement(5);
	}
	
	/**
	 * This function sets up the combo box
	 * @param buildings that are shown in the JComboBox
	 */
	private static void setBox(String[] buildings) {
		startBuildingBox = new JComboBox<String>(buildings);
		endBuildingBox = new JComboBox<String>(buildings);
		startBuildingBox.setPreferredSize(new Dimension(100, 50));
		endBuildingBox.setPreferredSize(new Dimension(100, 50));
	}
	
	/**
	 * This function sets up the labels
	 */
	private static void setLabel() {
		startLabel = new JLabel();
		endLabel = new JLabel();
		startLabel.setText("Start Building:");
		endLabel.setText("Destination Building:");
	}
	
	/**
	 * This function sets up the frame and layout the component
	 */
	private static void setFrame() {
		frame.add(optionPanel, BorderLayout.NORTH);
		frame.add(imagePane, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * This function adds all the components to the option panel
	 * @param find the find button that is already set up
	 * @param reset the reset button that is already set up
	 */
	private static void setOptionalPanel(JButton find, JButton reset) {
		optionPanel.add(startLabel, BorderLayout.CENTER);
		optionPanel.add(startBuildingBox, BorderLayout.CENTER);
		optionPanel.add(endLabel, BorderLayout.CENTER);
		optionPanel.add(endBuildingBox, BorderLayout.CENTER);
		optionPanel.add(find, BorderLayout.CENTER);
		optionPanel.add(reset, BorderLayout.CENTER);
	}
	
	/**
	 * This function returns the start building in a string representation
	 * @return the selected start building in a string representation
	 */
	public static String getStartBuilding() {
		return (String) startBuildingBox.getSelectedItem();
	}
	
	/**
	 * This function returns the end building in a string representation
	 * @return the selected end building in a string representation
	 */
	public static String getEndBuilding() {
		return (String) endBuildingBox.getSelectedItem();
	}
	
	/**
	 * This function draws the path from the start building to the end building
	 * @param path is a list of edges to be used to draw the path
	 * @param start is the start building node
	 * @param end is the end building node
	 */
	public static void drawPath(List<Edge<Building, Double>> path, Node<Building> start, Node<Building> end) {
		imagePanel.setBuildings(start, end, true);
		imagePanel.setPath(path, true);
	}
	
	/**
	 * This function reset all the components to the initial state.
	 */
	public static void reset() {
		startBuildingBox.setSelectedIndex(0);
		endBuildingBox.setSelectedIndex(0);
		imagePanel.setBuildings(null, null, false);
		imagePanel.setPath(null, false);
		
		imagePane.getVerticalScrollBar().setValue(0);
		imagePane.getHorizontalScrollBar().setValue(0);
		
		frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		imagePanel.setPreferredSize(imagePanel.getPreferredSize());
		optionPanel.setPreferredSize(new Dimension(frame.getWidth(), 50));
		imagePane.setPreferredSize(imagePane.getPreferredSize());
	}
}
