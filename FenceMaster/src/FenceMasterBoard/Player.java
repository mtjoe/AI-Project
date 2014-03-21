package FenceMasterBoard;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
	
	/* ATTRIBUTES */
	String name;
	char s;
	ArrayList<Position> positions;
	HashMap<String, ArrayList<Position>> startingPoints;
	
	/* PUBLIC CONSTRUCTOR */
	
	public Player(String name, char s){
		this.name = name;
		this.s = s;
		this.positions = new ArrayList<Position>();
		
		// Initialize starting Points
		for (String dir: new String[]{"N", "NW", "NE", "S", "SW", "SE"}) {
			startingPoints.put(dir, new ArrayList<Position>());
		}
	}
	
	/* GETTER METHODS*/
	
	/**
	 * @return Name of this Player
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * @return Short name of this Player
	 */
	public char getShort(){
		return this.s;
	}
	
	/**
	 * Add a Position of the position array, listing all the Positions that this Player are occupying
	 * @param pos
	 */
	public void addPosition(Position pos, int n){
		this.positions.add(pos);
		if (pos.isEdge && pos.isNonCorner) {
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
}
