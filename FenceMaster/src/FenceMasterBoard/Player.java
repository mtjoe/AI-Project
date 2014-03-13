package FenceMasterBoard;

public class Player {
	
	/* ATTRIBUTES */
	String name;
	char s;
	
	/* PUBLIC CONSTRUCTOR */
	
	public Player(String name, char s){
		this.name = name;
		this.s = s;
	}
	
	/* GETTER METHODS*/
	
	public String getName(){
		return this.name;
	}
	
	public char getShort(){
		return this.s;
	}
}
