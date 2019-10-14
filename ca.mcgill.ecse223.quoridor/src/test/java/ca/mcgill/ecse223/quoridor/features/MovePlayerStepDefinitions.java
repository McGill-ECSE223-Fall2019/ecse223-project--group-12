package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
	    // Write code here that turns the phrase above into concrete actions
	    
		throw new cucumber.api.PendingException();
	}

	@Given("The player is located at {int}:{int}")
	public void the_player_is_located_at(Integer row, Integer col) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("There are no {string} walls {string} from the player")
	public void there_are_no_walls_from_the_player(String dir, String side) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
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
	    // Write code here that turns the phrase above into concrete actions
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		GamePosition position = game.getCurrentPosition();
		throw new cucumber.api.PendingException();
	}

	@Then("The next player to move shall become {string}")
	public void the_next_player_to_move_shall_become(String colorOfNextPlayer) {
	    // Write code here that turns the phrase above into concrete actions
		assertEquals(colorOfNextPlayer,QuoridorApplication.
				getQuoridor().getCurrentGame().getCurrentPosition().
				getPlayerToMove().getUser().getName());
	}
	
	@Given("There is a {string} wall {string} from the player")
	public void there_is_a_wall_from_the_player(String dir, String side) {
	    // Write code here that turns the phrase above into concrete actions		
		throw new cucumber.api.PendingException();
	}

	@Given("My opponent is not {string} from the player")
	public void my_opponent_is_not_from_the_player(String side) {
	    
		
		// Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}
}
