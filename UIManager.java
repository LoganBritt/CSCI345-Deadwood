/*
//	UIManager manages the functionality for interacting with the UI and what that interaction
//	does for the game
//	It can create Window classes for showing different info
//	For prototype purposes, the current version of UIManager runs in the terminal, and produces
//	input prompts for the player, then sends that input to the board and game manager
//	They then return data to the UIManager for output to the terminal.
//	The code for beginning deadwood is stored in Deadwood.java, and can be played by running Deadwood.java
*/
import java.util.Scanner;
public class UIManager {
	public static void startGame(){
		System.out.println("Starting Deadwood...");
		System.out.println("Welcome to Deadwood!");

		Scanner terminalIn = new Scanner(System.in);

		//Board setup
		BoardManager.createBoard();

		//Player setting
		System.out.println("How many people will be playing today? (2-8 Players Only)");
		int playerNumInput = Integer.parseInt(promptInput(terminalIn));
		if(playerNumInput >= 2 && playerNumInput <= 8){
			System.out.println("Understood! " + playerNumInput + " players.");
		}

		BoardManager.setPlayerAmt(playerNameInput);
		BoardManager.createPlayers();
		//

	}

	private static String promptInput(Scanner input){
		System.out.print(">>");
		return input.nextLine();
	}








	//The following functions are for visual UI implementation and will not be used in the terminal version of Deadwood
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
