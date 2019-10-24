package ca.mcgill.ecse223.quoridor.controller;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.Board;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.User;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.model.WallMove;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game.MoveMode;
import ca.mcgill.ecse223.quoridor.to.UserTO;

public class QuoridorController {

	public QuoridorController() {

	}

	// ------------------------
	// Remi
	// ------------------------

	/**
	 * Create a new game with Initializing GameStatus
	 * 
	 * @author Remi Carriere
	 * @throws InvalidInputException
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static void initializeGame() throws InvalidInputException {
		if (QuoridorApplication.getQuoridor().getCurrentGame() == null) {
			new Game(GameStatus.Initializing, MoveMode.PlayerMove, QuoridorApplication.getQuoridor());
		} else {
			throw new InvalidInputException("A game is already running");
		}
	}
	/**
	 * 
	 */
	public static void destroyGame() {
		if (QuoridorApplication.getQuoridor().getCurrentGame() != null) {
			QuoridorApplication.getQuoridor().getCurrentGame().delete();
		}
	}

	/**
	 * Creates a new user
	 * 
	 * @author Remi Carriere
	 * @param name
	 *            The name of the user
	 * @throws java.lang.UnsupportedOperationException
	 * @throws ca.mcgill.ecse223.quoridor.controller.InvalidInputException
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
	 * @throws java.lang.UnsupportedOperationException
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
	 * 
	 * @param minutes
	 * @param seconds
	 * @throws InvalidInputException
	 */
	public static void setTotalThinkingTime(Integer minutes, Integer seconds) throws InvalidInputException {
		if (minutes != null && seconds != null) {
			if (seconds >= 0 && minutes >= 0) {
				Time time = Time.valueOf("00:" + minutes + ":" + seconds);
				setTotalThinkingTime(time);
			}
		} else {
			throw new InvalidInputException("Minutes and seconds cannot be null");
		}
	}

