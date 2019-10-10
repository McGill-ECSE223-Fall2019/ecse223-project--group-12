package ca.mcgill.ecse223.quoridor.features;

import java.sql.Time;
import java.util.ArrayList;

import org.junit.Assume;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Board;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.User;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game.MoveMode;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

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

	@Given("^The game is not running$")
	public void theGameIsNotRunning() {
		initQuoridorAndBoard();
		createUsersAndPlayers("user1", "user2");
	}

	@When("A new game is being initialized")
	public void a_new_game_is_being_initialized() {
		try {
			QuoridorController.initializeGame();
		} catch (java.lang.UnsupportedOperationException | NullPointerException e) {
			/*
			 * no handling necessary at this stage. Temporarily catch null pointers, must be
			 * removed once controller methods are implemented
			 */
		}
	}

	@When("White player chooses a username")
	public void white_player_chooses_a_username() {
		try {
			QuoridorController.setWhitePlayerInGame(getUser(0));
		} catch (java.lang.UnsupportedOperationException | NullPointerException e) {
			/*
			 * no handling necessary at this stage. Temporarily catch null pointers, must be
			 * removed once controller methods are implemented
			 */
		}
	}

	@When("Black player chooses a username")
	public void black_player_chooses_a_username() {
		try {
			QuoridorController.setBlackPlayerInGame(getUser(1));
		} catch (java.lang.UnsupportedOperationException | NullPointerException e) {
			/*
			 * no handling necessary at this stage. Temporarily catch null pointers, must be
			 * removed once controller methods are implemented
			 */
		}
	}

	@When("Total thinking time is set")
	public void total_thinking_time_is_set() {
		try {
			// choose whiteTime by default for scenario
			QuoridorController.setTotalThinkingTime(getWhiteTime());
		} catch (java.lang.UnsupportedOperationException | NullPointerException e) {
			/*
			 * no handling necessary at this stage. Temporarily catch null pointers, must be
			 * removed once controller methods are implemented
			 */
		}
	}

	@Then("The game shall become ready to start")
	public void the_game_shall_become_ready_to_start() {
		try {
			assertEquals(GameStatus.ReadyToStart, getCurrentGame().getGameStatus());
		} catch (NullPointerException e) {
			/*
			 * no handling necessary at this stage. Temporarily catch null pointers, must be
			 * removed once controller methods are implemented
			 */
		}
		throw new cucumber.api.PendingException();
	}

	@Given("The game is ready to start")
	public void the_game_is_ready_to_start() {
		try {
			getCurrentGame().setGameStatus(GameStatus.ReadyToStart);
		} catch (NullPointerException e) {
			/*
			 * no handling necessary at this stage. Temporarily catch null pointers, must be
			 * removed once controller methods are implemented
			 */
		}
	}

	@When("I start the clock")
	public void i_start_the_clock() {
		try {
			QuoridorController.startClock();
		} catch (java.lang.UnsupportedOperationException | NullPointerException e) {
			/*
			 * no handling necessary at this stage. Temporarily catch null pointers, must be
			 * removed once controller methods are implemented
			 */
		}
	}

	@Then("The game shall be running")
	public void the_game_shall_be_running() {
		try {
			assertEquals(GameStatus.Running, getCurrentGame().getGameStatus());
		} catch (NullPointerException e) {
			/*
			 * no handling necessary at this stage. Temporarily catch null pointers, must be
			 * removed once controller methods are implemented
			 */
		}
		throw new cucumber.api.PendingException();
	}

	@Then("The board shall be initialized")
	public void the_board_shall_be_initialized() {
		try {
			// check amount of tiles
			assertEquals(81, getBoard().getTiles().size()); 
			// check amount of walls
			assertEquals(10, getCurrentGame().getCurrentPosition().getBlackWallsInStock().size()); 
			assertEquals(10, getCurrentGame().getCurrentPosition().getBlackWallsInStock().size());
			 // check pawn is in starting positions
			assertEquals(getBoard().getTile(36), getWhitePositionTile());
			assertEquals(getBoard().getTile(44), getBlackPositionTile());
		} catch (NullPointerException e) {
			/*
			 * no handling necessary at this stage. Temporarily catch null pointers, must be
			 * removed once controller methods are implemented
			 */
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

	private Game getCurrentGame() {
		return QuoridorApplication.getQuoridor().getCurrentGame();
	}

	private Time getWhiteTime() {
		return QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().getRemainingTime();

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
