package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
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
public class SavePositionStepDefinitions {

	@Given("No file {string} exists in the filesystem")
	public void no_file_exists_in_the_filesystem(String fileName) {
		File file = new File(fileName); 
        file.delete();
	    throw new cucumber.api.PendingException();
	}

	@When("The user initiates to save the game with name {string}")
	public void the_user_initiates_to_save_the_game_with_name(String fileName) {
		GamePosition gamePosition = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		try {
			QuoridorController.savePosition(gamePosition ,fileName);
		} catch (java.lang.UnsupportedOperationException e) {
			// Skip test if method not implemented
			throw new cucumber.api.PendingException();
		}

	}

	@Then("A file with {string} shall be created in the filesystem")
	public void a_file_with_shall_be_created_in_the_filesystem(String fileName) {
		boolean fileExists = new File(fileName).exists();
		assertTrue(fileExists);
	    throw new cucumber.api.PendingException();
	}

	@Given("File {string} exists in the filesystem")
	public void file_exists_in_the_filesystem(String fileName) {
		File file = new File(fileName);
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    throw new cucumber.api.PendingException();
	}

	@When("The user confirms to overwrite existing file")
	public void the_user_confirms_to_overwrite_existing_file() {
		try {
			assertTrue(QuoridorController.askOverwriteFile());
		} catch (java.lang.UnsupportedOperationException e) {
			// Skip test if method not implemented
			throw new cucumber.api.PendingException();
		}
	}

	@Then("File with {string} shall be updated in the filesystem")
	public void file_with_shall_be_updated_in_the_filesystem(String fileName) {
		File file = new File(fileName);
	    Timestamp lastModified = new Timestamp(file.lastModified());
	    Timestamp now = new Timestamp(System.currentTimeMillis());
	    assertEquals(lastModified.compareTo(now), 0, 0);
	    throw new cucumber.api.PendingException();
	}

	@When("The user cancels to overwrite existing file")
	public void the_user_cancels_to_overwrite_existing_file() {
		try {
			assertFalse(QuoridorController.askOverwriteFile());
		} catch (java.lang.UnsupportedOperationException e) {
			// Skip test if method not implemented
			throw new cucumber.api.PendingException();
		}
	}

	@Then("File {string} shall not be changed in the filesystem")
	public void file_shall_not_be_changed_in_the_filesystem(String fileName) {
	    File file = new File(fileName);
	    Timestamp now = new Timestamp(System.currentTimeMillis());
	    Timestamp lastModified = new Timestamp(file.lastModified());
	    assertNotEquals(lastModified.compareTo(now), 0, 0);
	    throw new cucumber.api.PendingException();
	}
}
