package FenceMasterBoard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;


public class TripodCheck {
	Board b;
	
	ArrayList<Position> visited = new ArrayList<Position>();
	LinkedList<Position> queue = new LinkedList<Position>();
	HashMap<String, ArrayList<Position>> startingPoints = new HashMap<String, ArrayList<Position>>();
	ArrayList<String> visitedEdge;
	int visitedpos = 0;
	int queuepos = 0;
	int tripod = 1;
	int minimTripod = 3;
	
	public TripodCheck(Board b){
		this.b = b;
		visitedEdge = new ArrayList<String>();
	}

	private Position checkCurrent(Position neighbors, Position current) {
		
		if (!this.visited.contains(neighbors)) {

			this.visited.add(neighbors);

			/*if the node is the edge and non-corner position*/
			if (neighbors.isEdge && neighbors.isNonCorner) {
				
				/*check if it is in the same edge as the starting point*/
				if (this.visitedEdge.contains(neighbors.getWhichEdge())) {
					
					//get back to the last point in the queue and remove it
					if (this.queue.size() > 0) {
						current = this.queue.getLast();
						return current;

					} else {
						return null;
					}
					
					
				/*if not, then it is one of the goal node*/
				} else {

					//add the number of tripod found and put it as visited
					this.tripod++;
					this.visited.add(neighbors);
					this.visitedEdge.add(neighbors.getWhichEdge());
					if (this.tripod == 3) {
						return current;
					
					/*already find one goal, back to the last point when 
					 * the node has more than one neighbors to find other
					 * goal
					 */
					} else {
						
						//get back to the last point in the queue
						if (this.queue.size() > 0) {
							current = this.queue.getLast();
							return current;

						} else {
							return null;
						}
					}
				}
			

			/*if not the edge and non-corner, then explore this neighbor*/
			} else {
				return neighbors;
			} 
		}
		return null;
	}
	
	/**
	 * @return True if there is tripod, false if not 
	 */
	private boolean runDFS(Position pos) {
		
		Position current = pos;
		Position currentBe;
		visited.add(current);

		/*keep looping when there is something to read*/
		while (current != null) {
			
			ArrayList<Position> neighborsArray = current.getSameNeighbors(null);
			
			if (neighborsArray.size() < 2) {
				currentBe = checkCurrent(neighborsArray.get(0), current);
			
			} else {			
				
				if (!this.queue.contains(current)) {
					this.queue.addLast(current);
				}

				for (Position neighbors: neighborsArray) {

					/*remove it from the neighbor list in order to avoid the 
					 * double reading from this position
					 */
					neighborsArray.remove(neighbors);
					
					currentBe = checkCurrent(neighbors, current);

				}
			}

			if (currentBe == queue.getLast()) {
				this.queue.remove(current);
			
			} else if (currentBe == current) {
				return true;
			}
			current = currentBe;
		}

		return false;
	}
	
	/**
	 * @return The Player that wins the game with Tripod, or null if no winner is found
	 */
	public Player check() {	
		
		/*Loop through each player in the board*/
		for (Player player:b.getPlayers()) {
			startingPoints = player.startingPoints;
			tripod = 1;
				
			/*Loop through each edge non-corner side in the board*/
			for (Entry<String, ArrayList<Position>> whichEdge:startingPoints.entrySet()) {
				
				this.visitedEdge.clear();			
				this.visitedEdge.add(whichEdge.getKey());

				/*check if there is any player in the current side*/
				if (whichEdge.getValue().size() > 0) {

					/*get the position in the edge non-corner of the player*/
					for (Position pos:whichEdge.getValue()) {

						/*if not yet visited, run the search to explore the nodes*/
						if (!this.visited.contains(pos)) {
							if (runDFS(pos)) {
								return player;
					
							}
						}		
					}
				}
			} 
		}
		return null;
	}
}

