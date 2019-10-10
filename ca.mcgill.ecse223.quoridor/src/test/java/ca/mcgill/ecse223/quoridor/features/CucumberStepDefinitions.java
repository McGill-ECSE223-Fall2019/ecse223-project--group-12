package ca.mcgill.ecse223.quoridor.features;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.Wall;
import io.cucumber.java.After;

public class CucumberStepDefinitions {

	/*
	 * Note that all the Step Definitions were moved to their respective classes.
	 * Implement your Step Definitions in those classes, not here. Note that the
	 * classes may not conatain all relative steps for a certain feature, since
	 * there are duplicate steps between scenarios.
	 * 
	 * 
	 */
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
