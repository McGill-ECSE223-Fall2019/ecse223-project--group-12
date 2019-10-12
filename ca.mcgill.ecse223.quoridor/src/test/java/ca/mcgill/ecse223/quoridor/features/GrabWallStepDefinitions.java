package ca.mcgill.ecse223.quoridor.features;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Board;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.model.WallMove;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/*
 * Note that the classes may not conatain all relative steps for a certain feature, 
 * since there are duplicate steps between scenarios.
 */

/**
 * 
 * @author Kaan Gure
 *
 */
public class GrabWallStepDefinitions {
	private Wall aWall;
	
	//------------Helper Methods----------------//
	
	private Tile getInitialPositionTile() {
		Tile initialPosTile = null;
		List<Tile> currentTiles = QuoridorApplication.getQuoridor().getBoard().getTiles();
		for(int i=0; i < currentTiles.size(); i++) {
			int col = currentTiles.get(i).getColumn();
			int row = currentTiles.get(i).getRow();
			if(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove()
					.equals(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer()) && col == 5 && row == 9) {
				initialPosTile = currentTiles.get(i);
				break;
			}
			else if(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove()
					.equals(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer()) && col == 5 && row == 1) {
				initialPosTile = currentTiles.get(i);
				break;
			}
		}
		return initialPosTile;
	}
	
	private Player getCurrentPlayer() {
		Player currentPlayer = null;
			if (QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove()
					.equals(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer())) {
				
				currentPlayer = QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer();
			}
			else if(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove()
					.equals(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer())) {
				
				currentPlayer = QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer();

			}
			return currentPlayer;
	}

	//------------------------------------------//

	@Given("I have more walls on stock")
	public void i_have_more_walls_on_stock() { //add one more wall to current player's stock to make sure they have at least one wall in stock
		Player player = getCurrentPlayer();
		if (player.equals(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer())) {
			
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().addWhiteWallsInStock(aWall);
		}
		else if(player.equals(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer())) {
			
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().addBlackWallsInStock(aWall);

		}
		
	}

	@When("I try to grab a wall from my stock")
	public void i_try_to_grab_a_wall_from_my_stock() {
	    // Write code here that turns the phrase above into concrete actions
		try {
			QuoridorController.grabWall();
		} catch (java.lang.UnsupportedOperationException e) {
		}
	    //throw new cucumber.api.PendingException();
	}

	@Then("A wall move candidate shall be created at initial position")
	public void a_wall_move_candidate_shall_be_created_at_initial_position() {
		WallMove aNewWallMoveCandidate = null;
		
		aNewWallMoveCandidate.setTargetTile(getInitialPositionTile());
		QuoridorApplication.getQuoridor().getCurrentGame().setWallMoveCandidate(aNewWallMoveCandidate);
	    // Write code here that turns the phrase above into concrete actions
		assertEquals(true, QuoridorApplication.getQuoridor().getCurrentGame().hasWallMoveCandidate());
	    throw new cucumber.api.PendingException();
	}

	@Then("The wall in my hand shall disappear from my stock")
	public void the_wall_in_my_hand_shall_disappear_from_my_stock() {
	    // Write code here that turns the phrase above into concrete actions
		Player currentPlayer = getCurrentPlayer();
		boolean hasWallInStock = true;
	if (currentPlayer.equals(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer())) {
			
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().removeWhiteWallsInStock(aWall);
			hasWallInStock = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock().contains(aWall);
		}
		else if(currentPlayer.equals(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer())) {
			
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().removeBlackWallsInStock(aWall);
			hasWallInStock = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsInStock().contains(aWall);

		}
	assertEquals(false, hasWallInStock);
		
	    throw new cucumber.api.PendingException();
	}

	@Given("I have no more walls on stock")
	public void i_have_no_more_walls_on_stock() {
	    // Write code here that turns the phrase above into concrete actions
		Player currentPlayer = getCurrentPlayer();
		
	if (currentPlayer.equals(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer())) {
		
		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock().clear();

		}
		else if(currentPlayer.equals(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer())) {
			
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsInStock().clear();

		}
		
	    //throw new cucumber.api.PendingException();
	}

	@Then("I shall be notified that I have no more walls")
	public void i_shall_be_notified_that_I_have_no_more_walls() {
		boolean hasAlerted = QuoridorController.alertStockIsEmpty();
		assertEquals(true, hasAlerted);
	    //throw new cucumber.api.PendingException();
	}

	@Then("I shall have no walls in my hand")
	public void i_shall_have_no_walls_in_my_hand() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}
}
