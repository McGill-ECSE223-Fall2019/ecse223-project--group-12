package ca.mcgill.ecse223.quoridor.features;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.util.TestUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;


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
		boolean rotateWall = false;
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		PlayerPosition playerPosition = TestUtil.getPlayerPositionByColor
				(game.getCurrentPosition().getPlayerToMove().getUser().getName());
		Player player = TestUtil.getPlayerByColor
				(game.getCurrentPosition().getPlayerToMove().getUser().getName());
		List<Wall> allWall = player.getWalls();
		int row = playerPosition.getTile().getRow();
		int col = playerPosition.getTile().getColumn();
		for (int i = 0; i < allWall.size(); i++) {
			if (allWall.get(i).getMove().getTargetTile().getColumn() == col
				&& allWall.get(i).getMove().getTargetTile().getRow() == row
				&& allWall.get(i).getMove().getWallDirection() == TestUtil.getDirection(string)) {
				rotateWall = true;
				break;
			}
		}
		
		assertTrue(rotateWall);
		}
			
	}
}
