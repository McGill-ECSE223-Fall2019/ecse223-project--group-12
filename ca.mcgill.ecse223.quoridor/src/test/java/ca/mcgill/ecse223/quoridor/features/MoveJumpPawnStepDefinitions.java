package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.*;

import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.InvalidMoveException;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
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

public class MoveJumpPawnStepDefinitions {

	private boolean moveSuccess = false;

	@Given("The player is located at {int}:{int}")
	public void the_player_is_located_at(Integer row, Integer col) {
		GamePosition gp = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		Player p = gp.getPlayerToMove();
		Tile tile = TestUtil.getTile(row, col);
		PlayerPosition position = new PlayerPosition(p, tile);
		if (p.hasGameAsWhite()) {
			gp.setWhitePosition(position);
		} else {
			gp.setBlackPosition(position);
		}
	}

	@Given("The opponent is located at {int}:{int}")
	public void the_opponent_is_located_at(Integer row, Integer col) {
		GamePosition gp = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		Player p = gp.getPlayerToMove();
		Tile tile = TestUtil.getTile(row, col);
		PlayerPosition position = new PlayerPosition(p, tile);
		if (p.hasGameAsWhite()) { // then the other player is black
			gp.setBlackPosition(position);
		} else {
			gp.setWhitePosition(position);
		}

	}

	@Given("There are no {string} walls {string} from the player")
	public void there_are_no_walls_from_the_player(String dir, String side) {
		Game G = QuoridorApplication.getQuoridor().getCurrentGame();
		GamePosition GP = G.getCurrentPosition();
		Tile playerTile;
		if (GP.getPlayerToMove() == G.getBlackPlayer()) {
			playerTile = GP.getBlackPosition().getTile();
		}else {
			playerTile = GP.getWhitePosition().getTile();
		}
		Wall existedWall = TestUtil.checkWallExistence(dir, side, playerTile);
		if (existedWall != null) {
			Direction dirnew;
			if (dir.equals("vertical")) {
				dirnew = Direction.Horizontal;
			}else {
				dirnew = Direction.Vertical;
			}
			existedWall.getMove().setWallDirection(dirnew);
		}
	}

	@Given("The opponent is not {string} from the player")
	public void the_opponent_is_not_from_the_player(String side) {
		Game G = QuoridorApplication.getQuoridor().getCurrentGame();
		GamePosition GP = G.getCurrentPosition();
		PlayerPosition opponentPosition;
		Tile playerTile;
		if (GP.getPlayerToMove() == G.getBlackPlayer()) {
			playerTile = GP.getBlackPosition().getTile();
			opponentPosition = GP.getWhitePosition();
		}else {
			playerTile = GP.getWhitePosition().getTile();
			opponentPosition = GP.getBlackPosition();
		}
		Boolean exist = TestUtil.checkOpponentExistence(side, playerTile);
		if (exist) {
			opponentPosition.setTile(TestUtil.getTile(0, 0));
		}
	}


	@When("Player {string} initiates to move {string}")
	public void player_initiates_to_move(String color, String side) {
		try {
			Player p = TestUtil.getPlayerByColor(color);
			moveSuccess = QuoridorController.movePawn(p, side);
		} catch (InvalidMoveException e) {
			moveSuccess = false;
		}
	}

	@Then("The move {string} shall be {string}")
	public void the_move_shall_be(String side, String status) {
		boolean expected = status.equals("success");
		assertEquals(expected, moveSuccess);
	}

	@Then("Player's new position shall be {int}:{int}")
	public void player_s_new_position_shall_be(Integer row, Integer col) {
		GamePosition gp = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		Player p = gp.getPlayerToMove();
		PlayerPosition pos;

		if (p.hasGameAsWhite() && moveSuccess) { // success means player was switched, get black pos
			pos = gp.getBlackPosition();
		} else if (p.hasGameAsBlack() && moveSuccess) {
			pos = gp.getWhitePosition();
		} else if (p.hasGameAsWhite()) { // the move was illegal so the player did not change, get white pos
			pos = gp.getWhitePosition();
		} else {
			pos = gp.getBlackPosition();
		}
		assertEquals((int) row, pos.getTile().getRow());
		assertEquals((int) col, pos.getTile().getColumn());
	}

	@Then("The next player to move shall become {string}")
	public void the_next_player_to_move_shall_become(String color) {
		Player playerToMove = TestUtil.getCurrentPlayer();
		Player expected = TestUtil.getPlayerByColor(color);
		assertEquals(expected, playerToMove);
	}

	@Given("There is a {string} wall at {int}:{int}")
	public void there_is_a_wall_at(String dir, Integer row, Integer col) {
		Game g = QuoridorApplication.getQuoridor().getCurrentGame();
		GamePosition gp = g.getCurrentPosition();
		Wall wall = TestUtil.getAWallInStockForCurrenPlayer();
		Tile tile = TestUtil.getTile(row, col);
		Direction direction = TestUtil.getDirection(dir);
		Player p = TestUtil.getCurrentPlayer();
		new WallMove(0, 0, p, tile, g, direction, wall);
		if (p.hasGameAsWhite()) {
			gp.addWhiteWallsOnBoard(wall);
			gp.removeWhiteWallsInStock(wall);
		} else {
			gp.addBlackWallsOnBoard(wall);
			gp.removeBlackWallsInStock(wall);
		}
	}

	@Given("There are no {string} walls {string} from the player nearby")
	public void there_are_no_walls_from_the_player_nearby(String dir, String side) {
		Game G = QuoridorApplication.getQuoridor().getCurrentGame();
		GamePosition GP = G.getCurrentPosition();
		Tile playerTile;
		if (GP.getPlayerToMove() == G.getBlackPlayer()) {
			playerTile = GP.getBlackPosition().getTile();
		}else {
			playerTile = GP.getWhitePosition().getTile();
		}
		Wall existedWall = TestUtil.checkWallExistence(dir, side, playerTile);
		if (existedWall != null) {
			Direction dirnew;
			if (dir.equals("vertical")) {
				dirnew = Direction.Horizontal;
			}else {
				dirnew = Direction.Vertical;
			}
			existedWall.getMove().setWallDirection(dirnew);
		}
	}

}