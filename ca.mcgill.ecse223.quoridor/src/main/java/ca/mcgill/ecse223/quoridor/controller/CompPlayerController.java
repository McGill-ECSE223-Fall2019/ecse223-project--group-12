package ca.mcgill.ecse223.quoridor.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.StepMove;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.model.WallMove;
import ca.mcgill.ecse223.quoridor.to.PathAndMove;
import ca.mcgill.ecse223.quoridor.to.TileTO;
import ca.mcgill.ecse223.quoridor.to.WallMoveTO;

/**
 * @author Remi Carriere
 *
 */
public class CompPlayerController {

	static int currentBlackPathLength = 0;
	static int currentWhitePathLength = 0;
	static TileTO nextWhiteTile = null;

	/**
	 * Makes the Comp player do the "best possible" pawn move or jump move (the pawn
	 * selects the move type randomly, unless no "good" wall moves are available,
	 * then we default to pawn move)
	 */
	public static void doMove() {
		Game g = QuoridorApplication.getQuoridor().getCurrentGame();
		Player black = g.getBlackPlayer();
		Player white = g.getWhitePlayer();

		Random randomno = new Random();
		boolean random = randomno.nextBoolean();

		currentBlackPathLength = QuoridorController.getPath(black).size();
		currentWhitePathLength = QuoridorController.getPath(white).size();
		nextWhiteTile = QuoridorController.getPath(white).get(1);
		GamePosition gp = g.getCurrentPosition();
		WallMoveTO move = null;

		if (!gp.getBlackWallsInStock().isEmpty()) {
			move = getBestWallMove();
		}

		if (move != null && random) {
			doWallMove(move);
		} else {
			doBestPawnMove();
		}
		QuoridorController.checkGameWon();
	}

	/**
	 * Makes the pawn move on his current shortest path toward destination
	 */
	public static void doBestPawnMove() {
		Game g = QuoridorApplication.getQuoridor().getCurrentGame();
		GamePosition gp = g.getCurrentPosition();
		Player black = g.getBlackPlayer();
		TileTO targetTO = QuoridorController.getPath(black).get(1);
		Tile target = QuoridorController.getTile(targetTO.getRow(), targetTO.getCol());
		PlayerPosition newPos = new PlayerPosition(black, target);
		gp.setBlackPosition(newPos);
		StepMove move = new StepMove(0, 0, black, target, g);
		QuoridorController.addMoveToGameHistory(move);
		QuoridorController.confirmMove();
	}

