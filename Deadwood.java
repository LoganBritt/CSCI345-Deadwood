
/*
//	This is the startup file for Deadwood
//	This is the executable that will be ran to begin executing the game
*/

public class Deadwood {
	// main method that starts the game
	public static void main(String[] args) {
		System.out.println("Running Deadwood.java");
		System.out.println("Beginning Deadwood...");
		startUpFiles(args);
	}

	// Creates all the needeed boards/cards/decks/files/objects/etc. needed to play
	// the game and starts the official game loop
	private static void startUpFiles(String[] args) {
		UIManager.startGame();
	}
}
