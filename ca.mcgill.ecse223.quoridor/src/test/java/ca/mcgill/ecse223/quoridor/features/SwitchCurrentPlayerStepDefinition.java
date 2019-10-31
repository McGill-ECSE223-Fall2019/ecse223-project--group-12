package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.*;

import java.sql.Time;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import org.junit.AfterClass;

import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
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
	Timer timer = new Timer();

	@Given("The player to move is {string}")
	public void the_player_to_move_is(String color) {
		Game g = QuoridorApplication.getQuoridor().getCurrentGame();
		GamePosition gp = g.getCurrentPosition();
		Player p = TestUtil.getPlayerByColor(color);
		gp.setPlayerToMove(p);
	}

	@Given("The clock of {string} is running")
	public void the_clock_of_is_running(String color) {
		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			public void run() {
				Player p = TestUtil.getCurrentPlayer();
				Time time = p.getRemainingTime();
				Calendar cal = Calendar.getInstance();
				cal.setTime(time);
				cal.add(Calendar.SECOND, -1);
				Time newTime = new Time(cal.getTimeInMillis());
				p.setRemainingTime(newTime);
			}
		};
		timer.scheduleAtFixedRate(timerTask, 0, 1000);
	}

	@Given("The clock of {string} is stopped")
	public void the_clock_of_is_stopped(String color) {
		// Pre condition not necessary, since the thread above runs the clock for the
		// player to move (Player to move is also set as precondition, so this is ok).
		// We could use an assertion to show that the precondition holds and prove that
		// the background thread is indeed doing this, but this already verified by the
		// post condition clauses below
	}

	@When("Player {string} completes his move")
	public void player_completes_his_move(String color) {
		QuoridorController.makeMove();
	}

	@Then("The user interface shall be showing it is {string} turn")
	public void the_user_interface_shall_be_showing_it_is_turn(String color) {
		Player player = TestUtil.getPlayerByColor(color);
		assertEquals(player, quoridor.getCurrentGame().getCurrentPosition().getPlayerToMove());

	}

	@Then("The clock of {string} shall be stopped")
	public void the_clock_of_shall_be_stopped(String color) {
		// get the remaining time of current player
		Player otherPlayer = TestUtil.getPlayerByColor(color);
		Time initialTime = otherPlayer.getRemainingTime();
		// Wait 1 second
		try {
			Thread.sleep(1001);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// get the remaining time again
		Time sameTime = otherPlayer.getRemainingTime();
		// Check if time is the same
		boolean notcountingDown = initialTime.equals(sameTime);
		boolean countingDown = initialTime.after(sameTime);
		assertTrue(notcountingDown);
		assertFalse(countingDown);
	}

	@Then("The clock of {string} shall be running")
	public void the_clock_of_shall_be_running(String color) {
		// get the remaining time of current player
		Player currentPlayer = TestUtil.getPlayerByColor(color);
		Time initialTime = currentPlayer.getRemainingTime();
		// Wait 1 second
		try {
			Thread.sleep(1001);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// get the remaining time again
		Time afterTime = currentPlayer.getRemainingTime();
		// Check if time is counting down (00:30:00 is after 00:29:59)
		boolean countingDown = initialTime.after(afterTime);
		boolean notcountingDown = initialTime.equals(afterTime);
		assertTrue(countingDown);
		assertFalse(notcountingDown);
	}

	@Then("The next player to move shall be {string}")
	public void the_next_player_to_move_shall_be(String color) {
		Player player = TestUtil.getPlayerByColor(color);
		assertEquals(player, quoridor.getCurrentGame().getCurrentPosition().getPlayerToMove());
	}

	@AfterClass
	public void cleanUp() {
		timer.cancel();
	}
}
