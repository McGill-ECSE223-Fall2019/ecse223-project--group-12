package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
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
public class SavePositionStepDefinitions {

	@Given("No file {string} exists in the filesystem")
	public void no_file_exists_in_the_filesystem(String fileName) {
		File file = new File("src\\test\\resources\\" + fileName);
		file.delete();
	}

	@When("The user initiates to save the game with name {string}")
	public void the_user_initiates_to_save_the_game_with_name(String fileName) {
		try {
			QuoridorController.savePosition(fileName, true);
		} catch (java.lang.UnsupportedOperationException e) {

		}

	}

	@Then("A file with {string} shall be created in the filesystem")
	public void a_file_with_shall_be_created_in_the_filesystem(String fileName) {
		boolean fileExists = new File("src\\test\\resources\\" + fileName).exists();
		assertTrue(fileExists);
	}

	@Given("File {string} exists in the filesystem")
	public void file_exists_in_the_filesystem(String fileName) {
		File file = new File("src\\test\\resources\\"+fileName);
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		File file = new File("src\\test\\resources\\"+fileName);
		Timestamp lastModified = new Timestamp(file.lastModified());
		Timestamp now = new Timestamp(System.currentTimeMillis());
		assertEquals(lastModified.compareTo(now), 0, 0);
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
		File file = new File("src\\test\\resources\\"+fileName);
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Timestamp lastModified = new Timestamp(file.lastModified());
		assertNotEquals(lastModified.compareTo(now), 0, 0);
	}
	
	@After
	public void cleanUp() {
		File file = new File("src\\test\\resources\\save_game_test.dat" );
		file.delete();
	}
}
