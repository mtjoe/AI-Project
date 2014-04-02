package FenceMasterBoard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Marisa Tjoe (566322) & Erlangga Satria Gama (570748)
 *
 */
public class LoopCheck implements CheckLogic {
	// Board that needs loop-checking
	Board b; 
	
	// Keeps track of the visited Positions
	ArrayList<Position> visited; 
	
	// Keeps track of the current path taken by the graph searching algorithm
	LinkedList<Position> currentPath;
	
	// The player currently being checked
	Player currentPlayer; 

	/* PUBLIC CONSTRUCTOR */

	public LoopCheck(Board b) {
		this.b = b;
	}

	/* MAIN METHODS */
	
	/**
	 * @return the winning Player is there is one, null if there is no winner
	 */
	public Player check() {
		visited = new ArrayList<Position>();
		currentPath = new LinkedList<Position>();

		// Loop through each player
		for (Player p : b.getPlayers()) {
			currentPlayer = p;
			
			// Loop through each position that the player is occupying
			for (Position pos : p.positions) {
				if (!visited.contains(pos)) {
					if (DFS(pos, null) == true) {
						return p;
					}
				}
			}
		}
		return null;
	}

	/* HELPER METHODS */
	
	/**
	 * Recursive algorithm that will search through the board (graph form),
	 * while checking for loops and their validity
	 * 
	 * @return true if there is a loop in the board, false otherwise
	 */
	private boolean DFS(Position pos, Position prev) {
		visited.add(pos);
		currentPath.addLast(pos);

		// Loop through all the other neighbors of pos
		for (Position neighbor : pos.getSameNeighbors(prev)) {

			// If the current position has a neighbor that was visited before,
			// might be a loop and minimum of 5 Positions to form a valid loop
			if (visited.contains(neighbor)) {
				if ((currentPath.contains(neighbor)) && ((currentPath.indexOf(pos) - currentPath
						.indexOf(neighbor)) >= 5)) {
					
					// Make a List of Positions, containing all the
					// Positions in the suspected loop
					List<Position> currentLoop = currentPath.subList(
							currentPath.indexOf(neighbor) + 1,
							currentPath.indexOf(pos));

					// Checks whether center Position(s) is/are either owned
					// by a different player or is/are empty. If it is,
					// return true
					if (centerDifferent(currentLoop)) {
						return true;
					}
					
				}
			} else {
				// If neighbor is already visited, apply DFS to it
				if (DFS(neighbor, pos)) {
					return true;
				}
			}
		}

		currentPath.remove(pos);
		return false;
	}

	/**
	 * Given List of Positions that forms a loop, checks whether the center of
	 * the loop is either empty or owned by a different player
	 * 
	 * @return true if center of currentLoop contains either an empty Position
	 *         or a Position owned by a different player
	 */
	private boolean centerDifferent(List<Position> currentLoop) {

		// rowCurrentPath is a HashMap representation of the currentLoop, which
		// will map the x coordinates to a LinkedList containing the y
		// coordinates of all the Positions in the currentLoop
		HashMap<Integer, LinkedList<Integer>> rowCurrentPath = new HashMap<Integer, LinkedList<Integer>>();

		int minX = (2 * this.b.getN()) - 1;
		int maxX = 0;

		for (Position pos : currentLoop) {

			// Set up minX and maxX
			if (pos.getX() < minX) {
				minX = pos.getX();
			}

			if (pos.getX() > maxX) {
				maxX = pos.getX();
			}

			// Set up rowCurrentPath
			if (rowCurrentPath.containsKey(pos.getX())) {
				if (pos.getY() < rowCurrentPath.get(pos.getX()).getFirst()) {
					rowCurrentPath.get(pos.getX()).addFirst(pos.getY());
				} else {
					rowCurrentPath.get(pos.getX()).addLast(pos.getY());
				}
			} else {
				LinkedList<Integer> yPos = new LinkedList<Integer>();
				yPos.add(pos.getY());
				rowCurrentPath.put(pos.getX(), yPos);
			}
		}

		// Loop per row
		for (int currRow : rowCurrentPath.keySet()) {

			// Exclude checking of the upper-most and lower-most rows
			if ((currRow != minX) && (currRow != maxX)) {

				// Return true if there is a gap in between the leftmost
				// Position and the next loop Position in the row, and the gap
				// is either empty or owned by another player
				Position currColPos = this.b.getPosition(currRow,
						rowCurrentPath.get(currRow).getFirst()).getNeighborInDir("E");

				while (!(rowCurrentPath.get(currRow)
						.contains(currColPos.getY()))) {
					if (currColPos.isEmpty()
							|| !currColPos.getOwner()
									.equals(this.currentPlayer)) {
						return true;
					}
					currColPos = currColPos.getNeighborInDir("E");
				}
			}
		}
		return false;
	}
}
