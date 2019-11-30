package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.util.TestUtil;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/*
 * Note that the classes may not contain all relative steps for a certain feature, 
 * since there are duplicate steps between scenarios.
 */

/**
 * 
 * @author Francis Comeau
 *
 */

public class SaveGameStepDefinitions {

	private String fileName = "";
	
	@When("The user initiates to save the game with name {string}")
	public void the_user_initiates_to_save_the_game_with_name(String fileName) {
		//QuoridorController.savePosition(fileName, false, true);
		QuoridorController.saveGame(this.fileName, false, true);
	}
	
	@When("The user confirms to overwrite existing game file")
	public void the_user_confirms_to_overwrite_existing_game_file() {
		//QuoridorController.savePosition(fileName, true, true);
		QuoridorController.saveGame(this.fileName, true, true);

	}

	@When("The user cancels to overwrite existing game file")
	public void the_user_cancels_to_overwrite_existing_game_file() {
		//QuoridorController.savePosition(fileName, false, true);
		QuoridorController.saveGame(this.fileName, false, true);

	}
	
	@After
	public void cleanUp() {
		File file = new File("src\\test\\resources\\save_game_test.dat");
		file.delete();
		File file2 = new File("src\\test\\resources\\save_game_test.mov");
		file2.delete();
	}
	
	
}
