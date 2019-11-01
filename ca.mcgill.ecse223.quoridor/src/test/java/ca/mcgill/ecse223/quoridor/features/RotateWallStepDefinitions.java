package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.assertTrue;


import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Direction;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * 
 * @author Zechen Ren
 *
 */
public class RotateWallStepDefinitions {

	@When("I try to flip the wall")
	public void i_try_to_flip_the_wall() {
		QuoridorController.rotateWall();
	}

	@Then("The wall shall be rotated over the board to {string}")
	public void the_wall_shall_be_rotated_over_the_board_to(String string) {
		boolean existWall = false;
		switch (string) {
		case "horizontal":
			if (Direction.Horizontal == QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate()
					.getWallDirection()) {
				existWall = true;
				break;
			}
		case "vertical":
			if (Direction.Vertical == QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate()
					.getWallDirection()) {
				existWall = true;
				break;
			}
		}
		assertTrue(existWall);
	}
	}


