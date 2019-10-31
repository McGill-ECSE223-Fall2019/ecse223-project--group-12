package ca.mcgill.ecse223.quoridor.util;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.Board;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.User;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game.MoveMode;

/**
 * Contains useful methods for performing tests
 * 
 * @author Remi Carriere
 *
 */
public class TestUtil {

	/**
	 * Create a new board with 81 Tiles
	 * 
	 * @author Marton
	 */
	public static void initQuoridorAndBoard() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Board board = new Board(quoridor);
		// Creating tiles by rows, i.e., the column index changes with every tile
		// creation
		for (int i = 1; i <= 9; i++) { // rows
			for (int j = 1; j <= 9; j++) { // columns
				board.addTile(i, j);
			}
		}
	}

	/**
	 * Creates Users and associates them with black and white player with default
	 * thinking time
	 * 
	 * @author Marton
	 * @param userName1
	 * @param userName2
	 * @return
	 * 
	 */
	public static ArrayList<Player> createUsersAndPlayers(String userName1, String userName2) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		User user1 = quoridor.addUser(userName1);
		User user2 = quoridor.addUser(userName2);

		// Players are assumed to start on opposite sides and need to make progress
		// horizontally to get to the other side
		// @formatter:off
		/*
		 * __________ | | | | |x-> <-x| | | |__________|
		 * 
		 */
		// @formatter:on
		Player player1 = new Player(Time.valueOf("00:00:30"), user1, 9, Direction.Vertical);
		Player player2 = new Player(Time.valueOf("00:00:30"), user2, 1, Direction.Vertical);

		Player[] players = { player1, player2 };

		// Create all walls. Walls with lower ID belong to player1,
		// while the second half belongs to player 2
		for (int j = 0; j < 10; j++) {
			new Wall(j, players[0]);
		}

		for (int j = 0; j < 10; j++) {
			new Wall(j + 10, players[1]);
		}

		ArrayList<Player> playersList = new ArrayList<Player>();
		playersList.add(player1);
		playersList.add(player2);

		return playersList;
	}

	/**
	 * Starts a new Game with Running Game status. initializes board with pawns and
	 * walls
	 * 
	 * @author Marton
	 * @param players
	 */
	public static void createAndStartGame(ArrayList<Player> players) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		// There are total 36 tiles in the first four rows and
		// indexing starts from 0 -> tiles with indices 36 and 36+8=44 are the starting
		// positions
		Tile player1StartPos = getTile(9, 5);
		Tile player2StartPos = getTile(1, 5);

		Game game = new Game(GameStatus.Running, MoveMode.PlayerMove, quoridor);
		game.setWhitePlayer(players.get(0));
		game.setBlackPlayer(players.get(1));

		PlayerPosition player1Position = new PlayerPosition(quoridor.getCurrentGame().getWhitePlayer(),
				player1StartPos);
		PlayerPosition player2Position = new PlayerPosition(quoridor.getCurrentGame().getBlackPlayer(),
				player2StartPos);

		GamePosition gamePosition = new GamePosition(0, player1Position, player2Position, players.get(0), game);

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
		gamePosition.getBlackPosition().getTile();
	}

	/**
	 * Gets the Tile at the specified coordinates
	 * 
	 * @author Remi Carriere
	 * @param row
	 * @param col
	 * @return
	 */
	public static Tile getTile(int row, int col) {
		Iterator<Tile> itr = QuoridorApplication.getQuoridor().getBoard().getTiles().iterator();
		while (itr.hasNext()) {
			Tile t = itr.next();
			if (t.getRow() == row && t.getColumn() == col) {
				return t;
			}
		}
		throw new java.lang.IllegalArgumentException("tile does not exist:  row=" + row + " col=" + col);

	}

	/**
	 * Returns Direction Object based on string input
	 * 
	 * @author Remi Carriere
	 * @param dir
	 * @return
	 */
	public static Direction getDirection(String dir) {
		switch (dir) {
		case "horizontal":
			return Direction.Horizontal;
		case "vertical":
			return Direction.Vertical;
		default:
			throw new java.lang.IllegalArgumentException("Invalid direction: " + dir);
		}
	}

	/**
	 * Gets a user of quoridor by username
	 * 
	 * @author Remi Carriere
	 * @param name
	 * @return
	 */
	public static User getUserByName(String name) {
		Iterator<User> users = QuoridorApplication.getQuoridor().getUsers().iterator();
		while (users.hasNext()) {
			User u = users.next();
			if (u.getName().equals(name)) {
				return u;
			}
		}
		return null;
		// throw new java.lang.IllegalArgumentException("Username does not exist: " +
		// name);
	}

	/**
	 * Creates a time object from int values
	 * 
	 * @author Remi Carriere
	 * @param minutes
	 * @param seconds
	 * @return
	 */
	public static Time createTime(int minutes, int seconds) {
		int mills = ((60 * minutes) + seconds) * 1000;
		return new Time(mills);
	}

	/**
	 * @author Remi Carriere
	 * @param color
	 * @return
	 */
	public static Player getPlayerByColor(String color) {
		switch (color) {
		case "white":
			return QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer();
		case "black":
			return QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer();
		default:
			throw new java.lang.IllegalArgumentException("Invalid Color: " + color);
		}
	}

	/**
	 * @author Remi Carriere
	 * @param color
	 * @return
	 */
	public static PlayerPosition getPlayerPositionByColor(String color) {
		switch (color) {
		case "white":
			return QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition();
		case "black":
			return QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition();
		default:
			throw new java.lang.IllegalArgumentException("Invalid Color: " + color);
		}
	}

	/**
	 * @author Remi Carriere
	 */
	public static void removeCurrentPlayersWalls() {
		GamePosition gamePosition = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		Player currentPlayer = getCurrentPlayer();
		if (currentPlayer.equals(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer())) {
			List<Wall> walls = gamePosition.getWhiteWallsInStock();
			List<Wall> wallsToRemove = new ArrayList<Wall>(walls);
			for (Wall wall : wallsToRemove) {
				gamePosition.removeWhiteWallsInStock(wall);
			}
		} else if (currentPlayer.equals(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer())) {
			List<Wall> walls = gamePosition.getBlackWallsInStock();
			List<Wall> wallsToRemove = new ArrayList<Wall>(walls);
			for (Wall wall : wallsToRemove) {
				gamePosition.removeBlackWallsInStock(wall);
			}

		}
	}

	/**
	 * @authorRemi Carriere
	 * @return
	 */
	public static Player getCurrentPlayer() {
		return QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove();
	}

	/**
	 * @author Remi Carriere
	 * @return
	 */
	public static Wall getAWallInStockForCurrenPlayer() {
		GamePosition gamePosition = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		Player currentPlayer = getCurrentPlayer();

		if (currentPlayer.equals(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer())) {
			List<Wall> walls = gamePosition.getWhiteWallsInStock();
			if (!walls.isEmpty()) {
				return walls.get(1);
			}
		} else if (currentPlayer.equals(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer())) {
			List<Wall> walls = gamePosition.getBlackWallsInStock();
			if (!walls.isEmpty()) {
				return walls.get(1);
			}
		}
		return null;
	}

}
