package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Time;
import java.util.ArrayList;

import javax.swing.JDialog;

import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.InvalidMoveException;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController.Side;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.util.TestUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ReportFinalResultStepDefinitions {
	
	@When("The game is no longer running")
	public void the_game_is_no_longer_running() {
	    // Write code here that turns the phrase above into concrete actions
		
		// Entering random one of the exiting state
		TestUtil.initQuoridorAndBoard();
		ArrayList<Player> players = TestUtil.createUsersAndPlayers("test usr1", "test usr2");
		TestUtil.createAndStartGame(players);
		QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(GameStatus.WhiteWon);
	}

	@Then("The final result shall be displayed")
	public void the_final_result_shall_be_displayed() {
	    // Write code here that turns the phrase above into concrete actions
		//Quoridor game = QuoridorApplication.getQuoridor();
		throw new cucumber.api.PendingException();
	}

	@Then("White's clock shall not be counting down")
	public void white_s_clock_shall_not_be_counting_down() {
	    // Write code here that turns the phrase above into concrete actions
		// get the remaining time of current player
				Player p = TestUtil.getPlayerByColor("white");
				Time firstTime = p.getRemainingTime();
				// Wait 1 second
				try {
					Thread.sleep(1001);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// get the remaining time again
				Time sameTime = p.getRemainingTime();
				// Check if time is the same
				boolean notcountingDown = firstTime.equals(sameTime);
				boolean countingDown = firstTime.after(sameTime);
				assertTrue(notcountingDown);
				assertFalse(countingDown);
		
		//throw new cucumber.api.PendingException();
	}

	@Then("Black's clock shall not be counting down")
	public void black_s_clock_shall_not_be_counting_down() {
	    // Write code here that turns the phrase above into concrete actions
		Player p = TestUtil.getPlayerByColor("black");
		Time firstTime = p.getRemainingTime();
		// Wait 1 second
		try {
			Thread.sleep(1001);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// get the remaining time again
		Time sameTime = p.getRemainingTime();
		// Check if time is the same
		boolean notcountingDown = firstTime.equals(sameTime);
		boolean countingDown = firstTime.after(sameTime);
		assertTrue(notcountingDown);
		assertFalse(countingDown);
		
		//throw new cucumber.api.PendingException();
	}

	@Then("White shall be unable to move")
	public void white_shall_be_unable_to_move() {
	    // Write code here that turns the phrase above into concrete actions
		
		boolean moveSuccess = false;
		GamePosition gp = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		PlayerPosition oldWhitePostion = gp.getWhitePosition();
		try {
			Player p = TestUtil.getPlayerByColor("white");
			//trying both moving left and moving right, 
			//so to eliminate the case of reaching west 
			//or east boundary
			moveSuccess = QuoridorController.movePawn(p, Enum.valueOf(Side.class, "left"));
			moveSuccess = QuoridorController.movePawn(p, Enum.valueOf(Side.class, "right"));
		} catch (InvalidMoveException e) {
			moveSuccess = true;
		}
		GamePosition newGp = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		PlayerPosition newWhitePostion = newGp.getWhitePosition();
		assertTrue (oldWhitePostion == newWhitePostion);
		assertFalse (moveSuccess);
		
		//throw new cucumber.api.PendingException();
	}

	@Then("Black shall be unable to move")
	public void black_shall_be_unable_to_move() {
	    // Write code here that turns the phrase above into concrete actions
		boolean moveSuccess = false;
		GamePosition gp = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		PlayerPosition oldBlackPostion = gp.getBlackPosition();
		try {
			Player p = TestUtil.getPlayerByColor("black");
			//trying both moving left and moving right, 
			//so to eliminate the case of reaching west 
			//or east boundary
			moveSuccess = QuoridorController.movePawn(p, Enum.valueOf(Side.class, "left"));
			moveSuccess = QuoridorController.movePawn(p, Enum.valueOf(Side.class, "right"));
		} catch (InvalidMoveException e) {
			moveSuccess = true;
		}
		GamePosition newGp = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		PlayerPosition newBlackPostion = newGp.getBlackPosition();
		assertTrue (oldBlackPostion == newBlackPostion);
		assertFalse (moveSuccess);
		//throw new cucumber.api.PendingException();
	}
}
