package ca.mcgill.ecse223.quoridor.features;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/*
 * Note that the classes may not conatain all relative steps for a certain feature, 
 * since there are duplicate steps between scenarios.
 */

public class RotateWallStepDefinitions {
	
	@When("I try to flip the wall")
	public void i_try_to_flip_the_wall() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The wall shall be rotated over the board to {string}")
	public void the_wall_shall_be_rotated_over_the_board_to(String newDir) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}
}
