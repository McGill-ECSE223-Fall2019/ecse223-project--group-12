package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.assertEquals;

import java.sql.Time;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Move;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.util.TestUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

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
			p.setRemainingTime(addTime); //add more time if remaining time = 0
		}

	}


	@When("Checking of game result is initated")
	public void checking_of_game_result_is_initated() {
		QuoridorController.checkGameWon();
		//QuoridorController.checkGameDrawn();
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
		if  (status.equals("pending")) {
			expected = GameStatus.Running;
		} else if  (status.equals("whiteWon")) {
			expected = GameStatus.WhiteWon;
		} else if  (status.equals("blackWon")) {
			expected = GameStatus.BlackWon;
		} 
		
		assertEquals(expected, acutal);
	}

	@Then("The game shall no longer be running")
	public void the_game_shall_no_longer_be_running() {
//		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
//		assertEquals(null, game);
		
	}
	@Given("The following moves were executed:")
	public void the_following_moves_were_executed(io.cucumber.datatable.DataTable dataTable) {
		throw new cucumber.api.PendingException();
//		List<Map<String, String>> mappedValues = dataTable.asMaps();
//		Game g = QuoridorApplication.getQuoridor().getCurrentGame();
//		GamePosition gp = g.getCurrentPosition();
//		for (Map<String, String> map: mappedValues) {
//			Integer move = Integer.decode(map.get("move"));
//			Integer turn = Integer.decode(map.get("turn"));
//			Integer row = Integer.decode(map.get("row"));
//			Integer col = Integer.decode(map.get("col"));
//			
//			Tile targetTile = TestUtil.getTile(row, col);
//			
//			Player p = null;
//			switch (turn) {
//			case 1:
//				p = g.getWhitePlayer();
//				break;
//			case 2:
//				p = g.getBlackPlayer();
//				break;
//			default:
//				throw new IllegalArgumentException("Unsupported turn id was provided: " + turn);
//			}
//			
//			PlayerPosition pos = new PlayerPosition(p, targetTile);
//			
//			Move moveTodo = g.getCurrentMove();
//			int rn = 0;
//			if(moveTodo == null) {
//				rn = 1;
//			} else {
//				rn = moveTodo.getRoundNumber();
//			}
//			moveTodo.setMoveNumber(move);
//			moveTodo.setPlayer(p);
//			moveTodo.setRoundNumber(rn++);
//			moveTodo.setTargetTile(targetTile);
//			
//			if (p.hasGameAsWhite()) {
//				gp.setWhitePosition(pos);
//			} else {
//				gp.setBlackPosition(pos);
//			}
//			g.addMove(moveTodo);
//			
//			//System.out.println("rn: " +rn);
//
//		}
	}

	@Given("The last move of {string} is pawn move to {int}:{int}")
	public void the_last_move_of_is_pawn_move_to(String string, Integer int1, Integer int2) {
		Game g = QuoridorApplication.getQuoridor().getCurrentGame();
		int row = int1;
		int col = int2;
		String player = string;
		Player p = null;
		Tile targetTile = TestUtil.getTile(row, col);
		switch (player) {
		case "white":
			p = g.getWhitePlayer();
			p.getGameAsWhite().getCurrentMove().getPrevMove().setTargetTile(targetTile);
			break;
		case "black":
			p = g.getBlackPlayer();
			p.getGameAsBlack().getCurrentMove().getPrevMove().setTargetTile(targetTile);
			break;
		default:
			throw new IllegalArgumentException("Unsupported player was provided: " + string);
		}
		
	}
}
