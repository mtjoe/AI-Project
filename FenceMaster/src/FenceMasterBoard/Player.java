package FenceMasterBoard;

import java.util.ArrayList;

public class Player {
	
	/* ATTRIBUTES */
	String name;
	char s;
	ArrayList<Position> positions;
	
	/* PUBLIC CONSTRUCTOR */
	
	public Player(String name, char s){
		this.name = name;
		this.s = s;
		this.positions = new ArrayList<Position>();
		topLeft = null;
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
	public void addPosition(Position pos){
		this.positions.add(pos);
	}
}
