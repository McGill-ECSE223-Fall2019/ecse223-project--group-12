package ca.mcgill.ecse223.quoridor.controller;

import java.sql.Time;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.Board;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.User;

public class QuoridorController {

	public QuoridorController() {

	}

	// ------------------------
	// Remi
	// ------------------------

	/**
	 * <Description>
	 * @author Remi Carriere
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static void initializeGame() throws java.lang.UnsupportedOperationException {
		// TODO
		throw new java.lang.UnsupportedOperationException();
	}

	/**
	 * <Description>
	 * @author Remi Carriere
	 * @param name
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static void createUser(String name) throws java.lang.UnsupportedOperationException {
		// TODO 
		throw new java.lang.UnsupportedOperationException();
	}

	/**
	 * <Description>
	 * @author Remi Carriere
	 * @param user
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static void setBlackPlayer(User user) throws java.lang.UnsupportedOperationException {
		// TODO 
		throw new java.lang.UnsupportedOperationException();
	}

	/**
	 * <Description>
	 * @author Remi Carriere
	 * @param user
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static void setWhitePlayer(User user) throws java.lang.UnsupportedOperationException {
		// TODO 
		throw new java.lang.UnsupportedOperationException();
	}

	/**
	 * <Description>
	 * @author Remi Carriere
	 * @param whiteTime
	 * @param blackTime
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static void setTotalThinkingTime(Time whiteTime, Time blackTime)
			throws java.lang.UnsupportedOperationException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	/**
	 * <Description>
	 * @author Remi Carriere
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static void startClock() throws java.lang.UnsupportedOperationException {
		// TODO
		throw new java.lang.UnsupportedOperationException();
	}

	/**
	 * <Description>
	 * @author Remi Carriere
	 * @param gamePosition
	 * @return
	 * @throws java.lang.UnsupportedOperationException
	 */
	public static boolean validatePosition(GamePosition gamePosition) throws java.lang.UnsupportedOperationException {
		throw new java.lang.UnsupportedOperationException();
	}

	// ------------------------
	// Francis
	// ------------------------

	/**
	 * @author Francis Comeau Gherkin feature: LoadPosition.feature
	 */
	public static void loadPosition(String fullPath) throws java.lang.UnsupportedOperationException {
		throw new java.lang.UnsupportedOperationException();
	}

	/**
	 * @author Francis Comeau Gherkin feature: SavePosition.feature
	 */
	public static void savePosition(GamePosition gamePosition) throws java.lang.UnsupportedOperationException {
		throw new java.lang.UnsupportedOperationException();
	}

	// ------------------------
	// Weige
	// ------------------------

	/**
	 * @author Weige qian
	 * Gherkin feature:InitializeBoard.feature
	 */
	public static void initBoard() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		if (!quoridor.hasBoard()) {
			new Board(quoridor);
		}
		if (!quoridor.getBoard().hasTiles()) {
			for(int i = 1;i < 10;i++) {
				for(int j = 1;j < 10;j++) {
					quoridor.getBoard().addTile(i,j);
				}
			}
		}
	}

	// ------------------------
	// Khan
	// ------------------------

	// TODO

	// ------------------------
	// Zechen
	// ------------------------

	// TODO

}
