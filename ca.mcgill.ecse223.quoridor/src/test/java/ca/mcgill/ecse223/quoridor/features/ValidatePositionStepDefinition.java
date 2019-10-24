package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
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
	private Integer col = null;
	private Integer row = null;
	private String direction;
	private Boolean isValidPosition = null; // set to null so "expected false" does not pass if method is not called

	@Given("A game position is supplied with pawn coordinate {int}:{int}")
	public void a_game_position_is_supplied_with_pawn_coordinate(Integer row, Integer col) {
		this.col = col;
		this.row = row;
		this.direction = null;
	}

	@When("Validation of the position is initiated")
	public void validation_of_the_position_is_initiated() {
		isValidPosition = QuoridorController.validatePosition(col, row, direction);
	}

	@Then("The position shall be {string}")
	public void the_position_shall_be(String result) {
		boolean expected = result.equals("ok");
		assertEquals(expected, isValidPosition);
	}

	@Given("A game position is supplied with wall coordinate {int}:{int}-{string}")
	public void a_game_position_is_supplied_with_wall_coordinate(Integer row, Integer col, String dir) {
		this.col = col;
		this.row = row;
		this.direction = dir;
	}

	@Then("The position shall be valid")
	public void the_position_shall_be_valid() {
		assertTrue(isValidPosition);
	}

	@Then("The position shall be invalid")
	public void the_position_shall_be_invalid() {
		if (isValidPosition == null) {
			fail();
		}
		assertFalse(isValidPosition);
	}

	/**
	 * @author Marton
	 */
	@Given("The following walls exist:")
	public void the_following_walls_exist(io.cucumber.datatable.DataTable dataTable) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		// keys: wrow, wcol, wdir
		Player[] players = { quoridor.getCurrentGame().getWhitePlayer(), quoridor.getCurrentGame().getBlackPlayer() };
		int playerIdx = 0;
		int wallIdxForPlayer = 0;
		for (Map<String, String> map : valueMaps) {
			Integer wrow = Integer.decode(map.get("wrow"));
			Integer wcol = Integer.decode(map.get("wcol"));
			// Wall to place
			Wall wall = players[playerIdx].getWall(wallIdxForPlayer);

			String dir = map.get("wdir");

			Direction direction;
			switch (dir) {
			case "horizontal":
				direction = Direction.Horizontal;
				break;
			case "vertical":
				direction = Direction.Vertical;
				break;
			default:
				throw new IllegalArgumentException("Unsupported wall direction was provided: " + dir);
			}
			new WallMove(0, 1, players[playerIdx], quoridor.getBoard().getTile((wrow - 1) * 9 + wcol - 1),
					quoridor.getCurrentGame(), direction, wall);
			if (playerIdx == 0) {
				quoridor.getCurrentGame().getCurrentPosition().removeWhiteWallsInStock(wall);
				quoridor.getCurrentGame().getCurrentPosition().addWhiteWallsOnBoard(wall);
			} else {
				quoridor.getCurrentGame().getCurrentPosition().removeBlackWallsInStock(wall);
				quoridor.getCurrentGame().getCurrentPosition().addBlackWallsOnBoard(wall);
			}
			wallIdxForPlayer = wallIdxForPlayer + playerIdx;
			playerIdx++;
			playerIdx = playerIdx % 2;
		}
	}

	/**
	 * Reset variables
	 * 
	 */
	@After
	public void reset() {
		isValidPosition = null;
		row = null;
		col = null;
		direction = null;
	}

}
