package ca.mcgill.ecse223.quoridor.features;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * 
 * @author Remi Carriere
 *
 */
public class StepBackOrForWardStepDefinitions {
	
	@When("Step backward is initiated")
	public void step_backward_is_initiated() {
	    QuoridorController.stepBack();
	}
	@When("Step forward is initiated")
	public void step_forward_is_initiated() {
	    QuoridorController.stepForward();
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

	@Then("White has {int} on stock")
	public void white_has_wwallno_on_stock(Integer whiteWalls) {
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		GamePosition gp = game.getCurrentPosition();
		assertEquals(whiteWalls, gp.getWhiteWallsInStock().size());
	}

	@Then("Black has {int} on stock")
	public void black_has_on_stock(Integer blackWalls) {
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		GamePosition gp = game.getCurrentPosition();
		assertEquals(blackWalls, gp.getBlackWallsInStock().size());
	}
}
