package ca.mcgill.ecse223.quoridor.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import ca.mcgill.ecse223.quoridor.view.GamePanel;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Board;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.model.WallMove;
import ca.mcgill.ecse223.quoridor.util.TestUtil;
import ca.mcgill.ecse223.quoridor.view.QuoridorFrame;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/*
 * Note that the classes may not conatain all relative steps for a certain feature, 
 * since there are duplicate steps between scenarios.
 */

public class DropWallStepDefinitions {


	/**
	 * @author Kaan Gure
	 */
	@Given("^I have a wall in my hand over the board$")
	public void i_have_a_wall_in_my_hand_over_the_board() throws Throwable {
		Player currentPlayer = TestUtil.getCurrentPlayer();
		Wall wall = TestUtil.getAWallInStockForCurrenPlayer();
		if (wall == null) {
			//then create a new wall...
			wall = new Wall(21, currentPlayer);
		}
		try {
			GamePanel gPanel = new GamePanel();
			gPanel.refreshData();
		if(gPanel.hasWallInHand() == false) { //GUI related
			QuoridorController.setWallInHand(wall);
		}
		else return;
		}catch (java.lang.UnsupportedOperationException e) {
			//skips test if method is not yet implemented
			throw new cucumber.api.PendingException();
		}
	}
	
	/**
	 * @author Kaan Gure
	 */

	@Given("The wall move candidate with {string} at position \\({int}, {int}) is valid")
	public void the_wall_move_candidate_with_at_position_is_valid(String dir, Integer row, Integer col) {
		Player player = TestUtil.getCurrentPlayer();
		Wall wall = TestUtil.getAWallInStockForCurrenPlayer();
		if (wall == null) {
			//then create a new wall...
			wall = new Wall(21, player);
		}
		Board board = QuoridorApplication.getQuoridor().getBoard();
		Tile tile = new Tile(row, col, board);
		Game game =  QuoridorApplication.getQuoridor().getCurrentGame();
		Direction direction = TestUtil.getDirection(dir);
		
		WallMove wallMoveCandidate = new WallMove(2, 2, player, tile, game, direction, wall);
		GamePosition casePosition  = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		casePosition.getGame().setWallMoveCandidate(wallMoveCandidate);
		try {
			if (QuoridorController.validatePosition(casePosition) == false) {
				throw new IllegalStateException(
						"Wall move candidate at current position is invalid, position has to be valid for test to commence");
			}
			}catch (java.lang.UnsupportedOperationException e) {
				//skips test if method is not yet implemented
				throw new cucumber.api.PendingException();
			}
	}
	
	/**
	 * @author Kaan Gure
	 */

	@When("I release the wall in my hand")
	public void i_release_the_wall_in_my_hand() {
		// Write code here that turns the phrase above into concrete actions
		try {
			QuoridorController.dropWall();
		} catch (java.lang.UnsupportedOperationException e) {
			//skips test if method is not yet implemented
			throw new cucumber.api.PendingException();
		}
	}
	
	/**
	 * @author Kaan Gure
	 */

	@Then("A wall move shall be registered with {string} at position \\({int}, {int})")
	public void a_wall_move_shall_be_registered_with_at_position(String dir, Integer row, Integer col) {
		// Write code here that turns the phrase above into concrete actions
		Player player = TestUtil.getCurrentPlayer();
		Direction direction = TestUtil.getDirection(dir);
		List<Wall> allWalls = null;
		boolean isSet = false;
		if (player.equals(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer())) {

			allWalls = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsOnBoard();

		} else if (player.equals(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer())) {
			
			allWalls = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsOnBoard();

		}
		for(int i=0; i< allWalls.size(); i++) {
			int selectedRow = allWalls.get(i).getMove().getTargetTile().getRow();
			int selectedColumn = allWalls.get(i).getMove().getTargetTile().getColumn();
			Direction selectedDirection = allWalls.get(i).getMove().getWallDirection();
			
			if(selectedDirection == direction && selectedRow == row && selectedColumn == col) {
				isSet = true;
			}
			else {
				isSet = false;
			}
		}
		assertEquals(true, isSet);
	}
	
	/**
	 * @author Kaan Gure
	 */

	@Then("I shall not have a wall in my hand")
	public void i_shall_not_have_a_wall_in_my_hand() {
		try {
			GamePanel gPanel = new GamePanel();
			gPanel.refreshData();
		assertEquals(false, gPanel.hasWallInHand()); //GUI related
	}catch (java.lang.UnsupportedOperationException e) {
		//skips test if method is not yet implemented
		throw new cucumber.api.PendingException();
	}
	}
	
	/**
	 * @author Kaan Gure
	 */

	@Then("My move shall be completed")
	public void my_move_shall_be_completed() {
		boolean isStillMoving = QuoridorApplication.getQuoridor().getCurrentGame().hasWallMoveCandidate();
		assertEquals(false, isStillMoving);
	}
	
