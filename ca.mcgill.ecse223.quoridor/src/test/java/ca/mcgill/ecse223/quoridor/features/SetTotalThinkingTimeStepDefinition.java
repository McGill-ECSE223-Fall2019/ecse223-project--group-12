package ca.mcgill.ecse223.quoridor.features;

import java.sql.Time;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.*;

/*
 * Note that the classes may not conatain all relative steps for a certain feature, 
 * since there are duplicate steps between scenarios.
 */

public class SetTotalThinkingTimeStepDefinition {

	@When("{int}:{int} is set as the thinking time")
	public void is_set_as_the_thinking_time(Integer minutes, Integer seconds) {
		Time time = createTime(minutes, seconds);
		try {
			QuoridorController.setTotalThinkingTime(time);
		} catch (java.lang.UnsupportedOperationException e) {
			// Skip test if method not implemented
			throw new cucumber.api.PendingException();
		}
	}

	@Then("Both players shall have {int}:{int} remaining time left")
	public void both_players_shall_have_remaining_time_left(Integer minutes, Integer seconds) {
		Time time = createTime(minutes, seconds);
		assertEquals(time, QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer().getRemainingTime());
		assertEquals(time, QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().getRemainingTime());
	}

	private Time createTime(int m, int s) {
		int mills = (60 * m + s) * 1000;
		return new Time(mills);
	}
}
