package ca.mcgill.ecse223.quoridor.features;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.Tile;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.model.WallMove;
import ca.mcgill.ecse223.quoridor.util.TestUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/*
 * Note that the classes may not conatain all relative steps for a certain feature, 
 * since there are duplicate steps between scenarios.
 */

public class MovePlayerStepDefinitions {

	@Given("The player to move is {string}")
	public void the_player_to_move_is(String color) {

		Player player = TestUtil.getPlayerByColor(color);
		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().setPlayerToMove(player);
	}

	@Given("The player is located at {int}:{int}")
	public void the_player_is_located_at(Integer row, Integer col) {
		Tile tile = TestUtil.getTile(row, col);
		PlayerPosition playerPosition;
		if (TestUtil.getCurrentPlayer().hasGameAsWhite()) {
			playerPosition = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition();
			playerPosition.setTile(tile);
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().setWhitePosition(playerPosition);
		} else if (TestUtil.getCurrentPlayer().hasGameAsBlack()) {
			playerPosition = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition();
			playerPosition.setTile(tile);
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().setBlackPosition(playerPosition);
		}
	}

	@Given("There are no {string} walls {string} from the player")
	public void there_are_no_walls_from_the_player(String dir, String side) {
		PlayerPosition playerPosition = null;
		if (TestUtil.getCurrentPlayer().hasGameAsWhite()) {
			playerPosition = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition();
		} else if (TestUtil.getCurrentPlayer().hasGameAsBlack()) {
			playerPosition = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition();
		}

		Tile playerTile = playerPosition.getTile();
		int playerCol = playerTile.getColumn();
		int playerRow = playerTile.getColumn();
		// Get a list of all walls on the board
		List<Wall> allWallsOnBoard = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
				.getBlackWallsOnBoard();
		List<Wall> whiteWalls = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
				.getWhiteWallsOnBoard();
		allWallsOnBoard.addAll(whiteWalls);

		Iterator<Wall> itr = allWallsOnBoard.iterator();
		Direction direction = TestUtil.getDirection(dir);
		// check all the walls on board to see if we have issue
		while (itr.hasNext()) {
			Wall wall = itr.next();
			if (wall.getMove().getWallDirection() == direction) {
				int wallCol = wall.getMove().getTargetTile().getColumn();
				int wallRow = wall.getMove().getTargetTile().getRow();
				switch (side) {
				case "up":
					if (direction == Direction.Vertical) {
						//TODO implement logic to see if move is illegal
					}
					break;
				case "down":
					if (direction == Direction.Vertical) {
						//TODO implement logic to see if move is illegal
					}
					break;
				case "left":
					if (direction == Direction.Horizontal) {
						//TODO implement logic to see if move is illegal
					}
					break;
				case "right":
					if (direction == Direction.Horizontal) {
						//TODO implement logic to see if move is illegal
					}
					break;
				default:
					throw new java.lang.IllegalArgumentException("Direction not valid: " + dir);
				}
			}
		}
	}

	@Given("The opponent is not {string} from the player")
	public void the_opponent_is_not_from_the_player(String side) {
		// Write code here that turns the phrase above into concrete actions
		throw new cucumber.api.PendingException();
	}

	@When("Player {string} initiates to move {string}")
	public void player_initiates_to_move(String color, String side) {
		// Write code here that turns the phrase above into concrete actions
		throw new cucumber.api.PendingException();
	}

	@Then("The move {string} shall be {string}")
	public void the_move_shall_be(String side, String status) {
		// Write code here that turns the phrase above into concrete actions
		throw new cucumber.api.PendingException();
	}

	@Then("Player's new position shall be {int}:{int}")
	public void player_s_new_position_shall_be(Integer row, Integer col) {
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		GamePosition position = game.getCurrentPosition();

		boolean existPlayer = false;
		PlayerPosition playerPosition = TestUtil
				.getPlayerPositionByColor(TestUtil.getCurrentPlayer().getUser().getName());
		if (col == playerPosition.getTile().getColumn() && playerPosition.getTile().getRow() == row) {
			existPlayer = true;
		}
		assertTrue(existPlayer);

	}

	@Then("The next player to move shall become {string}")
	public void the_next_player_to_move_shall_become(String colorOfNextPlayer) {

		// Write code here that turns the phrase above into concrete actions
		throw new cucumber.api.PendingException();

		// Write code here that turns the phrase above into concrete actions
		// assertEquals(colorOfNextPlayer,QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().getUser().getName());

		// Write code here that turns the phrase above into concrete actions
	}

	@Given("There is a {string} wall {string} from the player")
	public void there_is_a_wall_from_the_player(String dir, String side) {

		throw new cucumber.api.PendingException();
	}

	@Given("My opponent is not {string} from the player")
	public void my_opponent_is_not_from_the_player(String side) {

		// Write code here that turns the phrase above into concrete actions
		throw new cucumber.api.PendingException();

	}
}
