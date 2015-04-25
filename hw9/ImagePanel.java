package hw9;

import hw5.Edge;
import hw5.Node;
import hw8.Building;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This class extends JPanel class and is customized to draw image and set size.
 * 
 * @author Haoming Liu
 *
 */
public class ImagePanel extends JPanel {
	
	private static final long serialVersionUID = -6801771565582250189L;

	private static final String CAMPUS_MAP_FILE = "src/hw8/data/campus_map.jpg";
	// The path of the map file.
	
	private static final double HEIGHT_TO_WIDTH_RATIO = 0.685;
	// The ratio of default height to width.
	
	private static final int TOP_HEIGHT = 80;
	// The top height given to the option panel
	
	private static final double IMAGE_WIDTH = 4330;
	// The default image width
	
	private static final double IMAGE_HEIGHT = 2964;
	// The default image height
	
	private static final int RADIUS = 5;
	// The radius of the oval to mark the start and end building

	private JFrame imageFrame; // frame that the panel is in
	private BufferedImage img; // the campus map image
	private boolean drawOval; // boolean value to determine whether to draw the image
	private boolean drawPath; // boolean value to determine whether to draw the path
	private Node<Building> start; // the start building
	private Node<Building> end; // the end building
	private List<Edge<Building, Double>> paths; // the path from the start building to the end building
	
	/**
	 * Constructs the imagePanel.
	 * @param frame is the outer frame used later.
	 */
	public ImagePanel(JFrame frame) {
		this.imageFrame = frame;
		try {
			img = ImageIO.read(new File(CAMPUS_MAP_FILE));
		} catch (IOException e) {
			System.err.println(e.toString());
			e.printStackTrace();
		} // read the image from the campus map path.
		drawPath = false;
		drawOval = false; // The GUI doesn't draw anything initially, so set them to false.
	}
	
	@Override
	/**
	 * The function overwrites the function in the JComponent class.
	 * It determines whether to mark the start and end building as well as the path from the start
	 * to the end.
	 * 
	 * start Building is marked as CYAN circle and end building is marked as RED circle
	 * paths are drawn in YELLOW lines.
	 * @param Graphics 
	 */
	public void paintComponent(Graphics g) {
		int width = imageFrame.getWidth();
		int height = imageFrame.getHeight() - TOP_HEIGHT;
		if (width * HEIGHT_TO_WIDTH_RATIO < height) {
			width = (int)Math.round((height * 1.0 / HEIGHT_TO_WIDTH_RATIO));
		} else {
			height = (int)Math.round(width * HEIGHT_TO_WIDTH_RATIO);
		} // set the image to the preferable size.
		g.drawImage(img, 0, 0, width, height, null);
		setSize(getPreferredSize());
		drawCircle(g);
		drawPath(g);
	}
	
	/**
	 * This function draws the circle to mark the start and end buildings if needed.
	 * @param g is the graphics to draw the Circle
	 */
	private void drawCircle(Graphics g) {
		if (drawOval && drawPath) {
			double xCor = start.getData().getXCor();
			double yCor = start.getData().getYCor(); 
			g.setColor(Color.CYAN);
			g.fillOval((int)Math.round(xCor / IMAGE_WIDTH * this.getWidth() - RADIUS), 
					(int)Math.round(yCor / IMAGE_HEIGHT * this.getHeight() - RADIUS), 2 * RADIUS, 2 * RADIUS);
			xCor = end.getData().getXCor();
			yCor = end.getData().getYCor();
			g.setColor(Color.RED);
			g.fillOval((int)Math.round(xCor / IMAGE_WIDTH * this.getWidth() - RADIUS), 
					(int)Math.round(yCor / IMAGE_HEIGHT * this.getHeight() - RADIUS), 2 * RADIUS, 2 * RADIUS);
		}
	}
	
	/**
	 * This function draws the path from the start building to the end building
	 * @param g is the graphics to drawn the path.
	 */
	private void drawPath(Graphics g) {
		if (drawPath && drawOval) { // make sure the path and oval are drawn at the same time
			Graphics2D g2 = (Graphics2D)g;
			g2.setStroke(new BasicStroke(3.0f));
			g.setColor(Color.YELLOW);
			for (Edge<Building, Double> e: paths) {
				double startX = e.getStart().getData().getXCor();
				double startY = e.getStart().getData().getYCor();
				double endX = e.getEnd().getData().getXCor();
				double endY = e.getEnd().getData().getYCor();
				g.drawLine((int)Math.round(startX / IMAGE_WIDTH * this.getWidth()), 
						(int)Math.round(startY / IMAGE_HEIGHT * this.getHeight()), 
						(int)Math.round(endX / IMAGE_WIDTH * this.getWidth()), 
						(int)Math.round(endY / IMAGE_HEIGHT * this.getHeight()));
			} // draw at appropriate size.
		}
	}
	
	/**
	 * This function is used to set whether to draw the buildings.
	 * @param start is the start building
	 * @param end is the end building
	 * @param toDraw true to draw the buildings, false otherwise.
	 */
	public void setBuildings(Node<Building> start, Node<Building> end, boolean toDraw) {
		this.start = start;
		this.end = end;
		drawOval = toDraw;
	}
	
	/**
	 * This function is used to set whether to draw the paths
	 * @param paths is the path to be drawn.
	 * @param toDraw true to draw the paths, false otherwise.
	 */
	public void setPath(List<Edge<Building, Double>> paths, boolean toDraw) {
		this.paths = paths;
		drawPath = true;
		if (drawPath) {
			this.paintComponent(this.getGraphics());
		}
	}
	
	@Override
	/**
	 * This function return a new Dimension to set the preferred size
	 * @return the new Dimension to set the size
	 */
	public Dimension getPreferredSize() {
		int width = imageFrame.getWidth();
		int height = imageFrame.getHeight() - TOP_HEIGHT;
		if (width * HEIGHT_TO_WIDTH_RATIO < height) {
			width = (int)Math.round((height * 1.0 / HEIGHT_TO_WIDTH_RATIO));
		} else {
			height = (int)Math.round(width * HEIGHT_TO_WIDTH_RATIO);
		}
		return new Dimension(width, height);
	}
}
