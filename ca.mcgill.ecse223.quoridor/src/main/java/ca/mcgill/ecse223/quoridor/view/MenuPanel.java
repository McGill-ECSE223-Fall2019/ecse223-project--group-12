package ca.mcgill.ecse223.quoridor.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.text.ParseException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import ca.mcgill.ecse223.quoridor.controller.InvalidInputException;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.to.UserTO;

public class MenuPanel extends JPanel {
	private static final long serialVersionUID = -4426310869335015542L;
	private JLabel addUserErrorMessage;
	// elements for new user
	private JTextField userNameTextField;
	private JLabel userNameLabel;
	private JButton addUserButton;
	
	// elements for start new game
	// Player 1
	private JComboBox<String> player1ToggleList;
	private JLabel player1ToggleLable;
	// Player 2
	private JComboBox<String> player2ToggleList;
	private JLabel player2ToggleLable;
	// Thinking Time
	private JLabel setThinkingTimeLable;
	private JFormattedTextField thinkingTimeTextField;
	private JButton stertGameButton;
	
	// Sizing
	private final int MIN_WIDTH = 100;
	private final int PREF_WIDTH = 100;
	private final int MAX_WIDTH = 200;
	private final int MIN_HEIGHT = 30;
	private final int PREF_HEIGHT = 30;
	private final int MAX_HEIGHT = 30;
	
	
	public MenuPanel() {
		initComponents();
		refreshData();
	}

	private void initComponents() {

		addUserErrorMessage = new JLabel();
		addUserErrorMessage.setForeground(Color.RED);
		// Elements for new user
		userNameTextField = new JTextField();
		userNameLabel = new JLabel();
		userNameLabel.setText("New User Name:");
		addUserButton = new JButton();
		addUserButton.setText("Add Player");
		
		// elements for start new game
		// Player 1
		player1ToggleList = new JComboBox<String>(new String[0]);
		player1ToggleLable = new JLabel();
		player1ToggleLable.setText("Select Player1:");
		// Player 2
		player2ToggleList = new JComboBox<String>(new String[0]);
		player2ToggleLable = new JLabel();
		player2ToggleLable.setText("Select Player2:");
		// Thinking time format
		setThinkingTimeLable = new JLabel();
		setThinkingTimeLable.setText("Total Thinking TIme per player");
		MaskFormatter timeFormat = null;
		try {
			timeFormat = new MaskFormatter("##min##s");
			timeFormat.setPlaceholderCharacter('#');
		} catch (ParseException e) {
			e.printStackTrace();
		}	
		thinkingTimeTextField = new JFormattedTextField(timeFormat);
		// start game button
		stertGameButton = new JButton();
		stertGameButton.setText("Start Game");
		
		// Action Listeners
		
		// listeners for driver
		addUserButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addDriverButtonActionPerformed(evt);
			}
		});
		
		stertGameButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				stertGameButtonActionPerformed(evt);
			}
		});
		
		
		// horizontal line elements
		//JSeparator horizontalLine = new JSeparator();

		// layout
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		// Horizontal Layout
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup() // Column 1
						.addGroup(layout.createParallelGroup()
							.addComponent(userNameLabel)
							.addComponent(userNameTextField, MIN_WIDTH, PREF_WIDTH, MAX_WIDTH)
						)
						
						.addGroup(layout.createParallelGroup()
							.addComponent(player1ToggleLable)
							.addComponent(player1ToggleList, MIN_WIDTH, PREF_WIDTH, MAX_WIDTH)
						)
						.addComponent(stertGameButton)
						.addComponent(addUserErrorMessage)
				)
				.addGroup(layout.createParallelGroup() // Column 2
						.addComponent(addUserButton)
						.addComponent(player2ToggleLable)
						.addComponent(player2ToggleList, MIN_WIDTH, PREF_WIDTH, MAX_WIDTH)
				)
				.addGroup(layout.createParallelGroup() // Column 3
						.addComponent(setThinkingTimeLable)
						.addComponent(thinkingTimeTextField, MIN_WIDTH, PREF_WIDTH, MAX_WIDTH)
				)
		);

		// Vertical Layout
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup() // Row 1
						.addComponent(userNameLabel)
				)
				.addGroup(layout.createParallelGroup() // Row 2
						.addComponent(userNameTextField, MIN_HEIGHT, PREF_HEIGHT, MAX_HEIGHT)
						.addComponent(addUserButton)
				)
				.addComponent(addUserErrorMessage)
				.addGroup(layout.createParallelGroup() // Row 3
						.addComponent(player1ToggleLable)
						.addComponent(player2ToggleLable)
						.addComponent(setThinkingTimeLable)
				)
				.addGroup(layout.createParallelGroup() // Row 4
						.addComponent(player1ToggleList, MIN_HEIGHT, PREF_HEIGHT, MAX_HEIGHT)
						.addComponent(player2ToggleList, MIN_HEIGHT, PREF_HEIGHT, MAX_HEIGHT)
						.addComponent(thinkingTimeTextField, MIN_HEIGHT, PREF_HEIGHT, MAX_HEIGHT)
						
				)
				.addGroup(layout.createParallelGroup() // Row 5
						.addComponent(stertGameButton)
				)
		);
	}
	
	private void refreshData() {
		player1ToggleList.removeAllItems();
		player2ToggleList.removeAllItems();
		for (UserTO user : QuoridorController.getAllUsers()) {
			player1ToggleList.addItem(user.getName());
			player2ToggleList.addItem(user.getName());
		}
		
		
		//Clean up text fields, errors and selections
		player1ToggleList.setSelectedItem(null);
		player2ToggleList.setSelectedItem(null);
		userNameTextField.setText("");
		addUserErrorMessage.setText("");
		
	}
	
	private void addDriverButtonActionPerformed(ActionEvent evt) {
			try {
				QuoridorController.createUser(userNameTextField.getText());
				refreshData();
			} catch (InvalidInputException e) {
				addUserErrorMessage.setText(e.getMessage());
			}	
	}
	
	private void stertGameButtonActionPerformed(ActionEvent evt) {
		CardLayout cardLayout = (CardLayout) this.getParent().getLayout();
		cardLayout.show(this.getParent(), "Game Panel");
		
	}
}
