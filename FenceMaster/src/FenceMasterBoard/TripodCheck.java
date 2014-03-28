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
	
	public TripodCheck(Board b){
		this.b = b;
		visitedEdge = new ArrayList<String>();
	}
	
	// Loop through each player
	public Player check(){
		for (Player player: b.getPlayers()) {
			startingPoints = player.startingPoints;
			int tripod = 1;
			
			// Loop through each edge and non-corner positions that the players is occupying
			for (Entry<String, ArrayList<Position>> whichEdge:startingPoints.entrySet()) {
				for (Position pos:whichEdge.getValue()) {
					
					visitedEdge.clear();
					
					if (!visited.contains(pos)) {
						visitedEdge.add(whichEdge.getKey());
						Position current = pos;
						visited.add(current);
						
						// DFS
						while (current != null) {
							
							// Loop through neighboring positions that are occupied by the current player
							for (Position neighbor:current.getSameNeighbors(null)) {
							
								// Skip if already visited
								if (!visited.contains(neighbor)) {
									
									if (visitedEdge.contains(neighbor)) {
										continue;
									}
									
									if (neighbor.isEdge && neighbor.isNonCorner) {
										visited.add(visitedpos++, neighbor);
										tripod++;
										
										if (tripod == 3) {
											return player;
										}
										current = queue.get(queue.size()-1);
										break;
									}
									
									visited.add(visitedpos++, neighbor);
									queue.addLast(neighbor);
									current = neighbor;
								}  
							}
						}
						if (queue.size() == 0) {
							break;
						
						}
					}
				}
			}
			
		}
		return null;
	}
}
