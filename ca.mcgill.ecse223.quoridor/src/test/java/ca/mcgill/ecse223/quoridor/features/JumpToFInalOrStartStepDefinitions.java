package ca.mcgill.ecse223.quoridor.features;

import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import io.cucumber.java.en.When;

/**
 * 
 * @author Weige
 *
 */
public class JumpToFInalOrStartStepDefinitions {

	@When("Jump to start position is initiated")
	public void jump_to_start_position_is_initiated() {
		// Write code here that turns the phrase above into concrete actions
		QuoridorController.jumpToStart();
	}

	@When("Jump to final position is initiated")
	public void jump_to_final_position_is_initiated() {
		// Write code here that turns the phrase above into concrete actions
		QuoridorController.jumpToFinal();
	}

}