	/**
	 * Does the specified wall move for black
	 * 
	 * @param moveTO
	 */
	public static void doWallMove(WallMoveTO moveTO) {
		try {
			QuoridorController.grabWall();
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Tile target = QuoridorController.getTile(moveTO.getRow(), moveTO.getColumn());
		QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().setTargetTile(target);
		QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate()
				.setWallDirection(moveTO.getDirection());
		try {
			QuoridorController.dropWall();
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Retuns a list of possible PathAndMoves (PathAndMoves contains:
	 * {BlackWallMove, BlackShortestPath, WhiteShortest Path, and a boolean which is
	 * true if white's step move options are reduced for the corresponding move})
	 * 
	 * @return
	 */
	public static List<PathAndMove> getPossibleWallMoves() {
		Game g = QuoridorApplication.getQuoridor().getCurrentGame();
		GamePosition gp = g.getCurrentPosition();
		Player black = g.getBlackPlayer();
		Player white = g.getWhitePlayer();
		List<PathAndMove> pathsToCheck = new ArrayList<PathAndMove>();
		int whiteMoveOptions = QuoridorController.getAdjTiles(white).size();
		// Get wall
		Wall wall = gp.getBlackWallsInStock().get(0);
		// Add the wall to the board so the graph can be updated
		Tile target = QuoridorController.getTile(1, 1);
		new WallMove(0, 0, black, target, g, Direction.Vertical, wall);
		gp.addBlackWallsOnBoard(wall);

		// Now Iterate through possible wall moves to find ideal
		for (int i = 1; i < 9; i++) {
			for (int j = 1; j < 9; j++) {

				target = QuoridorController.getTile(i, j);
				wall.getMove().setTargetTile(target);
				PathAndMove pathMove = new PathAndMove(null, null, null, false);

				// add vertical
				wall.getMove().setWallDirection(Direction.Vertical);
				if (QuoridorController.validatePosition() && QuoridorController.validatePath()[0] == true
						&& QuoridorController.validatePath()[1] == true) {
					int newWhiteMoveOptions = QuoridorController.getAdjTiles(white).size();
					if (newWhiteMoveOptions < whiteMoveOptions) {
						pathMove.setReducesOptions(true);
					}
					pathMove.setWhitePath(QuoridorController.getPath(white));
					pathMove.setBlackPath(QuoridorController.getPath(black));
					pathMove.setMove(
							new WallMoveTO(target.getRow(), target.getColumn(), wall.getMove().getWallDirection()));
					pathsToCheck.add(pathMove);
				}
				// add horizontal
				wall.getMove().setWallDirection(Direction.Horizontal);
				if (QuoridorController.validatePosition() && QuoridorController.validatePath()[0] == true
						&& QuoridorController.validatePath()[1] == true) {
					int newWhiteMoveOptions = QuoridorController.getAdjTiles(white).size();
					if (newWhiteMoveOptions < whiteMoveOptions) {
						pathMove.setReducesOptions(true);
					}
					pathMove.setWhitePath(QuoridorController.getPath(white));
					pathMove.setBlackPath(QuoridorController.getPath(black));
					pathMove.setMove(
							new WallMoveTO(target.getRow(), target.getColumn(), wall.getMove().getWallDirection()));
					pathsToCheck.add(pathMove);
				}
			}
		}
		gp.removeBlackWallsOnBoard(wall);
		wall.getMove().delete();

		return pathsToCheck;
	}

	/**
	 * Gets the best wall move for black
	 * 
	 * @return
	 */
	public static WallMoveTO getBestWallMove() {
		List<PathAndMove> pathsToCheck = getPossibleWallMoves();
		int bestWhiteDelta = 0;
		int bestRowDelta = 10;
		PathAndMove bestPathMove = null;
		boolean betterMove = false;
		boolean betterMove1 = false;
		for (PathAndMove pathMove : pathsToCheck) {
			if (pathMove.getWhitePath() != null) {
				int whiteLength = pathMove.getWhitePath().size();
				TileTO newNextWhiteTIle = pathMove.getWhitePath().get(1);
				boolean b = (!nextWhiteTile.equals(newNextWhiteTIle));

				int blackLength = pathMove.getBlackPath().size();
				int whiteDelta = whiteLength - currentWhitePathLength;
				int blackDelta = blackLength - currentBlackPathLength;
				int rowDelta = pathMove.getWhitePath().get(0).getRow() - pathMove.getMove().getRow();

				// longer white path, whites path was extended more than blacks
				if (whiteDelta > bestWhiteDelta && blackDelta < whiteDelta) {
					bestPathMove = pathMove;
					bestWhiteDelta = whiteDelta;
				}
				// Same as previous longest path, whites path was extended more than blacks
				else if (whiteDelta >= bestWhiteDelta && blackDelta < whiteDelta && pathMove.getReducesOptions()
						&& !betterMove && whiteDelta != 0) {
					if (b) {
						betterMove = true;
					}
					betterMove1 = true;
					bestPathMove = pathMove;
					bestWhiteDelta = whiteDelta;
				}
				// longer white path, paths were extented the same amount
				else if (whiteDelta > bestWhiteDelta && blackDelta <= whiteDelta  && whiteDelta != 0) {
					bestPathMove = pathMove;
					bestWhiteDelta = whiteDelta;
				}
				// Same as previous longest path, paths were extented the same amount
				else if (whiteDelta >= bestWhiteDelta && blackDelta <= whiteDelta && pathMove.getReducesOptions()
						&& !betterMove  && whiteDelta != 0) {
					if (b) {
						betterMove = true;
					}
					betterMove1 = true;
					bestPathMove = pathMove;
					bestWhiteDelta = whiteDelta;
				} else if (whiteDelta >= bestWhiteDelta && blackDelta < whiteDelta && !betterMove1
						&& rowDelta < bestRowDelta  && whiteDelta != 0) {
					bestPathMove = pathMove;
					bestWhiteDelta = whiteDelta;
					bestRowDelta = rowDelta;
				} else if (whiteDelta >= bestWhiteDelta && blackDelta <= whiteDelta && !betterMove1
						&& rowDelta < bestRowDelta  && whiteDelta != 0) {
					bestPathMove = pathMove;
					bestWhiteDelta = whiteDelta;
					bestRowDelta = rowDelta;
				}
			}
		}
		if (bestPathMove == null) {
			return null;
		}
		return bestPathMove.getMove();
	}
}
