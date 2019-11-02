package ca.mcgill.ecse223.quoridor.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
/*
 * Note that the classes may not conatain all relative steps for a certain feature, 
 * since there are duplicate steps between scenarios.
 */

import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.InvalidInputException;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.model.WallMove;
import ca.mcgill.ecse223.quoridor.util.TestUtil;
import ca.mcgill.ecse223.quoridor.view.GamePanel;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * 
 * @author Kaan Gure
 *
 */
public class GrabWallStepDefinitions {

	private String errorMessage = "";

	// ------------------------------------------//

	/**
	 * @author Marton
	 */
	@Given("^The game is running$")
	public void the_game_is_running() {
		TestUtil.initQuoridorAndBoard();
		ArrayList<Player> createUsersAndPlayers = TestUtil.createUsersAndPlayers("user1", "user2");
		TestUtil.createAndStartGame(createUsersAndPlayers);
	}

	/**
	 * @author Marton
	 */
	@Given("^It is my turn to move$")
	public void it_is_my_turn_to_move() throws Throwable {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Player currentPlayer = quoridor.getCurrentGame().getWhitePlayer();
		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().setPlayerToMove(currentPlayer);
	}

	@Given("I have more walls on stock")
	public void i_have_more_walls_on_stock() {
		Player player = TestUtil.getCurrentPlayer();
		Wall wall = TestUtil.getAWallInStockForCurrenPlayer();
		// Create a new wall if stock is empty to ensure pre condition holds
		if (wall == null) {
			wall = new Wall(21, player);
		}
		if (player.equals(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer())) {
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().addWhiteWallsInStock(wall);
		} else if (player.equals(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer())) {
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().addBlackWallsInStock(wall);
		}
	}

	/**
	 * @author Kaan Gure
	 */

	@Given("I do not have a wall in my hand")
	public void i_do_not_have_a_wall_in_my_hand() { // GUI related feature
		QuoridorApplication.getQuoridor().getCurrentGame().setWallMoveCandidate(null);
		GamePanel gPanel = new GamePanel();
		gPanel.refreshData();
		assertFalse(gPanel.hasWallInHand()); // just verify gui precondition
	}

	/**
	 * @author Kaan Gure
	 */
	@When("I try to grab a wall from my stock")
	public void i_try_to_grab_a_wall_from_my_stock() {
		try {
			QuoridorController.grabWall();
		} catch (InvalidInputException e) {
			errorMessage = e.getMessage();
		}
	}

	@Then("A wall move candidate shall be created at initial position")
	public void a_wall_move_candidate_shall_be_created_at_initial_position() {
		boolean b = QuoridorApplication.getQuoridor().getCurrentGame().hasWallMoveCandidate();
		assertTrue(b);
		WallMove candidate = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate();
		Tile initialTile = TestUtil.getTile(8, 5); // initial wall position of white
		Tile teargetTile = candidate.getTargetTile();
		assertEquals(initialTile, teargetTile);
		assertEquals(Direction.Vertical, candidate.getWallDirection());

	}

	@Then("The wall in my hand shall disappear from my stock")
	public void the_wall_in_my_hand_shall_disappear_from_my_stock() {
		Wall candidateWall = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallPlaced();
		List<Wall> inStockWalls = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
				.getWhiteWallsInStock();
		for (Wall wall : inStockWalls) {
			assertNotEquals(candidateWall, wall);
		}

	}

	@Given("I have no more walls on stock")
	public void i_have_no_more_walls_on_stock() {
		TestUtil.removeCurrentPlayersWalls();
		assertEquals(0,
				QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock().size());
	}

	@Then("I shall be notified that I have no more walls")
	public void i_shall_be_notified_that_I_have_no_more_walls() { // GUI related
		// Check internal error message
		assertEquals("Stock is Empty", errorMessage);
		GamePanel gPanel = new GamePanel();
		gPanel.refreshData();
		assertEquals("Walls Instock: 0", gPanel.getWallsInstockLabel());
		assertEquals("Stock is Empty", gPanel.getGrabWallErrorLabel());
	}

	@Then("I shall have a wall in my hand over the board")
	public void i_shall_have_a_wall_in_my_hand_over_the_board() {
		GamePanel gPanel = new GamePanel();
		gPanel.refreshData();
		assertTrue(gPanel.hasWallInHand());
	}

	@Then("I shall have no walls in my hand")
	public void i_shall_have_no_walls_in_my_hand() { // GUI related
		GamePanel gPanel = new GamePanel();
		gPanel.refreshData();
		assertFalse(gPanel.hasWallInHand());
	}

}
