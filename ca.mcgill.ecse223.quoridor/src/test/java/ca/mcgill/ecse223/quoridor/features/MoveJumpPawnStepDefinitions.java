package ca.mcgill.ecse223.quoridor.features;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/*
 * Note that the classes may not conatain all relative steps for a certain feature, 
 * since there are duplicate steps between scenarios.
 */

public class MoveJumpPawnStepDefinitions {

	@Given("The player is located at {int}:{int}")
	public void the_player_is_located_at(Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("The opponent is located at {int}:{int}")
	public void the_opponent_is_located_at(Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("There are no {string} walls {string} from the player nearby")
	public void there_are_no_walls_from_the_player_nearby(String string, String string2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("Player {string} initiates to move {string}")
	public void player_initiates_to_move(String string, String string2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The move {string} shall be {string}")
	public void the_move_shall_be(String string, String string2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("Player's new position shall be {int}:{int}")
	public void player_s_new_position_shall_be(Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The next player to move shall become {string}")
	public void the_next_player_to_move_shall_become(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("There is a {string} wall at {int}:{int}")
	public void there_is_a_wall_at(String string, Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("There are no {string} walls {string} from the player")
	public void there_are_no_walls_from_the_player(String string, String string2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("The opponent is not {string} from the player")
	public void the_opponent_is_not_from_the_player(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("My opponent is not {string} from the player")
	public void my_opponent_is_not_from_the_player(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}
}