package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.*;

import java.util.Iterator;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.model.WallMove;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/*
 * Note that the classes may not conatain all relative steps for a certain feature, 
 * since there are duplicate steps between scenarios.
 */
/**
 * 
 * @author Remi Carriere
 *
 */
public class ValidatePositionStepDefinition {
	private GamePosition position;
	private boolean isValidPosition = false;

	@Given("A game position is supplied with pawn coordinate {int}:{int}")
	public void a_game_position_is_supplied_with_pawn_coordinate(Integer row, Integer col) {
		// White will be the player to move for this test test (arbitrary)
		Player whitePlayer = QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer();
		Tile tile = getTile(row, col);
		if (tile != null) {
			PlayerPosition whitePos = new PlayerPosition(whitePlayer, tile);
			position = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
			position.setWhitePosition(whitePos);
		}

	}

	@When("Validation of the position is initiated")
	public void validation_of_the_position_is_initiated() {
		try {
			isValidPosition = QuoridorController.validatePosition(position);
		} catch (java.lang.UnsupportedOperationException e) {
			throw new cucumber.api.PendingException();
		}
	}

	@Then("The position shall be {string}")
	public void the_position_shall_be(String result) {
		boolean b = result.equals("ok");
		assertEquals(b, isValidPosition);
	}

	@Given("A game position is supplied with wall coordinate {int}:{int}-{string}")
	public void a_game_position_is_supplied_with_wall_coordinate(Integer row, Integer col, String dir) {
		QuoridorApplication.getQuoridor().getCurrentGame().setWallMoveCandidate(null);
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		Player whitePlayer = game.getWhitePlayer();
		Tile tile = getTile(row, col);
		Direction direction = getDirection(dir);
		position = game.getCurrentPosition();
		Wall wall = position.getWhiteWallsInStock().get(1);
		if (tile != null) {
			new WallMove(1, 1, whitePlayer, tile, game, direction, wall);
			position.addWhiteWallsOnBoard(wall);
		}
	}

	@Then("The position shall be valid")
	public void the_position_shall_be_valid() {
		assertTrue(isValidPosition);
	}

	@Then("The position shall be invalid")
	public void the_position_shall_be_invalid() {
		assertFalse(isValidPosition);
	}
	
	/**
	 * Reset variable just in case
	 * 
	 */
	@After
	public void reset() {
		position = null;
		isValidPosition = false;
	}

	private Tile getTile(int row, int col) {
		Iterator<Tile> itr = QuoridorApplication.getQuoridor().getBoard().getTiles().iterator();
		while (itr.hasNext()) {
			Tile t = itr.next();
			if (t.getRow() == row && t.getColumn() == col) {
				return t;
			}
		}
		return null;

	}

	private Direction getDirection(String dir) {
		switch (dir) {
		case "horizontal":
			return Direction.Horizontal;
		case "vertical":
			return Direction.Vertical;
		default:
			return null;
		}

	}
}
