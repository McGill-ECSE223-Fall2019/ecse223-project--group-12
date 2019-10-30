package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.assertEquals;

import java.sql.Time;
import java.util.List;

import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.InvalidInputException;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Board;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game.MoveMode;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.User;
import ca.mcgill.ecse223.quoridor.util.TestUtil;
import ca.mcgill.ecse223.quoridor.view.MenuPanel;
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
public class StartNewGameStepDefinition {

	private List<Player> players;

	/**
	 * @author Marton
	 */
	@Given("^The game is not running$")
	public void the_game_is_not_running() {
		TestUtil.initQuoridorAndBoard();
		// players = TestUtil.createUsersAndPlayers("user1", "user2");
	}

	@When("A new game is being initialized")
	public void a_new_game_is_being_initialized() {
		try {
			QuoridorController.initializeGame();
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@When("White player chooses a username")
	public void white_player_chooses_a_username() {
		try {
			QuoridorController.setNewUserAsWhite("User1");
			;
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@When("Black player chooses a username")
	public void black_player_chooses_a_username() {
		try {
			QuoridorController.setNewUserAsBlack("user2");
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@When("Total thinking time is set")
	public void total_thinking_time_is_set() {
		Time time = Time.valueOf("00:10:00");
		try {
			QuoridorController.setTotalThinkingTime(time);
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}
	}

	@Then("The game shall become ready to start")
	public void the_game_shall_become_ready_to_start() {
		assertEquals(GameStatus.ReadyToStart, getCurrentGame().getGameStatus());
	}

	@Given("The game is ready to start")
	public void the_game_is_ready_to_start() {
		players = TestUtil.createUsersAndPlayers("user1", "user2");
		Game game = new Game(GameStatus.ReadyToStart, MoveMode.PlayerMove, QuoridorApplication.getQuoridor());
		game.setWhitePlayer(players.get(0));
		game.setBlackPlayer(players.get(1));
	}

	@When("I start the clock")
	public void i_start_the_clock() {
		QuoridorController.startClock();
	}

	@Then("The game shall be running")
	public void the_game_shall_be_running() {
		assertEquals(GameStatus.Running, getCurrentGame().getGameStatus());
	}

	@Then("The board shall be initialized")
	public void the_board_shall_be_initialized() {
		// check amount of tiles
		assertEquals(81, getBoard().getTiles().size());
		// check amount of walls
		assertEquals(10, getCurrentGame().getCurrentPosition().getWhiteWallsInStock().size());
		assertEquals(10, getCurrentGame().getCurrentPosition().getBlackWallsInStock().size());
		// check pawn is in starting positions
		assertEquals(TestUtil.getTile(9, 5), getWhitePositionTile());
		assertEquals(TestUtil.getTile(1, 5), getBlackPositionTile());

	}

	/**
	 * Reset variables
	 * 
	 */
	@After
	public void reset() {
		players = null;
	}

	// Place your extracted methods below

	private Game getCurrentGame() {
		return QuoridorApplication.getQuoridor().getCurrentGame();
	}

	private User getUser(int i) {
		return QuoridorApplication.getQuoridor().getUser(i);
	}

	private Board getBoard() {
		return QuoridorApplication.getQuoridor().getBoard();
	}

	private Tile getWhitePositionTile() {
		return QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition().getTile();
	}

	private Tile getBlackPositionTile() {
		return QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition().getTile();
	}

}
