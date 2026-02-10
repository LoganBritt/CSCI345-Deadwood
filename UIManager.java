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

		//Deck setup
		BoardManager.createDeck();

		//Board setup
		BoardManager.createBoard();

		//Player setting
		System.out.println("How many people will be playing today? (2-8 Players Only)");
		int playerNumInput = Integer.parseInt(promptInput(terminalIn));
		int numInputFail = 0;
		while(playerNumInput < 2 || playerNumInput > 8){
			if(numInputFail != 2 && numInputFail != 5){
				System.out.println("I'm sorry, that's outside the boundaries (2-8 Players Only). Please try again:");
				numInputFail++;
			}else if (numInputFail == 5){
				System.out.println("Nope, not doing this");
				System.out.println("Bye.");
				System.exit(0);
			}else{
				System.out.println("Seriously, dude. 2 to 8 players. Including 2 and 8");
				System.out.println("That means you can choose 2, 3, 4, 5, 6, 7, or 8 players");
				System.out.println("Not 1, not 15, not any negative or decimal number");
				System.out.println("2 through 8, got it?");
				System.out.println("You had better got it");
				numInputFail++;
			}
			playerNumInput = Integer.parseInt(promptInput(terminalIn));
		}
		System.out.println("Understood! " + playerNumInput + " players.");

		GameManager.setPlayerAmt(playerNumInput);
		GameManager.createPlayers();

		//Code for beginning actual gameplay
		startGameplay();

	}

	private static void startGameplay(){
		Scanner in = new Scanner(System.in);
		while(GameManager.getDay() != 0){
			System.out.println("Player " + (GameManager.getActvPlyrIdx() + 1) +", what do you want to do?");
			String input = promptInput(in);
			switch(input){
				case "Help":
					printHelp();
					break;
				case "End Game**":
					GameManager.endGame();
					break;
				default:
					System.out.println("I'm sorry, I didn't understand that.");
					System.out.println("Please try again, or type 'Help' for input options");

			}
		}
	}

	private static void printHelp(){
		System.out.println("This is all in development, the help function right now is only for testing.");
		System.out.println("Please try again when Deadwood is completed");
	}

	public static void printStats(Player player){
		System.out.println("Printing player stats:");
		if(player == null){
			Player[] players = GameManager.getPlayerList();
			for(int i = 0; i < players.length; i++){
				System.out.println("Player " + (i + 1) + ":");
				System.out.println("Dollars: " + players[i].dollars);
				System.out.println("Credits: " + players[i].credits);
				System.out.println("Rank: " + players[i].rank);
				System.out.println();
			}
		}else{
			System.out.println("Player " + (GameManager.getActvPlyrIdx() + 1) + ":");
                        System.out.println("Dollars: " + player.dollars);
                        System.out.println("Credits: " + player.credits);
                        System.out.println("Rank: " + player.rank);
                        System.out.println();
		}
	}

	private static String promptInput(Scanner input){
		System.out.println();
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
