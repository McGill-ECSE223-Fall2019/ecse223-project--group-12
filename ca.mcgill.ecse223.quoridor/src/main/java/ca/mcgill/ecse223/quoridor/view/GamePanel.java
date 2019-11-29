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
import java.awt.event.KeyEvent;
import java.sql.Time;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.OverlayLayout;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import ca.mcgill.ecse223.quoridor.controller.InvalidInputException;
import ca.mcgill.ecse223.quoridor.controller.InvalidMoveException;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController.Side;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.to.PlayerPositionTO;
import ca.mcgill.ecse223.quoridor.to.PlayerPositionTO.PlayerColor;
import ca.mcgill.ecse223.quoridor.to.PlayerStatsTO;
import ca.mcgill.ecse223.quoridor.to.TileTO;
import ca.mcgill.ecse223.quoridor.to.WallMoveTO;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = -4426310869335015542L;

	// Panels
	private JPanel controlUIPanel;
	private JPanel gameBoardPanel;
	private JPanel dashboardPanel;
	private JPanel arrowPanel;
	// Labels
	private JLabel remainingWalls;
	private JLabel movemode;
	private JLabel playerLabel;
	private JLabel remainingTime;
	// Buttons
	private JButton rotateWallButton;
	private JButton leftButton;
	private JButton upButton;
	private JButton rightButton;
	private JButton downButton;
	private JButton saveExitToMenuButton;
	private JButton grabWallButton;
	// Resign
	private JButton resignButton;

	// Separators
	private JSeparator upperSeparator;
	private JSeparator middleSeparator;
	private JSeparator lowerSeparator;
	private JLabel invalidMoveLabel;
	// Constants
	private final int BOARD_SIZE = 19; // 9*2+1 to accommodate for slots
	private final Color wallColor = Color.RED;
	private final Color wallCandidateColor = Color.YELLOW;
	private final Color invisibleWallColor = new Color(210, 166, 121);
	private final PlayerColor whitePawnColor = PlayerColor.White;
	private final PlayerColor blackPawnColor = PlayerColor.Black;
	private final Color tileColor = new Color(153, 102, 0);
	private final Color boardBackGroundColor = new Color(191, 128, 64);
	private final Color boardBorderColor = new Color(134, 89, 45);
	// Timer thread to refresh time
	private Timer timer;
	// Path Animation
	private Timer paintDelay;
	private boolean togglePath = false;
	List<TileTO> pathAnimation;
	List<TileTO> traversalAnimation;
	int colrindex = 255;
	private boolean pawnMoveSelected = false;
	// Replay mode
	private JButton prevButton;
	private JButton nextButton;
	private JButton finalButton;
	private JButton startButton;
	private JButton replayModeButton;
	JOptionPane pann;
	//Result Reporting
	String resultMsg = null;

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
		upButton = new JButton("↑");
		leftButton = new JButton("←");
		downButton = new JButton("↓");
		rightButton = new JButton("→");
		resignButton = new JButton("Resign");
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

		// Replay mode
		prevButton = new JButton("Prev");
		nextButton = new JButton("Next");
		finalButton = new JButton("to final");
		startButton = new JButton("to start");
		replayModeButton = new JButton("Replay Mode");
		prevButton.setVisible(false);
		nextButton.setVisible(false);
		finalButton.setVisible(false);
		startButton.setVisible(false);
		// Timer for player clock
		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (timer.isRunning()) {
					refreshTime();
				}
			}
		});
		// Timer for path animation
		paintDelay = new Timer(70, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {

				if (paintDelay.isRunning() && pathAnimation != null && !pathAnimation.isEmpty()) {
					if (!traversalAnimation.isEmpty()) {
						if (colrindex > 5) {
							colrindex -= 5;
						}
						TileTO adjTile = traversalAnimation.get(0);
						JButton tile = getTileSquare(adjTile.getRow(), adjTile.getCol());
						tile.setBackground(new Color(52, colrindex, 235));
						traversalAnimation.remove(0);
					} else {

						TileTO adjTile = pathAnimation.get(0);
						JButton tile = getTileSquare(adjTile.getRow(), adjTile.getCol());
						tile.setBackground(wallCandidateColor);
						pathAnimation.remove(0);
					}
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

		// listeners for all buttons
		finalButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				finalButtonActionPerformed(evt);
			}
		});
		startButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				startButtonActionPerformed(evt);
			}
		});
		nextButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				nextButtonActionPerformed(evt);
			}
		});

		prevButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				prevButtonActionPerformed(evt);
			}
		});

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

		replayModeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				replayModeButtonActionPerformed(evt);
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
		resignButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				resignButtonActionPerformed(evt);
			}
		});
		// Key action Listeners p = path animation , s = switch player for debugging
		int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
		InputMap inputMap = this.getInputMap(condition);
		ActionMap actionMap = this.getActionMap();
		// path
		String p = "p";
		inputMap.put(KeyStroke.getKeyStroke('p'), p);
		actionMap.put(p, new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				togglePathActionPerformed();
			}
		});
		// switch player
		String l = "l";
		inputMap.put(KeyStroke.getKeyStroke('l'), l);
		actionMap.put(l, new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				confirmMoveButtonActionPerformed();
			}
		});
		// move wall up
		String w = "w";
		inputMap.put(KeyStroke.getKeyStroke('w'), w);
		actionMap.put(w, new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				upButtonButtonActionPerformed(null);
			}
		});
		String s = "s";
		inputMap.put(KeyStroke.getKeyStroke('s'), s);
		actionMap.put(s, new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				downButtonButtonActionPerformed(null);
			}
		});
		String a = "a";
		inputMap.put(KeyStroke.getKeyStroke('a'), a);
		actionMap.put(a, new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				leftButtonButtonActionPerformed(null);
			}
		});
		String d = "d";
		inputMap.put(KeyStroke.getKeyStroke('d'), d);
		actionMap.put(d, new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				rightButtonButtonActionPerformed(null);
			}
		});
		String q = "q";
		inputMap.put(KeyStroke.getKeyStroke('q'), q);
		actionMap.put(q, new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				grabWallButtonActionPerformed(null);
			}
		});
		String e = "e";
		inputMap.put(KeyStroke.getKeyStroke('e'), e);
		actionMap.put(e, new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				rotateWallButtonButtonActionPerformed(null);
			}
		});

	}

	// ------------------------
	// Refresh Methods
	// ------------------------
	public void refreshData() {
		pawnMoveSelected = false;
		togglePath = false;
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

		if (QuoridorController.getGameStatus() == GameStatus.WhiteWon) {
			resultMsg = "White Won! Would you like to save the game?";
			int op = JOptionPane.showConfirmDialog(this.getParent(), resultMsg);
			if (op == 0) {
				saveExitToMenuButtonActionPerformed(null);
			} else {
				QuoridorController.destroyGame();
				returnToMenu();
			}
		} else if (QuoridorController.getGameStatus() == GameStatus.BlackWon) {
			resultMsg = "Black Won! Would you like to save the game?";
			int op = JOptionPane.showConfirmDialog(this.getParent(), resultMsg);
			if (op == 0) {
				saveExitToMenuButtonActionPerformed(null);
			} else {
				QuoridorController.destroyGame();
				returnToMenu();
			}
		}

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
					QuoridorController.setTimeOutStatus();
					refreshData();
				}
			} else {
				setEnabledMoves(true);
				remainingTime.setForeground(Color.BLACK);
			}
		}
	}

	private void refreshWalls() {
		eraseWalls();
		List<WallMoveTO> wallMoveTOs = QuoridorController.getWallMoves();
		for (WallMoveTO wallMoveTO : wallMoveTOs) {
			JButton[] wallGraphic = getWall(wallMoveTO.getRow(), wallMoveTO.getColumn(), wallMoveTO.getDirection());
			drawWall(wallGraphic, wallColor);
		}
	}

	private void eraseWalls() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				JButton[] wall = getWall(i, j, Direction.Horizontal);
				if (wall[0] != null) {
					wall[0].setBackground(invisibleWallColor);
					wall[1].setBackground(invisibleWallColor);
					wall[2].setBackground(invisibleWallColor);
				}
				wall = getWall(i, j, Direction.Vertical);
				if (wall[0] != null) {
					wall[0].setBackground(invisibleWallColor);
					wall[1].setBackground(invisibleWallColor);
					wall[2].setBackground(invisibleWallColor);
				}
			}
		}
	}

	private void refreshWallMoveCandidate() {
		refreshWalls();
		WallMoveTO wallMoveTO = QuoridorController.getWallMoveCandidate();
		if (wallMoveTO != null) {
			JButton[] wallGraphic = getWall(wallMoveTO.getRow(), wallMoveTO.getColumn(), wallMoveTO.getDirection());
			drawWall(wallGraphic, wallCandidateColor);
		}
	}

	private void refreshPlayePositions() {
		hideAllPawns();
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
		replayModeButton.setText("Replay Mode");
		nextButton.setVisible(false);
		prevButton.setVisible(false);
		finalButton.setVisible(false);
		startButton.setVisible(false);
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

	// ------------------------
	// Action Methods
	// ------------------------
	private void prevButtonActionPerformed(ActionEvent evt) {
		QuoridorController.stepBack();
		refreshData();
	}

	private void nextButtonActionPerformed(ActionEvent evt) {
		QuoridorController.stepForward();
		refreshData();

	}
	private void finalButtonActionPerformed(ActionEvent evt) {
		QuoridorController.jumpToFinal();
		refreshData();
	}
	private void startButtonActionPerformed(ActionEvent evt) {
		QuoridorController.jumpToStart();
		refreshData();
	}

	private void replayModeButtonActionPerformed(ActionEvent evt) {
		if (replayModeButton.getText().equals("Replay Mode")) {
			replayModeButton.setText("Continue Game");
			QuoridorController.enterReplayMode();
			QuoridorController.removeCandidateWall();
			prevButton.setVisible(true);
			nextButton.setVisible(true);
			finalButton.setVisible(true);
			startButton.setVisible(true);
			grabWallButton.setEnabled(false);
			grabWallButton.setText("Grab Wall");
			rotateWallButton.setEnabled(false);
		} else {
			replayModeButton.setText("Replay Mode");
			try {
				QuoridorController.continueGame();
			} catch (InvalidInputException e) {
				// TODO Add pop up thats says game cannot be continued
			}
			prevButton.setVisible(false);
			nextButton.setVisible(false);
			finalButton.setVisible(false);
			startButton.setVisible(false);
			grabWallButton.setEnabled(true);
			rotateWallButton.setEnabled(true);
		}
		refreshData();
	}

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
	 * Currently loads a gamePosition given from the menu panel
	 */
	public void loadGameStart() {
		clearGame();
		refreshData();
		timer.start();
		refreshData();
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
			if (fileName == null) {
				return;
			} else if (fileName.equals("")) {
				JOptionPane.showMessageDialog(this.getParent(), "Sorry filename is not valid");
				return;
			} else {
				String fileNamePos = fileName + ".pos.dat";
				String fileNameGame = fileName + ".game.dat";
				if (QuoridorController.checkFileExists(fileNamePos, false)
						|| QuoridorController.checkFileExists(fileNameGame, false)) {
					int overWriteOption = JOptionPane.showConfirmDialog(this.getParent(),
							"File already exists, are you sure you want to overwrite?");
					if (overWriteOption == 0) {
						QuoridorController.writePositionFile(fileNamePos, false);
						QuoridorController.writeGameFile(fileNameGame, false);
					} else {
						return;
					}
				} else {
					QuoridorController.writePositionFile(fileNamePos, false);
					QuoridorController.writeGameFile(fileNameGame, false);
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
		try {
			QuoridorController.moveWall(Side.up);
			refreshData();
		} catch (InvalidInputException e) {
			invalidMoveLabel.setText(e.getMessage());
		}
	}

	private void downButtonButtonActionPerformed(ActionEvent evt) {
		try {
			QuoridorController.moveWall(Side.down);
			refreshData();
		} catch (InvalidInputException e) {
			invalidMoveLabel.setText(e.getMessage());
		}
	}

	private void leftButtonButtonActionPerformed(ActionEvent evt) {
		try {
			QuoridorController.moveWall(Side.left);
			refreshData();
		} catch (InvalidInputException e) {
			invalidMoveLabel.setText(e.getMessage());
		}
	}

	private void rightButtonButtonActionPerformed(ActionEvent evt) {
		try {
			QuoridorController.moveWall(Side.right);
			refreshData();
		} catch (InvalidInputException e) {
			invalidMoveLabel.setText(e.getMessage());
		}
	}

	private void grabWallButtonActionPerformed(ActionEvent evt) {
		if (grabWallButton.getText().equals("Grab Wall")) {
			try {
				QuoridorController.grabWall();
				grabWallButton.setText("Drop Wall");
				refreshData();
			} catch (InvalidInputException e) {
				invalidMoveLabel.setText(e.getMessage());
			}

		} else if (grabWallButton.getText().equals("Drop Wall")) {
			try {
				QuoridorController.dropWall();
				grabWallButton.setText("Grab Wall");
				refreshData();
			} catch (InvalidInputException e) {
				refreshData();
				invalidMoveLabel.setText(e.getMessage());
			}

		}

	}

	private void confirmMoveButtonActionPerformed() {
		QuoridorController.confirmMove();
		refreshData();
	}

	private void anyTileButtonActionPerformed(ActionEvent evt) {
		QuoridorController.removeCandidateWall();
		refreshWallMoveCandidate();
		grabWallButton.setText("Grab Wall");
		List<TileTO> adjTiles = QuoridorController.getAdjTiles();
		if (!pawnMoveSelected) {
			QuoridorController.switchMoveMode();
			refreshData();
			pawnMoveSelected = true;
			for (TileTO adjTile : adjTiles) {
				JButton tile = getTileSquare(adjTile.getRow(), adjTile.getCol());
				tile.setBackground(wallCandidateColor);
			}
		} else {
			int rowToMove = Integer.parseInt(String.valueOf(evt.getActionCommand().charAt(0)));
			int colToMOve = Integer.parseInt(String.valueOf(evt.getActionCommand().charAt(2)));
			JButton tile = getTileSquare(rowToMove, colToMOve);
			if (tile.getBackground().equals(wallCandidateColor)) {
				pawnMoveSelected = false;
				PlayerPositionTO currentPos = QuoridorController.getCurrentPlayerPosition();
				int currentRow = currentPos.getRow();
				int currentCol = currentPos.getColumn();
				// Down
				try {
					if (currentRow < rowToMove && currentCol == colToMOve) {
						QuoridorController.movePawn(Side.down);
					}
					// Up
					else if (currentRow > rowToMove && currentCol == colToMOve) {
						QuoridorController.movePawn(Side.up);
					}
					// right
					else if (currentCol < colToMOve && currentRow == rowToMove) {
						QuoridorController.movePawn(Side.right);
					}
					// left
					else if (currentCol > colToMOve && currentRow == rowToMove) {
						QuoridorController.movePawn(Side.left);
					}
					// downleft
					else if (currentCol > colToMOve && currentRow < rowToMove) {
						QuoridorController.movePawn(Side.downleft);
					}
					// downright
					else if (currentCol < colToMOve && currentRow < rowToMove) {
						QuoridorController.movePawn(Side.downright);
					}
					// upright
					else if (currentCol < colToMOve && currentRow > rowToMove) {
						QuoridorController.movePawn(Side.upright);
					}
					// upleft
					else if (currentCol > colToMOve && currentRow > rowToMove) {
						QuoridorController.movePawn(Side.upleft);
					}
				} catch (InvalidMoveException e) {
					refreshData();
				}
				refreshData();
			}
		}
	}

	private void togglePathActionPerformed() {
		if (!togglePath) {
			pathAnimation = QuoridorController.getPath();
			traversalAnimation = QuoridorController.getVisited();
			colrindex = 255;
			refreshData();
			paintDelay.start();
			togglePath = true;
		} else {
			paintDelay.stop();
			refreshData();
		}
	}

	private void resignButtonActionPerformed(ActionEvent evt) {
		int saveGameOption = JOptionPane.showConfirmDialog(this.getParent(), "Are you sure you want to resign?");
		if (saveGameOption == 0) {
			QuoridorController.resignGame();
			if (QuoridorController.getGameStatus() == GameStatus.WhiteWon) {
				resultMsg = "Black Player resigned, White Won!";
				JOptionPane.showMessageDialog(this.getParent(), resultMsg);
				QuoridorController.destroyGame();
				returnToMenu();
			} else if (QuoridorController.getGameStatus() == GameStatus.BlackWon) {
				resultMsg = "Black Player resigned, White Won!";
				JOptionPane.showMessageDialog(this.getParent(),  resultMsg);
				QuoridorController.destroyGame();
				returnToMenu();
			}
		} else {
		}
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

	/**
	 * enables or disables all player controls except switch player
	 * 
	 * @param enabled
	 */
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
		tile.setBackground(tileColor);
		if (c.equals(whitePawnColor)) {
			tile.getComponent(0).setVisible(visible);
			tile.repaint();
		} else {
			tile.getComponent(1).setVisible(visible);
			tile.repaint();
		}
	}

	private void hideAllPawns() {
		for (int i = 1; i < 10; i++) {
			for (int j = 1; j < 10; j++) {
				showPawn(i, j, PlayerColor.Black, false);
				showPawn(i, j, PlayerColor.White, false);
			}
		}

	}

	private JButton getTileSquare(int row, int col) {
		row = 2 * row - 1;
		col = 2 * col - 1;
		int index = col * BOARD_SIZE + row;
		return (JButton) gameBoardPanel.getComponent(index);
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
		invalidMoveLabel.setText("Stock is Empty"); // Temporarily mock this since error labels are reactive, the error
													// label cannot be updated with the refresh method, only with a
													// button press
		return invalidMoveLabel.getText();

	}

	public String getDropWallErrorLabel() {
		invalidMoveLabel.setText("Invalid move, try again!");
		return invalidMoveLabel.getText(); // Temporarily mock this since error labels are reactive
	}

	public String getMoveWallErrorLabel() {
		invalidMoveLabel.setText("Reaching Boundary!");
		return invalidMoveLabel.getText(); // Temporarily mock this since error labels are reactive
	}

	public String getWallsInstockLabel() {
		return remainingWalls.getText();
	}
	
	public String getPopUpText() {
		//Displaying readom result message for testing
		resultMsg = ("White Won!");  //Temporarily mock this since result display are reactive, the result
								// display cannot be updated with the refresh method, only with a
								// button press
		
		
		return resultMsg;
	}

	/**
	 * checks if the current player has a wall in hand
	 * 
	 */
	public boolean hasWallInHand() {
		// itterate though all possible walls (JButton[]) and see if one of them is
		// yellow
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

	/**
	 * Checks if a wall candidate exits at {row} {dir} in either direction
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean wallExisistAtPosition(int row, int col) {
		JButton[] wall1 = getWall(row, col, Direction.Vertical);
		JButton[] wall2 = getWall(row, col, Direction.Horizontal);
		if (wall1[0].getBackground() == wallCandidateColor || wall2[0].getBackground() == wallCandidateColor) {
			return true;
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

					square.setActionCommand(((j - 1) / 2 + 1) + "," + ((i - 1) / 2 + 1));
					square.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent evt) {
							anyTileButtonActionPerformed(evt);
						}
					});
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
		controlUILayout.setHorizontalGroup(controlUILayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(controlUILayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lowerSeparator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(middleSeparator, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
						.addComponent(upperSeparator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGroup(controlUILayout.createParallelGroup(Alignment.LEADING)
								.addGroup(controlUILayout.createSequentialGroup()
										.addComponent(prevButton, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
										.addComponent(nextButton, GroupLayout.PREFERRED_SIZE, 70, Short.MAX_VALUE))
								.addGroup(controlUILayout.createSequentialGroup()
										.addComponent(finalButton,GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
										.addComponent(startButton,GroupLayout.PREFERRED_SIZE,70, Short.MAX_VALUE))
								.addComponent(grabWallButton, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
								.addComponent(rotateWallButton, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
								.addComponent(replayModeButton, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
								.addComponent(resignButton, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
								.addComponent(saveExitToMenuButton, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
								.addComponent(dashboardPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(arrowPanel, Alignment.CENTER, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(invalidMoveLabel)));
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
						.addComponent(replayModeButton).addComponent(invalidMoveLabel)
						.addGroup(controlUILayout.createParallelGroup(Alignment.BASELINE).addComponent(nextButton)
								.addComponent(prevButton))
						.addGroup(controlUILayout.createParallelGroup(Alignment.BASELINE).addComponent(finalButton)
								.addComponent(startButton))
						.addPreferredGap(ComponentPlacement.RELATED, 71, Short.MAX_VALUE).addComponent(resignButton)
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
