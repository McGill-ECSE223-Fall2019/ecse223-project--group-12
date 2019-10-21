package ca.mcgill.ecse223.quoridor.controller;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.User;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
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
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static void initializeGame(String whiteName, String blackName) throws java.lang.UnsupportedOperationException {
		throw new java.lang.UnsupportedOperationException("");
	}

	/**
	 * Creates a new user
	 * 
	 * @author Remi Carriere
	 * @param name The name of the user
	 * @throws java.lang.UnsupportedOperationException
	 * @throws ca.mcgill.ecse223.quoridor.controller.InvalidInputException 
	 */
	public static void createUser(String name) throws InvalidInputException {
		if (name.trim().length() == 0 || name == null || name.length() < 3) {
			throw new InvalidInputException("Name must 3 or more charcters!");
		}
		try {
			QuoridorApplication.getQuoridor().addUser(name);
		}catch(RuntimeException e) {
			throw new InvalidInputException("User name already exists");
		}
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
	public static List<UserTO> getAllUsers() throws java.lang.UnsupportedOperationException {
		ArrayList<UserTO> users = new ArrayList<UserTO>();
		for(User user : QuoridorApplication.getQuoridor().getUsers()) {
			UserTO userTO = new UserTO(user.getName());
			users.add(userTO);
		}
		return users;
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
	/*
	 * Private Helper Methods
	 */
	
	/**
	 * Gets a user of quoridor by username
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
		//throw new java.lang.IllegalArgumentException("Username does not exist: " + name);
	}

	// ------------------------
	// Francis
	// ------------------------

	/**
	 * Loads a previously saved game position into the current game position
	 * 
	 * @author Francis Comeau Gherkin feature: LoadPosition.feature
	 * @param fullPath of the saved file
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
	 * @param gamePosiion to save and fullPath of where to save it
	 * @return True if load was successful, false is unable to load
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static void savePosition(GamePosition gamePosition, String fullPath) throws java.lang.UnsupportedOperationException {
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
	public static boolean ifClockCount() throws java.lang.UnsupportedOperationException {
		throw new java.lang.UnsupportedOperationException();
	}

	// ------------------------
	// Kaan
	// ------------------------

	/**
	 * Grabs a wall from the current players stock
	 * @author Kaan Gure
	 * Gherkin Feature: GrabWall.feature
	 * @throws java.lang.UnsupportedOperationException
	 */
	
	public static void grabWall() throws java.lang.UnsupportedOperationException{
		throw new java.lang.UnsupportedOperationException();
	}
	
	/**
	 * Drops the wall from the current player's hand to the board
	 * @author Kaan Gure
	 * Gherkin Feature: DropWall.feature
	 * @throws java.lang.UnsupportedOperationException
	 */

	public static void dropWall() throws java.lang.UnsupportedOperationException{ 
		//full implementation of GUI needed for implementation 
		throw new java.lang.UnsupportedOperationException();
	}
	
	/*
	 * 
	 * Query Methods
	 * 
	 */
	
	/**
	 * Query method: Gets the number of walls in stock of the current player so that it can be displayed in the GUI
	 * @author Kaan Gure
	 * @throws java.lang.UnsupportedOperationException
	 */
	
	public static int getRemainingWallsInStock() throws java.lang.UnsupportedOperationException{ 
		//full implementation of GUI needed for implementation 
		throw new java.lang.UnsupportedOperationException();
	}
	
	/**
	 * Query method: Checks if current wall placement move is valid so that the player can be notified in the GUI via another method call
	 * @author Kaan Gure
	 * @throws java.lang.UnsupportedOperationException
	 */
	
	public static boolean isCurrentWallMoveValid() throws java.lang.UnsupportedOperationException{ 
		//full implementation of GUI needed for implementation 
		throw new java.lang.UnsupportedOperationException();
	}
	
	/*
	 * 
	 * Future GUI Related Methods
	 * 
	 */
	
	/**
	 * Future GUI related method: Alert if player has no more walls in stock
	 * @author Kaan Gure
	 * @throws java.lang.UnsupportedOperationException
	 */
	
	public static boolean alertStockIsEmpty() throws java.lang.UnsupportedOperationException{ 
		//full implementation of GUI needed for implementation
		throw new java.lang.UnsupportedOperationException();
	}
	
	/**
	 * Future GUI related method: Checks if there is wall in hand that is represented in the GUI
	 * @author Kaan Gure
	 * @throws java.lang.UnsupportedOperationException
	 */
	
	public static boolean hasWallInHand() throws java.lang.UnsupportedOperationException{ 
		//full implementation of GUI needed for implementation 
		throw new java.lang.UnsupportedOperationException();
	}
	
	/**
	 * Future GUI related method: Sets wall in hand that will be represented in the GUI
	 * @author Kaan Gure
	 * @throws java.lang.UnsupportedOperationException
	 */
	
	public static void setWallInHand(Wall wallInHand) throws java.lang.UnsupportedOperationException{ 
		//full implementation of GUI needed for implementation 
		throw new java.lang.UnsupportedOperationException();
	}
	
	/**
	 * Future GUI related method: Gets wall in hand that will be represented in the GUI
	 * @author Kaan Gure
	 * @throws java.lang.UnsupportedOperationException
	 */
	
	public static Wall getWallInHand() throws java.lang.UnsupportedOperationException{ 
		//full implementation of GUI needed for implementation 
		throw new java.lang.UnsupportedOperationException();
	}
	
	
	/**
	 * Future GUI related method: Clears wall in hand that will be represented in the GUI
	 * @author Kaan Gure
	 * @throws java.lang.UnsupportedOperationException
	 */
	
	public static void clearWallInHand() throws java.lang.UnsupportedOperationException{ 
		//full implementation of GUI needed for implementation 
		throw new java.lang.UnsupportedOperationException();
	}
	
	
	/**
	 * Future GUI related method: notify invalid move in GUI
	 * @author Kaan Gure
	 * @throws java.lang.UnsupportedOperationException
	 */

	public static boolean notifyInvalidMove() throws java.lang.UnsupportedOperationException{ 
		//full implementation of GUI needed for implementation
		//returns true if player is notified of invalid move in GUI
		throw new java.lang.UnsupportedOperationException();
	}
	
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
