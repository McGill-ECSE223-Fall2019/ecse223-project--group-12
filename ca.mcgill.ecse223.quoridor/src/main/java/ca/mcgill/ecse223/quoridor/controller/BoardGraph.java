package ca.mcgill.ecse223.quoridor.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.model.WallMove;
/**
 * 
 * @author Remi Carriere
 *
 */
public class BoardGraph {
	private final int tileNodes = 81; // No. of tile nodes/tiles

	private LinkedList<Integer> adj[]; // Adjacency Lists

	// Constructor
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
				int currentTile = getTileIndex(i, j);
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

	/**
	 * Removes all edges based on current walls
	 */
	public void syncEdges() {
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
			int row = wall.getMove().getTargetTile().getRow();
			int col = wall.getMove().getTargetTile().getColumn();
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
				int adjTile = adjTiles.next();
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
}
