package ca.mcgill.ecse223.quoridor.view;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class QuoridorFrame extends JFrame {
	private static final long serialVersionUID = -4426310869335015542L;
	private JPanel contentPane;
	private MenuPanel menuPanel;
	private GamePanel gamePanel;

	public QuoridorFrame() {
		// Initialize main Panel
		contentPane = new JPanel();
		contentPane.setLayout(new CardLayout());

		// Set up Card Layout
		menuPanel = new MenuPanel();
		gamePanel = new GamePanel();
		contentPane.add(menuPanel, "Menu Panel");
		contentPane.add(gamePanel, "Game Panel");
		
		//Frame settings
		setContentPane(contentPane);
		setLocationByPlatform(true);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Quridor Application");
		this.setMinimumSize(new Dimension(900,680));
		pack();
	}
	
	public GamePanel getGamePanel() {
		return this.getGamePanel();
	}
}
