package ca.mcgill.ecse223.quoridor.features;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.Board;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game.MoveMode;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.User;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.model.WallMove;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CucumberStepDefinitions {

	// ***********************************************
	// Background step definitions
	// ***********************************************

	@Given("^The game is not running$")
	public void theGameIsNotRunning() {
		initQuoridorAndBoard();
		createUsersAndPlayers("user1", "user2");
	}

	@Given("^The game is running$")
	public void theGameIsRunning() {
		initQuoridorAndBoard();
		ArrayList<Player> createUsersAndPlayers = createUsersAndPlayers("user1", "user2");
		createAndStartGame(createUsersAndPlayers);
	}

	@And("^It is my turn to move$")
	public void itIsMyTurnToMove() throws Throwable {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Player currentPlayer = quoridor.getCurrentGame().getWhitePlayer();
		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().setPlayerToMove(currentPlayer);
	}

	@Given("The following walls exist:")
	public void theFollowingWallsExist(io.cucumber.datatable.DataTable dataTable) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		// keys: wrow, wcol, wdir
		Player[] players = { quoridor.getCurrentGame().getWhitePlayer(), quoridor.getCurrentGame().getBlackPlayer() };
		int playerIdx = 0;
		int wallIdxForPlayer = 0;
		for (Map<String, String> map : valueMaps) {
			Integer wrow = Integer.decode(map.get("wrow"));
			Integer wcol = Integer.decode(map.get("wcol"));
			// Wall to place
			// Walls are placed on an alternating basis wrt. the owners
			//Wall wall = Wall.getWithId(playerIdx * 10 + wallIdxForPlayer);
			Wall wall = players[playerIdx].getWall(wallIdxForPlayer); // above implementation sets wall to null

			String dir = map.get("wdir");

			Direction direction;
			switch (dir) {
			case "horizontal":
				direction = Direction.Horizontal;
				break;
			case "vertical":
				direction = Direction.Vertical;
				break;
			default:
				throw new IllegalArgumentException("Unsupported wall direction was provided");
			}
			new WallMove(0, 1, players[playerIdx], quoridor.getBoard().getTile((wrow - 1) * 9 + wcol - 1), quoridor.getCurrentGame(), direction, wall);
			if (playerIdx == 0) {
				quoridor.getCurrentGame().getCurrentPosition().removeWhiteWallsInStock(wall);
				quoridor.getCurrentGame().getCurrentPosition().addWhiteWallsOnBoard(wall);
			} else {
				quoridor.getCurrentGame().getCurrentPosition().removeBlackWallsInStock(wall);
				quoridor.getCurrentGame().getCurrentPosition().addBlackWallsOnBoard(wall);
			}
			wallIdxForPlayer = wallIdxForPlayer + playerIdx;
			playerIdx++;
			playerIdx = playerIdx % 2;
		}
		System.out.println();

	}

	@And("I do not have a wall in my hand")
	public void iDoNotHaveAWallInMyHand() {
		// GUI-related feature -- TODO for later
	}
	
	@And("^I have a wall in my hand over the board$")
	public void iHaveAWallInMyHandOverTheBoard() throws Throwable {
		// GUI-related feature -- TODO for later
	}
	
	// ***********************************************
	// Scenario and scenario outline step definitions
	// ***********************************************

	// ***********************************************
	// Scenario and scenario outline step definitions
	// ***********************************************
	
	
	// ***********************************************
	// Scenario and scenario outline step definitions
	// ***********************************************
	
	@Given("The wall move candidate with {string} at position \\({int}, {int}) is valid")
	public void the_wall_move_candidate_with_at_position_is_valid(String string, Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("I release the wall in my hand")
	public void i_release_the_wall_in_my_hand() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("A wall move shall be registered with {string} at position \\({int}, {int})")
	public void a_wall_move_shall_be_registered_with_at_position(String string, Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("I shall not have a wall in my hand")
	public void i_shall_not_have_a_wall_in_my_hand() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("My move shall be completed")
	public void my_move_shall_be_completed() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("It shall not be my turn to move")
	public void it_shall_not_be_my_turn_to_move() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("The wall move candidate with {string} at position \\({int}, {int}) is invalid")
	public void the_wall_move_candidate_with_at_position_is_invalid(String string, Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("I shall be notified that my wall move is invalid")
	public void i_shall_be_notified_that_my_wall_move_is_invalid() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("I shall have a wall in my hand over the board")
	public void i_shall_have_a_wall_in_my_hand_over_the_board() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("It shall be my turn to move")
	public void it_shall_be_my_turn_to_move() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("No wall move shall be registered with {string} at position \\({int}, {int})")
	public void no_wall_move_shall_be_registered_with_at_position(String string, Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("I have more walls on stock")
	public void i_have_more_walls_on_stock() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("I try to grab a wall from my stock")
	public void i_try_to_grab_a_wall_from_my_stock() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("A wall move candidate shall be created at initial position")
	public void a_wall_move_candidate_shall_be_created_at_initial_position() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The wall in my hand shall disappear from my stock")
	public void the_wall_in_my_hand_shall_disappear_from_my_stock() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("I have no more walls on stock")
	public void i_have_no_more_walls_on_stock() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("I shall be notified that I have no more walls")
	public void i_shall_be_notified_that_I_have_no_more_walls() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("I shall have no walls in my hand")
	public void i_shall_have_no_walls_in_my_hand() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("The initialization of the board is initiated")
	public void the_initialization_of_the_board_is_initiated() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("It shall be white player to move")
	public void it_shall_be_white_player_to_move() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("White's pawn shall be in its initial position")
	public void white_s_pawn_shall_be_in_its_initial_position() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("Black's pawn shall be in its initial position")
	public void black_s_pawn_shall_be_in_its_initial_position() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("All of White's walls shall be in stock")
	public void all_of_White_s_walls_shall_be_in_stock() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("All of Black's walls shall be in stock")
	public void all_of_Black_s_walls_shall_be_in_stock() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("White's clock shall be counting down")
	public void white_s_clock_shall_be_counting_down() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("It shall be shown that this is White's turn")
	public void it_shall_be_shown_that_this_is_White_s_turn() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("I initiate to load a saved game {string}")
	public void i_initiate_to_load_a_saved_game(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("The position to load is valid")
	public void the_position_to_load_is_valid() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("It shall be {string}'s turn")
	public void it_shall_be_s_turn(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("{string} shall be at {int}:{int}")
	public void shall_be_at(String string, Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("{string} shall have a vertical wall at {int}:{int}")
	public void shall_have_a_vertical_wall_at(String string, Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("{string} shall have a horizontal wall at {int}:{int}")
	public void shall_have_a_horizontal_wall_at(String string, Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("Both players shall have {int} in their stacks")
	public void both_players_shall_have_in_their_stacks(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("The position to load is invalid")
	public void the_position_to_load_is_invalid() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The load shall return an error")
	public void the_load_shall_return_an_error() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("The player to move is {string}")
	public void the_player_to_move_is(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("The player is located at {int}:{int}")
	public void the_player_is_located_at(Integer int1, Integer int2) {
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

	@Given("There is a {string} wall {string} from the player")
	public void there_is_a_wall_from_the_player(String string, String string2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("My opponent is not {string} from the player")
	public void my_opponent_is_not_from_the_player(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("A wall move candidate exists with {string} at position \\({int}, {int})")
	public void a_wall_move_candidate_exists_with_at_position(String string, Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("The wall candidate is not at the {string} edge of the board")
	public void the_wall_candidate_is_not_at_the_edge_of_the_board(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("I try to move the wall {string}")
	public void i_try_to_move_the_wall(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The wall shall be moved over the board to position \\({int}, {int})")
	public void the_wall_shall_be_moved_over_the_board_to_position(Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("A wall move candidate shall exist with {string} at position \\({int}, {int})")
	public void a_wall_move_candidate_shall_exist_with_at_position(String string, Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("The wall candidate is at the {string} edge of the board")
	public void the_wall_candidate_is_at_the_edge_of_the_board(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("I shall be notified that my move is illegal")
	public void i_shall_be_notified_that_my_move_is_illegal() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("A new game is initializing")
	public void a_new_game_is_initializing() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("Next player to set user name is {string}")
	public void next_player_to_set_user_name_is(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("There is existing user {string}")
	public void there_is_existing_user(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("The player selects existing {string}")
	public void the_player_selects_existing(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The name of player {string} in the new game shall be {string}")
	public void the_name_of_player_in_the_new_game_shall_be(String string, String string2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("There is no existing user {string}")
	public void there_is_no_existing_user(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("The player provides new user name: {string}")
	public void the_player_provides_new_user_name(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The player shall be warned that {string} already exists")
	public void the_player_shall_be_warned_that_already_exists(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("Next player to set user name shall be {string}")
	public void next_player_to_set_user_name_shall_be(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("I try to flip the wall")
	public void i_try_to_flip_the_wall() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The wall shall be rotated over the board to {string}")
	public void the_wall_shall_be_rotated_over_the_board_to(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("No file {string} exists in the filesystem")
	public void no_file_exists_in_the_filesystem(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("The user initiates to save the game with name {string}")
	public void the_user_initiates_to_save_the_game_with_name(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("A file with {string} shall be created in the filesystem")
	public void a_file_with_shall_be_created_in_the_filesystem(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("File {string} exists in the filesystem")
	public void file_exists_in_the_filesystem(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("The user confirms to overwrite existing file")
	public void the_user_confirms_to_overwrite_existing_file() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("File with {string} shall be updated in the filesystem")
	public void file_with_shall_be_updated_in_the_filesystem(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("The user cancels to overwrite existing file")
	public void the_user_cancels_to_overwrite_existing_file() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("File {string} shall not be changed in the filesystem")
	public void file_shall_not_be_changed_in_the_filesystem(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("{int}:{int} is set as the thinking time")
	public void is_set_as_the_thinking_time(Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("Both players shall have {int}:{int} remaining time left")
	public void both_players_shall_have_remaining_time_left(Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("A new game is being initialized")
	public void a_new_game_is_being_initialized() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("White player chooses a username")
	public void white_player_chooses_a_username() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("Black player chooses a username")
	public void black_player_chooses_a_username() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("Total thinking time is set")
	public void total_thinking_time_is_set() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The game shall become ready to start")
	public void the_game_shall_become_ready_to_start() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("The game is ready to start")
	public void the_game_is_ready_to_start() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("I start the clock")
	public void i_start_the_clock() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The game shall be running")
	public void the_game_shall_be_running() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The board shall be initialized")
	public void the_board_shall_be_initialized() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("The clock of {string} is running")
	public void the_clock_of_is_running(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("The clock of {string} is stopped")
	public void the_clock_of_is_stopped(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("Player {string} completes his move")
	public void player_completes_his_move(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The user interface shall be showing it is {string} turn")
	public void the_user_interface_shall_be_showing_it_is_turn(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The clock of {string} shall be stopped")
	public void the_clock_of_shall_be_stopped(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The clock of {string} shall be running")
	public void the_clock_of_shall_be_running(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The next player to move shall be {string}")
	public void the_next_player_to_move_shall_be(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("A game position is supplied with pawn coordinate {int}:{int}")
	public void a_game_position_is_supplied_with_pawn_coordinate(Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("Validation of the position is initiated")
	public void validation_of_the_position_is_initiated() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The position shall be {string}")
	public void the_position_shall_be(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("A game position is supplied with wall coordinate {int}:{int}-{string}")
	public void a_game_position_is_supplied_with_wall_coordinate(Integer int1, Integer int2, String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The position shall be valid")
	public void the_position_shall_be_valid() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The position shall be invalid")
	public void the_position_shall_be_invalid() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}
	
	// ***********************************************
	// Scenario and scenario outline step definitions
	// ***********************************************
	// ***********************************************
	// Scenario and scenario outline step definitions
	// ***********************************************
	// ***********************************************
	// Scenario and scenario outline step definitions
	// ***********************************************
	// ***********************************************
	// Scenario and scenario outline step definitions
	// ***********************************************
	/*
	 * TODO Insert your missing step definitions here
	 * 
	 * Call the methods of the controller that will manipulate the model once they
	 * are implemented
	 * 
	 */

	// ***********************************************
	// Clean up
	// ***********************************************

	// After each scenario, the test model is discarded
	@After
	public void tearDown() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		// Avoid null pointer for step definitions that are not yet implemented.
		if (quoridor != null) {
			quoridor.delete();
			quoridor = null;
		}
		for (int i = 0; i < 20; i++) {
			Wall wall = Wall.getWithId(i);
			if(wall != null) {
				wall.delete();
			}
		}
	}

	// ***********************************************
	// Extracted helper methods
	// ***********************************************

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

		// Players are assumed to start on opposite sides and need to make progress
		// horizontally to get to the other side
		//@formatter:off
		/*
		 *  __________
		 * |          |
		 * |          |
		 * |x->    <-x|
		 * |          |
		 * |__________|
		 * 
		 */
		//@formatter:on
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

	private void createAndStartGame(ArrayList<Player> players) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		// There are total 36 tiles in the first four rows and
		// indexing starts from 0 -> tiles with indices 36 and 36+8=44 are the starting
		// positions
		Tile player1StartPos = quoridor.getBoard().getTile(36);
		Tile player2StartPos = quoridor.getBoard().getTile(44);
		
		Game game = new Game(GameStatus.Running, MoveMode.PlayerMove, players.get(0), players.get(1), quoridor);

		PlayerPosition player1Position = new PlayerPosition(quoridor.getCurrentGame().getWhitePlayer(), player1StartPos);
		PlayerPosition player2Position = new PlayerPosition(quoridor.getCurrentGame().getBlackPlayer(), player2StartPos);

		GamePosition gamePosition = new GamePosition(0, player1Position, player2Position, players.get(0), game);

		// Add the walls as in stock for the players
		for (int j = 0; j < 10; j++) {
			Wall wall = Wall.getWithId(j);
			gamePosition.addWhiteWallsInStock(wall);
		}
		for (int j = 0; j < 10; j++) {
			Wall wall = Wall.getWithId(j + 10);
			gamePosition.addBlackWallsInStock(wall);
		}

		game.setCurrentPosition(gamePosition);
	}

}
