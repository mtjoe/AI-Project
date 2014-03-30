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
	
	/**
	 * @return True if there is tripod, false if not 
	 */
	private boolean runDFS(Position pos, Player p) {
		
		Position current = pos;
		visited.add(current);
		
		/*keep looping when there is something to read*/
		while (current != null) {
			ArrayList<Position> neighborsArray = current.getSameNeighbors(null);
			
			/*if the neighbor is only one, put it as visited then explore
			 * it
			 */
			if (neighborsArray.size() == 1) {
				current = neighborsArray.get(0);
				visited.add(current);
				continue;
					
			} else {
				
				/*read each player in the neighbors*/
				for (Position neighbors: neighborsArray) {
					
					/*remove it from the neighbor list in order to avoid the 
					 * double reading from this position
					 */
					neighborsArray.remove(neighbors);
					
					/*if it hasn't been visited before, mark it as visited then 
					 * explore this neighbor
					 */
					if (!visited.contains(neighbors)) {
						
						/*if the node is the edge and non-corner position*/
						if (neighbors.isEdge && neighbors.isNonCorner) {
							
							/*check if it is in the same edge as the starting point*/
							if (!visitedEdge.contains(neighbors.getWhichEdge())) {
								
								//get back to the last point in the queue and remove it
								current = queue.getLast();
								queue.remove(current);
								break;
							
							/*if not, then it is one of the goal node*/
							} else {
								
								//add the number of tripod found and put it as visited
								tripod++;
								visited.add(neighbors);
								visitedEdge.add(neighbors.getWhichEdge());
								if (tripod == 3) {
									current = null;
									return true;
								
								/*already find one goal, back to the last point when 
								 * the node has more than one neighbors to find other
								 * goal
								 */
								} else {
									current = queue.getLast();
									queue.remove(current);
									break;
								}
							}
							
						/*if not the edge and non-corner, then put it in the queue, 
						 * then explore this neighbor
						 */
						} else {
							queue.addLast(current);
							current = neighbors;
							break;
						} 
					}
				}	
			}
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
			
			/*Check if there are at least 3 position of the player in the edge and 
			 * non-corner position in order to check the possibility of tripod
			 */
			if (startingPoints.size() >= minimTripod) {
				
				/*Loop through each edge non-corner side in the board*/
				for (Entry<String, ArrayList<Position>> whichEdge:startingPoints.entrySet()) {
					visitedEdge.clear();	
					
					/*get the position in the edge non-corner of the player*/
					for (Position pos:whichEdge.getValue()) {	
						
						/*if not yet visited, run the search to explore the nodes*/
						if (!visited.contains(pos) && !visitedEdge.contains(whichEdge.getKey())) {
							if (runDFS(pos, player)) {
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

