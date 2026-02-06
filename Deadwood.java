/*
//	This is the startup file for Deadwood
//	This is the executable that will be ran to begin executing the game
*/

public class Deadwood {
	public static void main(String[] args) {
		System.out.println("Running Deadwood.java");
		System.out.println("Beginning Deadwood...");

		System.out.println("Beginning other files for initialization");
		System.out.println("------------------------------------------------------");
		// startUpFiles(args);
		GameManager.startGame();
	}

	private static void startUpFiles(String[] args) {
		Player.main(args);
		GameManager.main(args);
		BoardManager.main(args);
		Board.main(args);
		Casting.main(args);
		Scene.main(args);
		Role.main(args);
		Deck.main(args);
		Card.main(args);
		UIManager.main(args);
		Window.main(args);
	}
}
