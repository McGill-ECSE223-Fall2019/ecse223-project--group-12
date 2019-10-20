package ca.mcgill.ecse223.quoridor.view;

import java.awt.CardLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class QuoridorFrame extends JFrame {
	private static final long serialVersionUID = -4426310869335015542L;
	private JPanel contentPane;
	private MenuPanel menuPanel;
	private GamePanel gamePanel;

	public QuoridorFrame() {
		contentPane = new JPanel();
		contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new CardLayout());

		menuPanel = new MenuPanel();
		gamePanel = new GamePanel();
		contentPane.add(menuPanel, "Menu Panel");
		contentPane.add(gamePanel, "Game Panel");

		setContentPane(contentPane);
		pack();
		setLocationByPlatform(true);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setSize(800, 600);
		setTitle("Quridor Application");
	}
}
