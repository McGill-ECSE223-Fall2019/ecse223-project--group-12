package ca.mcgill.ecse223.quoridor.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.JOptionPane;

import ca.mcgill.ecse223.quoridor.controller.InvalidInputException;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.to.PlayerPositionTO;
import ca.mcgill.ecse223.quoridor.to.PlayerPositionTO.PlayerColor;
import ca.mcgill.ecse223.quoridor.to.PlayerStatsTO;
import ca.mcgill.ecse223.quoridor.to.WallMoveTO;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JSeparator;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = -4426310869335015542L;

	private JButton saveExitToMenuButton;
	private JButton grabWallButton;
	private JPanel controlUIPanel;
	private JPanel gameBoardPanel;
	private final int BOARD_SIZE = 19; // 9*2+1 to accommodate for slots
	private JLabel remainingWalls;
	private JLabel movemode;
	private JLabel playerLabel;
	private JButton rotateWallButton;
	private JPanel dashboardPanel;
	private JPanel arrowPanel;
	private JButton leftButton;
	private JButton upButton;
	private JButton rightButton;
	private JButton downButton;
	private JLabel remainingTime;
	private JButton confirmMoveButton;
	private JSeparator upperSeparator;
	private JSeparator middleSeparator;
	private JSeparator lowerSeparator;
	private JLabel invalidMoveLabel;
	private final Color wallColor = Color.RED;
	private final Color wallCandidateColor = Color.YELLOW;
	private final Color invisibleWallColor = new Color(210, 166, 121);
	private final PlayerColor whitePawnColor = PlayerColor.White;
	private final PlayerColor blackPawnColor = PlayerColor.Black;
	private final Color tileColor = new Color(153, 102, 0);
	private final Color boardBackGroundColor = new Color(191, 128, 64);
	private final Color boardBorderColor = new Color(134, 89, 45);
	private Timer timer;

	public GamePanel() {
		initComponents();
	}

	private void initComponents() {

		// ------------------------
		// Set up components
		// ------------------------
		// Panels
		controlUIPanel = new JPanel();
		gameBoardPanel = new JPanel(new GridBagLayout());
		gameBoardPanel.setBackground(boardBackGroundColor);
		gameBoardPanel.setOpaque(true);
		dashboardPanel = new JPanel();
		arrowPanel = new JPanel();
		// Buttons
		saveExitToMenuButton = new JButton("Save or Exit");
		grabWallButton = new JButton("Grab Wall");
		rotateWallButton = new JButton("Rotate Wall");
		confirmMoveButton = new JButton("Switch Player");
		upButton = new JButton("↑");
		leftButton = new JButton("←");
		downButton = new JButton("↓");
		rightButton = new JButton("→");
		// Separators
		upperSeparator = new JSeparator();
		middleSeparator = new JSeparator();
		lowerSeparator = new JSeparator();
		// Labels
		remainingWalls = new JLabel("Walls instock: ");
		movemode = new JLabel("Mode: ");
		playerLabel = new JLabel("Player: ");
		remainingTime = new JLabel("TIme left: ");
		playerLabel.setFont(new Font(null, Font.BOLD, 16));
		remainingTime.setFont(new Font(null, Font.BOLD, 14));
		movemode.setFont(new Font(null, Font.BOLD, 14));
		remainingWalls.setFont(new Font(null, Font.BOLD, 14));
		invalidMoveLabel = new JLabel();
		invalidMoveLabel.setForeground(Color.RED);

		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (timer.isRunning()) {
					refreshTime();
				}
			}
		});

		// ------------------------
		// Layout of Main Panel
		// ------------------------
		GroupLayout mainLayout = new GroupLayout(this);
		setLayout(mainLayout);
		mainLayout.setAutoCreateGaps(true);
		mainLayout.setAutoCreateContainerGaps(true);
		// Horizontal Layout
		mainLayout.setHorizontalGroup(mainLayout.createSequentialGroup().addGroup(mainLayout.createParallelGroup() // col
																													// 1
				.addComponent(gameBoardPanel)).addGroup(mainLayout.createParallelGroup() // col 2
						.addComponent(controlUIPanel)));
		// Vertical Layout
		mainLayout.setVerticalGroup(mainLayout.createSequentialGroup().addGroup(mainLayout.createParallelGroup() // row1
				.addComponent(controlUIPanel).addComponent(gameBoardPanel)));

		// ------------------------
		// Set up sub-panels
		// ------------------------

		createControlUI();
		createDashboard();
		createArrowKeys();
		creatBoardPane();

		// ------------------------
		// Action Listeners
		// ------------------------

		// listeners for save button
		saveExitToMenuButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveExitToMenuButtonActionPerformed(evt);
			}
		});

		grabWallButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				grabWallButtonActionPerformed(evt);
			}
		});

		confirmMoveButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				confirmMoveButtonActionPerformed(evt);
			}
		});
		rightButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				rightButtonButtonActionPerformed(evt);
			}

		});
		leftButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				leftButtonButtonActionPerformed(evt);
			}

		});
		upButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				upButtonButtonActionPerformed(evt);
			}

		});
		downButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				downButtonButtonActionPerformed(evt);
			}

		});
		rotateWallButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				rotateWallButtonButtonActionPerformed(evt);
			}
		});
	}

	// ------------------------
	// Refresh Methods
	// ------------------------

	public void refreshData() {

		PlayerStatsTO playerStats = QuoridorController.getPlayerStats();
		if (playerStats != null) {
			playerLabel.setText("Player: " + playerStats.getName());
			remainingTime.setText("Time left: " + playerStats.getRemaningTime().toString());
			movemode.setText("Move mode: " + playerStats.getMoveMode().split("Move")[0]);
			remainingWalls.setText("Walls Instock: " + playerStats.getRemainingWalls());
		}

		refreshWalls();
		refreshWallMoveCandidate();
		refreshPlayePositions();

		invalidMoveLabel.setText("");

	}

	private void refreshTime() {
		PlayerStatsTO playerStats = QuoridorController.getPlayerStats();
		if (playerStats != null) {
			remainingTime.setText("Time left: " + playerStats.getRemaningTime().toString());
			if (playerStats.getRemaningTime().before(Time.valueOf("00:00:30"))) {
				remainingTime.setForeground(Color.RED);
				setEnabledMoves(true);
				if (playerStats.getRemaningTime().equals(Time.valueOf("00:00:00"))) {
					setEnabledMoves(false);
					confirmMoveButton.setEnabled(true);
					if (QuoridorController.getWallMoveCandidate() != null) {
						grabWallButton.setText("Grab Wall");
						QuoridorController.removeCandidateWall();
						refreshData();
					}
				}
			} else {
				setEnabledMoves(true);
				remainingTime.setForeground(Color.BLACK);
			}
		}
	}

	private void refreshWalls() {
		List<WallMoveTO> wallMoveTOs = QuoridorController.getWallMoves();
		for (WallMoveTO wallMoveTO : wallMoveTOs) {
			JButton[] wallGraphic = getWall(wallMoveTO.getRow(), wallMoveTO.getColumn(), wallMoveTO.getDirection());
			drawWall(wallGraphic, wallColor);
		}
	}

	private void refreshWallMoveCandidate() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				JButton[] wall = getWall(i, j, Direction.Horizontal);
				if (wall[0] != null && (wall[0].getBackground() == wallCandidateColor
						|| wall[1].getBackground() == wallCandidateColor
						|| wall[2].getBackground() == wallCandidateColor)) {
					wall[0].setBackground(invisibleWallColor);
					wall[1].setBackground(invisibleWallColor);
					wall[2].setBackground(invisibleWallColor);
				}
				wall = getWall(i, j, Direction.Vertical);
				if (wall[0] != null && (wall[0].getBackground() == wallCandidateColor
						|| wall[1].getBackground() == wallCandidateColor
						|| wall[2].getBackground() == wallCandidateColor)) {
					wall[0].setBackground(invisibleWallColor);
					wall[1].setBackground(invisibleWallColor);
					wall[2].setBackground(invisibleWallColor);
				}
			}
		}
		refreshWalls();
		WallMoveTO wallMoveTO = QuoridorController.getWallMoveCandidate();
		if (wallMoveTO != null) {
			JButton[] wallGraphic = getWall(wallMoveTO.getRow(), wallMoveTO.getColumn(), wallMoveTO.getDirection());
			drawWall(wallGraphic, wallCandidateColor);
		}
	}

	private void refreshPlayePositions() {
		List<PlayerPositionTO> playerPositions = QuoridorController.getPlayerPositions();
		for (PlayerPositionTO p : playerPositions) {
			int row = p.getRow();
			int col = p.getColumn();
			PlayerColor color = p.getPlayerColor();
			showPawn(row, col, color, true);
		}
	}

	private void clearGame() {
		playerLabel.setText("Player: ");
		remainingTime.setText("Remaining time: ");
		movemode.setText("Move mode: ");
		remainingWalls.setText("Remaining walls: ");
		invalidMoveLabel.setText("");
		grabWallButton.setText("Grab Wall");
		for (int i = 1; i < 10; i++) {
			for (int j = 1; j < 10; j++) {
				JButton[] wall1 = getWall(i, j, Direction.Vertical);
				JButton[] wall2 = getWall(i, j, Direction.Horizontal);
				// wall1[0].getBackground() == candidateWallColor;
				drawWall(wall1, invisibleWallColor);
				drawWall(wall2, invisibleWallColor);
				showPawn(i, j, blackPawnColor, false);
				showPawn(i, j, whitePawnColor, false);
			}
		}
	}

	public void loadGameStart() {
		clearGame();
		refreshData();
		timer.start();
	}
	// ------------------------
	// Action Methods
	// ------------------------
	/**
	 * Starts a popup, only to be called when a game is ready to start, from the
	 * menu panel
	 */
	public void startGamePopUp() {
		clearGame();
		int option = JOptionPane.showConfirmDialog(this.getParent(), "Click yes to start the clock!", "StartGame", 0);
		if (option == 0) {
			QuoridorController.startClock();
			refreshData();
			timer.start();
			refreshData();
		} else {
			QuoridorController.destroyGame();
			returnToMenu();
		}
	}

	/**
	 * Exits the Game Panel and goes to the main menu
	 */
	private void returnToMenu() {
		CardLayout cardLayout = (CardLayout) this.getParent().getLayout();
		cardLayout.show(this.getParent(), "Menu Panel");
		MenuPanel a = (MenuPanel) this.getParent().getComponent(0);
		a.refreshData();
	}

	/**
	 * Prompts a user to save the game etc.
	 * 
	 * @param evt
	 */
	private void saveExitToMenuButtonActionPerformed(ActionEvent evt) {
		int saveGameOption = JOptionPane.showConfirmDialog(this.getParent(),
				"Would you like to save your game before exiting?");
		if (saveGameOption == 0) {
			String fileName = JOptionPane.showInputDialog(this.getParent(), "Enter the name of the file", "Save Game",
					1);
			if (fileName != null) {
				QuoridorController.savePosition(fileName + ".dat", false);
				if (true // the file already exists, ask to overwrite
				) {
					int overWriteOption = JOptionPane.showConfirmDialog(this.getParent(),
							"are you sure you want to overwrite?");
					if (overWriteOption == 0) {
						// overwrite
					} else {
						// dont overwrite
					}
				}
			}
			QuoridorController.destroyGame();
			returnToMenu();
		} else if (saveGameOption == 1) {
			QuoridorController.destroyGame();
			returnToMenu();
		} else if (saveGameOption == 3) {
			// just stay in the game maybe refresh?
		}
	}

	private void rotateWallButtonButtonActionPerformed(ActionEvent evt) {
		QuoridorController.rotateWall();
		refreshData();
	}

	private void upButtonButtonActionPerformed(ActionEvent evt) {
		try{
			QuoridorController.moveWall("up");
			refreshData();
		}catch (InvalidInputException e) {
			invalidMoveLabel.setText(e.getMessage());
		}
	}

	private void downButtonButtonActionPerformed(ActionEvent evt) {
		try{
			QuoridorController.moveWall("down");
			refreshData();
		}catch (InvalidInputException e) {
			invalidMoveLabel.setText(e.getMessage());
		}
	}


	private void leftButtonButtonActionPerformed(ActionEvent evt) {
		try{
			QuoridorController.moveWall("left");
			refreshData();
		}catch (InvalidInputException e) {
			invalidMoveLabel.setText(e.getMessage());
		}
	}

	private void rightButtonButtonActionPerformed(ActionEvent evt) {
		try{
			QuoridorController.moveWall("right");
			refreshData();
		}catch (InvalidInputException e) {
			invalidMoveLabel.setText(e.getMessage());
		}
	}

	private void grabWallButtonActionPerformed(ActionEvent evt) {
		if (grabWallButton.getText().equals("Grab Wall")) {
			try {
				QuoridorController.grabWall();
				grabWallButton.setText("Drop Wall");
				confirmMoveButton.setEnabled(false);
				refreshData();
			} catch (InvalidInputException e) {
				invalidMoveLabel.setText(e.getMessage());
			}

		} else if (grabWallButton.getText().equals("Drop Wall")) {
			try {
				QuoridorController.dropWall();
				grabWallButton.setText("Grab Wall");
				confirmMoveButton.setEnabled(true);
				refreshData();
			} catch (InvalidInputException e) {
				refreshData();
				invalidMoveLabel.setText(e.getMessage());
			}

		}

	}

	private void confirmMoveButtonActionPerformed(ActionEvent evt) {
		QuoridorController.makeMove();
		refreshData();
	}

	// ------------------------
	// UI Utility Methods
	// ------------------------
	/**
	 * Returns an array of JButtons representing the wall at <row> <col> dir<>
	 * 
	 * @param row
	 * @param col
	 * @param dir
	 * @return
	 */
	public JButton[] getWall(int row, int col, Direction dir) {
		JButton wallA = null;
		JButton wallB = null;
		JButton wallC = null;
		if (row > 0 && row < 9 && col > 0 && col < 9) {
			int index = 0;
			if (dir.equals(Direction.Vertical)) {
				col = 2 * col;
				row = 2 * row - 1;
				index = BOARD_SIZE * col + row;
				wallA = (JButton) gameBoardPanel.getComponent(index);
				wallB = (JButton) gameBoardPanel.getComponent(index + 1);
				wallC = (JButton) gameBoardPanel.getComponent(index + 2);
			} else {
				col = 2 * col - 1;
				row = 2 * row;
				index = BOARD_SIZE * col + row;
				wallA = (JButton) gameBoardPanel.getComponent(index);
				wallB = (JButton) gameBoardPanel.getComponent(index + BOARD_SIZE);
				wallC = (JButton) gameBoardPanel.getComponent(index + 2 * BOARD_SIZE);
			}
		}
		JButton[] wall = { wallA, wallB, wallC };
		return wall;

	}

	private void setEnabledMoves(boolean enabled) {
		grabWallButton.setEnabled(enabled);
		rotateWallButton.setEnabled(enabled);
		upButton.setEnabled(enabled);
		downButton.setEnabled(enabled);
		leftButton.setEnabled(enabled);
		rightButton.setEnabled(enabled);
	}

	/**
	 * Show or hide pawn at certain tile
	 * 
	 * @param row
	 *            rows 1 to 9
	 * @param col
	 *            cols 1 to 9 (A to I in specification)
	 * @param c
	 *            the color of the pawn to show
	 * @param visible
	 */
	private void showPawn(int row, int col, PlayerColor c, boolean visible) {
		row = 2 * row - 1;
		col = 2 * col - 1;
		int index = col * BOARD_SIZE + row;
		JButton tile = (JButton) gameBoardPanel.getComponent(index);
		if (c.equals(whitePawnColor)) {
			tile.getComponent(0).setVisible(visible);
			tile.repaint();
		} else {
			tile.getComponent(1).setVisible(visible);
			tile.repaint();
		}
	}

	/**
	 * Draw a wall in a selection of colors
	 * 
	 * @param wall
	 *            an array of JButtons representing a wall (see getWall())
	 * @param color
	 * 
	 */
	private void drawWall(JButton[] wall, Color color) {
		// Walls are made of three sections (two tile lengths and the slot between them)
		if (wall[0] != null) {
			wall[0].setBackground(color);
			wall[1].setBackground(color);
			wall[2].setBackground(color);
		}
	}

	// ------------------------
	// Public Methods for Testing
	// ------------------------

	public String getPlayerLabel() {
		return playerLabel.getText();
	}

	public String getGrabWallErrorLabel() {
		invalidMoveLabel.setText("Stock is Empty"); // Temporarily mock this since error labels are reactive
		return invalidMoveLabel.getText();
		// grabWallButtonActionPerformed(null); //press button to trigger error label
		// return invalidMoveLabel.getText() ;
	}

	public String getDropWallErrorLabel() {
		invalidMoveLabel.setText("Invalid move, try again!");
		return invalidMoveLabel.getText(); // Temporarily mock this since error labels are reactive
		// grabWallButton.setText("Drop Wall"); // make sure right event is triggered
		// with button press
		// grabWallButtonActionPerformed(null); //press button to trigger error label
		// return invalidMoveLabel.getText() ;
	}

	// TODO: Same as above but for the arrow buttons (uses the same label)
	public String getWallsInstockLabel() {
		return remainingWalls.getText();
	}

	/**
	 * checks if the current player has a wall in hand
	 * 
	 */
	public boolean hasWallInHand() {
		for (int i = 1; i < 9; i++) {
			for (int j = 1; j < 9; j++) {
				JButton[] wall1 = getWall(i, j, Direction.Vertical);
				JButton[] wall2 = getWall(i, j, Direction.Horizontal);
				if (wall1[0].getBackground() == wallCandidateColor || wall2[0].getBackground() == wallCandidateColor) {
					return true;
				}
			}
		}
		return false;
	}
	

	// ------------------------
	// UI Swing Components
	// ------------------------

	/**
	 * Creates a 9x9 board of tiles with slots between tiles. Adds invisible pawn of
	 * each color to each tile. (Use showPawn() to change visibility of pawns)
	 */
	private void creatBoardPane() {
		// create board
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				GridBagConstraints gbcBoard = new GridBagConstraints();
				JButton square = new JButton();
				square.setOpaque(true);
				square.setBorder(new LineBorder(Color.BLACK));
				gbcBoard.gridx = i;
				gbcBoard.gridy = j;
				gbcBoard.weightx = 1.0;
				gbcBoard.weighty = 1.0;
				gbcBoard.fill = GridBagConstraints.BOTH;
				gbcBoard.insets = new Insets(2, 2, 2, 2);
				square.setBackground(tileColor);
				// Check if the square is a slot or a tile
				if (j % 2 == 0 || i % 2 == 0) {
					square.setBackground(invisibleWallColor);
					square.setBorder(null);
					gbcBoard.insets = new Insets(0, 0, 0, 0);
					gbcBoard.weightx = 0.0;
					gbcBoard.weighty = 0.0;
					// set minimum dimensions for vertical or horizontal slots
					if (j % 2 == 0 && i % 2 == 0) {
						square.setPreferredSize(new Dimension(10, 10));
						gbcBoard.fill = GridBagConstraints.NONE;
					} else if (j % 2 == 0) {
						square.setPreferredSize(new Dimension(0, 10));
						gbcBoard.fill = GridBagConstraints.HORIZONTAL;
					} else {
						square.setPreferredSize(new Dimension(10, 0));
						gbcBoard.fill = GridBagConstraints.VERTICAL;
					}
				} else {
					// Add white and black invisible pawns to each tile,
					WhiteCircle whitePawn = new WhiteCircle();
					square.setLayout(new OverlayLayout(square));
					whitePawn.setVisible(false);
					whitePawn.getInsets(new Insets(6, 6, 6, 6));
					whitePawn.setAlignmentX(CENTER_ALIGNMENT);
					whitePawn.setAlignmentY(CENTER_ALIGNMENT);
					square.add(whitePawn);

					BlackCircle blackPawn = new BlackCircle();
					blackPawn.setVisible(false);
					blackPawn.getInsets(new Insets(6, 6, 6, 6));
					blackPawn.setAlignmentX(CENTER_ALIGNMENT);
					blackPawn.setAlignmentY(CENTER_ALIGNMENT);
					square.add(blackPawn);
				}
				// Make the outline of the board black
				if (j == 0 || j == BOARD_SIZE - 1 || i == 0 || i == BOARD_SIZE - 1) {
					square.setBackground(boardBorderColor);
				}
				gameBoardPanel.add(square, gbcBoard);
			}
		}
		// Preferences
		// gameBoardPanel.setBorder(new LineBorder(Color.BLACK));
		gameBoardPanel.setPreferredSize(new Dimension(650, 600));
	}

	/**
	 * Creates The contorll ui with all necessary buttons
	 */
	private void createControlUI() {

		controlUIPanel.setLayout(new GroupLayout(controlUIPanel));
		GroupLayout controlUILayout = (GroupLayout) controlUIPanel.getLayout();
		controlUILayout.setHorizontalGroup(controlUILayout.createParallelGroup(Alignment.LEADING).addGroup(
				Alignment.TRAILING,
				controlUILayout.createSequentialGroup().addGroup(controlUILayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(controlUILayout.createSequentialGroup().addComponent(lowerSeparator))
						.addGroup(controlUILayout.createSequentialGroup().addComponent(middleSeparator,
								GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING,
								controlUILayout.createSequentialGroup().addComponent(upperSeparator))
						.addGroup(Alignment.LEADING,
								controlUILayout.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(grabWallButton, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
										.addComponent(rotateWallButton, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
										.addComponent(confirmMoveButton, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
										.addComponent(saveExitToMenuButton, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
										.addComponent(dashboardPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(arrowPanel, Alignment.CENTER, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING,
								controlUILayout.createSequentialGroup().addComponent(invalidMoveLabel)))));
		controlUILayout.setVerticalGroup(controlUILayout.createParallelGroup(Alignment.LEADING)
				.addGroup(controlUILayout.createSequentialGroup()
						.addComponent(dashboardPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)

						.addComponent(upperSeparator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
						.addComponent(grabWallButton).addComponent(rotateWallButton)
						.addComponent(middleSeparator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(arrowPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lowerSeparator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
						.addComponent(confirmMoveButton).addComponent(invalidMoveLabel)
						.addPreferredGap(ComponentPlacement.RELATED, 0, Short.MAX_VALUE)
						.addComponent(saveExitToMenuButton)));
		controlUILayout.setAutoCreateGaps(true);
		controlUILayout.setAutoCreateContainerGaps(true);
	}

	/**
	 * Creates the arrow key Panel
	 */
	private void createArrowKeys() {
		GridBagLayout arrowKeyLayout = new GridBagLayout();
		arrowKeyLayout.columnWidths = new int[] { 0, 0, 0, 0 };
		arrowKeyLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		arrowKeyLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		arrowKeyLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		arrowPanel.setLayout(arrowKeyLayout);

		GridBagConstraints gbcBtnUp = new GridBagConstraints();
		gbcBtnUp.insets = new Insets(0, 0, 5, 5);
		gbcBtnUp.gridx = 1;
		gbcBtnUp.gridy = 0;
		arrowPanel.add(upButton, gbcBtnUp);

		GridBagConstraints gbcBtnLeft = new GridBagConstraints();
		gbcBtnLeft.insets = new Insets(0, 0, 5, 5);
		gbcBtnLeft.gridx = 0;
		gbcBtnLeft.gridy = 1;
		arrowPanel.add(leftButton, gbcBtnLeft);

		GridBagConstraints gbcBtnRight = new GridBagConstraints();
		gbcBtnRight.insets = new Insets(0, 0, 5, 0);
		gbcBtnRight.gridx = 2;
		gbcBtnRight.gridy = 1;
		arrowPanel.add(rightButton, gbcBtnRight);

		GridBagConstraints gbcBtnDown = new GridBagConstraints();
		gbcBtnDown.insets = new Insets(0, 0, 0, 5);
		gbcBtnDown.gridx = 1;
		gbcBtnDown.gridy = 2;
		arrowPanel.add(downButton, gbcBtnDown);
	}

	/**
	 * Creates the dashboard panel
	 */
	private void createDashboard() {
		GroupLayout dashBoardLayout = new GroupLayout(dashboardPanel);
		dashBoardLayout.setHorizontalGroup(dashBoardLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(dashBoardLayout.createSequentialGroup()
						// .addContainerGap()
						.addGroup(dashBoardLayout.createParallelGroup(Alignment.LEADING).addComponent(playerLabel)
								.addComponent(remainingTime).addComponent(movemode).addComponent(remainingWalls))));
		dashBoardLayout.setVerticalGroup(dashBoardLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(dashBoardLayout.createSequentialGroup().addContainerGap().addComponent(playerLabel)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(remainingTime)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(movemode)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(remainingWalls)));
		dashboardPanel.setLayout(dashBoardLayout);
	}
}
