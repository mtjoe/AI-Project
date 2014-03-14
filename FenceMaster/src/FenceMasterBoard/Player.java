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
	
	public void addPosition(Position pos){
		this.positions.add(pos);
	}
}
