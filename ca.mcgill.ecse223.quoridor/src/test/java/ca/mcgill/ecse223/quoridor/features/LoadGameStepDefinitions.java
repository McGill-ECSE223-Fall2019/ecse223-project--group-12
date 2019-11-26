package ca.mcgill.ecse223.quoridor.features;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoadGameStepDefinitions {
	// All the main steps for svae game are in the saveposition step definitions 
	//load/save game calls the same methods as load/save position (i.e. both tests should pass with the new version of the function)
	@When("I initiate to load a game in {string}")
	public void i_initiate_to_load_a_game_in(String string) {
		// Write code here that turns the phrase above into concrete actions
		throw new cucumber.api.PendingException();
	}

	// I think these 3 extra when clause are useless... loadGame(File file) should check all of those things
	@When("Each game move is valid")
	public void each_game_move_is_valid() {
		// Write code here that turns the phrase above into concrete actions
		throw new cucumber.api.PendingException();
	}

	@When("The game has no final results")
	public void the_game_has_no_final_results() {
		// Write code here that turns the phrase above into concrete actions
		throw new cucumber.api.PendingException();
	}

	@When("The game to load has an invalid move")
	public void the_game_to_load_has_an_invalid_move() {
		// Write code here that turns the phrase above into concrete actions
		throw new cucumber.api.PendingException();
	}

	@Then("The game shall notify the user that the game file is invalid")
	public void the_game_shall_notify_the_user_that_the_game_file_is_invalid() {
		// Write code here that turns the phrase above into concrete actions
		throw new cucumber.api.PendingException();
	}
}
