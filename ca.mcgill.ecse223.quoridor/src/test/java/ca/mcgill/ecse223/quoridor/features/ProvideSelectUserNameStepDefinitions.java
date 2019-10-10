package ca.mcgill.ecse223.quoridor.features;

/*
 * Note that the classes may not conatain all relative steps for a certain feature, 
 * since there are duplicate steps between scenarios.
 */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Board;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game.MoveMode;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.User;
import ca.mcgill.ecse223.quoridor.model.Wall;
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
		initQuoridorAndBoard();
		ArrayList<Player> players = createUsersAndPlayers("user1", "user2");
		new Game(GameStatus.Initializing, MoveMode.PlayerMove, players.get(0), players.get(1),
				QuoridorApplication.getQuoridor());

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
		if (nextPlayerIsWhite && nextPlayeColorWasSet) {
			try {
				QuoridorController.setWhitePlayerInGame(getUserByName(name));
			} catch (java.lang.UnsupportedOperationException e) {

			}
		} else if (!nextPlayerIsWhite && nextPlayeColorWasSet) {
			try {
				QuoridorController.setBlackPlayerInGame(getUserByName(name));
			} catch (java.lang.UnsupportedOperationException e) {

			}
		} else {
			throw new java.lang.IllegalArgumentException("player color was not properly set");
		}
		throw new cucumber.api.PendingException();
	}

	@Then("The name of player {string} in the new game shall be {string}")
	public void the_name_of_player_in_the_new_game_shall_be(String color, String name) {
		switch (color) {
		case "white":
			String actual1 = getCurrentGame().getWhitePlayer().getUser().getName();
			assertEquals(name, actual1);
			break;
		case "black":
			String actual2 = getCurrentGame().getBlackPlayer().getUser().getName();
			assertEquals(name, actual2);
			break;
		default:
			throw new java.lang.IllegalArgumentException("Invalid Color: " + color);
		}

	}

	@Given("There is no existing user {string}")
	public void there_is_no_existing_user(String name) {
		assertNull(getUserByName(name));
	}

	@When("The player provides new user name: {string}")
	public void the_player_provides_new_user_name(String name) {
		if (nextPlayerIsWhite && nextPlayeColorWasSet) {
			try {
				QuoridorController.setNewUserAsWhite(name);
				;
			} catch (java.lang.UnsupportedOperationException e) {
				error = e.getMessage();
			}
		} else if (!nextPlayerIsWhite && nextPlayeColorWasSet) {
			try {
				QuoridorController.setNewUserAsBlack(name);
				;
			} catch (java.lang.UnsupportedOperationException e) {
				error = e.getMessage();
			}
		} else {
			throw new java.lang.IllegalArgumentException("player color was not properly set");
		}
		throw new cucumber.api.PendingException();
	}

	@Then("The player shall be warned that {string} already exists")
	public void the_player_shall_be_warned_that_already_exists(String name) {
		assertEquals(error, "The username " + name + " already exists");
	}

	@Then("Next player to set user name shall be {string}")
	public void next_player_to_set_user_name_shall_be(String color) {
		/*
		 * Verify that the players names not been updated. This is a compromise because
		 * ideally we could initialize a game with no players, and just verify if the
		 * player is null, but the model prevents a game without players. For now, if a
		 * player has not been updated, then it must still be their turn to set user
		 * name
		 */
		switch (color) {
		case "white":
			assertEquals("user1", getCurrentGame().getWhitePlayer().getUser().getName());
			break;
		case "black":
			assertEquals("user2", getCurrentGame().getBlackPlayer().getUser().getName());
			break;
		default:
			throw new java.lang.IllegalArgumentException("Invalid Color: " + color);
		}

		throw new cucumber.api.PendingException();
	}

	// Place your extracted methods below

	private void initQuoridorAndBoard() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Board board = new Board(quoridor);
		// Creating tiles by rows, i.e., the column index changes with every tile
		// creation
		for (int i = 1; i <= 9; i++) { // rows
			for (int j = 1; j <= 9; j++) { // columns
				board.addTile(i, j);
			}
		}
	}

	private ArrayList<Player> createUsersAndPlayers(String userName1, String userName2) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		User user1 = quoridor.addUser(userName1);
		User user2 = quoridor.addUser(userName2);

		int thinkingTime = 180;

		Player player1 = new Player(new Time(thinkingTime), user1, 9, Direction.Horizontal);
		Player player2 = new Player(new Time(thinkingTime), user2, 1, Direction.Horizontal);

		Player[] players = { player1, player2 };

		// Create all walls. Walls with lower ID belong to player1,
		// while the second half belongs to player 2
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 10; j++) {
				new Wall(i * 10 + j, players[i]);
			}
		}

		ArrayList<Player> playersList = new ArrayList<Player>();
		playersList.add(player1);
		playersList.add(player2);

		return playersList;
	}

	private User getUserByName(String name) {
		Iterator<User> users = QuoridorApplication.getQuoridor().getUsers().iterator();
		while (users.hasNext()) {
			User u = users.next();
			if (u.getName().equals(name)) {
				return u;
			}
		}
		return null;
	}

	private Game getCurrentGame() {
		return QuoridorApplication.getQuoridor().getCurrentGame();
	}
}
