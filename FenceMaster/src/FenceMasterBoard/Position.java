package FenceMasterBoard;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * 
 *
 */
public class Position {
	
	/* ATTRIBUTES */
	private Board b;
	private int x, y, n;
	public boolean isEdge;
	public boolean isNonCorner;
	private Player occupiedBy;
	private HashMap<String, Position> neighbors;
	
	/* PUBLIC CONSTRUCTOR */
	
	public Position(Board b, int x, int y) {
		this.b = b;
		this.x = x;
		this.y = y;
		n = b.getN();
		
		this.neighbors = new HashMap<String, Position>();
		
		this.occupiedBy = null;
		
		
		this.setIsEdge();
		this.setIsNonCorner();
		
	}
	
	/**
	 * Set the class variable "neighbors" in this position, which is an HashMap which 
	 * maps the directions in String (e.g. "N", "NW", "SE", etc) to the Position in 
	 * the respective adjacent direction Position.	 
	 * */
	public void setNeighbors(){
		HashMap<String, int[]> neighborsCoord = this.getNeighborsCoordinates();
		
		for (String str: neighborsCoord.keySet()){
			int x = neighborsCoord.get(str)[0];
			int y = neighborsCoord.get(str)[1];
			if (Position.isValidPosition(n, x, y)){
				this.neighbors.put(str, b.getPosition(x, y));
			}
		}
	}
	
	/**
	 * 
	 * @return a HashMap, which maps the direction in String (e.g. "N", "NW", "SE", etc) 
	 * to a two-entry integer array, containing the coordinates {x, y}
	 */
	private HashMap<String, int[]> getNeighborsCoordinates(){
		HashMap<String, int[]> neighborsCoord = new HashMap<String, int[]>();
		
		neighborsCoord.put("E", new int[]{x, y+1});
		neighborsCoord.put("W", new int[]{x, y-1});
		
		if (x < (n-1)){
			neighborsCoord.put("NW", new int[]{x-1, y-1});
			neighborsCoord.put("NE", new int[]{x-1, y});
			neighborsCoord.put("SW", new int[]{x+1, y});
			neighborsCoord.put("SE", new int[]{x+1, y+1});
		} else if (x == (n-1)){
			neighborsCoord.put("NW", new int[]{x-1, y-1});
			neighborsCoord.put("NE", new int[]{x-1, y});
			neighborsCoord.put("SW", new int[]{x+1, y-1});
			neighborsCoord.put("SE", new int[]{x+1, y});
		} else {
			neighborsCoord.put("NW", new int[]{x-1, y});
			neighborsCoord.put("NE", new int[]{x-1, y+1});
			neighborsCoord.put("SW", new int[]{x+1, y-1});
			neighborsCoord.put("SE", new int[]{x+1, y});
		}
		return neighborsCoord;
	}
	
	/**
	 * If this Position is an edge position, set the variable 'isEdge' as true, false otherwise
	 */
	private void setIsEdge(){
		if ((x == 0) || (y == 0) || (x == ((2*n)-2)) || ((y-x) == (n-1)) || (y == (x + (n-1) - ((x-n+1)*2)))){
			this.isEdge = true;
		} else {
			this.isEdge = false;
		}
	}
	
	/**
	 * If this Position is a non-corner position, set the variable 'isNonCorner' as true, false otherwise
	 */
	private void setIsNonCorner(){
		if ((Math.abs(x) % (n-1) == 0) && (Math.abs(y) % (n-1) == 0) && ((x != (n-1)) || (y != (n-1)))){
			this.isNonCorner = false;
		} else {
			this.isNonCorner = true;
		}
	}
	
	/* SETTER METHODS */
	
	/**
	 * Set this Position to be occupied by Player p
	 */
	public void setOccupy(Player p){
		this.occupiedBy = p;
	}
	
	
	/* GETTER METHODS */
	
	/**
	 * @return an ArrayList, consisting of the valid neighboring Positions of this Position
	 */
	public ArrayList<Position> getNeighbors(){
		return (ArrayList<Position>)this.neighbors.values();
	}
	
	/**
	 * @return true if this position is not occupied by any player, false otherwise
	 */
	public boolean isEmpty(){
		if (this.occupiedBy == null){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @return The player that is occupying this Position, null if empty
	 */
	public Player getOwner(){
		return this.occupiedBy;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	/**
	 * @return The North-West neighboring Position of this Position 
	 */
	public Position getNW(){
		return this.neighbors.get("NW");
	}
	
	/**
	 * @return The North-East neighboring Position of this Position
	 */
	public Position getNE(){
		return this.neighbors.get("NE");
	}
	
	/**
	 * @return The South-West neighboring Position of this Position
	 */
	public Position getSW(){
		return this.neighbors.get("SW");
	}
	
	/**
	 * @return The South-East neighboring Position of this Position
	 */
	public Position getSE(){
		return this.neighbors.get("SE");
	}
	
	/**
	 * @return The East neighboring Position of this Position
	 */
	public Position getE(){
		return this.neighbors.get("E");
	}
	
	/**
	 * @return The West neighboring Position of this Position
	 */
	public Position getW(){
		return this.neighbors.get("W");
	}
	
	/**
	 * @return an ArrayList, consisting of the neighboring positions that 
	 * are occupied by the Player occupying this Position, not including
	 * Position prev from the ArrayList.
	 */
	public ArrayList<Position> getSameNeighbors(Position prev){
		ArrayList<Position> sameNeighbors = new ArrayList<Position>();
		
		for (Position pos:this.neighbors.values()){
			if ((this.getOwner() != null) && (pos.getOwner() != null)){
				if (pos.getOwner().equals(this.occupiedBy)){
					sameNeighbors.add(pos);
				}
			}
		}
		
		if (prev!= null){
			sameNeighbors.remove(prev);
		}
		
		return sameNeighbors;
	}
	
	/**
	 * @return true if the position in coordinates (x, y) are valid positions (i.e. withing the board), false otherwise
	 */
	public static boolean isValidPosition(int n, int x, int y){
		
		if ((x >= 0) && (y >= 0) && (x <= ((2*n)-2))){
			if (x < n){
				if ((y-x) <= (n-1)){
					return true;
				}
			} else {
				if (y <= (x + (n-1) - ((x-n+1)*2))){
					return true;
				}
			}
		}
		return false;
	}
}
