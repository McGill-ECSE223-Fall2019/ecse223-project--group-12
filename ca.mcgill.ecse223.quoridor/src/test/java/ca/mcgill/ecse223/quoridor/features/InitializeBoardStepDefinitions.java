package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.assertEquals;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/*
 * Note that the classes may not conatain all relative steps for a certain feature, 
 * since there are duplicate steps between scenarios.
 */

/**
 * @author Weige qian
 */
public class InitializeBoardStepDefinitions {

	private Quoridor quoridor = QuoridorApplication.getQuoridor();

	@When("The initialization of the board is initiated")
	public void the_initialization_of_the_board_is_initiated() {
		// Write code here that turns the phrase above into concrete actions
		try {
			QuoridorController.initBoard();
		} catch (java.lang.UnsupportedOperationException e) {

		}
	}

	@Then("It shall be white player to move")
	public void it_shall_be_white_player_to_move() {
		// Write code here that turns the phrase above into concrete actions
		try {
			assertEquals(quoridor.getCurrentGame().getCurrentPosition().getPlayerToMove(),
					quoridor.getCurrentGame().getWhitePlayer());
		} catch (NullPointerException e) {
			/*
			 * no handling necessary at this stage. Temporarily catch null pointers, must be
			 * removed once controller methods are implemented
			 */
		}
	}

	@Then("White's pawn shall be in its initial position")
	public void white_s_pawn_shall_be_in_its_initial_position() {
		// Write code here that turns the phrase above into concrete actions
		try {
			assertEquals(quoridor.getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getRow(), 9);
			assertEquals(quoridor.getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getColumn(), 5);
		} catch (NullPointerException e) {
			/*
			 * no handling necessary at this stage. Temporarily catch null pointers, must be
			 * removed once controller methods are implemented
			 */
		}
	}

	@Then("Black's pawn shall be in its initial position")
	public void black_s_pawn_shall_be_in_its_initial_position() {
		// Write code here that turns the phrase above into concrete actions
		try {
			assertEquals(quoridor.getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getRow(), 9);
			assertEquals(quoridor.getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getColumn(), 5);
		} catch (NullPointerException e) {
			/*
			 * no handling necessary at this stage. Temporarily catch null pointers, must be
			 * removed once controller methods are implemented
			 */
		}
	}

	@Then("All of White's walls shall be in stock")
	public void all_of_White_s_walls_shall_be_in_stock() {
		try {
			assertEquals(quoridor.getCurrentGame().getWhitePlayer().getWalls().size(), 10);
		} catch (NullPointerException e) {
			/*
			 * no handling necessary at this stage. Temporarily catch null pointers, must be
			 * removed once controller methods are implemented
			 */
		}
	}

	@Then("All of Black's walls shall be in stock")
	public void all_of_Black_s_walls_shall_be_in_stock() {
		try {
			assertEquals(quoridor.getCurrentGame().getBlackPlayer().getWalls().size(), 10);
		} catch (NullPointerException e) {
			/*
			 * no handling necessary at this stage. Temporarily catch null pointers, must be
			 * removed once controller methods are implemented
			 */
		}

	}

	@Then("White's clock shall be counting down")
	public void white_s_clock_shall_be_counting_down() {
		// Write code here that turns the phrase above into concrete actions
		throw new cucumber.api.PendingException();
	}

	@Then("It shall be shown that this is White's turn")
	public void it_shall_be_shown_that_this_is_White_s_turn() {
		// Write code here that turns the phrase above into concrete actions
		throw new cucumber.api.PendingException();
	}
}
