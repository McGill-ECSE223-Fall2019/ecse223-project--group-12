package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Time;

import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.view.GamePanel;
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
		QuoridorController.initBoard();
	}

	@Then("It shall be white player to move")
	public void it_shall_be_white_player_to_move() {
		assertEquals(quoridor.getCurrentGame().getWhitePlayer(),
				quoridor.getCurrentGame().getCurrentPosition().getPlayerToMove());
	}

	@Then("White's pawn shall be in its initial position")
	public void white_s_pawn_shall_be_in_its_initial_position() {
		assertEquals(9, quoridor.getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getRow());
		assertEquals(5, quoridor.getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getColumn());
	}

	@Then("Black's pawn shall be in its initial position")
	public void black_s_pawn_shall_be_in_its_initial_position() {

		assertEquals(1, quoridor.getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getRow());
		assertEquals(5, quoridor.getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getColumn());
	}

	@Then("All of White's walls shall be in stock")
	public void all_of_White_s_walls_shall_be_in_stock() {
		assertEquals(10, quoridor.getCurrentGame().getCurrentPosition().numberOfWhiteWallsInStock());
	}

	@Then("All of Black's walls shall be in stock")
	public void all_of_Black_s_walls_shall_be_in_stock() {
		assertEquals(10, quoridor.getCurrentGame().getCurrentPosition().numberOfBlackWallsInStock());
	}

	@Then("White's clock shall be counting down")
	public void white_s_clock_shall_be_counting_down() {
		// get the current remaining time
		Time initialTime = QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().getRemainingTime();
		// Wait 1 second
		try {
			Thread.sleep(1001);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// get the remaining time again
		Time afterTime = QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().getRemainingTime();
		// Check if time is counting down (00:30:00 is after 00:29:59)
		/*boolean countingDown = initialTime.after(afterTime);
		boolean notcountingDown = initialTime.equals(afterTime);*/
		//boolean countingDown = initialTime.getTime() > afterTime.getTime();
		assertTrue(initialTime.getTime() > afterTime.getTime());
		//assertFalse(notcountingDown);
	}

	@Then("It shall be shown that this is White's turn")
	public void it_shall_be_shown_that_this_is_White_s_turn() {
		assertEquals(quoridor.getCurrentGame().getWhitePlayer(),
				quoridor.getCurrentGame().getCurrentPosition().getPlayerToMove());
		GamePanel gamePanel = new GamePanel();
		gamePanel.refreshData();
		boolean b = gamePanel.getPlayerLabel().contains("White");
		assertTrue(b);

	}
}
