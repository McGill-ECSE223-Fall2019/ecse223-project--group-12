package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.sql.Time;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController.Side;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.util.TestUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


/**
 * 
 * @author Kaan Gure
 *
 */

public class IdentifyGameWonOrDrawStepDefinitions {

	@Given("Player {string} has just completed his move")
	public void player_has_just_completed_his_move(String color) {
		Player p = TestUtil.getPlayerByColor(color);
		GamePosition gp = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		gp.setPlayerToMove(p);
		QuoridorController.confirmMove();
	}

	@Given("The new position of {string} is {int}:{int}")
	public void the_new_position_of_is(String color, Integer row, Integer col) {
		// Write code here that turns the phrase above into concrete actions
		Player p = TestUtil.getPlayerByColor(color);
		GamePosition gp = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		Tile tile = TestUtil.getTile(row, col);
		PlayerPosition position = new PlayerPosition(p, tile);
		if (p.hasGameAsWhite()) {
			gp.setWhitePosition(position);
		} else {
			gp.setBlackPosition(position);
		}
	}

	@Given("The clock of {string} is more than zero")
	public void the_clock_of_is_more_than_zero(String string) {
		Player p = TestUtil.getPlayerByColor(string);
		Time zeroTime = Time.valueOf("00:00:00");
		Time addTime = Time.valueOf("00:10:00");
		Time t = p.getRemainingTime();
		if (!(t.compareTo(zeroTime) > 0)) {
			p.setRemainingTime(addTime); // add more time if remaining time = 0
		}

	}

	@When("Checking of game result is initated")
	public void checking_of_game_result_is_initated() {
		QuoridorController.checkGameWon();
		QuoridorController.checkGameDrawn();
	}

	@When("The clock of {string} counts down to zero")
	public void the_clock_of_counts_down_to_zero(String color) {
		Player player = TestUtil.getPlayerByColor(color);
		QuoridorController.setTimeOutStatus(player);
	}

	@Then("Game result shall be {string}")
	public void game_result_shall_be(String status) {
		GameStatus acutal = QuoridorApplication.getQuoridor().getCurrentGame().getGameStatus();
		GameStatus expected = null;
		if  (status.equals("Pending") || status.equals("pending")) {
			expected = GameStatus.Running;
		} else if  (status.equals("WhiteWon") || status.equals("whiteWon")) {
			expected = GameStatus.WhiteWon;
		} else if  (status.equals("BlackWon") || status.equals("blackWon")) {
			expected = GameStatus.BlackWon;
		} else if  (status.equals("Drawn")) {
			expected = GameStatus.Draw;
		}
		
		assertEquals(expected, acutal);
	}

	@Then("The game shall no longer be running")
	public void the_game_shall_no_longer_be_running() {
		GameStatus running = GameStatus.Running;
		GameStatus status = QuoridorApplication.getQuoridor().getCurrentGame().getGameStatus();
		assertNotEquals(running, status);
		
	}

	@Given("The following moves were executed:")
	public void the_following_moves_were_executed(io.cucumber.datatable.DataTable dataTable) {
		//throw new cucumber.api.PendingException();
		List<Map<String, String>> mappedValues = dataTable.asMaps();
		for (int i=0; i < mappedValues.size(); i++) {
			Game g = QuoridorApplication.getQuoridor().getCurrentGame();
			GamePosition gp = g.getCurrentPosition();
			Map<String, String> map = mappedValues.get(i);
			Integer move = Integer.decode(map.get("move"));
			Integer turn = Integer.decode(map.get("turn"));
			Integer row = Integer.decode(map.get("row"));
			Integer col = Integer.decode(map.get("col"));
						
			Player p = null;
			switch (turn) {
			case 1:
				p = g.getWhitePlayer();
				break;
			case 2:
				p = g.getBlackPlayer();
				break;
			default:
				throw new IllegalArgumentException("Unsupported turn id was provided: " + turn);
			}
			
			PlayerPosition pos = null;
			if (gp.getPlayerToMove().hasGameAsWhite()) {
				pos = gp.getWhitePosition();
			} else {
				pos = gp.getBlackPosition();
			}
			
			int pRow = pos.getTile().getRow();
			int pCol = pos.getTile().getColumn();
			
			int colDifference = col - pCol;
			int rowDifference = row - pRow;
			
			//check if target tile is current tile
			if (colDifference == 0 && rowDifference == 0) {
				throw new IllegalArgumentException("Already at target location");
			}
			//finding movement side
				Side side;
				if (colDifference==0 && rowDifference>0) {
					side = Side.down;
				} else if (colDifference==0 && rowDifference<0) {
					side = Side.up;
				} else if (colDifference>0 && rowDifference==0) {
					side = Side.right;
				} else if (colDifference<0 && rowDifference==0) {
					side = Side.left;
				} else if (colDifference>0 && rowDifference>0) {
					side = Side.downright;
				} else if (colDifference>0 && rowDifference<0) {
					side = Side.upright;
				} else if (colDifference<0 && rowDifference>0) {
					side = Side.downleft;
				} else {
					side = Side.upleft;
				}
				QuoridorController.movePawn(p, side);

		}
	}

	@Given("The last move of {string} is pawn move to {int}:{int}")
	public void the_last_move_of_is_pawn_move_to(String string, Integer int1, Integer int2) {
		String player = string;
		int row = int1;
		int col = int2;
		Player p = null;
		Game g = QuoridorApplication.getQuoridor().getCurrentGame();
		GamePosition gp = g.getCurrentPosition();
		switch (player) {
		case "white":
			p = g.getWhitePlayer();
			break;
		case "black":
			p = g.getBlackPlayer();
			break;
		default:
			throw new IllegalArgumentException("Unsupported player was provided: " + string);
		}
		
			PlayerPosition pos = null;
			if (p.equals(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer())) {
				pos = gp.getWhitePosition();
			} else {
				pos = gp.getBlackPosition();
			}
			
		    int pRow = pos.getTile().getRow();
			int pCol = pos.getTile().getColumn();

			
			int colDifference = col - pCol;
			int rowDifference = row - pRow;
			
			//check if target tile is current tile
			if (colDifference == 0 && rowDifference == 0) {
				throw new IllegalArgumentException("Already at target location");
			}
			//Finding movement side
			Side side;
			if (colDifference==0 && rowDifference>0) {
				side = Side.down;
			} else if (colDifference==0 && rowDifference<0) {
				side = Side.up;
			} else if (colDifference>0 && rowDifference==0) {
				side = Side.right;
			} else if (colDifference<0 && rowDifference==0) {
				side = Side.left;
			} else if (colDifference>0 && rowDifference>0) {
				side = Side.downright;
			} else if (colDifference>0 && rowDifference<0) {
				side = Side.upright;
			} else if (colDifference<0 && rowDifference>0) {
				side = Side.downleft;
			} else {
				side = Side.upleft;
			}
			QuoridorController.movePawn(p, side);

		
	}
}
