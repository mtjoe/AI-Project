package FenceMasterBoard;


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
		p.addPosition(this.getPosition(x, y), n);
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
		Player winner;
		
		TripodCheck tripodCheck = new TripodCheck(this);
		if ((winner = tripodCheck.check()) == null){
			System.out.println("No winner yet");
		} else {
			System.out.println(winner.name);
			System.out.println("Tripod");
		}
		
		/*
		LoopCheck loopCheck = new LoopCheck(this);
		if ((winner = loopCheck.check()) == null){
			TripodCheck tripodCheck = new TripodCheck(this);
			if ((winner = tripodCheck.check()) == null){
				System.out.println("No Winner yet");
			} else {
				System.out.println(winner.name);
				System.out.println("Tripod");
			}
		} else {
			System.out.println(winner.name);
			System.out.println("Loop");
		}*/
	}
	
	/* HELPER FUNCTIONS */
	
	/**
	 * For testing purposes
	 */
	public void printBoard(){
		// Loop through all the elements in bArray
		for (int i=0; i<this.bArray.length; i++){
			for(int j=0; j<this.bArray[i].length; j++){
				
				// Prints out "-" if Position empty
				if (this.bArray[i][j].isEmpty()){
					System.out.print("-");
					
				// If not empty, prints out short name of owner of the Position
				} else {
					System.out.print(this.bArray[i][j].getOwner().s);
					
				}
				//System.out.print("{"+ Position.getRealPosition(n, bArray[i][j])[0] + ", " + Position.getRealPosition(n, bArray[i][j])[1] +"}");
				
				System.out.print(" ");
			}
			System.out.println("");
		}
	}
	
	/**
	 * @return true if pos1 and pos2 are adjacent/neighbors, false otherwise
	 */
	public boolean isAdjacent(Position pos1, Position pos2){
		if (pos1.getNeighbors().contains(pos2)){
			return true;
		}
		return false;
	}
}
