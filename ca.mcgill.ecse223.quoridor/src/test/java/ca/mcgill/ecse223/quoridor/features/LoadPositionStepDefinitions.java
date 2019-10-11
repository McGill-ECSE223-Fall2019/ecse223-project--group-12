package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
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

	@When("I initiate to load a saved game {string}")
	public void i_initiate_to_load_a_saved_game(String fileName) {
		// Write code here that turns the phrase above into concrete actions
		try {
			QuoridorController.loadPosition(fileName);
		} catch (java.lang.UnsupportedOperationException e) {
			// Skip test if method not implemented
			throw new cucumber.api.PendingException();
		}

	}

	@When("The position to load is valid")
	public void the_position_to_load_is_valid() {
		// Write code here that turns the phrase above into concrete actions
		try {
			assertTrue(QuoridorController
					.validatePosition(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()));
		} catch (java.lang.UnsupportedOperationException e) {
			// Skip test if method not implemented
			throw new cucumber.api.PendingException();
		}
	}

	@Then("It shall be {string}'s turn")
	public void it_shall_be_s_turn(String color) {
		// Write code here that turns the phrase above into concrete actions
		assertEquals(color, QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove()
				.getUser().getName());
	}

	@Then("{string} shall be at {int}:{int}")
	public void shall_be_at(String color, Integer row, Integer col) {
		// Write code here that turns the phrase above into concrete actions
		PlayerPosition playerPosition = TestUtil.getPlayerPositionByColor(color);
		assertEquals(playerPosition.getTile().getColumn(), col, 0);
		assertEquals(playerPosition.getTile().getRow(), row, 0);
	}

	@Then("{string} shall have a vertical wall at {int}:{int}")
	public void shall_have_a_vertical_wall_at(String color, Integer row, Integer col) {
		// Write code here that turns the phrase above into concrete actions
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
		throw new cucumber.api.PendingException();
	}

	@Then("{string} shall have a horizontal wall at {int}:{int}")
	public void shall_have_a_horizontal_wall_at(String color, Integer row, Integer col) {
		// Write code here that turns the phrase above into concrete actions
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
		// Write code here that turns the phrase above into concrete actions
		throw new cucumber.api.PendingException();
	}

	@When("The position to load is invalid")
	public void the_position_to_load_is_invalid(GamePosition gamePosition) {
		// Write code here that turns the phrase above into concrete actions
		try {
			assertFalse(QuoridorController.validatePosition(gamePosition));
		} catch (java.lang.UnsupportedOperationException e) {
			// Skip test if method not implemented
			throw new cucumber.api.PendingException();
		}
	}

	@Then("The load shall return an error")
	public void the_load_shall_return_an_error() {
		// Write code here that turns the phrase above into concrete actions
		throw new cucumber.api.PendingException();
	}
}