	/**
	 * Starts the clock for player that has the current turn. If the game is not yet
	 * running, sets the GameStatus to Running and Initializes the board
	 * 
	 * @author Remi Carriere
	 * @throws InvalidInputException
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static void startClock() throws InvalidInputException {
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		if (game.getGameStatus() == GameStatus.ReadyToStart) {
			initBoard();
			game.setGameStatus(GameStatus.Running);
		} else {
			throw new InvalidInputException("Game must be ready to start before starting the clock");
		}

	}

	/**
	 * Verfies if the given GamePosition is legal
	 * 
	 * @author Remi Carriere
	 * @param gamePosition
	 *            The game position to verify
	 * @return
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static boolean validatePosition(Integer row, Integer col, String dir) {
		boolean tileExists = false;
		boolean currentPositionIsValid = false;
		// Check if the new Pawn move is in bounds
		if (row != null && col != null && dir == null) {
			if (row < 10 && row > 0 && col < 10 && col > 0) {
				tileExists = true;
			}
		}
		// Check if the new Wall move is in bounds
		else if (row != null && col != null && dir != null) {
			if (row < 9 && row > 0 && col < 9 && col > 0) {
				tileExists = true;
			}
		}
		// SUpplied coordinates were all null, just check the current position
		else {
			tileExists = true;
		}
		// Validate the current position (including current wall move candidate)
		currentPositionIsValid = validateCurrentWallPositions();

		return tileExists && currentPositionIsValid;
	}
	/**
	 * 
	 * @return
	 */
	private static boolean validateCurrentWallPositions() {

		GamePosition gp = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();

		//// Just to test if the method works when adding a new wall
		// Player p =
		// QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer();
		// Game g = QuoridorApplication.getQuoridor().getCurrentGame();
		// Wall newWall =
		// QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock()
		// .get(5);
		// Tile tile1 = TestUtil.getTile(2, 1);
		// WallMove newMove = new WallMove(0, 0, p, tile1, g, Direction.Vertical,
		// newWall);
		// g.setWallMoveCandidate(newMove);

		// Create a list of all walls on the board
		List<Wall> boardWalls = new ArrayList<Wall>();
		List<Wall> whitewalls = gp.getWhiteWallsOnBoard();
		List<Wall> blackwalls = gp.getBlackWallsOnBoard();
		boardWalls.addAll(blackwalls);
		boardWalls.addAll(whitewalls);
		// Add the current wall move candidate to the list
		WallMove candidateWallMove = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate();
		if (candidateWallMove != null) {
			Wall candidateWall = candidateWallMove.getWallPlaced();
			boardWalls.add(candidateWall);
		}

		// Case 1, less than 2 walls, clearly no overlap or criss-cross
		if (boardWalls.size() < 2) {
			return true;
		}
		// Case 2, two or more walls, check for overlap and criss-cross
		else {
			for (Wall wall : boardWalls) {
				// Make a sublist to compare to the full list
				List<Wall> subList = boardWalls.subList(boardWalls.indexOf(wall) + 1, boardWalls.size());
				for (Wall wallToCompare : subList) {
					Tile tile = wall.getMove().getTargetTile();
					Tile tileToCompare = wallToCompare.getMove().getTargetTile();
					// Check for FULL overlap or criss-cross
					if (tile.equals(tileToCompare)) {
						return false;
					}
					// Check horizontal overlap
					else if (wall.getMove().getWallDirection() == Direction.Horizontal
							&& wallToCompare.getMove().getWallDirection() == Direction.Horizontal) {
						if (tile.getColumn() == tileToCompare.getColumn() + 1
								|| tile.getColumn() == tileToCompare.getColumn() - 1) {
							return false;
						}
					}
					// check vertical overlap
					else if (wall.getMove().getWallDirection() == Direction.Vertical
							&& wallToCompare.getMove().getWallDirection() == Direction.Vertical) {
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
	 * 
	 * Sets the given user as the white player
	 * 
	 * @author Remi Carriere
	 * @param user
	 *            The selected user
	 * @throws InvalidInputException 
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static void setWhitePlayerInGame(User user) throws InvalidInputException {
		if (user != null) {
		Player player = new Player(null, user, 1, Direction.Vertical);
		QuoridorApplication.getQuoridor().getCurrentGame().setWhitePlayer(player);
		} else {
			throw new InvalidInputException("cannot set null user as player");
		}
	}
	/**
	 * 
	 * @param userName
	 * @throws InvalidInputException
	 */
	public static void setWhitePlayerInGame(String userName) throws InvalidInputException {
		User user = getUserByName(userName);
		setWhitePlayerInGame(user);
	}

	/**
	 * 
	 * Sets the given user as the black player
	 * 
	 * @author Remi Carriere
	 * @param user
	 *            The selected user
	 * @throws InvalidInputException 
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static void setBlackPlayerInGame(User user) throws InvalidInputException {
		if (user != null) {
			Player player = new Player(null, user, 9, Direction.Vertical);
			QuoridorApplication.getQuoridor().getCurrentGame().setBlackPlayer(player);
		} else {
			throw new InvalidInputException("cannot set null user as player");
		}
	}
	/**
	 * 
	 * @param userName
	 * @throws InvalidInputException
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
	 * @throws java.lang.UnsupportedOperationException
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
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static void setNewUserAsBlack(String name) throws InvalidInputException {
		createUser(name);
		setBlackPlayerInGame(name);
	}

	/*
	 * Query Methods
	 */

	/**
	 * Gets a list of all Users of the Quoridor Application so that players can
	 * select user name
	 * 
	 * @author Remi Carriere
	 * @return List<User> A list of all existing users
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static List<UserTO> getAllUsers() throws java.lang.UnsupportedOperationException {
		ArrayList<UserTO> users = new ArrayList<UserTO>();
		for (User user : QuoridorApplication.getQuoridor().getUsers()) {
			UserTO userTO = new UserTO(user.getName());
			users.add(userTO);
		}
		return users;
	}
	/**
	 * 
	 * @return
	 */
	public static String getCurrentPlayer() {
		if (QuoridorApplication.getQuoridor().getCurrentGame() != null) {

			return QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().getUser()
					.getName();
		}
		return null;
	}

	/**
	 * Gets the game position so that a player can see the board in its current
	 * position
	 * 
	 * @author Remi Carriere
	 * @return The current game position
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static GamePosition getGamePosition() throws java.lang.UnsupportedOperationException {
		throw new java.lang.UnsupportedOperationException();
	}

	/**
	 * Gets the remaining time of a player so that a player can see his clock
	 * counting down
	 * 
	 * @author Remi Carriere
	 * @param player
	 * @return The remaining time of the given player
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static Time getPlayerClock(Player player) throws java.lang.UnsupportedOperationException {
		throw new java.lang.UnsupportedOperationException();
	}
	/*
	 * Private Helper Methods
	 */

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

	// ------------------------
	// Francis
	// ------------------------

	/**
	 * Loads a previously saved game position into the current game position
	 * 
	 * @author Francis Comeau Gherkin feature: LoadPosition.feature
	 * @param fullPath
	 *            of the saved file
	 * @return True if load was successful, false is unable to load
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static boolean loadPosition(String fullPath) throws java.lang.UnsupportedOperationException {
		throw new java.lang.UnsupportedOperationException();
	}

	/**
	 * Saves the current game position into a file
	 * 
	 * @author Francis Comeau Gherkin feature: SavePosition.feature
	 * @param gamePosiion
	 *            to save and fullPath of where to save it
	 * @return True if load was successful, false is unable to load
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static void savePosition(GamePosition gamePosition, String fullPath)
			throws java.lang.UnsupportedOperationException {
		throw new java.lang.UnsupportedOperationException();
	}

	/**
	 * Asks the user if they want to overwrite the already existing file
	 * 
	 * @author Francis Comeau
	 * @return The user's answer to overwriting the file
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static boolean askOverwriteFile() {
		throw new java.lang.UnsupportedOperationException();
	}

	// ------------------------
	// Weige
	// ------------------------

	/**
	 * @author Weige qian Gherkin feature:InitializeBoard.feature
	 */
	public static void initBoard() throws java.lang.UnsupportedOperationException {
		Board board;
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
		Tile player1StartPos = QuoridorApplication.getQuoridor().getBoard().getTile(36);
		Tile player2StartPos = QuoridorApplication.getQuoridor().getBoard().getTile(44);

		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		PlayerPosition player1Position = new PlayerPosition(game.getWhitePlayer(), player1StartPos);
		PlayerPosition player2Position = new PlayerPosition(game.getBlackPlayer(), player2StartPos);

		GamePosition gamePosition = new GamePosition(0, player1Position, player2Position, w, game);
		// Add the walls as in stock for the players
		for (int j = 0; j < 10; j++) {
			Wall wall = Wall.getWithId(j);
			gamePosition.addWhiteWallsInStock(wall);
		}
		for (int j = 0; j < 10; j++) {
			Wall wall = Wall.getWithId(j + 10);
			gamePosition.addBlackWallsInStock(wall);
		}
		game.setCurrentPosition(gamePosition);
	}

	/**
	 * @param player
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static void stopClock(Player player) throws java.lang.UnsupportedOperationException {
		throw new java.lang.UnsupportedOperationException();
	}

	/**
	 * 
	 * @param player
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static void makeMove(Player player) throws java.lang.UnsupportedOperationException {
		throw new java.lang.UnsupportedOperationException();
	}

	public static boolean ifClockCount() throws java.lang.UnsupportedOperationException {
		throw new java.lang.UnsupportedOperationException();
	}

	// ------------------------
	// Kaan
	// ------------------------

	/**
	 * Grabs a wall from the current players stock
	 * 
	 * @author Kaan Gure Gherkin Feature: GrabWall.feature
	 * @throws java.lang.UnsupportedOperationException
	 */

	public static void grabWall() throws java.lang.UnsupportedOperationException {
		throw new java.lang.UnsupportedOperationException();
	}

	/**
	 * Drops the wall from the current player's hand to the board
	 * 
	 * @author Kaan Gure Gherkin Feature: DropWall.feature
	 * @throws java.lang.UnsupportedOperationException
	 */

	public static void dropWall() throws java.lang.UnsupportedOperationException {
		// full implementation of GUI needed for implementation
		throw new java.lang.UnsupportedOperationException();
	}

	/*
	 * 
	 * Query Methods
	 * 
	 */

	/**
	 * Query method: Gets the number of walls in stock of the current player so that
	 * it can be displayed in the GUI
	 * 
	 * @author Kaan Gure
	 * @throws java.lang.UnsupportedOperationException
	 */

	public static int getRemainingWallsInStock() throws java.lang.UnsupportedOperationException {
		// full implementation of GUI needed for implementation
		throw new java.lang.UnsupportedOperationException();
	}

	/**
	 * Query method: Checks if current wall placement move is valid so that the
	 * player can be notified in the GUI via another method call
	 * 
	 * @author Kaan Gure
	 * @throws java.lang.UnsupportedOperationException
	 */

	public static boolean isCurrentWallMoveValid() throws java.lang.UnsupportedOperationException {
		// full implementation of GUI needed for implementation
		throw new java.lang.UnsupportedOperationException();
	}

	/*
	 * 
	 * Future GUI Related Methods
	 * 
	 */

	/**
	 * Future GUI related method: Alert if player has no more walls in stock
	 * 
	 * @author Kaan Gure
	 * @throws java.lang.UnsupportedOperationException
	 */

	public static boolean alertStockIsEmpty() throws java.lang.UnsupportedOperationException {
		// full implementation of GUI needed for implementation
		throw new java.lang.UnsupportedOperationException();
	}

	/**
	 * Future GUI related method: Checks if there is wall in hand that is
	 * represented in the GUI
	 * 
	 * @author Kaan Gure
	 * @throws java.lang.UnsupportedOperationException
	 */

	public static boolean hasWallInHand() throws java.lang.UnsupportedOperationException {
		// full implementation of GUI needed for implementation
		throw new java.lang.UnsupportedOperationException();
	}

	/**
	 * Future GUI related method: Sets wall in hand that will be represented in the
	 * GUI
	 * 
	 * @author Kaan Gure
	 * @throws java.lang.UnsupportedOperationException
	 */

	public static void setWallInHand(Wall wallInHand) throws java.lang.UnsupportedOperationException {
		// full implementation of GUI needed for implementation
		throw new java.lang.UnsupportedOperationException();
	}

	/**
	 * Future GUI related method: Gets wall in hand that will be represented in the
	 * GUI
	 * 
	 * @author Kaan Gure
	 * @throws java.lang.UnsupportedOperationException
	 */

	public static Wall getWallInHand() throws java.lang.UnsupportedOperationException {
		// full implementation of GUI needed for implementation
		throw new java.lang.UnsupportedOperationException();
	}

	/**
	 * Future GUI related method: Clears wall in hand that will be represented in
	 * the GUI
	 * 
	 * @author Kaan Gure
	 * @throws java.lang.UnsupportedOperationException
	 */

	public static void clearWallInHand() throws java.lang.UnsupportedOperationException {
		// full implementation of GUI needed for implementation
		throw new java.lang.UnsupportedOperationException();
	}

	/**
	 * Future GUI related method: notify invalid move in GUI
	 * 
	 * @author Kaan Gure
	 * @throws java.lang.UnsupportedOperationException
	 */

	public static boolean notifyInvalidMove() throws java.lang.UnsupportedOperationException {
		// full implementation of GUI needed for implementation
		// returns true if player is notified of invalid move in GUI
		throw new java.lang.UnsupportedOperationException();
	}

	// ------------------------
	// Zechen
	// ------------------------

	/**
	 * @author Zechen Ren Gherkin feature: RotateWall.feature
	 */
	public static void rotateWall() throws java.lang.UnsupportedOperationException {
		throw new java.lang.UnsupportedOperationException();
	}

	/**
	 * @author Zechen Ren Gherkin feature: MoveWall.feature
	 */

	public static void moveWall(String side) throws java.lang.UnsupportedOperationException {
		throw new java.lang.UnsupportedOperationException();
	}

}
