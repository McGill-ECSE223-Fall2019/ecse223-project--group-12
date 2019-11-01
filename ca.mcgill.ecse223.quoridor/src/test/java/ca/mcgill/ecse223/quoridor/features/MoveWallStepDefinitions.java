package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.InvalidInputException;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.model.WallMove;
import ca.mcgill.ecse223.quoridor.util.TestUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/*
 * Note that the classes may not conatain all relative steps for a certain feature, 
 * since there are duplicate steps between scenarios.
 * 
 */
/**
 * 
 * @author Zechen Ren
 *
 */
public class MoveWallStepDefinitions {

	private String errorMessage = "";

	@Given("A wall move candidate exists with {string} at position \\({int}, {int})")
	public void a_wall_move_candidate_exists_with_at_position(String dir, Integer row, Integer col) {
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		Player blackPlayer = game.getBlackPlayer();
		QuoridorApplication.getQuoridor().getBoard().addTile(row, col);
		Tile tile = TestUtil.getTile(row, col);
		Direction direction = null;
		try {
			direction = TestUtil.getDirection(dir);
		} catch (java.lang.IllegalArgumentException e) {
		}
		Wall wall = game.getCurrentPosition().getBlackWallsInStock().get(1);
		game.setWallMoveCandidate(new WallMove(1, 1, blackPlayer, tile, game, direction, wall));
	}

	@Given("The wall candidate is not at the {string} edge of the board")
	public void the_wall_candidate_is_not_at_the_edge_of_the_board(String side) {
		switch (side) {
		case "right":
			assertNotEquals(8, QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile()
					.getColumn());
			break;
		case "left":
			assertNotEquals(1, QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile()
					.getColumn());
			break;
		case "up":
			assertNotEquals(1,
					QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile().getRow());
			break;
		case "down":
			assertNotEquals(8,
					QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile().getRow());
			break;
		default:
			throw new java.lang.IllegalArgumentException("Invalid Side: " + side);
		}
	}

	@When("I try to move the wall {string}")
	public void i_try_to_move_the_wall(String side) throws InvalidInputException {
		try {
			QuoridorController.moveWall(side);
		} catch (InvalidInputException e) {
			errorMessage = e.getMessage();
		}
	}

	@Then("The wall shall be moved over the board to position \\({int}, {int})")
	public void the_wall_shall_be_moved_over_the_board_to_position(Integer row, Integer col) {
		boolean existWall = false;
		Tile tile = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile();
		if (tile.getRow() == row && tile.getColumn() == col) {
			existWall = true;
			}
		assertTrue(existWall);
	}

	@Then("A wall move candidate shall exist with {string} at position \\({int}, {int})")
	public void a_wall_move_candidate_shall_exist_with_at_position(String dir, Integer row, Integer col) {
		boolean existWall = false;
		switch (dir) {
		case "horizontal":
			if (Direction.Horizontal == QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate()
					.getWallDirection()
					&& QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile()
							.getRow() == row
					&& QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile()
							.getColumn() == col) {
				existWall = true;
				break;
			}
		case "vertical":
			if (Direction.Vertical == QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate()
					.getWallDirection()
					&& QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile()
							.getRow() == row
					&& QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile()
							.getColumn() == col) {
				existWall = true;
				break;
			}
		}
		assertTrue(existWall);
	}

	@Given("The wall candidate is at the {string} edge of the board")
	public void the_wall_candidate_is_at_the_edge_of_the_board(String side) {
		// Write code here that turns the phrase above into concrete actions
		switch (side) {
		case "right":
			assertEquals(8 , QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile()
					.getColumn());
			break;
		case "left":
			assertEquals(1 , QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile()
					.getColumn());
			break;
		case "up":
			assertEquals(1 ,
					QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile().getRow());
			break;
		case "down":
			assertEquals(8 ,
					QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile().getRow());
			break;
		default:
			throw new java.lang.IllegalArgumentException("Invalid side: " + side);
		}
	}

	@Then("I shall be notified that my move is illegal")
	public void i_shall_be_notified_that_my_move_is_illegal() {
	assertTrue (errorMessage.contains("Reaching"));	
	}
}
