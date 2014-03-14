package FenceMasterBoard;

import java.util.ArrayList;


public class Board {
	
	private Position bArray[][];
	private int n;
	private int nRow;
	private int nCol;
	
	/**
	 * Initializes the board
	 * @param n
	 */
	public Board(int n){
		
		this.n = n;
		
		nRow = (2*n)-1;
		
		bArray = new Position[nRow][];
		
		/* Loop through each row to initialize empty slots */
		
		// Initialize increasing rows (0 -- n)
		for (int i=0; i<n; i++) {
			nCol = n + i;
			
			Position[] rowArray = new Position[nCol];
			
			for (int j=0; j<nCol; j++){
				rowArray[j] = new Position(this, i, j); 
			}
			
			bArray[i] = rowArray;
			
		}
		
		// initialize decreasing rows (n+1 -- 2n-1
		
		for (int i=n; i<nRow; i++) {
			nCol = (nRow - i + n - 1);
			
			Position[] rowArray = new Position[nCol];
			
			for (int j=0; j<nCol; j++){
				rowArray[j] = new Position(this, i, j); 
			}
			
			bArray[i] = rowArray;
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
		try {
			return bArray[x][y];
		} catch(Exception e) {
			return null;
		}
		
	}
	
	/**
	 * Checks whether there is a winner in the board.
	 * @return The name of the winner if there is a winner, null if there is no winner
	 */
	public String hasWinner(){
		return null;
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
	
	public boolean checkTripod(){
		// TODO
		return false;
	}
	
	public boolean checkLoop(){
		ArrayList<Position> visited = new ArrayList<Position>();
		
		return false;
	}
	
}
