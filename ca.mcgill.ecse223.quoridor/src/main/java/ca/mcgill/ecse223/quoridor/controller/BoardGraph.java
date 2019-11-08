package ca.mcgill.ecse223.quoridor.controller;

import java.util.Iterator;
import java.util.LinkedList;

public class BoardGraph {
	private final int tileNodes = 81; // No. of tile nodes/tiles

	private LinkedList<Integer> adj[]; // Adjacency Lists

	// Constructor
	@SuppressWarnings("unchecked")
	public BoardGraph() {

		adj = new LinkedList[tileNodes];
		for (int i = 0; i < tileNodes; i++) {
			adj[i] = new LinkedList<Integer>();
			// add north, east, south and west adjacent tiles
			int northTile = 0; // TODO: calc index of tiles
			int eastTile = 0;
			int southTile = 0;
			int westTile = 0;
			adj[i].add(northTile); // ... add other Tiles
		}
	}

	/**
	 * Removes all edges based on current walls
	 */
	public void syncEdges() {

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

		// TODO: set the possible destination indices
		if (destination == 9) {

		} else if (destination == 1) {

		}

		int source = startRow + startCol; // TODO calculate index
		// Mark the tile nodes as unvisited
		boolean visited[] = new boolean[tileNodes];

		// Create a queue for BFS
		LinkedList<Integer> queue = new LinkedList<Integer>();

		// Mark the source tile node as visited and add it to queue
		visited[source] = true;
		queue.add(source);

		while (queue.size() != 0) {
			// Dequeue a tile node
			source = queue.poll();
			if (source == destination) {
				// TODO: check if we're at the destination properly
				return true;
			}
			// iterate through adjacent tiles at source tile node
			Iterator<Integer> i = adj[source].listIterator();
			while (i.hasNext()) {
				int n = i.next();
				// visit the tile node and add it to the queue if it has not been visited
				if (!visited[n]) {
					visited[n] = true;
					queue.add(n);
				}
			}
		}
		return false;
	}

}
