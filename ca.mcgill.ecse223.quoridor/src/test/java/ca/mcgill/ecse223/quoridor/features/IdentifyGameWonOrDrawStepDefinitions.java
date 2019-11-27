package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.assertEquals;

import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.util.TestUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class IdentifyGameWonOrDrawStepDefinitions {
	
	@Given("Player {string} has just completed his move")
	public void player_has_just_completed_his_move(String color) {
		//Player p = TestUtil.getPlayerByColor(color);
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

	}


	@When("Checking of game result is initated")
	public void checking_of_game_result_is_initated() {
		QuoridorController.checkGameWon();
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
		
	}
	@Given("The following moves were executed:")
	public void the_following_moves_were_executed(io.cucumber.datatable.DataTable dataTable) {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
		// Map<K, List<V>>. E,K,V must be a String, Integer, Float,
		// Double, Byte, Short, Long, BigInteger or BigDecimal.
		//
		// For other transformations you can register a DataTableType.
		throw new cucumber.api.PendingException();
	}

	@Given("The last move of {string} is pawn move to {int}:{int}")
	public void the_last_move_of_is_pawn_move_to(String string, Integer int1, Integer int2) {
		// Write code here that turns the phrase above into concrete actions
		throw new cucumber.api.PendingException();
	}
}
