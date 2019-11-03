package ca.mcgill.ecse223.quoridor.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.Board;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Move;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.User;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.model.WallMove;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game.MoveMode;
import ca.mcgill.ecse223.quoridor.to.PlayerPositionTO;
import ca.mcgill.ecse223.quoridor.to.PlayerPositionTO.PlayerColor;
import ca.mcgill.ecse223.quoridor.to.PlayerStatsTO;
import ca.mcgill.ecse223.quoridor.to.UserTO;
import ca.mcgill.ecse223.quoridor.to.WallMoveTO;

public class QuoridorController {

	public static final String TEST_SAVED_GAMES_FOLDER = "src\\test\\resources\\";
	public static final String SAVED_GAMES_FOLDER = "savedgames\\";

	public QuoridorController() {

	}

	private static Timer timer = new Timer();
	private static TimerTask timerTask;

	// ------------------------
	// Remi
	// ------------------------

	/**
	 * Create a new game with Initializing GameStatus
	 * 
	 * @author Remi Carriere
	 * @throws InvalidInputException
	 *             if the game is already running
	 */
	public static void initializeGame() throws InvalidInputException {
		if (QuoridorApplication.getQuoridor().getCurrentGame() == null) {
			new Game(GameStatus.Initializing, MoveMode.PlayerMove, QuoridorApplication.getQuoridor());
		} else {
			throw new InvalidInputException("A game is already running");
		}
	}

	/**
	 * Destroys the current game and the timer task associated with it
	 * 
	 * @author Remi Carriere
	 */
	public static void destroyGame() {
		if (QuoridorApplication.getQuoridor().getCurrentGame() != null) {
			QuoridorApplication.getQuoridor().getCurrentGame().delete();
			if (timerTask != null) {
				timerTask.cancel();
			}
		}
	}

	/**
	 * Creates a new user
	 * 
	 * @author Remi Carriere
	 * @param name
	 *            The name of the user
	 * @throws ca.mcgill.ecse223.quoridor.controller.InvalidInputException
	 *             if the user already exists or the username is invalid
	 */
	public static void createUser(String name) throws InvalidInputException {
		if (name.trim().length() == 0 || name == null || name.length() < 3) {
			throw new InvalidInputException("Name must 3 or more charcters!");
		}
		try {
			QuoridorApplication.getQuoridor().addUser(name);
		} catch (RuntimeException e) {
			throw new InvalidInputException("The username " + name + " already exists");
		}
	}

	/**
	 * Sets the remainingTime of each player to totalTime
	 * 
	 * @author Remi Carriere
	 * @param totalTime
	 *            The desired thinking time
	 * @throws InvalidInputException
	 *             if the time set is less than 0
	 */
	public static void setTotalThinkingTime(Time totalTime) throws InvalidInputException {
		// check if time is more than 0 seconds
		Time minTime = Time.valueOf("00:00:00");
		if (totalTime.compareTo(minTime) > 0) {
			Player w = QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer();
			Player b = QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer();
			b.setRemainingTime(totalTime);
			w.setRemainingTime(totalTime);
			QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(GameStatus.ReadyToStart);
		} else {
			throw new InvalidInputException("Total time has to be more than 0 seconds");
		}
	}

	/**
	 * Sets the remainingTime of each player to totalTime
	 * 
	 * @author Remi Carriere
	 * 
	 * @param minutes
	 *            integer representing minutes (2 digits)
	 * @param seconds
	 *            integer representing seconds(2 digits)
	 * @throws InvalidInputException
	 *             if the time set is less than 0, and if seconds or minutes is null
	 *             or not 2 digits each
	 * 
	 */
	public static void setTotalThinkingTime(Integer minutes, Integer seconds) throws InvalidInputException {
		if (minutes != null && seconds != null && minutes.toString().length() != 2
				&& seconds.toString().length() != 2) {
			if (seconds >= 0 && minutes >= 0) {
				Time time = Time.valueOf("00:" + minutes + ":" + seconds);
				setTotalThinkingTime(time);
			}
		} else {
			throw new InvalidInputException("Minutes and seconds cannot be null");
		}
	}

	/**
	 * Starts the game, starts a thread that refreshes the current players thinking
	 * time every second. If the game is not yet running, sets the GameStatus to
	 * Running and Initializes the board.
	 * 
	 * @author Remi Carriere
	 */
	public static void startClock() {
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		if (game.getGameStatus() == GameStatus.ReadyToStart) {
			game.setGameStatus(GameStatus.Running);
			if (game.getCurrentPosition() == null) {
				initBoard();
			}

			timerTask = new TimerTask() {
				public void run() {
					refreshPlayerTIme();
				}
			};
			timer.scheduleAtFixedRate(timerTask, 0, 1000);

		}
	}

