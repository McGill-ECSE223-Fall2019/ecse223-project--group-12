package ca.mcgill.ecse223.quoridor.view;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MenuPage extends JFrame {
	private static final long serialVersionUID = -4426310869335015542L;
	private JTextField userNameTextField;
	private JLabel userNameLabel;
	private JButton addUserButton;
	private JComboBox<String> player1ToggleList;
	private JLabel player1ToggleLable;
	private JComboBox<String> player2ToggleList;
	private JLabel player2ToggleLable;
	
	private final int MIN_WIDTH = 100;
	private final int PREF_WIDTH = 100;
	private final int MAX_WIDTH = 200;
	
	private final int MIN_HEIGHT = 30;
	private final int PREF_HEIGHT = 30;
	private final int MAX_HEIGHT = 30;
	
	

	public MenuPage() {
		initComponents();
		refreshData();
	}

	private void refreshData() {
		// TODO Auto-generated method stub

	}

	private void initComponents() {

		// elements for new user
		userNameTextField = new JTextField();
		userNameLabel = new JLabel();
		userNameLabel.setText("New User Name:");
		addUserButton = new JButton();
		addUserButton.setText("Add Player");
		// elements for start new game
		player1ToggleList = new JComboBox<String>(new String[0]);
		player1ToggleLable = new JLabel();
		player1ToggleLable.setText("Select Player1:");
		
		player2ToggleList = new JComboBox<String>(new String[0]);
		player2ToggleLable = new JLabel();
		player2ToggleLable.setText("Select Player2:");
		
		// horizontal line elements
		JSeparator horizontalLine = new JSeparator();

		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup() // Column 1
						.addComponent(userNameLabel)
						.addComponent(userNameTextField, MIN_WIDTH, PREF_WIDTH, MAX_WIDTH)
						.addComponent(player1ToggleLable)
						.addComponent(player1ToggleList, MIN_WIDTH, PREF_WIDTH, MAX_WIDTH)
						
				)
				.addGroup(layout.createParallelGroup() // Column 2
						.addComponent(addUserButton)
						.addComponent(player2ToggleLable)
						.addComponent(player2ToggleList, MIN_WIDTH, PREF_WIDTH, MAX_WIDTH)
				)
		);


		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup() // Row 1
						.addComponent(userNameLabel)
				)
				.addGroup(layout.createParallelGroup() // Row 2
						.addComponent(userNameTextField, MIN_HEIGHT, PREF_HEIGHT, MAX_HEIGHT)
						.addComponent(addUserButton)
				)
				.addGroup(layout.createParallelGroup() // Row 3
						.addComponent(player1ToggleLable)
						.addComponent(player2ToggleLable)
				)
				.addGroup(layout.createParallelGroup() // Row 4
						.addComponent(player1ToggleList, MIN_HEIGHT, PREF_HEIGHT, MAX_HEIGHT)
						.addComponent(player2ToggleList, MIN_HEIGHT, PREF_HEIGHT, MAX_HEIGHT)
				)
		);
		pack();
	}
}
