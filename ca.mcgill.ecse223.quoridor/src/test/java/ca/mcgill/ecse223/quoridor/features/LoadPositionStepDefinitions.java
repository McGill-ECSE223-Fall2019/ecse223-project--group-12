package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.util.TestUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/*
 * Note that the classes may not contain all relative steps for a certain feature, 
 * since there are duplicate steps between scenarios.
 */

/**
 * 
 * @author Francis Comeau
 *
 */
public class LoadPositionStepDefinitions {
	
	private Boolean isValid;

	@When("I initiate to load a saved game {string}")
	public void i_initiate_to_load_a_saved_game(String fileName) {
		try {
			isValid = QuoridorController.loadPosition(fileName, true);
		} catch (java.lang.UnsupportedOperationException e) {
			// Skip test if method not implemented
			throw new cucumber.api.PendingException();
		}

	}

	@When("The position to load is valid") //Causing trouble for now, will fix it later
	public void the_position_to_load_is_valid() {
		isValid = true;
		throw new cucumber.api.PendingException();
	}

	@Then("It shall be {string}'s turn")
	public void it_shall_be_s_turn(String color) {
		assertEquals(color, QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove()
				.getUser().getName());
	}

	@Then("{string} shall be at {int}:{int}")
	public void shall_be_at(String color, Integer row, Integer col) {
		PlayerPosition playerPosition = TestUtil.getPlayerPositionByColor(color);
		assertEquals(playerPosition.getTile().getColumn(), col, 0);
		assertEquals(playerPosition.getTile().getRow(), row, 0);
	}

	@Then("{string} shall have a vertical wall at {int}:{int}")
	public void shall_have_a_vertical_wall_at(String color, Integer row, Integer col) {
		Player player = TestUtil.getPlayerByColor(color);
		List<Wall> walls = player.getWalls();
		boolean wallExists = false;
		for (int i = 0; i < walls.size(); i++) {
			if (walls.get(i).getMove().getTargetTile().getColumn() == col
					&& walls.get(i).getMove().getTargetTile().getRow() == row
					&& walls.get(i).getMove().getWallDirection() == Direction.Vertical) {
				wallExists = true;
				break;
			}
		}
		assertTrue(wallExists);
	}

	@Then("{string} shall have a horizontal wall at {int}:{int}")
	public void shall_have_a_horizontal_wall_at(String color, Integer row, Integer col) {
		Player player = TestUtil.getPlayerByColor(color);
		List<Wall> walls = player.getWalls();
		boolean wallExists = false;
		for (int i = 0; i < walls.size(); i++) {
			if (walls.get(i).getMove().getTargetTile().getColumn() == col
					&& walls.get(i).getMove().getTargetTile().getRow() == row
					&& walls.get(i).getMove().getWallDirection() == Direction.Horizontal) {
				wallExists = true;
				break;
			}
		}
		assertTrue(wallExists);
	}

	@Then("Both players shall have {int} in their stacks")
	public void both_players_shall_have_in_their_stacks(Integer remainingWalls) {
		int whiteRemainingWalls = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock().size();
		int blackRemainingWalls = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsInStock().size();
		assertEquals(whiteRemainingWalls, remainingWalls, 0);
		assertEquals(blackRemainingWalls, remainingWalls, 0);
		throw new cucumber.api.PendingException();
	}

	@When("The position to load is invalid")
	public void the_position_to_load_is_invalid() {
		isValid = false;
		throw new cucumber.api.PendingException();
	}

	@Then("The load shall return an error")
	public void the_load_shall_return_an_error() {
		assertFalse(isValid);
		throw new cucumber.api.PendingException();
	}
}
