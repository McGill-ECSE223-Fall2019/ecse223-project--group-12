package ca.mcgill.ecse223.quoridor.features;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Direction;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.assertEquals;


/**
 * 
 * @author Zechen Ren
 *
 */
public class RotateWallStepDefinitions {
	
	@When("I try to flip the wall")
	public void i_try_to_flip_the_wall() {
		try {
			QuoridorController.rotateWall();
		} catch (java.lang.UnsupportedOperationException e) {
		    throw new cucumber.api.PendingException();
		}
	}

	
	@Then("The wall shall be rotated over the board to {string}")
	public void the_wall_shall_be_rotated_over_the_board_to(String string) {
	    // Write code here that turns the phrase above into concrete actions
		try {
			switch (string) {
			case "horizontal" :
				assertEquals(Direction.Horizontal, QuoridorApplication.getQuoridor().
					getCurrentGame().getWallMoveCandidate().getWallDirection());
			case "vertical":
				assertEquals(Direction.Vertical, QuoridorApplication.getQuoridor().
						getCurrentGame().getWallMoveCandidate().getWallDirection());
			}	
			} catch (java.lang.UnsupportedOperationException e) {
				throw new cucumber.api.PendingException();
			}
			
	}
}
