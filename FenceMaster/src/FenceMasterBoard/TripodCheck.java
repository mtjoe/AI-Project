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
	* @return the last element in queue, or null if there is nothing in queue
	*/
	private Position getCurrentFromQueue() {
		Position current;

		if (this.queue.size() > 0) {
			current = this.queue.getLast();
			//this.queue.remove(current);
			return current;

		} else {
			return null;
		}
	}
	/*
	private void printQueue() {
		if (this.queue.size() > 0) {
			System.out.println(" queue : ");
			for (Position pos:this.queue) {
				System.out.println("(" + pos.getX() + ", " + pos.getY() +  ") ; ");
			}
		}
	}*/

	/**
	* @return current, if the neighbors is already visited before. Otherwise, return 
	* the neighbors node for further exploration in runDFS function. Return null, 
	* if the goal state is found and there is nothing in queue or tripod is found.
	*/
	private Position checkCurrent(Position neighbors, Position current) {
		Position result = current;

		/*if not yet visited, mark it as visited*/
		if (!this.visited.contains(neighbors)) {
			//System.out.println("add neighbors to visited" + neighbors.getX() + " " + neighbors.getY());
			this.visited.add(neighbors);

			/*check if the neighbor is the edge non-corner*/
			if (neighbors.isEdge && neighbors.isNonCorner) {
				
				/*if it is in the same other side of the board, check the queue*/
				if (this.visitedEdge.contains(neighbors.getWhichEdge())) {
					
					/*if current is the edge, then explore the neighbor to 
					*find other neighbors from this point
					*/
					if (current.isEdge) {
						result = neighbors;
					
					/*if current is not at the edge, then find the last 
					*position from the queue
					*/	
					} else {
						result =  getCurrentFromQueue();
					}
				
				/*if not, then it must be the goal state*/	
				} else {
					this.tripod++;
					this.visitedEdge.add(neighbors.getWhichEdge());
					
					/*no node to check*/	
					if (this.tripod == 3) {
						result =  null;

					/*check the queue*/
					} else {	
						result =  getCurrentFromQueue();
					}
				}

			/*if it is not the edge, return it for further exploration*/	
			} else {
				//System.out.println("return neighbors: " + neighbors.getX() + " " + neighbors.getY());
				result =  neighbors;
			}
		}
		/*return current, to find other unvisited node*/
		/*if (result != null ){
			System.out.println("return result: " + result.getX() + " " + result.getY());	
		}*/
		
		return result;	
	}
	
	/**
	 * @return True if there is a tripod, false if not 
	 */
	private boolean runDFS(Position pos) {
		
		int numLoop = 0;
		Position before = null;
		Position current = pos;
		Position neighborPoint = null;
		Position currentBe = current;
		

		/*keep looping when there is something to read*/
		while (current != null && this.tripod != 3) {
			
			//printQueue();
			numLoop = 0;
			//System.out.println("current : " + current.getX() + " " + current.getY());
			visited.add(current);
			//System.out.println("add current to visited");
			//System.out.println("tripod : " + this.tripod + "----------");
			/*get the array of the same neighbor*/
			ArrayList<Position> neighborsArray = current.getSameNeighbors(before);
			
			//System.out.println("neighborPoint: " + neighborPoint.getX() + " " + neighborPoint.getY());

			/*if the same neighbor only 1*/
			if (neighborsArray.size() == 1) {
					neighborPoint = neighborsArray.get(0);
					currentBe = checkCurrent(neighborPoint, current);
					/*if (currentBe != null) {
						System.out.println("currentBe 1 : " + currentBe.getX() + " " + currentBe.getY());
					}*/
			/*if the neighbors are more than 1, put the current in queue for later check, then 
			find the unvisited neighbor and explore it*/	
			} else if (neighborsArray.size() > 1){			
				
				if (!this.queue.contains(current)) {
					this.queue.addLast(current);
				} else {
					this.queue.remove(current);
				}
				//printQueue();
				/*find the unvisited neighbor*/
				for (Position neighbors:neighborsArray) {

					currentBe = checkCurrent(neighbors, current);
					/*if (currentBe != null) {
						System.out.println("currentBe: " + currentBe.getX() + " " + currentBe.getY());
					}*/
					numLoop++;
					if (currentBe != current) {
						break;
					
					/*to avoid the cycle of the loop, stop checking when there
					*is no unvisited node
					*/
					} else if (currentBe == current && numLoop == neighborsArray.size()) {
						
						/*if it is in the queue, remove it first*/ 
						if (this.queue.contains(currentBe)) {
							this.queue.remove(currentBe);
						}
						//get from the queue
						currentBe = getCurrentFromQueue();
						//System.out.println("currentBe is null");
						break;
					}	
						
				}
				
			/*if there is no same neighbor, stop*/
			} else if (neighborsArray.size() == 0) {
				//System.out.println("no neighbors");
				break;
			} 
			
			/*store the current node, and move it to the neighbor*/
			before = current;
			//System.out.println("before : " + before.getX() + " " + before.getY()); 
			current = currentBe;
			
			
		} 
		/*if tripod is found, return true*/
		if (this.tripod == 3) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @return The Player that wins the game with Tripod, or null if no winner is found
	 */
	public Player check() {	
		
		/*Loop through each player in the board*/
		for (Player player:b.getPlayers()) {
			startingPoints = player.startingPoints;
			tripod = 1;
			this.visitedEdge.clear();	
			/*Loop through each edge non-corner side in the board*/
			for (Entry<String, ArrayList<Position>> whichEdge:startingPoints.entrySet()) {
				
				if (!this.visitedEdge.contains(whichEdge.getKey())) {
					this.visitedEdge.add(whichEdge.getKey());

					/*check if there is any player in the current side*/
					if (whichEdge.getValue().size() > 0) {
						
						/*get the position in the edge non-corner of the player*/
						for (Position pos:whichEdge.getValue()) {
							System.out.println("switch! " + pos.getX() + " " + pos.getY());
							
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
		}
		return null;
	}
}

