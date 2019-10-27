package ca.mcgill.ecse223.quoridor.view;

import java.awt.Color;
import java.awt.Graphics;


import javax.swing.JPanel;

public class BlackCircle extends JPanel {
	private static final long serialVersionUID = -4426310869335015542L;
    @Override
    protected void paintComponent(Graphics g) {
    	g.setColor(Color.black);
        g.fillOval(0, 0, g.getClipBounds().width, g.getClipBounds().height);
    }
}
