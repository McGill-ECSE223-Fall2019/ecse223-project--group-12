package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.assertFalse;

import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoadGameStepDefinitions {
	// All the main steps for svae game are in the saveposition step definitions 
	//load/save game calls the same methods as load/save position (i.e. both tests should pass with the new version of the function)
	
	private Boolean isValid;
	private String path;
	private GameStatus gameStatus;
	
	@When("I initiate to load a game in {string}")
	public void i_initiate_to_load_a_game_in(String string) {
		path = string;
		isValid = QuoridorController.loadGame(string, true);
	}

	@When("Each game move is valid")
	public void each_game_move_is_valid() {
		isValid = QuoridorController.loadGame(path, true);
	}

	@When("The game has no final results")
	public void the_game_has_no_final_results() {
		gameStatus = QuoridorApplication.getQuoridor().getCurrentGame().getGameStatus();
	}

	@When("The game to load has an invalid move")
	public void the_game_to_load_has_an_invalid_move() {
		isValid = QuoridorController.loadGame(path, true);
	}

	@Then("The game shall notify the user that the game file is invalid")
	public void the_game_shall_notify_the_user_that_the_game_file_is_invalid() {
		assertFalse(isValid);
	}
}
