package ca.mcgill.ecse223.quoridor.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.InvalidInputException;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.model.WallMove;
import ca.mcgill.ecse223.quoridor.util.TestUtil;
import ca.mcgill.ecse223.quoridor.view.GamePanel;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/*
 * Note that the classes may not conatain all relative steps for a certain feature, 
 * since there are duplicate steps between scenarios.
 */

public class DropWallStepDefinitions {

	private String errorMessage = "";
	/**
	 * @author Kaan Gure
	 */
	@Given("^I have a wall in my hand over the board$")
	public void i_have_a_wall_in_my_hand_over_the_board() throws Throwable {
		Wall wall = TestUtil.getAWallInStockForCurrenPlayer();
		Player p = TestUtil.getCurrentPlayer();
		Tile tile = TestUtil.getTile(8, 5);
		Game g  = QuoridorApplication.getQuoridor().getCurrentGame();
		WallMove candidate = new WallMove(0, 0, p, tile, g, Direction.Vertical, wall);
		g.setWallMoveCandidate(candidate);
		GamePanel gPanel = new GamePanel();
		gPanel.refreshData();
		assertTrue(gPanel.hasWallInHand()); // just verify gui precondition (already taken care of by creating the wall
											// candidate in step before)
	}

	/**
	 * @author Kaan Gure
	 */

	@Given("The wall move candidate with {string} at position \\({int}, {int}) is valid")
	public void the_wall_move_candidate_with_at_position_is_valid(String dir, Integer row, Integer col) {
		Wall wall = TestUtil.getAWallInStockForCurrenPlayer();
		Player p = TestUtil.getCurrentPlayer();
		Tile tile = TestUtil.getTile(row, col);
		Direction direction = TestUtil.getDirection(dir);
		Game g  = QuoridorApplication.getQuoridor().getCurrentGame();
		if(wall.getMove() != null) {
			wall.getMove().delete();
		}
		WallMove candidate = new WallMove(0, 0, p, tile, g, direction, wall);
		g.setWallMoveCandidate(candidate);
		assertTrue(QuoridorController.validatePosition());
	}

	/**
	 * @author Kaan Gure
	 */

	@When("I release the wall in my hand")
	public void i_release_the_wall_in_my_hand() {
		try {
			QuoridorController.dropWall();
		} catch (InvalidInputException e) {
			errorMessage = e.getMessage();
		}
	}

	/**
	 * @author Kaan Gure
	 */

	@Then("A wall move shall be registered with {string} at position \\({int}, {int})")
	public void a_wall_move_shall_be_registered_with_at_position(String dir, Integer row, Integer col) {
		GamePosition gamePosition = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		List<Wall> boardWalls = new ArrayList<Wall>();
		List<Wall> whitewalls = gamePosition.getWhiteWallsOnBoard();
		List<Wall> blackwalls = gamePosition.getBlackWallsOnBoard();
		boardWalls.addAll(blackwalls);
		boardWalls.addAll(whitewalls);
		Tile tile = TestUtil.getTile(row, col);
		Direction direction = TestUtil.getDirection(dir);
		boolean moveExists = false;
		for (Wall wall: boardWalls) {
			Tile tileToCheck = wall.getMove().getTargetTile();
			Direction dirToCheck = wall.getMove().getWallDirection();
			if(tile.equals(tileToCheck) && direction.equals(dirToCheck)) {
				moveExists = true;
			}
		}
		assertTrue(moveExists);
	}

	/**
	 * @author Kaan Gure
	 */

	@Then("I shall not have a wall in my hand")
	public void i_shall_not_have_a_wall_in_my_hand() {
			GamePanel gPanel = new GamePanel();
			gPanel.refreshData();
			assertFalse(gPanel.hasWallInHand()); // GUI related
	}

	/**
	 * @author Kaan Gure
	 */

	@Then("My move shall be completed")
	public void my_move_shall_be_completed() {
		boolean candidateExists = QuoridorApplication.getQuoridor().getCurrentGame().hasWallMoveCandidate();
		assertFalse(candidateExists);
	}

	/**
	 * @author Kaan Gure
	 */

	@Then("It shall not be my turn to move")
	public void it_shall_not_be_my_turn_to_move() {
		Player currentPlayer = TestUtil.getCurrentPlayer();
		Player whitePlayer = TestUtil.getPlayerByColor("white"); // the player to move was set as whit in beginning of scenario
		assertNotEquals(currentPlayer, whitePlayer);
	}

	/**
	 * @author Kaan Gure
	 */

	@Given("The wall move candidate with {string} at position \\({int}, {int}) is invalid")
	public void the_wall_move_candidate_with_at_position_is_invalid(String dir, Integer row, Integer col) {
		Wall wall = TestUtil.getAWallInStockForCurrenPlayer();
		Player p = TestUtil.getCurrentPlayer();
		Tile tile = TestUtil.getTile(row, col);
		Direction direction = TestUtil.getDirection(dir);
		Game g  = QuoridorApplication.getQuoridor().getCurrentGame();
		if(wall.getMove() != null) {
			wall.getMove().delete();
		}
		WallMove candidate = new WallMove(0, 0, p, tile, g, direction, wall);
		g.setWallMoveCandidate(candidate);
		assertFalse(QuoridorController.validatePosition());
	}

	/**
	 * @author Kaan Gure
	 */
	@Then("I shall be notified that my wall move is invalid") // GUI related
	public void i_shall_be_notified_that_my_wall_move_is_invalid() {
		assertEquals("Invalid move, try again!",errorMessage );
		// gui test TBD
	}

	/**
	 * @author Kaan Gure
	 */

	@Then("It shall be my turn to move")
	public void it_shall_be_my_turn_to_move() {
		Player currentPlayer = TestUtil.getCurrentPlayer();
		Player whitePlayer = TestUtil.getPlayerByColor("white"); // the player to move was set as whit in beginning of scenario
		assertEquals(currentPlayer, whitePlayer);	
	}

	/**
	 * @author Kaan Gure
	 */

	@Then("No wall move shall be registered with {string} at position \\({int}, {int})")
	public void no_wall_move_shall_be_registered_with_at_position(String dir, Integer row, Integer col) {
		GamePosition gamePosition = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		List<Wall> boardWalls = new ArrayList<Wall>();
		List<Wall> whitewalls = gamePosition.getWhiteWallsOnBoard();
		List<Wall> blackwalls = gamePosition.getBlackWallsOnBoard();
		boardWalls.addAll(blackwalls);
		boardWalls.addAll(whitewalls);
		Tile tile = TestUtil.getTile(row, col);
		Direction direction = TestUtil.getDirection(dir);
		boolean moveExists = false;
		for (Wall wall: boardWalls) {
			Tile tileToCheck = wall.getMove().getTargetTile();
			Direction dirToCheck = wall.getMove().getWallDirection();
			if(tile.equals(tileToCheck) && direction.equals(dirToCheck)) {
				moveExists = true;
			}
		}
		assertFalse(moveExists);
	}

}
