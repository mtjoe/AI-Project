package FenceMasterBoard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	
	static Board b;
	static Player black;
	static Player white;
	
	public static void readInput() {
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	 
			String input;
			int i, n;
			String[] entries;
			black = new Player("Black", 'B');
			white = new Player("White", 'W');
			Player[] players = {black, white};
			
			
			// Read first line (size of the board)
			input=br.readLine();
			n = Integer.parseInt(input);
			
			b = new Board(n, players);
			
			// Read ((2*n)-1) lines of input
			for (i=0; i<((2*n)-1); i++){
				int j = 0;
				input=br.readLine();
				entries = input.split(" ");
				for (String e:entries){
					if (!e.equals("-")) {
						if (e.charAt(0) == black.s){
							b.setMove(i, j, black);
						} else if (e.charAt(0) == white.s){
							b.setMove(i,  j, white);
						} else {
							System.out.println("Player not defined.");
		 				}
						
					}
					j++;
				}
			}
	 
		}catch(IOException io){
			io.printStackTrace();
		}	
		return;
	}
	
	
	/** 
	 * Main Function
	 *	- For each input, create a board out of it, then 
	 */	
	public static void main(String args[]){
		
		readInput();
		//b = new Board(7);
		b.printBoard();
		b.hasWinner();
		
		return;
	}
}