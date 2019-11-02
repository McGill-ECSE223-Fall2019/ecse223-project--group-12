package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.assertEquals;

import java.sql.Time;

import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.InvalidInputException;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/*
 * Note that the classes may not conatain all relative steps for a certain feature, 
 * since there are duplicate steps between scenarios.
 */
/**
 * 
 * @author Remi Carriere
 *
 */
public class SetTotalThinkingTimeStepDefinition {

	@When("{int}:{int} is set as the thinking time")
	public void is_set_as_the_thinking_time(Integer minutes, Integer seconds) {
		try {
			Time time = Time.valueOf("00:"+minutes+":"+seconds);
			QuoridorController.setTotalThinkingTime(time);
		} catch (InvalidInputException e) {
			throw new cucumber.api.PendingException();
		}
	}

	@Then("Both players shall have {int}:{int} remaining time left")
	public void both_players_shall_have_remaining_time_left(Integer minutes, Integer seconds) {
		Time time = Time.valueOf("00:"+minutes+":"+seconds);
		assertEquals(time, QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer().getRemainingTime());
		assertEquals(time, QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().getRemainingTime());
	}

}
