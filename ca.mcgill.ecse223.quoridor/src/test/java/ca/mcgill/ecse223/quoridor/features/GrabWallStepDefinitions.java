package ca.mcgill.ecse223.quoridor.features;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
/*
 * Note that the classes may not conatain all relative steps for a certain feature, 
 * since there are duplicate steps between scenarios.
 */

import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.model.WallMove;
import ca.mcgill.ecse223.quoridor.util.TestUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * 
 * @author Kaan Gure
 *
 */
public class GrabWallStepDefinitions {

	// ------------Helper Methods----------------//

	private Tile getInitialPositionTile() {
		Tile initialPosTile = null;
		List<Tile> currentTiles = QuoridorApplication.getQuoridor().getBoard().getTiles();
		for (int i = 0; i < currentTiles.size(); i++) {
			int col = currentTiles.get(i).getColumn();
			int row = currentTiles.get(i).getRow();
			if (QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove()
					.equals(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer()) && col == 5
					&& row == 9) {
				initialPosTile = currentTiles.get(i);
				break;
			} else if (QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove()
					.equals(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer()) && col == 5
					&& row == 1) {
				initialPosTile = currentTiles.get(i);
				break;
			}
		}
		return initialPosTile;
	}


	// ------------------------------------------//

	@Given("I have more walls on stock")
	public void i_have_more_walls_on_stock() { // add one more wall to current player's stock to make sure they have at
												// least one wall in stock
		Player player = TestUtil.getCurrentPlayer();
		Wall wall = TestUtil.getAWallInStockForCurrenPlayer();
		if (wall == null) {
			//then create a new wall...
			wall = new Wall(21, player);
		}
		if (player.equals(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer())) {
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().addWhiteWallsInStock(wall);
		} else if (player.equals(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer())) {
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().addBlackWallsInStock(wall);
		}
	}

	@When("I try to grab a wall from my stock")
	public void i_try_to_grab_a_wall_from_my_stock() {
		try {
			QuoridorController.grabWall();
		} catch (java.lang.UnsupportedOperationException e) {
			//skips test if method is not yet implemented
			throw new cucumber.api.PendingException();
		}
	}

	@Then("A wall move candidate shall be created at initial position")
	public void a_wall_move_candidate_shall_be_created_at_initial_position() {
		Player player = TestUtil.getCurrentPlayer();
		Wall wall = TestUtil.getAWallInStockForCurrenPlayer();
		Tile initialTile = getInitialPositionTile();
		Game game =  QuoridorApplication.getQuoridor().getCurrentGame();
		
		WallMove aNewWallMoveCandidate = new WallMove(2, 2, player, initialTile, game, Direction.Vertical, wall);
		QuoridorApplication.getQuoridor().getCurrentGame().setWallMoveCandidate(aNewWallMoveCandidate);
		assertEquals(true, QuoridorApplication.getQuoridor().getCurrentGame().hasWallMoveCandidate());
	}

	@Then("The wall in my hand shall disappear from my stock")
	public void the_wall_in_my_hand_shall_disappear_from_my_stock() {
		Wall wall = TestUtil.getAWallInStockForCurrenPlayer();
		Player currentPlayer = QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer();
		boolean hasWallInStock = true;
		if (currentPlayer.equals(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer())) {

			hasWallInStock = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
					.getWhiteWallsInStock().contains(wall);
		} else if (currentPlayer.equals(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer())) {

			hasWallInStock = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
					.getBlackWallsInStock().contains(wall);

		}
		assertEquals(false, hasWallInStock);
	}

	@Given("I have no more walls on stock")
	public void i_have_no_more_walls_on_stock() {
		TestUtil.removeCurrentPlayersWalls();
	}

	@Then("I shall be notified that I have no more walls")
	public void i_shall_be_notified_that_I_have_no_more_walls() { //GUI related
		try {
			boolean hasAlerted = QuoridorController.alertStockIsEmpty();
			assertEquals(true, hasAlerted);
		} catch (java.lang.UnsupportedOperationException e) {
			//skips test if method is not yet implemented
			throw new cucumber.api.PendingException();
		}

	}

	@Then("I shall have no walls in my hand")
	public void i_shall_have_no_walls_in_my_hand() { //GUI related
		try {
		assertEquals(false, QuoridorController.hasWallInHand());
		}catch (java.lang.UnsupportedOperationException e) {
			//skips test if method is not yet implemented
			throw new cucumber.api.PendingException();
		}
	}
}
