package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.Wall;
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
	public void i_initiate_to_load_a_saved_game(String string) {
		// Write code here that turns the phrase above into concrete actions
		try {
			QuoridorController.loadPosition(string);
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
	public void it_shall_be_s_turn(String string) {
		// Write code here that turns the phrase above into concrete actions
		assertEquals(string, QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove()
				.getUser().getName());
	}

	@Then("{string} shall be at {int}:{int}")
	public void shall_be_at(String string, Integer int1, Integer int2) {
		// Write code here that turns the phrase above into concrete actions
		PlayerPosition playerPosition;
		if (QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().getUser().getName().equals(string)) {
			playerPosition = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition();
		} else {
			playerPosition = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition();
		}
		assertEquals(playerPosition.getTile().getColumn(), int1, 0);
		assertEquals(playerPosition.getTile().getRow(), int2, 0);
	}

	@Then("{string} shall have a vertical wall at {int}:{int}")
	public void shall_have_a_vertical_wall_at(String string, Integer int1, Integer int2) {
		// Write code here that turns the phrase above into concrete actions
		List<Wall> walls;
		if (QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().getUser().getName().equals(string)) {
			walls = QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().getWalls();
		} else {
			walls = QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer().getWalls();
		}
		boolean wallExists = false;
		for (int i = 0; i < walls.size(); i++) {
			if (walls.get(i).getMove().getTargetTile().getColumn() == int1
					&& walls.get(i).getMove().getTargetTile().getRow() == int2
					&& walls.get(i).getMove().getWallDirection() == Direction.Vertical) {
				wallExists = true;
				break;
			}
		}
		assertTrue(wallExists);
		throw new cucumber.api.PendingException();
	}

	@Then("{string} shall have a horizontal wall at {int}:{int}")
	public void shall_have_a_horizontal_wall_at(String string, Integer int1, Integer int2) {
		// Write code here that turns the phrase above into concrete actions
		List<Wall> walls;
		if (QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().getUser().getName().equals(string)) {
			walls = QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().getWalls();
		} else {
			walls = QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer().getWalls();
		}
		boolean wallExists = false;
		for (int i = 0; i < walls.size(); i++) {
			if (walls.get(i).getMove().getTargetTile().getColumn() == int1
					&& walls.get(i).getMove().getTargetTile().getRow() == int2
					&& walls.get(i).getMove().getWallDirection() == Direction.Horizontal) {
				wallExists = true;
				break;
			}
		}
		assertTrue(wallExists);
	}

	@Then("Both players shall have {int} in their stacks")
	public void both_players_shall_have_in_their_stacks(Integer int1) {
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
