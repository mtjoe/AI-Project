package FenceMasterBoard;

import java.util.ArrayList;
import java.util.HashMap;

public class LoopCheck implements CheckLogic{
	Board b;
	ArrayList<Position> visited;
	HashMap<Position, Integer> depth;
	ArrayList<Position> currentPath;
	Player currentPlayer;
	
	public LoopCheck(Board b){
		this.b = b;
	}
	
	public Player check(){
		visited = new ArrayList<Position>();
		depth = new HashMap<Position, Integer>();
		currentPath = new ArrayList<Position>();
		
		// Loop through each player
		for (Player p: b.getPlayers()){
			currentPlayer = p;
			// Loop through each position that the player is occupying
			for (Position pos: p.positions){
				if (!visited.contains(pos)){
					if (DFS(pos, null) == true){
						return p;
					}
				}
			}
		}
		
		return null;
	}
	
	private boolean DFS(Position pos, Position prev){
		visited.add(pos);
		currentPath.add(pos);
		if (prev == null){
			depth.put(pos, 0);
		} else {
			depth.put(pos, depth.get(prev) + 1);
		}
			
		// Loop through all the other neighbors of pos
		for (Position neighbor:pos.getSameNeighbors(prev)){
			if (visited.contains(neighbor)){
				if (depth.get(pos) >= 5){
					if (centerDifferent()){
						return true;
					}
				}
			} else {
				if (DFS(neighbor, pos)){
					return true;
				}
			}
		}
		currentPath.remove(pos);
		return false;
	}
	
	private boolean centerDifferent(){
		for (Position pos: this.getLoopCenterPos()){
			if (pos.getOwner().equals(this.currentPlayer)){
				return true;
			}
		}
		
		return false;
	}
	
	private ArrayList<Position> getLoopCenterPos(){
		Position topLeft = null;
		
		for (Position pos: this.currentPath){
			// Update topLeft
			if (topLeft == null){
				topLeft = pos;
			} else {
				if (pos.getX() < topLeft.getX()){
					topLeft = pos;
				} else if (pos.getX() ==  topLeft.getX()){
					if (pos.getY() < topLeft.getY()){
						topLeft = pos;
					}
				}
			}
		}
		return null;
	}
}


