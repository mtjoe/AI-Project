package FenceMasterBoard;

import java.util.HashMap;

/**
 * 
 * 
 *
 */
public class Position {
	
	/* ATTRIBUTES */
	private Board b;
	private int x, y;
	public boolean isEdge;
	public boolean isNonCorner;
	private Player occupiedBy;
	private HashMap<String, Position> neighbors;
	
	/* PUBLIC CONSTRUCTOR */
	
	public Position(Board b, int x, int y) {
		this.b = b;
		this.x = x;
		this.y = y;
		int n = b.getN();
		this.neighbors = new HashMap<String, Position>();
		
		this.occupiedBy = null;
		
		// Set neighboring positions
		this.neighbors.put("NW", b.getPosition(x-1, y-1));
		this.neighbors.put("NE", b.getPosition(x-1, y));
		this.neighbors.put("SW", b.getPosition(x+1, y));
		this.neighbors.put("SE", b.getPosition(x+1, y+1));
		this.neighbors.put("E", b.getPosition(x, y+1));
		this.neighbors.put("W", b.getPosition(x, y-1));
		
		// Set isEdge
		if ((x == 0) || (y == 0) || (x == ((2*n)-2)) || ((y-x) == (n-1)) || (y == (x + (n-1) - ((x-n+1)*2)))){
			this.isEdge = true;
		} else {
			this.isEdge = false;
		}
			
		// Set isNonCorner
		if ((Math.abs(x) % (n-1) == 0) && (Math.abs(y) % (n-1) == 0) && ((x != (n-1)) || (y != (n-1)))){
			this.isNonCorner = false;
		} else {
			this.isNonCorner = true;
		}
		
	}
	
	/* SETTER METHODS */
	
	public void setOccupy(Player p){
		this.occupiedBy = p;
	}
	
	
	/* GETTER METHODS */
	
	public boolean isEmpty(){
		if (this.occupiedBy == null){
			return true;
		} else {
			return false;
		}
	}
	
	public Player getOwner(){
		return this.occupiedBy;
	}
	
	public Position getNW(){
		return this.neighbors.get("NW");
	}
	
	public Position getNE(){
		return this.neighbors.get("NE");
	}
	
	public Position getSW(){
		return this.neighbors.get("SW");
	}
	
	public Position getSE(){
		return this.neighbors.get("SE");
	}
	
	public Position getE(){
		return this.neighbors.get("E");
	}
	
	public Position getW(){
		return this.neighbors.get("W");
	}
	
}
