package ca.mcgill.ecse223.quoridor.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.model.WallMove;

/**
 *
 * @author Remi Carriere
 *
 */
public class BoardGraph {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	private final int tileNodes = 81; // No. of tile nodes/tiles
	private LinkedList<Integer> adj[]; // Adjacency Lists

	// ------------------------
	// CONSTRUCTOR
	// ------------------------
	/**
	 * Creates A BoardGraph object of 81 tiles with default edges
	 */
	@SuppressWarnings("unchecked")
	public BoardGraph() {
		adj = new LinkedList[tileNodes];
		// First, Create all the nodes
		for (int i = 0; i < tileNodes; i++) {
			adj[i] = new LinkedList<Integer>();
		}
		// Then, create all the edges
		for (int i = 1; i < 10; i++) {
			for (int j = 1; j < 10; j++) {
				Integer currentTile = getTileIndex(i, j);
				// get the indices of adjacent tiles
				Integer northTile = getTileIndex(i - 1, j);
				Integer eastTile = getTileIndex(i, j + 1);
				Integer southTile = getTileIndex(i + 1, j);
				Integer westTile = getTileIndex(i, j - 1);
				// add tiles to the adjacency list of current tile (tiles will be null for
				// edge cases like borders and corners)
				if (northTile != null) {
					adj[currentTile].add(northTile);
				}
				if (eastTile != null) {
					adj[currentTile].add(eastTile);
				}
				if (southTile != null) {
					adj[currentTile].add(southTile);
				}
				if (westTile != null) {
					adj[currentTile].add(westTile);
				}
			}
		}
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	/**
	 * Removes all edges based on current walls, after calling this method on the
	 * graph, we can find possible paths
	 */
	public void syncWallEdges() {
		GamePosition gamePosition = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		// Create a list of all walls
		List<Wall> boardWalls = new ArrayList<Wall>();
		List<Wall> whitewalls = gamePosition.getWhiteWallsOnBoard();
		List<Wall> blackwalls = gamePosition.getBlackWallsOnBoard();
		boardWalls.addAll(blackwalls);
		boardWalls.addAll(whitewalls);
		// Add the current wall move candidate to the list
		WallMove candidateWallMove = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate();
		if (candidateWallMove != null) {
			Wall candidateWall = candidateWallMove.getWallPlaced();
			boardWalls.add(candidateWall);
		}
		// Iterate through current walls and remove edges, each wall removes two edges
		for (Wall wall : boardWalls) {
			Integer row = wall.getMove().getTargetTile().getRow();
			Integer col = wall.getMove().getTargetTile().getColumn();
			Direction direction = wall.getMove().getWallDirection();
			Integer wallTile1 = null;
			Integer wallTile2 = null;
			Integer blockedTile1 = null;
			Integer blockedTile2 = null;
			if (direction == Direction.Vertical) {
				// get the indices of effected nodes
				wallTile1 = getTileIndex(row, col);
				wallTile2 = getTileIndex(row + 1, col);
				blockedTile1 = getTileIndex(row, col + 1); // tiles on the other side of the wall
				blockedTile2 = getTileIndex(row + 1, col + 1);
			} else {
				// get the indices of effected nodes
				wallTile1 = getTileIndex(row, col);
				wallTile2 = getTileIndex(row, col + 1);
				blockedTile1 = getTileIndex(row + 1, col);
				blockedTile2 = getTileIndex(row + 1, col + 1);
			}
			// Now remove the edges between nodes
			adj[wallTile1].remove(blockedTile1);
			adj[wallTile2].remove(blockedTile2);
			adj[blockedTile1].remove(wallTile1);
			adj[blockedTile2].remove(wallTile2);
		}
	}

	/**
	 * Adds all possible jump moves based on current gamePosition as edges in the
	 * graph
	 */
	public void syncJumpMoves() {
		GamePosition gamePosition = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		int wRow = gamePosition.getWhitePosition().getTile().getRow();
		int wCol = gamePosition.getWhitePosition().getTile().getColumn();
		int bRow = gamePosition.getBlackPosition().getTile().getRow();
		int bCol = gamePosition.getBlackPosition().getTile().getColumn();

		Integer wIndex = getTileIndex(wRow, wCol);
		Integer bIndex = getTileIndex(bRow, bCol);
		// every legal jump move is an illegal step move

		// reset edges to check if pawns are adjacent
		resetEdges();
		// Check if opponent is adjacent (i.e. a jump move is possible)
		if (!adj[wIndex].contains(bIndex)) {
			syncStepMoves();
			syncWallEdges();
			return; // No jump moves possible
		}
		syncAllJumpMoves();
		syncStepMoves();
		syncWallEdges();
	}

	/**
	 * Removes illegal step moves (Player overlap)
	 */
	public void syncStepMoves() {
		GamePosition gamePosition = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		Tile whiteTile = gamePosition.getWhitePosition().getTile();
		Tile blackTile = gamePosition.getBlackPosition().getTile();
		Integer wIndex = getTileIndex(whiteTile.getRow(), whiteTile.getColumn());
		Integer bIndex = getTileIndex(blackTile.getRow(), blackTile.getColumn());
		// check if the pawns are adjacent to each other
		if (adj[wIndex].contains(bIndex)) { // Not going to work with diagonal jumps
			adj[wIndex].remove(bIndex);
			adj[bIndex].remove(wIndex);
		}
	}

	/**
	 * Verifies if a step move or jump move is possible between two coordinates
	 *
	 * @param row
	 *            Row of starting tile
	 * @param col
	 *            Column of starting tile
	 * @param adjRow
	 *            Row of tile to move to
	 * @param adjCol
	 *            Column of tile to move to
	 * @return True if the move is possible, false otherwise
	 */
	public boolean isAdjacent(int row, int col, int adjRow, int adjCol) {
		int tile = getTileIndex(row, col);
		Integer adjTile = getTileIndex(adjRow, adjCol);
		if (adj[tile].contains(adjTile)) {
			return true;
		}
		return false;
	}

	/**
	 * Returns a list of adjacent tiles (See QuriodorController.getAdjTiles())
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public LinkedList<Integer> getAdjacentNodes(int row, int col) {
		int tileIndex = getTileIndex(row, col);
		return adj[tileIndex];
	}

	/**
	 * Checks if a path exists to the destination
	 *
	 * @param startRow
	 *            row position of player
	 * @param startCol
	 *            row position of player
	 * @param destination
	 *            the winning position for player (1 or 9 for two player)
	 * @return
	 */
	public boolean pathExists(int startRow, int startCol, int destination) {
		int currentNode = getTileIndex(startRow, startCol);
		// Mark the tile nodes as unvisited (initialized to false)
		boolean visited[] = new boolean[tileNodes];
		// Create a queue for BFS
		LinkedList<Integer> queue = new LinkedList<Integer>();
		// Mark the source tile node as visited and add it to queue
		visited[currentNode] = true;
		queue.add(currentNode);
		while (queue.size() != 0) {
			// Dequeue a tile node
			currentNode = queue.poll();
			// Check if the source tile node is a winning position
			for (int i = 1; i < 10; i++) {
				if (currentNode == getTileIndex(destination, i)) {
					return true;
				}
			}
			// iterate through adjacent tiles at source tile node
			Iterator<Integer> adjTiles = adj[currentNode].listIterator();
			while (adjTiles.hasNext()) {
				Integer adjTile = adjTiles.next();
				// visit the tile node and add it to the queue if it has not been visited
				if (!visited[adjTile]) {
					visited[adjTile] = true;
					queue.add(adjTile);
				}
			}
		}
		// no path was found, return false
		return false;
	}

	// ------------------------
	// PRIVATE HELPER METHODS
	// ------------------------

	/**
	 * Gets the index of specified tile
	 *
	 * @param row
	 *            Integer from 1 to 9
	 * @param col
	 *            Integer from 1 to 9
	 * @return The index of the tile (Integer from 0 to 80). (1,1) = 0, (2,1) = 9,
	 *         (9,9)=80
	 */
	private Integer getTileIndex(int row, int col) {
		if (row > 0 && col > 0 && row < 10 && col < 10) {
			return ((row - 1) * 9 + col - 1);
		}
		return null;
	}

	/**
	 * Syncs the graph edges all possible jump moves
	 */
	private void syncAllJumpMoves() {
		GamePosition gamePosition = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		int wRow = gamePosition.getWhitePosition().getTile().getRow();
		int wCol = gamePosition.getWhitePosition().getTile().getColumn();
		int bRow = gamePosition.getBlackPosition().getTile().getRow();
		int bCol = gamePosition.getBlackPosition().getTile().getColumn();

		Integer wIndex = getTileIndex(wRow, wCol);
		Integer bIndex = getTileIndex(bRow, bCol);
		syncWallEdges();
		// north for white / south for black
		if (wRow > bRow && wCol == bCol) {
			// north jump for white
			Integer target = getTileIndex(bRow - 1, wCol);
			// check no walls between white and black, and between black and target
			if (adj[wIndex].contains(bIndex) && adj[bIndex].contains(target) && target != null) {
				adj[wIndex].add(target);
			}
			// regular jump move is blocked by one or more walls, find diagonal jumps
			else {
				// white northwest
				target = getTileIndex(bRow, wCol - 1);
				addDiagonalJumpMove(wIndex, target, bIndex);
				// white northEast
				target = getTileIndex(bRow, wCol + 1);
				addDiagonalJumpMove(wIndex, target, bIndex);
			}

			// south jump for black
			target = getTileIndex(wRow + 1, wCol);
			if (adj[bIndex].contains(wIndex) && adj[wIndex].contains(target) && target != null) {
				adj[bIndex].add(target);
			}
			// regular jump move is blocked by one or more walls, find diagonal jumps
			else {
				// black southwest
				target = getTileIndex(wRow, wCol - 1);
				addDiagonalJumpMove(bIndex, target, wIndex);
				// Black southEast
				target = getTileIndex(wRow, wCol + 1);
				addDiagonalJumpMove(bIndex, target, wIndex);
			}
		}
		// north for black / south for white
		else if (wRow < bRow && wCol == bCol) {
			// south jump for white
			Integer target = getTileIndex(bRow + 1, wCol);
			// check no walls between white and black, and between black and target
			if (adj[wIndex].contains(bIndex) && adj[bIndex].contains(target) && target != null) {
				adj[wIndex].add(target);
			} else {
				// white southwest
				target = getTileIndex(bRow, wCol - 1);
				addDiagonalJumpMove(wIndex, target, bIndex);
				// white southEast
				target = getTileIndex(bRow, wCol + 1);
				addDiagonalJumpMove(wIndex, target, bIndex);
			}
			// north jump for black
			target = getTileIndex(wRow - 1, wCol);
			if (adj[bIndex].contains(wIndex) && adj[wIndex].contains(target) && target != null) {
				adj[bIndex].add(target);
			}
			// regular jump move is blocked by one or more walls, find diagonal jumps
			else {
				// black Northwest
				target = getTileIndex(wRow, wCol - 1);
				addDiagonalJumpMove(bIndex, target, wIndex);
				// Black northEast
				target = getTileIndex(wRow, wCol + 1);
				addDiagonalJumpMove(bIndex, target, wIndex);
			}
		}
		// east for white and west for black
		else if (wCol < bCol && wRow == bRow) {
			// east jump for white
			Integer target = getTileIndex(bRow, bCol + 1);
			// check no walls between white and black, and between black and target
			if (adj[wIndex].contains(bIndex) && adj[bIndex].contains(target) && target != null) {
				adj[wIndex].add(target);
			}
			// regular jump move is blocked by one or more walls, find diagonal jumps
			else {
				// white southEast
				target = getTileIndex(bRow + 1, bCol);
				addDiagonalJumpMove(wIndex, target, bIndex);
				// white northEast
				target = getTileIndex(bRow - 1, bCol);
				addDiagonalJumpMove(wIndex, target, bIndex);
			}
			// west jump for black
			target = getTileIndex(wRow, wCol - 1);
			if (adj[bIndex].contains(wIndex) && adj[wIndex].contains(target) && target != null) {
				adj[bIndex].add(target);
			}
			// regular jump move is blocked by one or more walls, find diagonal jumps
			else {
				// black northEast
				target = getTileIndex(wRow + 1, wCol);
				addDiagonalJumpMove(bIndex, target, wIndex);
				// black southEast
				target = getTileIndex(wRow - 1, wCol);
				addDiagonalJumpMove(bIndex, target, wIndex);
			}

		}
		// east for black and west for white
		else if (wCol > bCol && wRow == bRow) {
			// east jump for white
			Integer target = getTileIndex(bRow, bCol - 1);
			// check no walls between white and black, and between black and target
			if (adj[wIndex].contains(bIndex) && adj[bIndex].contains(target) && target != null) {
				adj[wIndex].add(target);
			}
			// regular jump move is blocked by one or more walls, find diagonal jumps
			else {
				// white southWest
				target = getTileIndex(bRow + 1, bCol);
				addDiagonalJumpMove(wIndex, target, bIndex);
				// white northWest
				target = getTileIndex(bRow - 1, bCol);
				addDiagonalJumpMove(wIndex, target, bIndex);
			}
			// west jump for black
			target = getTileIndex(wRow, wCol + 1);
			if (adj[bIndex].contains(wIndex) && adj[wIndex].contains(target) && target != null) {
				adj[bIndex].add(target);
			}
			// regular jump move is blocked by one or more walls, find diagonal jumps
			else {
				// black northWest
				target = getTileIndex(wRow + 1, wCol);
				addDiagonalJumpMove(bIndex, target, wIndex);
				// black southWest
				target = getTileIndex(wRow - 1, wCol);
				addDiagonalJumpMove(bIndex, target, wIndex);
			}
		}
	}

	/**
	 * Adds possible jump moves for specified player and target.
	 * 
	 * @param playerIndex
	 *            position of player
	 * @param target
	 *            position of target
	 * @param opponentIndex
	 *            position of opponent
	 */
	private void addDiagonalJumpMove(Integer playerIndex, Integer target, Integer opponentIndex) {
		// L shaped path to target through the opponent
		if (target !=null && adj[playerIndex].contains(opponentIndex) && adj[target].contains(opponentIndex)) {
			adj[playerIndex].add(target);
		}
	}

	/**
	 * Resets all the edges of the board to default
	 */
	private void resetEdges() {
		// Then, create all the edges
		for (int i = 1; i < 10; i++) {
			for (int j = 1; j < 10; j++) {
				Integer currentTile = getTileIndex(i, j);
				// get the indices of adjacent tiles
				Integer northTile = getTileIndex(i - 1, j);
				Integer eastTile = getTileIndex(i, j + 1);
				Integer southTile = getTileIndex(i + 1, j);
				Integer westTile = getTileIndex(i, j - 1);

				adj[currentTile] = new LinkedList<Integer>();
				;
				// add tiles to the adjacency list of current tile (tiles will be null for
				// edge cases like borders and corners)
				if (northTile != null) {
					adj[currentTile].add(northTile);
				}
				if (eastTile != null) {
					adj[currentTile].add(eastTile);
				}
				if (southTile != null) {
					adj[currentTile].add(southTile);
				}
				if (westTile != null) {
					adj[currentTile].add(westTile);
				}
			}
		}
	}
}