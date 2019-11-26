package ca.mcgill.ecse223.quoridor.features;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.InvalidInputException;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.StepMove;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.model.WallMove;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Move;
import ca.mcgill.ecse223.quoridor.util.TestUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * 
 * @author Remi Carriere
 *
 */
public class EnterReplayModeStepDefinitions {
	
	private String error = "";

	@When("I initiate replay mode")
	public void i_initiate_replay_mode() {
		QuoridorController.enterReplayMode();
	}

	@Then("The game shall be in replay mode")
	public void the_game_shall_be_in_replay_mode() {
		assertEquals(GameStatus.Replay, QuoridorApplication.getQuoridor().getCurrentGame().getGameStatus());
	}

	@Given("The game is in replay mode")
	public void the_game_is_replay_mode() {
		TestUtil.initQuoridorAndBoard();
		ArrayList<Player> players = TestUtil.createUsersAndPlayers("test usr1", "test usr2");
		TestUtil.createAndStartGame(players);
		QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(GameStatus.Replay);
	}

	@Given("The following moves have been played in game:")
	public void the_following_moves_have_been_played_in_game(io.cucumber.datatable.DataTable dataTable) {
		Player white = TestUtil.getPlayerByColor("white");
		Player black = TestUtil.getPlayerByColor("black");
		Player player = null;
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		GamePosition gp = game.getCurrentPosition();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Integer moveNum = Integer.decode(map.get("mv"));
			Integer roundNum = Integer.decode(map.get("rnd"));
			String move = map.get("move");
			Wall wall;
			if (roundNum == 1) {
				player = white;
				wall = gp.getWhiteWallsInStock().get(0);
			} else {
				player = black;
				wall = gp.getBlackWallsInStock().get(0);
			}

			String pawnMoveRegex = "[a-i][1-9]";
			String wallMoveRegex = "[a-i][1-9](v|h)";
			int row;
			int col;
			Direction dir;
			if (move.matches(pawnMoveRegex)) {
				col = move.charAt(0) - 96;
				row = move.charAt(1) - 48;
				Tile tile = TestUtil.getTile(row, col);
				StepMove stepMove = new StepMove(0, 0, player, tile, game); // move and round numbers are set
																			// automatically
				TestUtil.addMoveToGameHistory(stepMove);
			} else if (move.matches(wallMoveRegex)) {
				col = move.charAt(0) - 96;
				row = move.charAt(1) - 48;
				Tile tile = TestUtil.getTile(row, col);

				if (move.charAt(2) == 'v') {
					dir = Direction.Vertical;
				} else {
					dir = Direction.Horizontal;
				}
				WallMove wallMove = new WallMove(0, 0, player, tile, game, dir, wall); // move and round numbers are set
																						// automatically
				TestUtil.addMoveToGameHistory(wallMove);
				if (player.hasGameAsBlack()) {
					gp.removeBlackWallsInStock(wall);
					gp.addBlackWallsOnBoard(wall);
				} else {
					gp.removeWhiteWallsInStock(wall);
					gp.addWhiteWallsOnBoard(wall);
				}
			}
		}
	}

	@Given("The game does not have a final result")
	public void the_game_does_not_have_a_final_result() {

	}

	@Given("The next move is \\({int}, {int})")
	public void the_next_move_is(Integer moveNum, Integer roundNum) {
		// use current move instead of next move
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		if (roundNum == 1) {
			roundNum = 2;
			moveNum--;
		} else {
			roundNum = 1;
		}
		Move currentMove = TestUtil.getMove(moveNum, roundNum);
		game.setCurrentMove(currentMove);
	}

	@When("I initiate to continue game")
	public void i_initiate_to_continue_game() {
		try {
			QuoridorController.continueGame();
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
	}

	@Then("The remaining moves of the game shall be removed")
	public void the_remaining_moves_of_the_game_shall_be_removed() {
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		assertNull(game.getCurrentMove().getNextMove());
	}

	@Then("The next move shall be \\({int}, {int})")
	public void the_next_move_shall_be(Integer expectedMoveNum, Integer expectedRoundNum) {
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		Move currentMove = game.getCurrentMove();
		int nextMoveNum;
		int nextRoundNum;
		if (currentMove == null) {
			nextMoveNum = 1;
			nextRoundNum = 1;
		}
		else {
			nextMoveNum = game.getCurrentMove().getMoveNumber();
			nextRoundNum = game.getCurrentMove().getRoundNumber();
			if (nextRoundNum == 2) {
				nextRoundNum = 1;
				nextMoveNum += 1;
			} else {
				nextRoundNum = 2;
			}
		}
		assertEquals(expectedMoveNum, nextMoveNum);
		assertEquals(expectedRoundNum, nextRoundNum);
	}

	@Given("The game has a final result")
	public void the_game_has_a_final_result() {
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		game.setGameStatus(GameStatus.WhiteWon);
	}

	@Then("I shall be notified that finished games cannot be continued")
	public void i_shall_be_notified_that_finished_games_cannot_be_continued() {
		assertEquals("Cannot continue a game with a final result!", error);
	}
}
