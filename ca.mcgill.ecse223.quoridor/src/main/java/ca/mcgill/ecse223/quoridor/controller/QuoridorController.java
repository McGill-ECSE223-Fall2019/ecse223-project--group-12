package ca.mcgill.ecse223.quoridor.controller;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.User;

public class QuoridorController {
	
	public QuoridorController() {
		
	}
	/**
	 * 
	 * @param name
	 */
	public static void creatUser(String name) {
		//to do check inputs
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		quoridor.addUser(name);
	}
	public static void setBlackPlayer(User user) {
		Player player = new Player(null, user, 0, null);
		//Game game = new Game();
	}
	
	public static void setWhitePlayer() {
		
	}
	
	public static boolean validatePosition(GamePosition gamePosition) throws java.lang.UnsupportedOperationException {
		throw new java.lang.UnsupportedOperationException(); 
	}
	
	/**
	 * @author Francis Comeau
	 * Gherkin feature: LoadPosition.feature
	 */
	public static void loadPosition(String fullPath) throws java.lang.UnsupportedOperationException {
		throw new java.lang.UnsupportedOperationException(); 
	}
	
	/**
	 * @author Francis Comeau
	 * Gherkin feature: SavePosition.feature
	 */
	public static void savePosition(GamePosition gamePosition) throws java.lang.UnsupportedOperationException {
		throw new java.lang.UnsupportedOperationException(); 
	}


}
