package ca.mcgill.ecse223.quoridor.features;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.Direction;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;


/*
 * Note that the classes may not conatain all relative steps for a certain feature, 
 * since there are duplicate steps between scenarios.
 */

public class MoveWallStepDefinitions {
	@Given("A wall move candidate exists with {string} at position \\({int}, {int})")
	public void a_wall_move_candidate_exists_with_at_position(String dir, Integer row, Integer col) {
	    // Write code here that turns the phrase above into concrete actions
		boolean existWall = false;
		switch(dir){
		case "horizontal":
			if(Direction.Horizontal == QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallDirection()
				&& QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile().getRow() == row
				&& QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile().getColumn() == col) {
			existWall = true;
			break;
			}
		case "vertical":
			if(Direction.Vertical == QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallDirection()
			&& QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile().getRow() == row
			&& QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile().getColumn() == col) {
		existWall = true;
		break;	
		}
		}
		assertTrue(existWall);
		throw new cucumber.api.PendingException();
	}

	@Given("The wall candidate is not at the {string} edge of the board")
	public void the_wall_candidate_is_not_at_the_edge_of_the_board(String side) {
	    // Write code here that turns the phrase above into concrete actions
		switch (side) {
		case "right":
			assertNotEquals(8, QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile().getColumn());
		case "left":
			assertNotEquals(1, QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile().getColumn());
		case "up":
			assertNotEquals(1, QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile().getRow());
		case "down":
			assertNotEquals(8, QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile().getRow());
		}
	    throw new cucumber.api.PendingException();
	}

	@When("I try to move the wall {string}")
	public void i_try_to_move_the_wall(String side) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The wall shall be moved over the board to position \\({int}, {int})")
	public void the_wall_shall_be_moved_over_the_board_to_position(Integer row, Integer col) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("A wall move candidate shall exist with {string} at position \\({int}, {int})")
	public void a_wall_move_candidate_shall_exist_with_at_position(String dir, Integer row, Integer col) {
	    // Write code here that turns the phrase above into concrete actions
		boolean existWall = false;
		switch(dir){
		case "horizontal":
			if(Direction.Horizontal == QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallDirection()
				&& QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile().getRow() == row
				&& QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile().getColumn() == col) {
			existWall = true;
			break;
			}
		case "vertical":
			if(Direction.Vertical == QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallDirection()
			&& QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile().getRow() == row
			&& QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile().getColumn() == col) {
		existWall = true;
		break;	
		}
		}
		assertTrue(existWall);
		throw new cucumber.api.PendingException();
}


	@Given("The wall candidate is at the {string} edge of the board")
	public void the_wall_candidate_is_at_the_edge_of_the_board(String dir) {
	    // Write code here that turns the phrase above into concrete actions
		switch (dir) {
		case "right":
			assertEquals(8, QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile().getColumn());
		case "left":
			assertEquals(1, QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile().getColumn());
		case "up":
			assertEquals(1, QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile().getRow());
		case "down":
			assertEquals(8, QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile().getRow());
		}
	    throw new cucumber.api.PendingException();
	}

	@Then("I shall be notified that my move is illegal")
	public void i_shall_be_notified_that_my_move_is_illegal() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}
}
