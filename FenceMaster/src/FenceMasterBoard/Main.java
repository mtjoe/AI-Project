package FenceMasterBoard;

import java.util.Scanner;

/**
 * 
 * @author Marisa Tjoe (566322) & Erlangga Satria Gama (570748)
 *
 */
public class Main {

	static Board b;
	static Player black;
	static Player white;

	/**
	 * Read input, form a board of minimum size 5
	 * 
	 * Input format: 
	 * [Size of Board] 
	 * [Current Board Form, use '-' for empty position, and Player's short 
	 * name for Positions that is occupied by the Player]
	 * 
	 * @param players
	 *            - Array of size 2, containing the {@link Player} objects
	 */
	public static boolean readInput(Player[] players) {

		Scanner sc = new Scanner(System.in);
		String input;
		int i, n;
		String[] entries;

		/* Get value of n */

		// Read input
		input = sc.nextLine();
		n = Integer.parseInt(input.substring(0, 1));

		// Read first line (size of the board)
		if (n < 5) {
			System.out.println("Minimum size of board is 5");
			return false;
		}

		/* Initialize and set moves on board */

		b = new Board(n, players);

		// Read ((2*n)-1) lines of input
		for (i = 0; i < ((2 * n) - 1); i++) {
			int j = 0;
			input = sc.nextLine();
			entries = input.split(" ");

			// Check if input line of valid size
			if (entries.length != ((i < n) ? (i + n) : ((3 * n) - i - 2))) {
				System.out.println("Please put in a valid board input");
				return false;
			}

			// If valid, loop through the entries, and add moves to the board
			for (String e : entries) {
				if (!e.equals("-")) {
					if (e.charAt(0) == players[0].s) {
						b.setMove(i, j, players[0]);
					} else if (e.charAt(0) == players[1].s) {
						b.setMove(i, j, players[1]);
					} else {
						System.out.println("Player not defined.");
						return false;
					}

				}
				j++;
			}
		}
		return true;
	}

	/**
	 * main function
	 * - Read Input
	 * - Check for winner
	 */
	public static void main(String args[]) {
		// Initialize Players
		Player[] players = { new Player("Black", 'B'), new Player("White", 'W') };

		// Set board based on input
		if (readInput(players)){
			// Check for winner
			b.hasWinner();
		}
		return;
	}
}