package ca.mcgill.ecse223.quoridor.features;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Board;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.model.WallMove;
import ca.mcgill.ecse223.quoridor.util.TestUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/*
 * Note that the classes may not conatain all relative steps for a certain feature, 
 * since there are duplicate steps between scenarios.
 */

public class DropWallStepDefinitions {
	/**
	 * @author Marton
	 */
	@Given("^The game is running$")
	public void the_game_is_running() {
		TestUtil.initQuoridorAndBoard();
		ArrayList<Player> createUsersAndPlayers = TestUtil.createUsersAndPlayers("user1", "user2");
		TestUtil.createAndStartGame(createUsersAndPlayers);
	}

	/**
	 * @author Marton
	 */
	@And("^It is my turn to move$")
	public void it_is_my_turn_to_move() throws Throwable {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Player currentPlayer = quoridor.getCurrentGame().getWhitePlayer();
		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().setPlayerToMove(currentPlayer);
	}

	/**
	 * @author Marton
	 */
	@Given("The following walls exist:")
	public void the_following_walls_exist(io.cucumber.datatable.DataTable dataTable) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		// keys: wrow, wcol, wdir
		Player[] players = { quoridor.getCurrentGame().getWhitePlayer(), quoridor.getCurrentGame().getBlackPlayer() };
		int playerIdx = 0;
		int wallIdxForPlayer = 0;
		for (Map<String, String> map : valueMaps) {
			Integer wrow = Integer.decode(map.get("wrow"));
			Integer wcol = Integer.decode(map.get("wcol"));
			// Wall to place
			// Walls are placed on an alternating basis wrt. the owners
			// Wall wall = Wall.getWithId(playerIdx * 10 + wallIdxForPlayer);
			Wall wall = players[playerIdx].getWall(wallIdxForPlayer); // above implementation sets wall to null

			String dir = map.get("wdir");

			Direction direction;
			switch (dir) {
			case "horizontal":
				direction = Direction.Horizontal;
				break;
			case "vertical":
				direction = Direction.Vertical;
				break;
			default:
				throw new IllegalArgumentException("Unsupported wall direction was provided: " + dir);
			}
			new WallMove(0, 1, players[playerIdx], quoridor.getBoard().getTile((wrow - 1) * 9 + wcol - 1),
					quoridor.getCurrentGame(), direction, wall);
			if (playerIdx == 0) {
				quoridor.getCurrentGame().getCurrentPosition().removeWhiteWallsInStock(wall);
				quoridor.getCurrentGame().getCurrentPosition().addWhiteWallsOnBoard(wall);
			} else {
				quoridor.getCurrentGame().getCurrentPosition().removeBlackWallsInStock(wall);
				quoridor.getCurrentGame().getCurrentPosition().addBlackWallsOnBoard(wall);
			}
			wallIdxForPlayer = wallIdxForPlayer + playerIdx;
			playerIdx++;
			playerIdx = playerIdx % 2;
		}
		System.out.println();

	}
	
	/**
	 * @author Kaan Gure
	 */

	@And("I do not have a wall in my hand")
	public void i_do_not_have_a_wall_in_my_hand() {
	if(QuoridorController.hasWallInHand() == true) {
		QuoridorController.clearWallInHand();
	}
	}
	
	/**
	 * @author Kaan Gure
	 */

	@And("^I have a wall in my hand over the board$")
	public void i_have_a_wall_in_my_hand_over_the_board() throws Throwable {
		Player currentPlayer = TestUtil.getCurrentPlayer();
		Wall wall = TestUtil.getAWallInStockForCurrenPlayer();
		if (wall == null) {
			//then create a new wall...
			wall = new Wall(21, currentPlayer);
		}
		if(QuoridorController.hasWallInHand() == false) {
			QuoridorController.setWallInHand(wall);
		}
		else return;
	}
	
	/**
	 * @author Kaan Gure
	 */

	@Given("The wall move candidate with {string} at position \\({int}, {int}) is valid")
	public void the_wall_move_candidate_with_at_position_is_valid(String dir, Integer row, Integer col) {
		Player player = TestUtil.getCurrentPlayer();
		Wall wall = TestUtil.getAWallInStockForCurrenPlayer();
		if (wall == null) {
			//then create a new wall...
			wall = new Wall(21, player);
		}
		Board board = QuoridorApplication.getQuoridor().getBoard();
		Tile tile = new Tile(row, col, board);
		Game game =  QuoridorApplication.getQuoridor().getCurrentGame();
		
		Direction direction;
		switch (dir) {
		case "horizontal":
			direction = Direction.Horizontal;
			break;
		case "vertical":
			direction = Direction.Vertical;
			break;
		default:
			throw new IllegalArgumentException("Unsupported wall direction was provided: " + dir);
		}
		
		WallMove wallMoveCandidate = new WallMove(2, 2, player, tile, game, direction, wall);
		GamePosition casePosition  = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		casePosition.getGame().setWallMoveCandidate(wallMoveCandidate);
		if(QuoridorController.validatePosition(casePosition) == false) {
			throw new IllegalStateException("Wall move candidate at current position is invalid, position has to be valid for test to commence");
		}
			}
	
	/**
	 * @author Kaan Gure
	 */

	@When("I release the wall in my hand")
	public void i_release_the_wall_in_my_hand() {
		// Write code here that turns the phrase above into concrete actions
		try {
			QuoridorController.dropWall();
		} catch (java.lang.UnsupportedOperationException e) {
			//skips test if method is not yet implemented
			throw new cucumber.api.PendingException();
		}
	}
	
	/**
	 * @author Kaan Gure
	 */

	@Then("A wall move shall be registered with {string} at position \\({int}, {int})")
	public void a_wall_move_shall_be_registered_with_at_position(String dir, Integer row, Integer col) {
		// Write code here that turns the phrase above into concrete actions
		Player player = TestUtil.getCurrentPlayer();
		Board board = QuoridorApplication.getQuoridor().getBoard();
		Tile tile = new Tile(row, col, board);
		Game game =  QuoridorApplication.getQuoridor().getCurrentGame();
		Direction direction;
		switch (dir) {
		case "horizontal":
			direction = Direction.Horizontal;
			break;
		case "vertical":
			direction = Direction.Vertical;
			break;
		default:
			throw new IllegalArgumentException("Unsupported wall direction was provided: " + dir);}
		
		Wall wallInHand = QuoridorController.getWallInHand();
	WallMove wallMoveSet = new WallMove(2, 2, player, tile, game, direction, wallInHand);
	boolean isSet = wallMoveSet.setWallPlaced(wallInHand);
	assertEquals(true, isSet);
	}
	
	/**
	 * @author Kaan Gure
	 */

	@Then("I shall not have a wall in my hand")
	public void i_shall_not_have_a_wall_in_my_hand() {
		assertEquals(false, QuoridorController.hasWallInHand());
	}
	
	/**
	 * @author Kaan Gure
	 */

	@Then("My move shall be completed")
	public void my_move_shall_be_completed() {
		boolean isStillMoving = QuoridorApplication.getQuoridor().getCurrentGame().hasWallMoveCandidate();
		assertEquals(false, isStillMoving);
	}
	
	/**
	 * @author Kaan Gure
	 */

	@Then("It shall not be my turn to move")
	public void it_shall_not_be_my_turn_to_move() {
		// Write code here that turns the phrase above into concrete actions
		Player currentPlayer = TestUtil.getCurrentPlayer();
		Player playertoMove = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove();
		boolean moveChanged = true;
		if(currentPlayer.equals(playertoMove)) {
			moveChanged = false;
		}
		else {
				moveChanged = true;
			}
		assertEquals(true, moveChanged);
	}
	
	/**
	 * @author Kaan Gure
	 */

	@Given("The wall move candidate with {string} at position \\({int}, {int}) is invalid")
	public void the_wall_move_candidate_with_at_position_is_invalid(String dir, Integer row, Integer col) {
		Player player = TestUtil.getCurrentPlayer();
		Wall wall = TestUtil.getAWallInStockForCurrenPlayer();
		if (wall == null) {
			//then create a new wall...
			wall = new Wall(21, player);
		}
		Board board = QuoridorApplication.getQuoridor().getBoard();
		Tile tile = new Tile(row, col, board);
		Game game =  QuoridorApplication.getQuoridor().getCurrentGame();
		
		Direction direction;
		switch (dir) {
		case "horizontal":
			direction = Direction.Horizontal;
			break;
		case "vertical":
			direction = Direction.Vertical;
			break;
		default:
			throw new IllegalArgumentException("Unsupported wall direction was provided: " + dir);
		}
		
		WallMove wallMoveCandidate = new WallMove(2, 2, player, tile, game, direction, wall);
		GamePosition casePosition  = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		casePosition.getGame().setWallMoveCandidate(wallMoveCandidate);
		if(QuoridorController.validatePosition(casePosition) == true) {
			throw new IllegalStateException("Wall move candidate at current position is valid, position has to be invalid for test to commence");
		}
	}
	
	/**
	 * @author Kaan Gure
	 */

	@Then("I shall be notified that my wall move is invalid")
	public void i_shall_be_notified_that_my_wall_move_is_invalid() {
		boolean hasNotified;
		hasNotified = QuoridorController.notifyInvalidMove();
		assertEquals(true, hasNotified);
	}
	
	/**
	 * @author Kaan Gure
	 */

	@Then("I shall have a wall in my hand over the board")
	public void i_shall_have_a_wall_in_my_hand_over_the_board() {
		boolean hasWallInHand;
		hasWallInHand = QuoridorController.hasWallInHand();
		assertEquals(true, hasWallInHand);
	}
	
	/**
	 * @author Kaan Gure
	 */

	@Then("It shall be my turn to move")
	public void it_shall_be_my_turn_to_move() {
		// Write code here that turns the phrase above into concrete actions
		Player currentPlayer = TestUtil.getCurrentPlayer();
		Player playertoMove = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove();
		boolean isMyTurn = false;
		if(currentPlayer.equals(playertoMove)) {
			isMyTurn = true;
		}
		else {
				isMyTurn = false;
			}
	assertEquals(true, isMyTurn);
	}
	
	/**
	 * @author Kaan Gure
	 */

	@Then("No wall move shall be registered with {string} at position \\({int}, {int})")
	public void no_wall_move_shall_be_registered_with_at_position(String dir, Integer row, Integer col) {
		Player player = TestUtil.getCurrentPlayer();
		Board board = QuoridorApplication.getQuoridor().getBoard();
		Tile tile = new Tile(row, col, board);
		Game game =  QuoridorApplication.getQuoridor().getCurrentGame();
		Direction direction;
		switch (dir) {
		case "horizontal":
			direction = Direction.Horizontal;
			break;
		case "vertical":
			direction = Direction.Vertical;
			break;
		default:
			throw new IllegalArgumentException("Unsupported wall direction was provided: " + dir);}
		
		Wall wallInHand = QuoridorController.getWallInHand();
	WallMove wallMoveSet = new WallMove(2, 2, player, tile, game, direction, wallInHand);
	boolean isSet = wallMoveSet.setWallPlaced(wallInHand);
	assertEquals(false, isSet);
	}

}
