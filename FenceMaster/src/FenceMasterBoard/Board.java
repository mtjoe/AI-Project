package FenceMasterBoard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class Board {
	private Player[] players; 
	private Position bArray[][];
	private int n;
	private int nRow;
	private int nCol;
	
	
	/**
	 * Initializes the board
	 * @param n
	 */
	public Board(int n, Player[] players){
		this.players = players;
		
		this.n = n;
		
		nRow = (2*n)-1;
		
		bArray = new Position[nRow][];
		
		/* Loop through each row to initialize empty slots */
		
		// Initialize increasing rows (0 -- n) to null
		for (int i=0; i<n; i++) {
			nCol = n + i;
			
			Position[] rowArray = new Position[nCol];
			
			for (int j=0; j<nCol; j++){
				rowArray[j] = new Position(this, i, j); 
			}
			
			bArray[i] = rowArray;
			
		}
		
		// initialize decreasing rows (n+1 -- 2n-1) to null
		
		for (int i=n; i<nRow; i++) {
			nCol = (nRow - i + n - 1);
			
			Position[] rowArray = new Position[nCol];
			
			for (int j=0; j<nCol; j++){
				rowArray[j] = new Position(this, i, j); 
			}
			
			bArray[i] = rowArray;
		}
		
		// Set neighboring positions
		for (Position[] rowPos: this.bArray){
			for (Position pos: rowPos){
				pos.setNeighbors();
			}
		}
		
		return;
	}
	
	/**
	 * Set Poisition of with coordinates (x, y) to be occupied by Player p
	 */
	public void setMove(int x, int y, Player p){
		bArray[x][y].setOccupy(p);
		p.addPosition(this.getPosition(x, y));
		return;
	}
	
	/**
	 * @return n, the size of each side in the board
	 */
	public int getN(){
		return this.n;
	}
	
	/**
	 * @return Position with coordinates (x, y)
	 */
	public Position getPosition(int x, int y){
		// If out of bounds, return null
		if (Position.isValidPosition(n, x, y)){
			return bArray[x][y];
		}
		return null;
	}
	
	public Player[] getPlayers(){
		return this.players;
	}
	
	/**
	 * Checks whether there is a winner in the board.
	 * @return The name of the winner if there is a winner, null if there is no winner
	 */
	public void hasWinner(){
		
		CheckLogic loopCheck = new CheckLogic(this);
		Player p = loopCheck.checkLoop();
		
		if (p != null){
			System.out.println(p.name);
			System.out.println("Loop");
		} else {
			System.out.println("No Winner yet");
		}
		
		
	}
	
	/* HELPER FUNCTIONS */
	
	/**
	 * For testing purposes
	 */
	public void printBoard(){
		for (int i=0; i<this.bArray.length; i++){
			for(int j=0; j<this.bArray[i].length; j++){
				if (this.bArray[i][j].isEmpty()){
					System.out.print("-");
				} else {
					System.out.print(this.bArray[i][j].getOwner().s);
				}
				
				System.out.print(" ");
			}
			System.out.println("");
		}
	}
	
	public boolean isAdjacent(Position pos1, Position pos2){
		if (pos1.getNeighbors().contains(pos2)){
			return true;
		}
		return false;
	}
		
public HashMap<String, ArrayList<Position>> getStartingPoints(Player p, ArrayList<Position> visited) {
		
		HashMap<String, ArrayList<Position>> startingPoints = new HashMap<String, ArrayList<Position>>();
		
		String[] directions = new String[]{"N", "NW", "NE", "S", "SW", "SE"};
		
		for (String dir: directions) {
			startingPoints.put(dir, new ArrayList<Position>());
		}
		
		
		for (Position pos: p.positions) {
			
			if (pos.isEdge && pos.isNonCorner && !visited.contains(pos)) {
				if (pos.getX() == 0){
					startingPoints.get("N").add(pos);
				} else if (pos.getX() == ((2*n) -2)) {
					startingPoints.get("S").add(pos);
				} else if (pos.getY() == 0) {
					if (pos.getX()< (n-1)){
						startingPoints.get("NW").add(pos);
					} else {
						startingPoints.get("SW").add(pos);
					}
				} else {
					if (pos.getX()< (n-1)){
						startingPoints.get("NE").add(pos);
					} else {
						startingPoints.get("SE").add(pos);
					}
				}
				
			}
		}
		
		return startingPoints;
	}
	
	public boolean checkTripod() {
		// TODO
		
		ArrayList<Position> visited = new ArrayList<Position>();
		ArrayList<Position> queue = new ArrayList<Position>();
		HashMap<String, ArrayList<Position>> startingPoints = new HashMap<String, ArrayList<Position>>();
		int visitedpos = 0;
		int queuepos = 0;
		
		for (int i=0; i<2; i++) {
			Player player = players[i];
			startingPoints = getStartingPoints(player, visited);
			int tripod = 1;
			
			for (Entry<String, ArrayList<Position>> whichEdge:startingPoints.entrySet()) {
				
				for (Position pos:whichEdge.getValue()) {
					
					if (!visited.contains(pos)) {
						Position start = pos;
						visited.add(visitedpos++, start);
					
						while (start != null) {
							
							for (Entry<String, Position> entry:start.neighbors.entrySet()) {
								String posName = entry.getKey();
								Position realPos = entry.getValue();
							
								if (!visited.contains(realPos) && realPos.getOwner() == player) {
									
									if (whichEdge.getValue().contains(realPos)) {
										continue;
									}
									
									if (realPos.isEdge && realPos.isNonCorner) {
										visited.add(visitedpos++, realPos);
										tripod++;
										
										if (tripod == 3) {
											return true;
										}
										start = queue.get(queue.size()-1);
										break;
									}
									
									visited.add(visitedpos++, realPos);
									queue.add(queuepos++, realPos);
									start = realPos;
								}  
							}
						}
						if (queue.size() == 0) {
							break;
						
						} /*else {
							int fixSize = queue.size();
							
							for (int j=fixSize-1; j>=0; j++) {
								Position current = queue.get(j);
								
								if (visited.contains(current)) {
									queue.remove(j);
								
								} else {
									start = current;
									break;
								}
						}*/
					}
				}
			}
			
		}
		return false;
		
	}
	
}
