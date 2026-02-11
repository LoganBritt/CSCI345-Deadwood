
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
	public static void startGame() {
		System.out.println("Starting Deadwood...");
		System.out.println("Welcome to Deadwood!");

		Scanner terminalIn = new Scanner(System.in);

		// Deck setup
		BoardManager.createDeck();

		// Board setup
		BoardManager.createBoard();

		// Player setting
		System.out.println("How many people will be playing today? (2-8 Players Only)");
		int playerNumInput = Integer.parseInt(promptInput(terminalIn));
		int numInputFail = 0;
		while (playerNumInput < 2 || playerNumInput > 8) {
			if (numInputFail != 2 && numInputFail != 5) {
				System.out.println("I'm sorry, that's outside the boundaries (2-8 Players Only). Please try again:");
				numInputFail++;
			} else if (numInputFail == 5) {
				System.out.println("Nope, not doing this");
				System.out.println("Bye.");
				System.exit(0);
			} else {
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

		// Code for beginning actual gameplay
		startGameplay();

	}

	private static void startGameplay() {
		Scanner in = new Scanner(System.in);
		Player actvPlayer = GameManager.getActivePlayer();
		while (GameManager.getDay() != 0) {
			System.out.println();
			System.out.println("Player " + (GameManager.getActvPlyrIdx() + 1) + ", what do you want to do?");
			String input = promptInput(in);
			input = input.toLowerCase();
			Role workingRole = actvPlayer.currRole;

			switch (input) {
				case "help":
					printHelp();
					break;
				case "stats":
					printStats(actvPlayer);
					break;
				case "stats all":
					printStats(null);
					break;
				case "space":
					Space currSpace = actvPlayer.currLocation;
					if (currSpace instanceof Trailers) {
						System.out.println("You are at the Trailers");
						currSpace.printNeighbors();
					} else if (currSpace instanceof Casting) {
						System.out.println("You are at the Casting Office");
						currSpace.printNeighbors();
						System.out.println("You can see exchange rates by typing 'Exchange Info'");
					} else {
						Scene scene = (Scene) currSpace;
						System.out.println("You are at " + scene.getName());
						System.out.println("Card Title: " + scene.getCard().getTitle());
						printRoles(scene.getUntakenRoles());
						printRoles(scene.getTakenRoles());
					}
					break;
				case "card":
					if(actvPlayer.currLocation instanceof Trailers || actvPlayer.currLocation instanceof Casting){
						System.out.println("The space you're at does not have a card");
					}else{
						Scene scene = (Scene) actvPlayer.currLocation;
						Card card = scene.getCard();
						System.out.println("'" + card.getTitle() + "'");
						System.out.println(card.getDesc());
						printRoles(card.getRoles());
					}
					break;
				case "role":
					if(workingRole != null){
						System.out.println("'" + workingRole.getTitle() + "'");
                        			System.out.println("'" + workingRole.getLine() + "'");
                        			System.out.println("Level: " + workingRole.getRank());
					}else{
						System.out.println("You are not currently working at a role");
					}
					break;
				case "act":
					if(actvPlayer.currLocation instanceof Scene){
						Scene scene = (Scene) actvPlayer.currLocation;
						if(workingRole == null){
							System.out.println("You cannot act. You are not working at a role");
						}else if(GameManager.getPlayerActed()){
							System.out.println("You cannot act. You have already acted this turn");
						}else{
							actvPlayer.act(workingRole.isOnCard());
						}
					}else{
						System.out.println("You are not in a place where you can act");
					}
					break;
				case "exchange info":
					System.out.println("Exchange Info:");
					System.out.println(" Rank |  Dollars  | Credits");
					System.out.println("  2   |    04     |   05   ");
					System.out.println("  3   |    10     |   10   ");
					System.out.println("  4   |    18     |   15   ");
					System.out.println("  5   |    28     |   20   ");
					System.out.println("  6   |    40     |   25   ");
					System.out.println("Remember, exchanges can only be made at the Casting Office");
					break;
				case "end turn":
                                        System.out.println("Next turn...");
                                        GameManager.changeTurn();
                                        break;
				case "end game":
					GameManager.endGame();
					break;
				default:
					if(input.startsWith("move")){
						String newScene;
						newScene = cutFront(input, 1);
						System.out.println("New scene: " + newScene);
						actvPlayer.move(newScene);
					}else if(input.startsWith("exchange")){
						String newRank;
						newRank = cutFront(input, 1);
						System.out.println("New rank for exchange: " + newRank);

					}else if(input.startsWith("take role")){
						String newRole;
						newRole = cutFront(input, 2);
						System.out.println("New role: " + newRole);

					}else{
						System.out.println("I'm sorry, I didn't understand that.");
						System.out.println("Some actions are not fully implemented. Deadwood is still in development");
						System.out.println("Please try again, or type 'Help' for input options");
					}
					break;

			}
		}
	}

	private static void printHelp() {
		System.out.println("Game Actions:");
		System.out.println("* Help (Ex 'Help'): This shows the action menu. You're seeing it now.");
		System.out.println(
				"* Stats (Ex 'Stats'): This shows the stats for the active player, including space, role, money, credits, rank, etc");
		System.out.println("* Stats All (Ex 'Stats All'): This shows the stats listed above for all players");
		System.out.println("* Space (Ex 'Space'): Presents info about the space you're at");
		System.out.println("* Card (Ex 'Card'): Presents info about the card at the scene you're at");
		System.out.println(
				"* Role (Ex 'Role'): Presents info about the role you're currently working on, only if you're working on a role");
		System.out.println(
				"* Move + place name (Ex 'Move Jail'): This moves your player from the space you're on to another neighboring it. You can only move once a turn, and only if you're not at a role");
		System.out.println(
				"* Act (Ex 'Act'): This causes your player to act. Act only if you have not acted this turn and if you have taken a role");
		System.out.println(
				"* Exchange + rank number (Ex 'Exchange 4'): This causes your player to make an exchange. Exchange only at the Casting Office");
		System.out.println("* Exchage Info (Ex 'Exchange Info'): Provides info about the costs for each rank exchange");
		System.out.println(
				"* Take Role + role name (Ex 'Take Role Squeaking Boy'): This allows your player to take a new role. You can only take a new role that isn't taken and if you do not have a role");
		System.out.println("* End turn (Ex 'End turn'): Ends your turn");
		System.out.println("* End game (Ex 'End game'): Ends the game early");
	}

	public static void printStats(Player player) {
		System.out.println("Printing player stats:");
		if (player == null) {
			Player[] players = GameManager.getPlayerList();
			for (int i = 0; i < players.length; i++) {
				System.out.println("Player " + (i + 1) + ":");
				System.out.println("Dollars: " + players[i].dollars);
				System.out.println("Credits: " + players[i].credits);
				System.out.println("Rank: " + players[i].rank);
				System.out.println();
			}
		} else {
			System.out.println("Player " + (GameManager.getActvPlyrIdx() + 1) + ":");
			System.out.println("Dollars: " + player.dollars);
			System.out.println("Credits: " + player.credits);
			System.out.println("Rank: " + player.rank);
			System.out.println();
		}
	}

	private static String promptInput(Scanner input) {
		System.out.print(">>");
		return input.nextLine();
	}

	private static void printRoles(Role[] roleList){
		System.out.println("Roles:");
		Role workingRole;
		for(int i = 0; i < roleList.length; i++){
			workingRole = roleList[i];
			System.out.println("'" + workingRole.getTitle() + "'");
			System.out.println("'" + workingRole.getLine() + "'");
			System.out.println("Level: " + workingRole.getRank());
			if(i+1 != roleList.length){ System.out.println();}
		}
	}

	private static String cutFront(String input, int removed){
		if(removed == 0){ return input;}
		int i = 0;
                while(input.charAt(i) != ' '){
                	i++;
                }
                i++;
                return cutFront(input.substring(i, input.length()), removed - 1);
	}

	// The following functions are for visual UI implementation and will not be used
	// in the terminal version of Deadwood
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
