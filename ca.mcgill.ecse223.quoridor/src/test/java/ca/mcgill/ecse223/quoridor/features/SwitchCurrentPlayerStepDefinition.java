package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.assertEquals;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.util.TestUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/*
 * Note that the classe may not conatain all relative steps for a certain feature, 
 * since there are duplicate steps between scenarios.
 */

public class SwitchCurrentPlayerStepDefinition {
	private Quoridor quoridor = QuoridorApplication.getQuoridor();
	@Given("The clock of {string} is running")
	public void the_clock_of_is_running(String color) {
		// Write code here that turns the phrase above into concrete actions
		throw new cucumber.api.PendingException();
	}

	@Given("The clock of {string} is stopped")
	public void the_clock_of_is_stopped(String color) {
		// Write code here that turns the phrase above into concrete actions
		Player player = TestUtil.getPlayerByColor(color);
		try {
			QuoridorController.stopClock(player);
		} catch (java.lang.UnsupportedOperationException e) {
			// Skip test if method not implemented
			throw new cucumber.api.PendingException();
		}
	}

	@When("Player {string} completes his move")
	public void player_completes_his_move(String color) {
		// Write code here that turns the phrase above into concrete actions
		Player player = TestUtil.getPlayerByColor(color);
		try {
			QuoridorController.makeMove(player);
		} catch (java.lang.UnsupportedOperationException e) {
			// Skip test if method not implemented
			throw new cucumber.api.PendingException();
		}
	}

	@Then("The user interface shall be showing it is {string} turn")
	public void the_user_interface_shall_be_showing_it_is_turn(String color) {
		// Write code here that turns the phrase above into concrete actions
		throw new cucumber.api.PendingException();
	}

	@Then("The clock of {string} shall be stopped")
	public void the_clock_of_shall_be_stopped(String color) {
		// Write code here that turns the phrase above into concrete actions
		throw new cucumber.api.PendingException();
	}

	@Then("The clock of {string} shall be running")
	public void the_clock_of_shall_be_running(String color) {
		// Write code here that turns the phrase above into concrete actions
		throw new cucumber.api.PendingException();
	}

	@Then("The next player to move shall be {string}")
	public void the_next_player_to_move_shall_be(String color) {
		// Write code here that turns the phrase above into concrete actions
		Player player = TestUtil.getPlayerByColor(color);
		assertEquals(quoridor.getCurrentGame().getCurrentPosition().getPlayerToMove(),player);
	}
}
