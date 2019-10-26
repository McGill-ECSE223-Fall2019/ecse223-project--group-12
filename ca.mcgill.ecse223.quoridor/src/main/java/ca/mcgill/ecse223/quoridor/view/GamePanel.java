package ca.mcgill.ecse223.quoridor.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
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
	private final Color invisibleWallColor = Color.BLUE;
	private final PlayerColor whitePawnColor = PlayerColor.White;
	private final PlayerColor blackPawnColor = PlayerColor.Black;
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
		dashboardPanel = new JPanel();
		arrowPanel = new JPanel();
		// Buttons
		saveExitToMenuButton = new JButton("Save or Exit");
		grabWallButton = new JButton("Grab Wall");
		System.out.println("hii5555i");
		rotateWallButton = new JButton("Rotate Wall");
		confirmMoveButton = new JButton("Confirm Move");
		upButton = new JButton("↑");
		leftButton = new JButton("←");
		downButton = new JButton("↓");
		rightButton = new JButton("→");
		// Separators
		upperSeparator = new JSeparator();
		middleSeparator = new JSeparator();
		lowerSeparator = new JSeparator();
		// Labels
		remainingWalls = new JLabel("Remaining Walls: ");
		movemode = new JLabel("MoveMode: ");
		playerLabel = new JLabel("Player: ");
		remainingTime = new JLabel("Remaining TIme: ");
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

	public void refreshData() {
		PlayerStatsTO playerStats = QuoridorController.getPlayerStats();
		wallRefresh();
		refreshPlayePositions();
		if (playerStats != null) {
			playerLabel.setText("Player: " + playerStats.getName());
			remainingTime.setText("Remaining time: " + playerStats.getRemaningTime().toString());
			movemode.setText("Move mode: " + playerStats.getMoveMode());
			remainingWalls.setText("Remaining walls: " + playerStats.getRemainingWalls());

		}
		invalidMoveLabel.setText("");

	}

	public void refreshTime() {
		PlayerStatsTO playerStats = QuoridorController.getPlayerStats();
		if (playerStats != null) {
			remainingTime.setText("Remaining time: " + playerStats.getRemaningTime().toString());
		}
	}

	public void clearGame() {
		playerLabel.setText("Player");
		remainingTime.setText("Remaining time: ");
		movemode.setText("Move mode: ");
		remainingWalls.setText("Remaining walls: ");
		invalidMoveLabel.setText("");
		for (int i = 1; i < 10; i++) {
			for (int j = 1; j < 10; j++) {
				JButton[] wall1 = getWall(i, j, Direction.Vertical);
				JButton[] wall2 = getWall(i, j, Direction.Horizontal);
				drawWall(wall1, invisibleWallColor);
				drawWall(wall2, invisibleWallColor);
				showPawn(i, j, blackPawnColor, false);
				showPawn(i, j, whitePawnColor, false);
			}
		}
	}

	public void startGamePopUp() {
		clearGame();
		int name = JOptionPane.showConfirmDialog(this.getParent(), "Click yes to start the clock!", "StartGame", 0);
		if (name == 0) {
			try {
				QuoridorController.startClock();
				refreshData();
				timer.start();

			} catch (InvalidInputException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			refreshData();
		} else {
			QuoridorController.destroyGame();
			returnToMenu();
		}
	}

	private void returnToMenu() {
		CardLayout cardLayout = (CardLayout) this.getParent().getLayout();
		cardLayout.show(this.getParent(), "Menu Panel");
	}

	private void saveExitToMenuButtonActionPerformed(ActionEvent evt) {
		int name = JOptionPane.showConfirmDialog(this.getParent(), "Would you like to save your game before exiting?");
		if (name == 0) {
			String fileName = JOptionPane.showInputDialog(this.getParent(), "Enter the name of the file", "Save Game",
					1);
			if (fileName != null) {
				QuoridorController.savePosition(fileName);
			}
			QuoridorController.destroyGame();
			returnToMenu();
		} else if (name == 1) {
			QuoridorController.destroyGame();
			returnToMenu();
		} else if (name == 3) {
			// just stay in the game maybe refresh?
		}
	}

	private void rotateWallButtonButtonActionPerformed(ActionEvent evt) {
		QuoridorController.rotateWall();
		refreshData();
		refreshWallMoveCandidate();

	}

	private void upButtonButtonActionPerformed(ActionEvent evt) {
		QuoridorController.moveWall("up");
		refreshData();
		refreshWallMoveCandidate();
	}

	private void leftButtonButtonActionPerformed(ActionEvent evt) {
		QuoridorController.moveWall("left");
		refreshData();
		refreshWallMoveCandidate();
	}

	private void downButtonButtonActionPerformed(ActionEvent evt) {
		QuoridorController.moveWall("down");
		refreshData();
		refreshWallMoveCandidate();
	}

	private void rightButtonButtonActionPerformed(ActionEvent evt) {
		QuoridorController.moveWall("right");
		refreshData();
		refreshWallMoveCandidate();
	}

	private JButton[] getWall(int row, int col, Direction dir) {
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
		wallRefresh();
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

	private void grabWallButtonActionPerformed(ActionEvent evt) {
		if (grabWallButton.getText().equals("Grab Wall")) {
			grabWallButton.setText("Drop Wall");
			confirmMoveButton.setEnabled(false);
			QuoridorController.grabWall();
		} else if (grabWallButton.getText().equals("Drop Wall")) {
			grabWallButton.setText("Grab Wall");
			confirmMoveButton.setEnabled(true);
			QuoridorController.dropWall();
			System.out.println("hiii");
		}
		refreshData();
		refreshWallMoveCandidate();
	}

	private void confirmMoveButtonActionPerformed(ActionEvent evt) {
		QuoridorController.makeMove();
		invalidMoveLabel.setText("Invalid move, try again!");
		refreshData();
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
		} else {
			tile.getComponent(1).setVisible(visible);
		}
	}

	/**
	 * Draw a wall at specific tile
	 * 
	 * @param row
	 *            1 to 8
	 * @param col
	 *            1 to 8 (A to H in specification)
	 * @param dir
	 *            the direction of the wall
	 */
	private void drawWall(JButton[] wall, Color color) {
		// Walls are made of three sections (two tile lengths and the slot between them)
		if (wall[0] != null) {
			wall[0].setBackground(color);
			wall[1].setBackground(color);
			wall[2].setBackground(color);
		}
	}

	private void wallRefresh() {
		List<WallMoveTO> wallMoveTOs = QuoridorController.getWallMoves();
		for (WallMoveTO wallMoveTO : wallMoveTOs) {
			JButton[] wallGraphic = getWall(wallMoveTO.getRow(), wallMoveTO.getColumn(), wallMoveTO.getDirection());
			drawWall(wallGraphic, wallColor);
		}
	}

	/**
	 * Creates a 9x9 board of tiles with slots between tiles. Adds invisible pawn of
	 * each color to each tile. (Use showPawn() to change visibility of pawns)
	 */
	private void creatBoardPane() {
		// Icon for Pawns
		// ImageIcon wPawnIcon = new
		// ImageIcon(getClass().getClassLoader().getResource("images\\whitePawn.png"));
		ImageIcon wPawnIcon = new ImageIcon("src\\main\\resources\\images\\whitePawn.png");
		Image img = wPawnIcon.getImage();
		img = img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		wPawnIcon.setImage(img);
		ImageIcon bPawnIcon = new ImageIcon("src\\main\\resources\\images\\blackPawn.png");
		Image img1 = bPawnIcon.getImage();
		img1 = img1.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		bPawnIcon.setImage(img1);
		// create board
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				GridBagConstraints c = new GridBagConstraints();
				JButton square = new JButton();
				square.setOpaque(true);
				square.setBorder(new LineBorder(Color.BLACK));
				c.gridx = i;
				c.gridy = j;
				c.weightx = 1.0;
				c.weighty = 1.0;
				c.fill = GridBagConstraints.BOTH;
				c.insets = new Insets(2, 2, 2, 2);
				square.setBackground(Color.LIGHT_GRAY);
				// Check if the square is a slot or a tile
				if (j % 2 == 0 || i % 2 == 0) {
					square.setBackground(invisibleWallColor);
					square.setBorder(null);
					c.insets = new Insets(0, 0, 0, 0);
					c.weightx = 0.0;
					c.weighty = 0.0;
					// set minimum dimensions for vertical or horizontal slots
					if (j % 2 == 0 && i % 2 == 0) {
						square.setPreferredSize(new Dimension(10, 10));
						c.fill = GridBagConstraints.NONE;
					} else if (j % 2 == 0) {
						square.setPreferredSize(new Dimension(0, 10));
						c.fill = GridBagConstraints.HORIZONTAL;
					} else {
						square.setPreferredSize(new Dimension(10, 0));
						c.fill = GridBagConstraints.VERTICAL;
					}
				} else {
					// Add white and black invisible pawns to each tile,
					JButton whitePawn = new JButton();
					whitePawn.setIcon(wPawnIcon);
					square.setLayout(new OverlayLayout(square));
					whitePawn.setVisible(false);
					whitePawn.setAlignmentX(CENTER_ALIGNMENT);
					whitePawn.setAlignmentY(CENTER_ALIGNMENT);
					whitePawn.setBorder(null);
					whitePawn.setOpaque(false);
					whitePawn.setBackground(Color.LIGHT_GRAY);
					square.add(whitePawn);

					JButton blackPawn = new JButton();
					blackPawn.setIcon(bPawnIcon);
					square.setLayout(new OverlayLayout(square));
					blackPawn.setVisible(false);
					blackPawn.setAlignmentX(CENTER_ALIGNMENT);
					blackPawn.setAlignmentY(CENTER_ALIGNMENT);
					blackPawn.setBorder(null);
					blackPawn.setOpaque(false);
					blackPawn.setBackground(Color.LIGHT_GRAY);
					square.add(blackPawn);
				}
				// Make the outline of the board black
				if (j == 0 || j == BOARD_SIZE - 1 || i == 0 || i == BOARD_SIZE - 1) {
					square.setBackground(Color.BLACK);
				}
				gameBoardPanel.add(square, c);
			}
		}
		// Preferences
		gameBoardPanel.setBorder(new LineBorder(Color.BLACK));
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
										.addComponent(arrowPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
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
								.addComponent(remainingTime).addComponent(remainingWalls).addComponent(movemode))));
		dashBoardLayout.setVerticalGroup(dashBoardLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(dashBoardLayout.createSequentialGroup().addContainerGap().addComponent(playerLabel)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(remainingTime)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(remainingWalls)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(movemode)));
		dashboardPanel.setLayout(dashBoardLayout);
	}
}
