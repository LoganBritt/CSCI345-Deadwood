/*
//	UIManager manages the functionality for interacting with the UI and what that interaction
//	does for the game
//	It can create Window classes for showing different info
*/

public class UIManager {
	private BoardManager bm;
	private GameManager gm;

	UIManager(BoardManager bmSet, GameManager gmSet) {
		bm = bmSet;
		gm = gmSet;
	}

	public static void main(String[] args) {
		System.out.println("Running UIManager.java");
	}

	// Presents the board object to the screen
	public void presentBoard() {
	}

	// Presents the player objects
	private void presentPlayers() {
	}

	// Presents the card object
	private void presentCards() {
	}

	// Shows a window on top of all things
	public void showWindow(Window window) {
	}

	// Hides a specific window
	public void hideWindow(Window window) {
	}

}
