package ca.mcgill.ecse223.quoridor.view;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class QuoridorFrame extends JFrame {

	private static final long serialVersionUID = -4426310869335015542L;
	private JPanel contentPane;
	private MenuPanel menuPanel;
	private GamePanel gamePanel;

	public QuoridorFrame() {
		try {
			UIManager.setLookAndFeel(
					//UIManager.getCrossPlatformLookAndFeelClassName());
					// "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					// "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
					// "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			//"com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			"javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Initialize main Panel
		contentPane = new JPanel();
		contentPane.setLayout(new CardLayout());

		// Set up Card Layout
		menuPanel = new MenuPanel();
		gamePanel = new GamePanel();
		contentPane.add(menuPanel, "Menu Panel");
		contentPane.add(gamePanel, "Game Panel");

		// Frame settings
		setContentPane(contentPane);
		setLocationByPlatform(true);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Quridor Application");
		this.setMinimumSize(new Dimension(900, 680));
		pack();
	}

	public GamePanel getGamePanel() {
		return this.getGamePanel();
	}
}
