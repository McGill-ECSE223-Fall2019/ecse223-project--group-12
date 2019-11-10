package ca.mcgill.ecse223.quoridor.features;

import static org.junit.jupiter.api.Assertions.*;

import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
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

public class PathExistsStepDefinitions {
	private boolean[] pathExists = new boolean[2];

	@Given("A {string} wall move candidate exists at position {int}:{int}")
	public void a_wall_move_candidate_exists_at_position(String dir, Integer row, Integer col) {
		Wall wall = TestUtil.getAWallInStockForCurrenPlayer();
		Player p = TestUtil.getCurrentPlayer();
		Tile tile = TestUtil.getTile(row, col);
		Direction direction = TestUtil.getDirection(dir);
		Game g = QuoridorApplication.getQuoridor().getCurrentGame();
		if (wall.getMove() != null) {
			wall.getMove().delete();
		}
		WallMove candidate = new WallMove(0, 0, p, tile, g, direction, wall);
		g.setWallMoveCandidate(candidate);
	}

	@Given("The black player is located at {int}:{int}")
	public void the_black_player_is_located_at(Integer row, Integer col) {
		Game g = QuoridorApplication.getQuoridor().getCurrentGame();
		Player p = g.getBlackPlayer();
		GamePosition gp = g.getCurrentPosition();
		Tile tile = TestUtil.getTile(row, col);
		PlayerPosition position = new PlayerPosition(p, tile);
		gp.setBlackPosition(position);
		g.setCurrentPosition(gp);

	}

	@Given("The white player is located at {int}:{int}")
	public void the_white_player_is_located_at(Integer row, Integer col) {
		Game g = QuoridorApplication.getQuoridor().getCurrentGame();
		Player p = g.getWhitePlayer();
		GamePosition gp = g.getCurrentPosition();
		Tile tile = TestUtil.getTile(row, col);
		PlayerPosition position = new PlayerPosition(p, tile);
		gp.setWhitePosition(position);
		g.setCurrentPosition(gp);
	}

	@When("Check path existence is initiated")
	public void check_path_existence_is_initiated() {
		pathExists = QuoridorController.validatePath();
	}

	@Then("Path is available for {string} player\\(s)")
	public void path_is_available_for_player_s(String expected) {
		String result = "";
		if (pathExists[0] && pathExists[1]) {
			result = "both";
		} else if (pathExists[0]) {
			result = "white";
		} else if (pathExists[1]) {
			result = "black";
		} else {
			result = "none";
		}
		assertEquals(expected, result);
	}
}
