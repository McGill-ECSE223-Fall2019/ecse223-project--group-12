package ca.mcgill.ecse223.quoridor.features;

/*
 * Note that the classes may not conatain all relative steps for a certain feature, 
 * since there are duplicate steps between scenarios.
 */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.InvalidInputException;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game.MoveMode;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.util.TestUtil;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * 
 * @author Remi Carriere
 *
 */
public class ProvideSelectUserNameStepDefinitions {

	private boolean nextPlayerIsWhite = false;
	private boolean nextPlayeColorWasSet = false;
	private String error = "";

	/**
	 * @author Marton
	 */
	@Given("A new game is initializing")
	public void a_new_game_is_initializing() {
		TestUtil.initQuoridorAndBoard();
		//TestUtil.createUsersAndPlayers("user1", "user2");
		new Game(GameStatus.Initializing, MoveMode.PlayerMove, QuoridorApplication.getQuoridor());
	}

	@Given("Next player to set user name is {string}")
	public void next_player_to_set_user_name_is(String color) {
		
		switch (color) {
		case "white":
			nextPlayerIsWhite = true;
			nextPlayeColorWasSet = true;
			break;
		case "black":
			nextPlayerIsWhite = false;
			nextPlayeColorWasSet = true;
			break;
		default:
			nextPlayeColorWasSet = false;
			throw new java.lang.IllegalArgumentException("Invalid Color: " + color);
		}
	}

	@Given("There is existing user {string}")
	public void there_is_existing_user(String name) {
		QuoridorApplication.getQuoridor().addUser(name);
	}

	@When("The player selects existing {string}")
	public void the_player_selects_existing(String name) {
		try {
			if (nextPlayerIsWhite && nextPlayeColorWasSet) {
				QuoridorController.setWhitePlayerInGame(TestUtil.getUserByName(name));

			} else if (!nextPlayerIsWhite && nextPlayeColorWasSet) {

				QuoridorController.setBlackPlayerInGame(TestUtil.getUserByName(name));
			} else {
				throw new java.lang.IllegalArgumentException("player color was not properly set");
			}
		} catch ( InvalidInputException e) {
		}
	}

	@Then("The name of player {string} in the new game shall be {string}")
	public void the_name_of_player_in_the_new_game_shall_be(String color, String name) {
		Player player = TestUtil.getPlayerByColor(color);
		String actual = player.getUser().getName();
		assertEquals(name, actual);
	}

	@Given("There is no existing user {string}")
	public void there_is_no_existing_user(String name) {
		assertNull(TestUtil.getUserByName(name));
	}

	@When("The player provides new user name: {string}")
	public void the_player_provides_new_user_name(String name) {
		try {
			if (nextPlayerIsWhite && nextPlayeColorWasSet) {
				QuoridorController.setNewUserAsWhite(name);
			} else if (!nextPlayerIsWhite && nextPlayeColorWasSet) {
				QuoridorController.setNewUserAsBlack(name);
			} else {
				throw new java.lang.IllegalArgumentException("player color was not properly set");
			}
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
	}

	@Then("The player shall be warned that {string} already exists")
	public void the_player_shall_be_warned_that_already_exists(String name) {
		assertEquals("The username " + name + " already exists" , error);
	}

	@Then("Next player to set user name shall be {string}")
	public void next_player_to_set_user_name_shall_be(String color) {
		/*
		 * Verify that the players names have not been updated. This is a compromise
		 * because ideally we could initialize a game with no players, and just verify
		 * if the player is null, but the model prevents a game without players. For
		 * now, if a player has not been updated, then it must still be their turn to
		 * set user name
		 */
		switch (color) {
		case "white":
			assertNull(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer());
			break;
		case "black":
			assertNull(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer());
			break;
		default:
			throw new java.lang.IllegalArgumentException("Invalid Color: " + color);
		}
	}

	/**
	 * Reset variables
	 * 
	 */
	@After
	public void reset() {
		nextPlayerIsWhite = false;
		nextPlayeColorWasSet = false;
		error = "";
	}

	// Place your extracted methods below

	private Game getCurrentGame() {
		return QuoridorApplication.getQuoridor().getCurrentGame();
	}
}
