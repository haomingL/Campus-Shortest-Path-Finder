package hw9;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * This class extends from the JScrollPane.
 * It can set the size to fit the frame.
 * @author Haoming Liu
 *
 */
public class ImageScrollPane extends JScrollPane {

	private static final long serialVersionUID = 2933865807273293236L;
	
	public static final int TOP_HEIGHT = 80; // The top height given to the option panel
	
	private JFrame frame;// The outer frame to be used to call back
	
	public ImageScrollPane(Component view, JFrame frame) {
		super(view);
		this.frame = frame;
	}
	
	@Override
	/**
	 * This functions overwrites the original one and set the preferred size
	 * @param g  Graphics g to draw the component
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setSize(getPreferredSize());
	}
	
	@Override
	/**
	 * This function returns the preferred size of the panel.
	 * @return the new Dimension with the preferred size
	 */
	public Dimension getPreferredSize() {
		return new Dimension(frame.getWidth(), frame.getHeight() - TOP_HEIGHT);
	}
}