	/**
	 * @author Kaan Gure
	 */

	@Then("It shall not be my turn to move")
	public void it_shall_not_be_my_turn_to_move() {
		// Write code here that turns the phrase above into concrete actions
		Player currentPlayer = TestUtil.getCurrentPlayer();
		Player playertoMove = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove();
		boolean moveChanged = true;
		if(currentPlayer.equals(playertoMove)) {
			moveChanged = false;
		}
		else {
				moveChanged = true;
			}
		assertEquals(true, moveChanged);
	}
	
	/**
	 * @author Kaan Gure
	 */

	@Given("The wall move candidate with {string} at position \\({int}, {int}) is invalid")
	public void the_wall_move_candidate_with_at_position_is_invalid(String dir, Integer row, Integer col) {
		Player player = TestUtil.getCurrentPlayer();
		Wall wall = TestUtil.getAWallInStockForCurrenPlayer();
		if (wall == null) {
			//then create a new wall...
			wall = new Wall(21, player);
		}
		Board board = QuoridorApplication.getQuoridor().getBoard();
		Tile tile = new Tile(row, col, board);
		Game game =  QuoridorApplication.getQuoridor().getCurrentGame();
		Direction direction = TestUtil.getDirection(dir);
		
		WallMove wallMoveCandidate = new WallMove(2, 2, player, tile, game, direction, wall);
		GamePosition casePosition  = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		casePosition.getGame().setWallMoveCandidate(wallMoveCandidate);
		try {
		if(QuoridorController.validatePosition(casePosition) == true) {
			throw new IllegalStateException("Wall move candidate at current position is valid, position has to be invalid for test to commence");
		}
		}catch (java.lang.UnsupportedOperationException e) {
			//skips test if method is not yet implemented
			throw new cucumber.api.PendingException();
		}
	}
	
	/**
	 * @author Kaan Gure
	 */

	@Then("I shall be notified that my wall move is invalid") //GUI related
	public void i_shall_be_notified_that_my_wall_move_is_invalid() {
		boolean hasNotified;
		try {
		GamePanel gPanel = new GamePanel();
		gPanel.refreshData();
		//** Use gPanel.getErrorLabel() here instead, also see hot the gui tests that were implemented for grab wall
		//hasNotified = gPanel.notifiedInvalidDrop();
		//assertEquals(true, hasNotified);
		}catch (java.lang.UnsupportedOperationException e) {
			//skips test if method is not yet implemented
			throw new cucumber.api.PendingException();
		}
	}
	
	/**
	 * @author Kaan Gure
	 */

//	@Then("I shall have a wall in my hand over the board")
//	public void i_shall_have_a_wall_in_my_hand_over_the_board() { //GUI related
////		boolean hasWallInHand;
////		try {
////			GamePanel gPanel = new GamePanel();
////			gPanel.refreshData();
////		hasWallInHand = gPanel.hasWallInHand();
////		assertEquals(true, hasWallInHand);
////		}catch (java.lang.UnsupportedOperationException e) {
////			//skips test if method is not yet implemented
////			throw new cucumber.api.PendingException();
////		}
//		throw new cucumber.api.PendingException();
//	}
	
	/**
	 * @author Kaan Gure
	 */

	@Then("It shall be my turn to move")
	public void it_shall_be_my_turn_to_move() {
		// Write code here that turns the phrase above into concrete actions
		Player currentPlayer = TestUtil.getCurrentPlayer();
		Player playertoMove = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove();
		boolean isMyTurn = false;
		if(currentPlayer.equals(playertoMove)) {
			isMyTurn = true;
		}
		else {
				isMyTurn = false;
			}
	assertEquals(true, isMyTurn);
	}
	
	/**
	 * @author Kaan Gure
	 */

	@Then("No wall move shall be registered with {string} at position \\({int}, {int})")
	public void no_wall_move_shall_be_registered_with_at_position(String dir, Integer row, Integer col) {
		Player player = TestUtil.getCurrentPlayer();
		Direction direction = TestUtil.getDirection(dir);
		List<Wall> allWalls = null;
		boolean isSet = false;
		if (player.equals(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer())) {

			allWalls = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsOnBoard();

		} else if (player.equals(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer())) {
			
			allWalls = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsOnBoard();

		}
		for(int i=0; i< allWalls.size(); i++) {
			int selectedRow = allWalls.get(i).getMove().getTargetTile().getRow();
			int selectedColumn = allWalls.get(i).getMove().getTargetTile().getColumn();
			Direction selectedDirection = allWalls.get(i).getMove().getWallDirection();
			
			if(selectedDirection == direction && selectedRow == row && selectedColumn == col) {
				isSet = true;
			}
			else {
				isSet = false;
			}
		}
		assertEquals(false, isSet);
	}

}