	/**
	 * Validates the given game position, including any wall candidate in the
	 * current position
	 * 
	 * @author Remi Carriere
	 * @param gamePosition
	 *            The game position to verify
	 * @return true if the position is valid, false otherwise
	 */
	public static boolean validatePosition(GamePosition gamePosition) {
		if (gamePosition == null) {
			gamePosition = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		}

		Tile whiteTile = gamePosition.getWhitePosition().getTile();
		Tile blackTile = gamePosition.getBlackPosition().getTile();

		// Check overLapping pawns
		if (whiteTile.equals(blackTile)) {
			return false;
		}
		// Pawns are ok, check walls
		// Create a list of all walls on the board
		List<Wall> boardWalls = new ArrayList<Wall>();
		List<Wall> whitewalls = gamePosition.getWhiteWallsOnBoard();
		List<Wall> blackwalls = gamePosition.getBlackWallsOnBoard();
		boardWalls.addAll(blackwalls);
		boardWalls.addAll(whitewalls);
		// Add the current wall move candidate to the list
		WallMove candidateWallMove = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate();
		if (candidateWallMove != null) {
			Wall candidateWall = candidateWallMove.getWallPlaced();
			boardWalls.add(candidateWall);
		}

		// Case 1, only one wall, clearly no overlap or criss-cross
		if (boardWalls.size() < 2) {
			return true;
		}
		// Case 2, two or more walls, check for overlap and criss-cross
		else {
			for (Wall wall : boardWalls) {
				// Make a sublist to compare to the full list
				List<Wall> wallsToCompare = boardWalls.subList(boardWalls.indexOf(wall) + 1, boardWalls.size());
				for (Wall wallToCompare : wallsToCompare) {
					Tile tile = wall.getMove().getTargetTile();
					Tile tileToCompare = wallToCompare.getMove().getTargetTile();
					// Check for FULL overlap or criss-cross
					if (tile.equals(tileToCompare)) {
						return false;
					}
					// Check horizontal overlap
					else if (wall.getMove().getWallDirection() == Direction.Horizontal // same row
							&& wallToCompare.getMove().getWallDirection() == Direction.Horizontal && wall.getMove()
									.getTargetTile().getRow() == wallToCompare.getMove().getTargetTile().getRow()) {
						if (tile.getColumn() == tileToCompare.getColumn() + 1
								|| tile.getColumn() == tileToCompare.getColumn() - 1) {
							return false;
						}
					}
					// check vertical overlap
					else if (wall.getMove().getWallDirection() == Direction.Vertical // same column
							&& wallToCompare.getMove().getWallDirection() == Direction.Vertical
							&& wall.getMove().getTargetTile().getColumn() == wallToCompare.getMove().getTargetTile()
									.getColumn()) {
						if (tile.getRow() == tileToCompare.getRow() + 1
								|| tile.getRow() == tileToCompare.getRow() - 1) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * Validates the currentPosition of the board, including the current wall move
	 * candidate, In addition to checking a new pawn position at <row> <col> for the
	 * current player
	 * 
	 * @author Remi Carriere
	 * 
	 * @param row
	 *            The row of the tile for the player position
	 * @param col
	 *            The column of the tile for the player position
	 * @return true if the current position is valid and the new supplied pawn
	 *         position is valid, false otherwise
	 */
	public static boolean validatePosition(Integer row, Integer col) {
		boolean currentPositionIsValid = false;
		Game g = QuoridorApplication.getQuoridor().getCurrentGame();
		GamePosition currentPositionToCheck = g.getCurrentPosition();
		Player p;
		Tile tile = getTile(row, col);
		// check who's turn it is and the new position
		if (row != null && col != null) {
			if (currentPositionToCheck.getPlayerToMove().hasGameAsBlack()) {
				p = g.getBlackPlayer();
				PlayerPosition blackPos = new PlayerPosition(p, tile);
				currentPositionToCheck.setBlackPosition(blackPos);
			} else {
				p = g.getWhitePlayer();
				PlayerPosition whitePos = new PlayerPosition(p, tile);
				currentPositionToCheck.setWhitePosition(whitePos);
			}
		}
		// No use in adding a new wall move here, since that is already set as the wall
		// move candidate when a user grabs a wall
		currentPositionIsValid = validatePosition(currentPositionToCheck);
		return currentPositionIsValid;
	}

	/**
	 * Validates the currentPosition of the board, including the current wall move
	 * candidate
	 * 
	 * @author Remi Carriere
	 * @return true if the current position is valid, false otherwise
	 *
	 */
	public static boolean validatePosition() {
		GamePosition gamePosition = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		return validatePosition(gamePosition);
	}

	/**
	 * 
	 * Sets the given user as the white player, and adds the walls for the player
	 * 
	 * @author Remi Carriere
	 * @param user
	 *            The selected user
	 * @throws InvalidInputException
	 *             if the input user is null
	 */
	public static void setWhitePlayerInGame(User user) throws InvalidInputException {
		if (user != null) {
			Player player = new Player(null, user, 1, Direction.Vertical);
			if (player.getWalls().isEmpty()) {
				for (int j = 0; j < 10; j++) {
					new Wall(j, player);
				}
			}
			QuoridorApplication.getQuoridor().getCurrentGame().setWhitePlayer(player);
		} else {
			throw new InvalidInputException("cannot set null user as player");
		}
	}

	/**
	 * Sets the given user as the white player, and adds the walls for the player
	 * 
	 * @author Remi Carriere
	 * @param userName
	 *            The name of the user
	 * @throws InvalidInputException
	 *             if the input user does not exist
	 */
	public static void setWhitePlayerInGame(String userName) throws InvalidInputException {
		User user = getUserByName(userName);
		setWhitePlayerInGame(user);
	}

	/**
	 * 
	 * Sets the given user as the black player, and adds the walls for the player
	 * 
	 * @author Remi Carriere
	 * @param user
	 *            The selected user
	 * @throws InvalidInputException
	 *             if the input user is null
	 */
	public static void setBlackPlayerInGame(User user) throws InvalidInputException {
		if (user != null) {
			Player player = new Player(null, user, 9, Direction.Vertical);
			if (player.getWalls().isEmpty()) {
				for (int j = 0; j < 10; j++) {
					new Wall(j + 10, player);
				}
			}
			QuoridorApplication.getQuoridor().getCurrentGame().setBlackPlayer(player);
		} else {
			throw new InvalidInputException("cannot set null user as player");
		}
	}

	/**
	 * Sets the given user as the black player, and adds the walls for the player
	 * 
	 * @author Remi Carriere
	 * @param userName
	 *            The name of the user
	 * @throws InvalidInputException
	 *             if the input user does not exist
	 */
	public static void setBlackPlayerInGame(String userName) throws InvalidInputException {
		User user = getUserByName(userName);
		setBlackPlayerInGame(user);
	}

	/**
	 * Creates new user, and sets the user as the white player
	 * 
	 * @author Remi Carriere
	 * @param name
	 *            Name of the user
	 * @throws InvalidInputException
	 * 
	 */
	public static void setNewUserAsWhite(String name) throws InvalidInputException {
		createUser(name);
		setWhitePlayerInGame(name);
	}

	/***
	 * Creates new user, and sets the user as the black player
	 * 
	 * @author Remi Carriere
	 * @param name
	 *            Name of the user
	 * @throws InvalidInputException
	 */
	public static void setNewUserAsBlack(String name) throws InvalidInputException {
		createUser(name);
		setBlackPlayerInGame(name);
	}

	/**
	 * Removes the current wall move candidate. Will be useful when a player changes
	 * his mind between wall move and player move
	 * 
	 * @author Remi Carriere
	 */
	public static void removeCandidateWall() {
		QuoridorApplication.getQuoridor().getCurrentGame().setWallMoveCandidate(null);
	}

	/*
	 * Query Methods
	 * 
	 */

	/**
	 * Gets the stats of the current player to move
	 * 
	 * @author Remi Carriere
	 * 
	 * @return a wrapper object containing the players remaining walls, name,
	 *         moveMode and remiaining time
	 */
	public static PlayerStatsTO getPlayerStats() {
		String name;
		Time remainingTime;
		int remainingWalls;
		String moveMode;

		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		if (game != null && game.getGameStatus() == GameStatus.Running) {
			Player p = game.getCurrentPosition().getPlayerToMove();

			name = p.getUser().getName();
			if (p.hasGameAsBlack()) {
				name = "Black - " + name;
				remainingWalls = game.getCurrentPosition().getBlackWallsInStock().size();
			} else {
				name = "White - " + name;
				remainingWalls = game.getCurrentPosition().getWhiteWallsInStock().size();
			}
			remainingTime = p.getRemainingTime();
			moveMode = game.getMoveMode().toString();
			return new PlayerStatsTO(name, remainingTime, remainingWalls, moveMode);
		}
		return null;

	}

	/**
	 * Gets the current player positions on the board
	 * 
	 * @author Remi Carriere
	 * @return A list of playerPositionTOs (wrapper object containing the row and
	 *         column of the tile of the player position)
	 */
	public static List<PlayerPositionTO> getPlayerPositions() {
		ArrayList<PlayerPositionTO> playerPositions = new ArrayList<PlayerPositionTO>();
		GamePosition gp = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		int row1 = gp.getBlackPosition().getTile().getRow();
		int col1 = gp.getBlackPosition().getTile().getColumn();
		int row2 = gp.getWhitePosition().getTile().getRow();
		int col2 = gp.getWhitePosition().getTile().getColumn();
		PlayerPositionTO black = new PlayerPositionTO(row1, col1, PlayerColor.Black);
		PlayerPositionTO white = new PlayerPositionTO(row2, col2, PlayerColor.White);
		playerPositions.add(white);
		playerPositions.add(black);
		return playerPositions;
	}

	/**
	 * Gets a list of all Users of the Quoridor Application so that players can
	 * select user name
	 * 
	 * @author Remi Carriere
	 * @return List<UserTO> A list of all existing users
	 */
	public static List<UserTO> getAllUsers() {
		ArrayList<UserTO> users = new ArrayList<UserTO>();
		for (User user : QuoridorApplication.getQuoridor().getUsers()) {
			UserTO userTO = new UserTO(user.getName());
			users.add(userTO);
		}
		return users;
	}

	/**
	 * Gets the locations of all the walls that are on the board
	 * 
	 * @author Remi Carriere
	 * @return A list of wallMoveTOs (wrapper object containing the row, column and
	 *         direction of the wall move)
	 */
	public static List<WallMoveTO> getWallMoves() {
		List<WallMoveTO> wallMoveTOs = new ArrayList<WallMoveTO>();

		GamePosition gp = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		// Create a list of all walls on the board
		List<Wall> boardWalls = new ArrayList<Wall>();
		List<Wall> whitewalls = gp.getWhiteWallsOnBoard();
		List<Wall> blackwalls = gp.getBlackWallsOnBoard();
		boardWalls.addAll(blackwalls);
		boardWalls.addAll(whitewalls);
		for (Wall wall : boardWalls) {
			WallMove wm = wall.getMove();
			WallMoveTO wmto = new WallMoveTO(wm.getTargetTile().getRow(), wm.getTargetTile().getColumn(),
					wm.getWallDirection());
			wallMoveTOs.add(wmto);
		}
		return wallMoveTOs;
	}

	/**
	 * Gets the location of the current wall move candidate
	 * 
	 * @author Remi Carriere
	 * @return wallMoveTO of current candidate (wrapper object containing the row,
	 *         column and direction of the wall move)
	 */
	public static WallMoveTO getWallMoveCandidate() {
		WallMove wm = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate();
		if (wm != null) {
			return new WallMoveTO(wm.getTargetTile().getRow(), wm.getTargetTile().getColumn(), wm.getWallDirection());
		}
		return null;
	}

	/*
	 * Private Helper Methods
	 * 
	 */

	/**
	 * Method that is called by the background thread mentioned above
	 * 
	 * @author Remi Carriere
	 */
	private static void refreshPlayerTIme() {
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		if (game != null && game.getGameStatus() == GameStatus.Running) {
			Player p = game.getCurrentPosition().getPlayerToMove();
			Time time = p.getRemainingTime();
			Time minTime = Time.valueOf("00:00:00");
			// Stop decrementing time when we reach 0
			if (!minTime.equals(time)) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(time);
				cal.add(Calendar.SECOND, -1);
				Time newTime = new Time(cal.getTimeInMillis());
				p.setRemainingTime(newTime);
			}
		}
	}

	/**
	 * Gets a user of quoridor by username
	 * 
	 * @author Remi Carriere
	 * @param name
	 * @return
	 */
	private static User getUserByName(String name) {
		Iterator<User> users = QuoridorApplication.getQuoridor().getUsers().iterator();
		while (users.hasNext()) {
			User u = users.next();
			if (u.getName().equals(name)) {
				return u;
			}
		}
		return null;
	}

	/**
	 * Gets the Tile at the specified coordinates
	 * 
	 * @author Remi Carriere
	 * @param row
	 * @param col
	 * @return
	 */
	private static Tile getTile(int row, int col) {
		Iterator<Tile> itr = QuoridorApplication.getQuoridor().getBoard().getTiles().iterator();
		while (itr.hasNext()) {
			Tile t = itr.next();
			if (t.getRow() == row && t.getColumn() == col) {
				return t;
			}
		}
		throw new java.lang.IllegalArgumentException("tile does not exist:  row=" + row + " col=" + col);

	}

	// ------------------------
	// Francis
	// ------------------------

	/**
	 * Loads a previously
	 * 
	 * d game position into the current game position
	 * 
	 * @author Francis Comeau Gherkin feature: LoadPosition.feature
	 * @param fullPath
	 *            of the saved file
	 * @return True if load was successful, false is unable to load
	 */
	public static boolean loadPosition(String fileName, boolean test) {
		// Initialize Game Add default users and time
		try {
			initializeGame();
			User defaultWhite = getUserByName("User 1");
			User defaultBlack = getUserByName("User 2");
			if (defaultWhite == null) {
				createUser("User 1");
				defaultWhite = getUserByName("User 1");
			}
			if (defaultBlack == null) {
				createUser("User 2");
				defaultBlack = getUserByName("User 2");
			}
			setWhitePlayerInGame(defaultWhite);
			setBlackPlayerInGame(defaultBlack);
			setTotalThinkingTime(Time.valueOf("00:30:00"));
			startClock(); // Francis: I put this here, but you only need to call it right before creating
							// the new game position, seems to be working ok like this though
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}

		String fullPath = SAVED_GAMES_FOLDER + fileName;
		// Necessary since Travis CI expects resources created during tests to be in
		// test folder
		if (test) {
			fullPath = fileName;
		}

		// validate text file
		if (!validateTextFile(fullPath)) {
			return false;
		}

		// extract text line for each player
		File file = new File(fullPath);
		String firstPlayerLine = new String();
		String secondPlayerLine = new String();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			try {
				firstPlayerLine = br.readLine();
				secondPlayerLine = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// parse text data into objects and variables
		String whitePlayerLine, blackPlayerLine;
		Player whitePlayer, blackPlayer, playerToMove;
		Game myGame = QuoridorApplication.getQuoridor().getCurrentGame();
		whitePlayer = myGame.getWhitePlayer();
		blackPlayer = myGame.getBlackPlayer();

		if (firstPlayerLine.charAt(0) == 'W') {
			whitePlayerLine = firstPlayerLine;
			blackPlayerLine = secondPlayerLine;
			playerToMove = whitePlayer;
		} else {
			whitePlayerLine = secondPlayerLine;
			blackPlayerLine = firstPlayerLine;
			playerToMove = blackPlayer;
		}

		int whiteCol, whiteRow, blackCol, blackRow;
		PlayerPosition whitePos, blackPos;
		Tile whiteTile, blackTile;

		whiteCol = whitePlayerLine.charAt(3) - 96;
		whiteRow = whitePlayerLine.charAt(4) - 48;
		whiteTile = getTile(whiteRow, whiteCol);
		whitePos = new PlayerPosition(whitePlayer, whiteTile);

		blackCol = blackPlayerLine.charAt(3) - 96;
		blackRow = blackPlayerLine.charAt(4) - 48;
		blackTile = getTile(blackRow, blackCol);
		blackPos = new PlayerPosition(blackPlayer, blackTile);

		int id;
		List<GamePosition> posList = QuoridorApplication.getQuoridor().getCurrentGame().getPositions();
		if (posList.isEmpty()) {
			id = 0;
		} else {
			id = posList.size();
		}

		GamePosition loadedPos = myGame.getCurrentPosition();
		loadedPos.setWhitePosition(whitePos);
		loadedPos.setBlackPosition(blackPos);
		loadedPos.setId(id);
		loadedPos.setPlayerToMove(playerToMove);
		// Add walls for each player
		loadWalls(whitePlayerLine, whitePlayer);
		loadWalls(blackPlayerLine, blackPlayer);
		myGame.setCurrentPosition(loadedPos);
		// validate and set position into model
		return validatePosition();
	}

	private static void loadWalls(String line, Player player) {
		if (line.length() <= 5) {
			return;
		}

		int col, row;
		Direction direction;
		Wall wall;

		Game g = QuoridorApplication.getQuoridor().getCurrentGame();
		GamePosition gp = g.getCurrentPosition();

		for (int i = 0; i < ((line.length() / 5) - 1); i++) {
			if (player.hasGameAsWhite()) {
				wall = gp.getWhiteWallsInStock(0);
				gp.removeWhiteWallsInStock(wall);
			} else {
				wall = gp.getBlackWallsInStock(0);
				gp.removeBlackWallsInStock(wall);
			}

			col = line.charAt(i * 5 + 7) - 96;
			row = line.charAt(i * 5 + 8) - 48;
			if (line.charAt(i * 5 + 9) == 'v') {
				direction = Direction.Vertical;
			} else {
				direction = Direction.Horizontal;
			}
			Tile tile = getTile(row, col);
			new WallMove(i, 0, player, tile, g, direction, wall);
			if (player.hasGameAsWhite()) {
				gp.addWhiteWallsOnBoard(wall);
			} else {
				gp.addBlackWallsOnBoard(wall);
			}
		}
		QuoridorApplication.getQuoridor().getCurrentGame().setCurrentPosition(gp);
	}

	private static boolean validateTextFile(String path) {
		File file = new File(path);

		String firstPlayerLine = new String();
		String secondPlayerLine = new String();
		String thirdLine = new String();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			try {
				firstPlayerLine = br.readLine();
				secondPlayerLine = br.readLine();
				thirdLine = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// check if file contains exactly 2 lines
		if (firstPlayerLine == null || secondPlayerLine == null || thirdLine != null) {
			return false;
		}

		// check if file contains white player and black player
		if (firstPlayerLine.charAt(0) == 'W') {
			if (secondPlayerLine.charAt(0) != 'B') {
				return false;
			}
		} else if (firstPlayerLine.charAt(0) == 'B') {
			if (secondPlayerLine.charAt(0) != 'W') {
				return false;
			}
		} else {
			return false;
		}

		// check if each line is correct format
		if (!validateTextLine(firstPlayerLine) || !validateTextLine(firstPlayerLine)) {
			return false;
		}

		return true;
	}

	private static boolean validateTextLine(String line) {
		int l = line.length();
		if (l < 5 || l > 55) {
			return false;
		}

		String regPattern = "^(B|W): [a-i][1-9]";
		String subString = line.substring(0, 5);
		if (!subString.matches(regPattern)) {
			return false;
		}

		if (l > 5) {
			if (l % 5 != 0) {
				return false;
			}

			regPattern = "^";
			for (int i = 0; i < ((l / 5) - 1); i++) {
				regPattern += ", [a-i][1-9](v|h)";
			}
			regPattern += "$";

			subString = line.substring(5);

			if (!subString.matches(regPattern)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Saves the current game position into a file
	 * 
	 * @author Francis Comeau Gherkin feature: SavePosition.feature @param
	 *         gamePosiion to save and fileName of what to save it as @return True
	 *         if load was successful, false is unable to load @throws
	 */
	public static void writePositionFile(String fileName, boolean test) {
		GamePosition gamePos = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();

		// make string for white player's pawn and wall positions
		char column;

		Tile whitePlayerTile = gamePos.getWhitePosition().getTile();
		column = (char) (whitePlayerTile.getColumn() + 96);
		String whitePos = "W: " + column + whitePlayerTile.getRow();
		WallMove wallMove = null;
		for (int i = 0; i < gamePos.getWhiteWallsOnBoard().size(); i++) {
			wallMove = gamePos.getWhiteWallsOnBoard(i).getMove();
			column = (char) (wallMove.getTargetTile().getColumn() + 96);
			whitePos += ", " + column + wallMove.getTargetTile().getRow()
					+ wallMove.getWallDirection().toString().toLowerCase().charAt(0);
		}
		// make string for black player's pawn and wall positions
		Tile blackPlayerTile = gamePos.getBlackPosition().getTile();
		column = (char) (blackPlayerTile.getColumn() + 96);
		String blackPos = "B: " + column + blackPlayerTile.getRow();
		for (int i = 0; i < gamePos.getBlackWallsOnBoard().size(); i++) {
			wallMove = gamePos.getBlackWallsOnBoard(i).getMove();
			column = (char) (wallMove.getTargetTile().getColumn() + 96);

			blackPos += ", " + column + wallMove.getTargetTile().getRow()
					+ wallMove.getWallDirection().toString().toLowerCase().charAt(0);
		}

		// save whitePos and blackPos to file
		PrintWriter pw;
		String fullPath = SAVED_GAMES_FOLDER + fileName;
		// Necessary since Travis CI expects resources created during tests to be in
		// test folder
		if (test) {
			fullPath = TEST_SAVED_GAMES_FOLDER + fileName;
		}
		try {
			pw = new PrintWriter(fullPath, "UTF-8");
			if (gamePos.getPlayerToMove().equals(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer())) {
				pw.println(whitePos);
				pw.print(blackPos);
			} else if (gamePos.getPlayerToMove()
					.equals(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer())) {
				pw.println(blackPos);
				pw.print(whitePos);
			}
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Asks the user if they want to overwrite the already existing file
	 * 
	 * @author Francis Comeau
	 * @return The user's answer to overwriting the file
	 */

	public static boolean checkFileExists(String fileName, boolean test) {

		String fullPath = SAVED_GAMES_FOLDER + fileName;
		if (test) {
			fullPath = TEST_SAVED_GAMES_FOLDER + fileName;
		}

		File file = new File(fullPath);
		if (file.exists()) {
			return true;
		}

		return false;
	}

	public static void savePosition(String fileName, boolean overWrite, boolean test) {
		if (!checkFileExists(fileName, test) || overWrite) {
			writePositionFile(fileName, test);
		}
	}

	// ------------------------
	// Weige
	// ------------------------

	/**
	 * @author Weige qian Gherkin feature:InitializeBoard.feature
	 * @throws InvalidInputException
	 */
	public static void initBoard() {
		/*Board board;
		Player w = QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer();
		if (QuoridorApplication.getQuoridor().getBoard() == null) {
			// create a new board
			board = new Board(QuoridorApplication.getQuoridor());
			// Creating tiles by rows, i.e., the column index changes with every tile
			// creation
			for (int i = 1; i <= 9; i++) { // rows
				for (int j = 1; j <= 9; j++) { // columns
					board.addTile(i, j);
				}
			}
		}

		// update to use getTile() and set to tiles E1 and E9

		Tile player1StartPos = getTile(9, 5);
		Tile player2StartPos = getTile(1, 5);

		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		PlayerPosition player1Position = new PlayerPosition(game.getWhitePlayer(), player1StartPos);
		PlayerPosition player2Position = new PlayerPosition(game.getBlackPlayer(), player2StartPos);

		GamePosition gamePosition = new GamePosition(0, player1Position, player2Position, w, game);
		// Add the walls as in stock for the players
		Player white = game.getWhitePlayer();
		Player black = game.getBlackPlayer();
		List<Wall> whiteWalls = white.getWalls();
		List<Wall> blackWalls = black.getWalls();

		for (Wall wall : whiteWalls) {
			gamePosition.addWhiteWallsInStock(wall);
		}
		for (Wall wall : blackWalls) {
			gamePosition.addBlackWallsInStock(wall);
		}
		game.setCurrentPosition(gamePosition);
		if (game.getGameStatus() == GameStatus.ReadyToStart) {
			startClock();
		}*/
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game g = quoridor.getCurrentGame();
		Player b = g.getBlackPlayer();
		Player w = g.getWhitePlayer();
		GamePosition gp;
		Board currentBoard = quoridor.getBoard();
		if (currentBoard == null) {
			currentBoard = new Board(quoridor);
			for (int i = 1;i<10;i++) {
				for (int j = 1;j<10;j++) {
					currentBoard.addTile(i, j);
				}
			}
		}
		gp = new GamePosition(0,new PlayerPosition(w,getTile(9,5)),new PlayerPosition(b,getTile(1,5)),w,g);
		for (int i = 0;i < 10;i++) {
			if (i < b.getWalls().size()) {
				gp.addBlackWallsInStock(b.getWall(i));
			}
			if (i < b.getWalls().size()) {
				gp.addWhiteWallsInStock(w.getWall(i));
			}
		}
		g.setCurrentPosition(gp);
		if (g.getGameStatus() == GameStatus.ReadyToStart) {
			startClock();	
		}	
	}

	/**
	 * 
	 * @param player
	 */
	/*public static void makeMove() {
		Game g = QuoridorApplication.getQuoridor().getCurrentGame();
		GamePosition gp = g.getCurrentPosition();
		Player p = gp.getPlayerToMove();
		if (p.hasGameAsWhite()) {
			gp.setPlayerToMove(g.getBlackPlayer());
		} else if (p.hasGameAsBlack()) {
			gp.setPlayerToMove(g.getWhitePlayer());
		}
		g.setCurrentPosition(gp);

	}*/
	private static GamePosition copyGamePosition(GamePosition gp) {
		Game g = gp.getGame();
		GamePosition newGp;
		Tile white = gp.getWhitePosition().getTile();
		Player w = g.getWhitePlayer();
		Tile black = gp.getBlackPosition().getTile();
		Player b = g.getBlackPlayer();
		PlayerPosition wP = new PlayerPosition(w,white);
		PlayerPosition bP = new PlayerPosition(b,black);
		newGp = new GamePosition(gp.getId()+1,wP,bP,w,g);
		for(Wall wall:gp.getBlackWallsInStock()) {
			newGp.addBlackWallsInStock(wall);
		}
		for(Wall wall:gp.getWhiteWallsInStock()) {
			newGp.addWhiteWallsInStock(wall);
		}
		for(Wall wall:gp.getWhiteWallsOnBoard()) {
			newGp.addWhiteWallsOnBoard(wall);
		}
		for(Wall wall:gp.getBlackWallsOnBoard()) {
			newGp.addBlackWallsOnBoard(wall);
		}

		return newGp;
		
	}
	public static void confirmMove() {
		Game g = QuoridorApplication.getQuoridor().getCurrentGame();
		GamePosition gp = g.getCurrentPosition();
		GamePosition newGp = copyGamePosition(gp);
		Player p = gp.getPlayerToMove();
		if (p.hasGameAsWhite()) {
			newGp.setPlayerToMove(g.getBlackPlayer());
		} else if (p.hasGameAsBlack()) {
			newGp.setPlayerToMove(g.getWhitePlayer());
		}
		g.addPosition(newGp);
		g.setCurrentPosition(newGp);

	}

	// ------------------------
	// Kaan
	// ------------------------

	/**
	 * Grabs a wall from the current players stock
	 * 
	 * @author Kaan Gure Gherkin Feature: GrabWall.feature
	 * @throws InvalidInputException
	 */

	public static void grabWall() throws InvalidInputException {
		Game g = QuoridorApplication.getQuoridor().getCurrentGame();

		GamePosition gp = g.getCurrentPosition();
		Player p = gp.getPlayerToMove();
		Wall w = null;
		Tile tile = getTile(1, 1); // just to avoid null pointers for now
		if (p.hasGameAsWhite()) { 
			tile = getTile(8, 5);
			// check if wall left in stock
			if (gp.getWhiteWallsInStock().size() == 0) {
				throw new InvalidInputException("Stock is Empty");
			}
			w = gp.getWhiteWallsInStock(0); //grab first wall in the stock

			gp.removeWhiteWallsInStock(w); //remove garbbed wall from stock
		} else if (p.hasGameAsBlack()) {
			tile = getTile(1, 5);
			if (gp.getBlackWallsInStock().size() == 0) {
				throw new InvalidInputException("Stock is Empty");
			}
			w = gp.getBlackWallsInStock(0); //grab first wall in the stock

			gp.removeBlackWallsInStock(w); //remove garbbed wall from stock
		}
		WallMove wm = new WallMove(0, 0, p, tile, g, Direction.Vertical, w);
		g.setWallMoveCandidate(wm);
		g.setMoveMode(MoveMode.WallMove);
	}

	/**
	 * Drops the wall from the current player's hand to the board
	 * 
	 * @author Kaan Gure Gherkin Feature: DropWall.feature
	 * @throws InvalidInputException
	 */

	public static void dropWall() throws InvalidInputException {
		Game g = QuoridorApplication.getQuoridor().getCurrentGame();
		GamePosition gp = g.getCurrentPosition();
		WallMove wallMove = g.getWallMoveCandidate();
		Wall wall = wallMove.getWallPlaced();
		Player p = gp.getPlayerToMove();
		if (validatePosition()) { //check if wall move is valid
			if (p.hasGameAsWhite()) {
				gp.addWhiteWallsOnBoard(wall);
			} else if (p.hasGameAsBlack()) {
				gp.addBlackWallsOnBoard(wall);
			}
			g.setWallMoveCandidate(null);
			g.setMoveMode(MoveMode.PlayerMove);

			List<Move> moves = g.getMoves(); //increment the round number and move number
			int ms = moves.size();
			Move lastMove = moves.get(ms-1);
			int currentRound = lastMove.getRoundNumber();
			int currentMove = lastMove.getMoveNumber();
			Move nextMove = lastMove;
			nextMove.setRoundNumber(currentRound++);
			nextMove.setMoveNumber(currentMove++);
			lastMove.setNextMove(nextMove);

			confirmMove();
		} else {
			throw new InvalidInputException("Invalid move, try again!");
		}

	}

	// ------------------------
	// Zechen
	// ------------------------

	/**
	 * @author Zechen Ren Gherkin feature: RotateWall.feature
	 */
	public static void rotateWall() {
		WallMove wm = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate();
		if (wm != null && wm.getWallDirection() == Direction.Vertical) {
			wm.setWallDirection(Direction.Horizontal);
		} else if (wm != null && wm.getWallDirection() == Direction.Horizontal) {
			wm.setWallDirection(Direction.Vertical);
		}
		QuoridorApplication.getQuoridor().getCurrentGame().setWallMoveCandidate(wm);
	}

	/**
	 * @author Zechen Ren Gherkin feature: MoveWall.feature
	 * @throws InvalidInputException
	 */

	public static void moveWall(String side) throws InvalidInputException {
		WallMove wm = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate();
		if (wm != null) {
			if (side.contains("up")) {
				int urow = wm.getTargetTile().getRow() - 1;
				int ucol = wm.getTargetTile().getColumn();
				if (urow > 0) {
					wm.setTargetTile(getTile(urow, ucol));
					QuoridorApplication.getQuoridor().getCurrentGame().setWallMoveCandidate(wm);
				} else {
					throw new InvalidInputException("Reaching Top Boundary!");
				}
			} else if (side.contains("down")) {
				int drow = wm.getTargetTile().getRow() + 1;
				int dcol = wm.getTargetTile().getColumn();
				if (drow < 9) {
					wm.setTargetTile(getTile(drow, dcol));
					QuoridorApplication.getQuoridor().getCurrentGame().setWallMoveCandidate(wm);
				} else {
					throw new InvalidInputException("Reaching Bottom Boundary!");
				}
			} else if (side.contains("left")) {
				int lrow = wm.getTargetTile().getRow();
				int lcol = wm.getTargetTile().getColumn() - 1;
				if (lcol > 0) {
					wm.setTargetTile(getTile(lrow, lcol));
					QuoridorApplication.getQuoridor().getCurrentGame().setWallMoveCandidate(wm);
				} else {
					throw new InvalidInputException("Reaching Left Boundary!");
				}
			} else if (side.contains("right")) {
				int rrow = wm.getTargetTile().getRow();
				int rcol = wm.getTargetTile().getColumn() + 1;
				if (rcol < 9) {
					wm.setTargetTile(getTile(rrow, rcol));
					QuoridorApplication.getQuoridor().getCurrentGame().setWallMoveCandidate(wm);
				} else {
					throw new InvalidInputException("Reaching Right Boundary!");
				}
			}
		}
	}
}
