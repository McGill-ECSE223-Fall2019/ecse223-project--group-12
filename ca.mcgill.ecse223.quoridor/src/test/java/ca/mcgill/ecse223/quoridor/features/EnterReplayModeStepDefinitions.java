package ca.mcgill.ecse223.quoridor.features;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
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

public class EnterReplayModeStepDefinitions {
	Integer moveNum = null;
	Integer roundNum = null;

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
			//Integer moveNum = Integer.decode(map.get("mv"));
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
		Move nextMove = TestUtil.getMove(moveNum, roundNum);
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		game.addOrMoveMoveAt(nextMove, 0);
//		String s = "";
//		Move m = TestUtil.getMove(1,1);
//		while(m != null) {
//			s+="move: " + m.getMoveNumber() +" Round:  "+ m.getRoundNumber();
//			s+="\n";
//			s+= "Player: " + m.getPlayer().getUser().getName();
//			s+="\n";
//			s+=m.getTargetTile().toString();
//			s+="\n \n";
//			m =m.getNextMove();
//		}
//		s+="\n \n";
//		s+=game.getMoves().get(0);
//		throw new cucumber.api.PendingException(s);
	}

	@When("I initiate to continue game")
	public void i_initiate_to_continue_game() {
		QuoridorController.continueGame();
	}

	@Then("The remaining moves of the game shall be removed")
	public void the_remaining_moves_of_the_game_shall_be_removed() {
		
	}

	@Given("The game has a final result")
	public void the_game_has_a_final_result() {
		// Write code here that turns the phrase above into concrete actions
		throw new cucumber.api.PendingException();
	}

	@Then("I shall be notified that finished games cannot be continued")
	public void i_shall_be_notified_that_finished_games_cannot_be_continued() {
		// Write code here that turns the phrase above into concrete actions
		throw new cucumber.api.PendingException();
	}
}
