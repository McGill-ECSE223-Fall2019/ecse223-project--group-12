package ca.mcgill.ecse223.quoridor.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.Time;
import java.text.ParseException;
import java.util.Iterator;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.text.MaskFormatter;

import org.apache.commons.io.FileUtils;

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
	private JComboBox<String> whiteToggleList;
	private JLabel whiteToggleLable;
	// Player 2
	private JComboBox<String> blackToggleList;
	private JLabel blackToggleLable;
	// Thinking Time
	private JLabel setThinkingTimeLable;
	private JFormattedTextField thinkingTimeTextField;
	private JButton stertGameButton;
	// Load position
	private JComboBox<String> loadGameToggelList;
	private JButton loadGameButton;
	private JLabel loadGameLabel;
	// Panels
	private JPanel interfacePanel;
	private JPanel bannerPanel;
	private JPanel imagePanel; // Place holder for ads?
	private JLabel startGameErrorLabel;
	private JLabel titleLabel;

	private JRadioButton aiRadioButton;

	public MenuPanel() {
		initComponents();
		refreshData();
	}

	private void initComponents() {

		// ------------------------
		// Set up components
		// ------------------------

		// Panels
		interfacePanel = new JPanel();
		bannerPanel = new JPanel();
		imagePanel = new JPanel();

		// Time
		MaskFormatter timeFormat = null;
		try {
			timeFormat = new MaskFormatter("##min##s");
			timeFormat.setPlaceholderCharacter('#');
		} catch (ParseException e) {
			e.printStackTrace();
		}
		setThinkingTimeLable = new JLabel();
		setThinkingTimeLable.setText("Total Thinking TIme");
		// Title
		titleLabel = new JLabel("Quoridor");
		titleLabel.setFont(new Font(null, Font.BOLD, 44));
		// Add user
		userNameTextField = new JTextField();
		userNameLabel = new JLabel();
		userNameLabel.setText("New User Name:");
		addUserButton = new JButton();
		addUserButton.setText("Add Player");
		addUserErrorMessage = new JLabel();
		addUserErrorMessage.setForeground(Color.RED);
		// Start Game
		whiteToggleLable = new JLabel("Select White Player");
		blackToggleLable = new JLabel("Select Black Player");
		blackToggleList = new JComboBox<String>();
		whiteToggleList = new JComboBox<String>();
		thinkingTimeTextField = new JFormattedTextField(timeFormat);
		stertGameButton = new JButton();
		stertGameButton.setText("Start Game");
		startGameErrorLabel = new JLabel();
		startGameErrorLabel.setForeground(Color.RED);
		// Load Games
		loadGameToggelList = new JComboBox<String>();
		loadGameButton = new JButton("Load Game");
		loadGameLabel = new JLabel("Load Game");
		//
		aiRadioButton = new JRadioButton("Set Black as AI");
		
		//
		try {
			QuoridorController.createUser("Default");
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		aiRadioButton.setSelected(true);
		blackToggleList.setEnabled(false);
		thinkingTimeTextField.setText("2000");
		// ------------------------
		// Layout of Main Panel
		// ------------------------
		GroupLayout mainLayout = new GroupLayout(this);
		mainLayout.setHorizontalGroup(mainLayout.createParallelGroup()
				.addGroup(mainLayout
						.createSequentialGroup().addContainerGap().addGroup(mainLayout.createParallelGroup()
								.addComponent(imagePanel).addComponent(interfacePanel).addComponent(bannerPanel))
						.addContainerGap()));
		mainLayout.setVerticalGroup(mainLayout.createParallelGroup().addGroup(mainLayout.createSequentialGroup()
				.addContainerGap().addComponent(bannerPanel, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
				.addComponent(interfacePanel).addPreferredGap(ComponentPlacement.RELATED).addComponent(imagePanel)));
		setLayout(mainLayout);
		mainLayout.setAutoCreateGaps(true);
		mainLayout.setAutoCreateContainerGaps(true);

		// ------------------------
		// Set up sub-panels
		// ------------------------

		createTitlePanel();
		createInterfacePanel();

		// ------------------------
		// Action Listeners
		// ------------------------

		addUserButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addUserButtonActionPerformed(evt);
			}
		});

		stertGameButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				stertGameButtonActionPerformed(evt);
			}
		});

		loadGameButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loadGameButtonActionPerformed(evt);
			}
		});
		aiRadioButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				aiButtonActionPerformed(evt);
			}

		});

	}

	public void refreshData() {
		whiteToggleList.removeAllItems();
		blackToggleList.removeAllItems();
		loadGameToggelList.removeAllItems();
		for (UserTO user : QuoridorController.getAllUsers()) {
			whiteToggleList.addItem(user.getName());
			blackToggleList.addItem(user.getName());
		}

		@SuppressWarnings("unchecked")
		Iterator<File> it = FileUtils.iterateFiles(new File("savedgames"), null, true);
		while (it.hasNext()) {
			String i = it.next().getName();
			loadGameToggelList.addItem(i);
		}

		// Clean up text fields, errors and selections
		whiteToggleList.setSelectedItem("Default");
		blackToggleList.setSelectedItem(null);
		loadGameToggelList.setSelectedItem(null);
		userNameTextField.setText("");
		addUserErrorMessage.setText("");
		startGameErrorLabel.setText("");
	}

	private void addUserButtonActionPerformed(ActionEvent evt) {
		try {
			QuoridorController.createUser(userNameTextField.getText());
			refreshData();
		} catch (InvalidInputException e) {
			addUserErrorMessage.setText(e.getMessage());
		}
	}

	private void aiButtonActionPerformed(ActionEvent evt) {
		refreshData();
		if (aiRadioButton.isSelected()) {
			blackToggleList.setEnabled(false);
		} else {
			blackToggleList.setEnabled(true);
		}

	}

	private void stertGameButtonActionPerformed(ActionEvent evt) {
		String w = null;
		String b = null;
		Time time = null;
		try {
			int min = Integer.parseInt(thinkingTimeTextField.getText().substring(0, 2));
			int sec = Integer.parseInt(thinkingTimeTextField.getText().substring(5, 7));
			time = Time.valueOf("00:" + min + ":" + sec);
			w = whiteToggleList.getSelectedItem().toString();
			if (blackToggleList.isEnabled()) {
				b = blackToggleList.getSelectedItem().toString();
			}
		} catch (Exception e) {
			startGameErrorLabel.setText("Could not parse selections");
		}
		if (aiRadioButton.isSelected() && w != null && time != null) {
			try {
				QuoridorController.initializeGame();
				QuoridorController.setWhitePlayerInGame(w);
				QuoridorController.setBlackComp();
				QuoridorController.setTotalThinkingTime(time);
			} catch (InvalidInputException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			changeToGamePanel();
		} else if (w != null && b != null && time != null) {
			try {
				QuoridorController.initializeGame();
				QuoridorController.setWhitePlayerInGame(w);
				QuoridorController.setBlackPlayerInGame(b);
				QuoridorController.setTotalThinkingTime(time);
			} catch (InvalidInputException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			changeToGamePanel();
		} else {
			startGameErrorLabel.setText("Please select names and time");
		}
	}

	private void loadGameButtonActionPerformed(ActionEvent evt) {
		// TODO check if the position is valid
		if (loadGameToggelList.getSelectedItem() != null) {
			String fileName = loadGameToggelList.getSelectedItem().toString();

			if (!QuoridorController.loadPosition(fileName, false)) {
				JOptionPane.showMessageDialog(this.getParent(), "Sorry file is not valid");
				QuoridorController.destroyGame();
			} else {
				CardLayout cardLayout = (CardLayout) this.getParent().getLayout();
				cardLayout.show(this.getParent(), "Game Panel");
				GamePanel a = (GamePanel) this.getParent().getComponent(1);
				a.loadGameStart();
			}
		}
	}

	private void changeToGamePanel() {
		CardLayout cardLayout = (CardLayout) this.getParent().getLayout();
		cardLayout.show(this.getParent(), "Game Panel");
		GamePanel a = (GamePanel) this.getParent().getComponent(1);
		a.startGamePopUp();
	}

	private void createTitlePanel() {
		GroupLayout gl_panel_1 = new GroupLayout(bannerPanel);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup()
				.addGroup(gl_panel_1.createSequentialGroup().addContainerGap().addComponent(titleLabel)));
		gl_panel_1.setVerticalGroup(
				gl_panel_1.createParallelGroup().addGroup(gl_panel_1.createSequentialGroup().addComponent(titleLabel)));
		gl_panel_1.setAutoCreateGaps(true);
		bannerPanel.setLayout(gl_panel_1);
	}

	private void createInterfacePanel() {
		GroupLayout interfaceLayout = new GroupLayout(interfacePanel);
		interfaceLayout.setHorizontalGroup(interfaceLayout.createParallelGroup()
				.addGroup(interfaceLayout.createSequentialGroup().addContainerGap()
						.addGroup(interfaceLayout.createParallelGroup()
								.addGroup(interfaceLayout.createParallelGroup().addComponent(userNameLabel)
										.addComponent(addUserButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(userNameTextField)))
						.addGap(31)
						.addGroup(interfaceLayout.createParallelGroup().addGroup(interfaceLayout.createSequentialGroup()
								.addGroup(interfaceLayout.createParallelGroup(Alignment.LEADING, false)
										.addGroup(interfaceLayout.createSequentialGroup()
												.addGroup(interfaceLayout.createParallelGroup()
														.addComponent(whiteToggleLable).addComponent(whiteToggleList))
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(interfaceLayout.createParallelGroup()
														.addComponent(blackToggleList).addComponent(blackToggleLable))
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(interfaceLayout.createParallelGroup()
														.addComponent(thinkingTimeTextField)
														.addComponent(setThinkingTimeLable)
														.addComponent(aiRadioButton)))
										.addComponent(stertGameButton, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGap(34)
								.addGroup(interfaceLayout.createParallelGroup()
										.addGroup(interfaceLayout.createSequentialGroup().addComponent(loadGameLabel)
												.addContainerGap())
										.addGroup(interfaceLayout.createSequentialGroup()
												.addGroup(interfaceLayout.createParallelGroup()
														.addComponent(loadGameButton, Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(loadGameToggelList))
												.addContainerGap())))
								.addComponent(startGameErrorLabel)))
				.addGap(10).addComponent(addUserErrorMessage));
		interfaceLayout.setVerticalGroup(interfaceLayout.createParallelGroup().addGroup(interfaceLayout
				.createSequentialGroup()
				.addGroup(interfaceLayout
						.createParallelGroup().addComponent(userNameLabel).addComponent(whiteToggleLable)
						.addComponent(blackToggleLable).addComponent(loadGameLabel).addComponent(setThinkingTimeLable))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(interfaceLayout.createParallelGroup()
						.addComponent(userNameTextField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(whiteToggleList, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(blackToggleList, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(loadGameToggelList, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(thinkingTimeTextField, GroupLayout.PREFERRED_SIZE, 30,
								GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(interfaceLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(addUserButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(stertGameButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(loadGameButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(interfaceLayout.createParallelGroup(Alignment.BASELINE).addComponent(startGameErrorLabel)
						.addComponent(aiRadioButton).addComponent(addUserErrorMessage))));
		interfacePanel.setLayout(interfaceLayout);
		interfaceLayout.setAutoCreateContainerGaps(true);
	}
}
