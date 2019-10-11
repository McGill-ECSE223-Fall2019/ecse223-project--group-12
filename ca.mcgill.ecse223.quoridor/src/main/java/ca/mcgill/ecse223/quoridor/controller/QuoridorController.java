package ca.mcgill.ecse223.quoridor.controller;

import java.sql.Time;
import java.util.List;

import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.User;

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
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static void initializeGame() throws java.lang.UnsupportedOperationException {
		throw new java.lang.UnsupportedOperationException("");
	}

	/**
	 * Creates a new user
	 * 
	 * @author Remi Carriere
	 * @param name The name of the user
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static void createUser(String name) throws java.lang.UnsupportedOperationException {
		// TODO
		throw new java.lang.UnsupportedOperationException();
	}

	/**
	 * Sets the remainingTime of each player to totalTime
	 * 
	 * @author Remi Carriere
	 * @param totalTime The desired thinking time
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static void setTotalThinkingTime(Time totalTime) throws java.lang.UnsupportedOperationException {
		throw new java.lang.UnsupportedOperationException();
	}

	/**
	 * Starts the clock for player that has the current turn. If the game is not yet
	 * running, sets the GameStatus to Running and Initializes the board
	 * 
	 * @author Remi Carriere
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static void startClock() throws java.lang.UnsupportedOperationException {
		throw new java.lang.UnsupportedOperationException();
	}

	/**
	 * Verfies if the given GamePosition is legal
	 * 
	 * @author Remi Carriere
	 * @param gamePosition The game position to verify
	 * @return
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static boolean validatePosition(GamePosition gamePosition) throws java.lang.UnsupportedOperationException {
		throw new java.lang.UnsupportedOperationException();
	}

	/**
	 * 
	 * Sets the given user as the white player
	 * 
	 * @author Remi Carriere
	 * @param user The selected user
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static void setWhitePlayerInGame(User user) throws java.lang.UnsupportedOperationException {
		throw new java.lang.UnsupportedOperationException();
	}

	/**
	 * 
	 * Sets the given user as the black player
	 * 
	 * @author Remi Carriere
	 * @param user The selected user
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static void setBlackPlayerInGame(User user) throws java.lang.UnsupportedOperationException {
		throw new java.lang.UnsupportedOperationException();
	}

	/**
	 * Creates new user, and sets the user as the white player
	 * 
	 * @author Remi Carriere
	 * @param name Name of the user
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static void setNewUserAsWhite(String name) throws java.lang.UnsupportedOperationException {
		throw new java.lang.UnsupportedOperationException();
	}

	/***
	 * Creates new user, and sets the user as the black player
	 * @author Remi Carriere
	 * @param name Name of the user
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static void setNewUserAsBlack(String name) throws java.lang.UnsupportedOperationException {
		throw new java.lang.UnsupportedOperationException();
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
	public static List<User> getAllUsers() throws java.lang.UnsupportedOperationException {
		throw new java.lang.UnsupportedOperationException();
	}
	
	/**
	 * Gets the game position so that a player can see the board in its current position
	 * 
	 * @author Remi Carriere
	 * @return The current game position
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static GamePosition getGamePosition() throws java.lang.UnsupportedOperationException {
		throw new java.lang.UnsupportedOperationException();
	}
	
	/**
	 * Gets the remaining time of a player so that a player can see his clock counting down
	 * 
	 * @author Remi Carriere
	 * @param player
	 * @return The remaining time of the given player
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static Time getPlayerClock(Player player) throws java.lang.UnsupportedOperationException{
		throw new java.lang.UnsupportedOperationException();
	}

	// ------------------------
	// Francis
	// ------------------------

	/**
	 * @author Francis Comeau Gherkin feature: LoadPosition.feature
	 */
	public static void loadPosition(String fullPath) throws java.lang.UnsupportedOperationException {
		throw new java.lang.UnsupportedOperationException();
	}

	/**
	 * @author Francis Comeau Gherkin feature: SavePosition.feature
	 */
	public static void savePosition(GamePosition gamePosition) throws java.lang.UnsupportedOperationException {
		throw new java.lang.UnsupportedOperationException();
	}

	// ------------------------
	// Weige
	// ------------------------

	/**
	 * @author Weige qian Gherkin feature:InitializeBoard.feature
	 */
	public static void initBoard() throws java.lang.UnsupportedOperationException {
		throw new java.lang.UnsupportedOperationException();
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

	// ------------------------
	// Khan
	// ------------------------

	// TODO

	// ------------------------
	// Zechen
	// ------------------------

	/**
	 * @author Zechen Ren
	 * Gherkin feature: RotateWall.feature
	 */
	public static void rotateWall () throws java.lang.UnsupportedOperationException {
		throw new java.lang.UnsupportedOperationException();
	}
	
	/**
	 * @author Zechen Ren
	 * Gherkin feature: MoveWall.feature
	 */
	
	public static void moveWall (String side) throws java.lang.UnsupportedOperationException {
		throw new java.lang.UnsupportedOperationException();
	}

}
