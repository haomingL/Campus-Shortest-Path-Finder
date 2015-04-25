package hw9;

import java.awt.Graphics; 
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ImageCanvas extends JPanel {
	
	private JScrollPane imagePane;
	
	public ImageCanvas(JScrollPane imagePane) {
		super();
		this.imagePane = imagePane;
	}
	
	private static final long serialVersionUID = -6065969575650178048L;
	
	private static final String CAMPUS_MAP_FILE = "src/hw8/data/campus_map.jpg";
	
	@Override
	public void paint(Graphics g) {
		Image image = Toolkit.getDefaultToolkit().getImage(CAMPUS_MAP_FILE);
		g.drawImage(image, 20, 0, imagePane.getWidth() - 40, imagePane.getHeight() - 80, imagePane);
	}
}
