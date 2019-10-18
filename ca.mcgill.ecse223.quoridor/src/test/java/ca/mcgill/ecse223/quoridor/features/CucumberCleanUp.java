package ca.mcgill.ecse223.quoridor.features;

import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.Wall;
import io.cucumber.java.After;

/**
 * 
 * @author Remi Carriere
 *
 */
public class CucumberCleanUp {

	/**
	 * Tear Down
	 * 
	 */
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
}
