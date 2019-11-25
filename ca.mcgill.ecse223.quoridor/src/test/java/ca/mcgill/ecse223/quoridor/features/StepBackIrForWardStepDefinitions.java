package ca.mcgill.ecse223.quoridor.features;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepBackIrForWardStepDefinitions {
	
	@When("Step backward is initiated")
	public void step_backward_is_initiated() {
	    QuoridorController.stepBack();
	}
	@When("Step forward is initiated")
	public void step_forward_is_initiated() {
	    QuoridorController.stepForward();
	}
	@Then("The next move shall be \\({int}, {int})")
	public void the_next_move_shall_be(Integer int1, Integer int2) {

	}

	@Then("White player's position shall be \\({int}, {int})")
	public void white_player_s_position_shall_be(Integer row, Integer col) {

		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		GamePosition gp = game.getCurrentPosition();
		assertEquals(row,gp.getWhitePosition().getTile().getRow());
		assertEquals(col,gp.getWhitePosition().getTile().getColumn());
	}

	@Then("Black player's position shall be \\({int}, {int})")
	public void black_player_s_position_shall_be(Integer row, Integer col) {
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		GamePosition gp = game.getCurrentPosition();
		assertEquals(row, gp.getBlackPosition().getTile().getRow());
		assertEquals(col, gp.getBlackPosition().getTile().getColumn());
	}

	@Then("White has <wwallno> on stock")
	public void white_has_wwallno_on_stock() {

	}

	@Then("Black has {int} on stock")
	public void black_has_on_stock(Integer int1) {

	}
}
