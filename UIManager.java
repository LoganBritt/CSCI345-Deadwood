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
	private static Scanner terminalIn;
	//This starts the game and creates the needed objects
	// Also allows user to specify player number
	public static void startGame() {
		System.out.println("Starting Deadwood...");
		System.out.println(bold(underline("Welcome to Deadwood!")));

		terminalIn = new Scanner(System.in);

		// Deck setup
		BoardManager.createDeck();

		// Board setup
		BoardManager.createBoard();

		// Player setting
		System.out.println("How many people will be playing today? (2-8 Players Only)");
		int glitchInt = 0;
		int playerNumInput = 0;
		int numInputFail = 0;
		while (playerNumInput < 2 || playerNumInput > 8) {
			System.out.print(blink(">>"));
			while(!(terminalIn.hasNextInt())){
				if(glitchInt == 0){ glitchInt = 1;
	                        	System.out.println("I'm sorry. That's not a number");
	                        	System.out.print(blink(">>"));
	                        	terminalIn.nextLine();
				}else{ glitchInt = 0;}
	                }
			playerNumInput = terminalIn.nextInt();
			if(playerNumInput < 2 || playerNumInput > 8){
				if (numInputFail != 2 && numInputFail != 5) {
					System.out.println("I'm sorry, that's outside the boundaries (2-8 Players Only). Please try again:");
//					numInputFail++;
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
//					numInputFail++;
				}
			}
		}
		System.out.println("Understood! " + playerNumInput + " players.");

		GameManager.setPlayerAmt(playerNumInput);
		GameManager.createPlayers();
		// Code for beginning actual gameplay
		startGameplay();

	}
	//Main game loop
	private static void startGameplay() {
		terminalIn = new Scanner(System.in);
		while (GameManager.getDay() != 0) {
			Player actvPlayer = GameManager.getActivePlayer();
			System.out.println();
			System.out.println("Player " + (GameManager.getActvPlyrIdx() + 1) + ", what do you want to do?");
			String input = promptInput(terminalIn);
			input = input.toLowerCase();
			Role workingRole = actvPlayer.currRole;

			switch (input) {
				case "help": printHelp(); break;
				case "stats": printStats(actvPlayer); break;
				case "stats all": printStats(null); break;
				case "space": printSpace(actvPlayer); break;
				case "card": printCard(actvPlayer); break;
				case "role": printPlayerRole(actvPlayer); break;
				case "act": printAct(actvPlayer); break;
				case "upgrade info": printUpgradeInfo(); break;
				case "rehearse": printRehearse(actvPlayer); break;
				case "end turn": printEndTurn(); break;
				case "end game": GameManager.endGame(); break;
				default:
					if(input.startsWith("move")){
						printMove(actvPlayer, input);
					}else if(input.startsWith("upgrade")){
						printUpgrade(actvPlayer, input);
					}else if(input.startsWith("take role")){
						printRoleChange(actvPlayer, input);
					}else{
						System.out.println("I'm sorry, I didn't understand that.");
						System.out.println("I'm a dumb computer and only understand specific commands");
						System.out.println("Some actions are not fully implemented. Deadwood is still in development");
						System.out.println("Please try again, or type 'Help' for input options");
					}
					break;

			}
		}
	}

	// This is a method we decided to add to have a list of all the possible actions 
	private static void printHelp() {
		System.out.println(underline("Game Actions:"));
		System.out.println("* Help (Ex 'Help'): This shows the action menu. You're seeing it now.");
		System.out.println("* Stats (Ex 'Stats'): This shows the stats for the active player, including space, role, money, credits, rank, etc");
		System.out.println("* Stats All (Ex 'Stats All'): This shows the stats listed above for all players");
		System.out.println("* Space (Ex 'Space'): Presents info about the space you're at");
		System.out.println("* Card (Ex 'Card'): Presents info about the card at the scene you're at");
		System.out.println("* Role (Ex 'Role'): Presents info about the role you're currently working on, only if you're working on a role");
		System.out.println("* Move + place name (Ex 'Move Jail'): This moves your player from the space you're on to another neighboring it. You can only move once a turn, and only if you're not at a role");
		System.out.println("* Act (Ex 'Act'): This causes your player to act. Act only if you have not acted this turn and if you have taken a role");
		System.out.println("* Upgrade + new rank (Ex 'Upgrade 4'): This causes your player to make an upgrade. Upgrade only at the Casting Office");
		System.out.println("* Upgrade Info (Ex 'Upgrade Info'): Provides info about the costs for each rank upgrade");
		System.out.println("* Take Role + role name (Ex 'Take Role Squeaking Boy'): This allows your player to take a new role. You can only take a new role that isn't taken and if you do not have a role");
		System.out.println("* Rehearse (Ex 'Rehearse'): Rehearse and get a rehearsal token. You can only rehearse if you're working on a role");
		System.out.println("* End turn (Ex 'End turn'): Ends your turn");
		System.out.println("* End game (Ex 'End game'): Ends the game early");
	}

	// Prints the Player's dollars/credits/rank/rehearsal tokens. 
	// Will also print the space, role
	// If the player object is null, then ALL of the players' stats will be printed
	public static void printStats(Player player) {
		System.out.println(underline("Player Stats:"));
		if (player == null) {
			Player[] players = GameManager.getPlayerList();
			for (int i = 0; i < players.length; i++) {
				System.out.println(bold("Player " + (i + 1) + ":"));
                                System.out.println("  Dollars: " + players[i].dollars);
                                System.out.println("  Credits: " + players[i].credits);
                                System.out.println("  Rank: " + players[i].rank);
                                System.out.println("  Rehearsal Tokens: " + players[i].rehearseTokens);
				System.out.println("  You are at the " + players[i].currLocation.name);
				if(players[i].currRole != null){
					System.out.println("  Current role: " + players[i].currRole.getTitle());
				}else{
					System.out.println("  You are not working on a role");
				}
				System.out.println();
			}
		} else {
			System.out.println("Player " + (GameManager.getActvPlyrIdx() + 1) + ":");
                        System.out.println("  Dollars: " + player.dollars);
                        System.out.println("  Credits: " + player.credits);
                        System.out.println("  Rank: " + player.rank);
                        System.out.println("  Rehearsal Tokens: " + player.rehearseTokens);
			System.out.println("  You are at the " + player.currLocation.name);
			if(player.currRole != null){
                                 System.out.println("  Current role: " + player.currRole.getTitle());
                        }else{
                        	System.out.println("  You are not working on a role");
                        }
			System.out.println();
		}
	}

	// Prints everything the player will need to see with the 'Space' command
	private static void printSpace(Player actvPlayer){
		Space currSpace = actvPlayer.currLocation;
                if (currSpace instanceof Trailers) {
                        System.out.println(bold("You are at the Trailers"));
                        currSpace.printNeighbors();
                } else if (currSpace instanceof Casting) {
                        System.out.println(bold("You are at the Casting Office"));
                        currSpace.printNeighbors();
                        System.out.println("You can see upgrade rates by typing 'Upgrade Info'");
                } else {
                        Scene scene = (Scene) currSpace;
                        System.out.println(bold("You are at the " + currSpace.name));
			if(scene.getShots() > 0){
				System.out.println("We're still shooting " + scene.getShots() + " shots for the day");
			}

			currSpace.printNeighbors();
			if(scene.getCard() == null){
				System.out.println("This scene is done shooting for the day");
				System.out.println("Please try again tomorrow");
			}else{
				System.out.println();
				System.out.println(underline("Roles on the scene"));
				System.out.println("Available Roles:");
				Role[] utRoles = scene.getUntakenRoles();
				printRoles(utRoles);
				System.out.println();
				System.out.println("Taken Roles:");
				Role[] tRoles = scene.getTakenRoles();
				printRoles(tRoles);
				System.out.println();
			}
                }
	}

	// Prints everything the player will need to know about the card of the scene they're one
	private static void printCard(Player actvPlayer){
		if(actvPlayer.currLocation instanceof Trailers || actvPlayer.currLocation instanceof Casting){
                        System.out.println("You're not at a scene. Only scenes have cards");
			return;
                }
		Scene scene = (Scene) actvPlayer.currLocation;
		if(scene.getCard() == null){
			System.out.println("This scene is fininshed shooting for the day");
			System.out.println("There is no card because of that");
			System.out.println("Please check again tomorrow");
			return;
		}
                Card card = scene.getCard();
                System.out.println("'" + card.getTitle() + "'");
                System.out.println(card.getDesc());
        	printRoles(card.getRoles());
	}

	// Prints all the info the player needs to know about the role they're working on
	private static void printPlayerRole(Player actvPlayer){
		Role workingRole = actvPlayer.currRole;
		if(workingRole == null){
			System.out.println("You are not currently working at a role");
			return;
		}
                System.out.println("'" + workingRole.getTitle() + "'");
                System.out.println("'" + workingRole.getLine() + "'");
                System.out.println("Level: " + workingRole.getRank());
	}

	// Prints everything the player needs to see for acting
	private static void printAct(Player actvPlayer){
		Role workingRole = actvPlayer.currRole;
		if(!(actvPlayer.currLocation instanceof Scene)){
                        System.out.println("You are not in a place where you can act");
			return;
		}
                if(workingRole == null){
                	System.out.println("You cannot act. You are not working at a role");
                        return;
                }
                if(GameManager.getPlayerActed()){
                        System.out.println("You cannot act. You have already acted this turn");
                        System.out.println("You can only either act or rehearse once per turn");
                        return;
                }
		boolean successful = actvPlayer.act(workingRole.isOnCard());
		if(successful){
			System.out.println("You've successfully acted your role.");
			if(workingRole.isOnCard()){
				System.out.println("You earned 2 credits");
			}else{
				System.out.println("You earned 1 dollar and 1 credit");
			}
			System.out.println("Use the command 'Stats' to see your new balance");
		}else{
			System.out.println("You stumbled over your words while acting your lines");
			if(workingRole.isOnCard()){
				System.out.println("You earned nothing for your work");
			}else{
				System.out.println("The set stubbornly paid you 1 dollar anyway for your efforts");
			}
			System.out.println("Remember that you can rehearse to get better at your lines");
		}
	}

	// Prints all the rank upgrade exchange rates
	private static void printUpgradeInfo(){
		System.out.println("Upgrade Info:");
                System.out.println(" Rank |  Dollars  | Credits");
                System.out.println("  2   |    04     |   05   ");
                System.out.println("  3   |    10     |   10   ");
                System.out.println("  4   |    18     |   15   ");
                System.out.println("  5   |    28     |   20   ");
                System.out.println("  6   |    40     |   25   ");
                System.out.println("Remember, upgrades can only be made at the Casting Office");

	}

	// Prints all the rehearse info
	private static void printRehearse(Player actvPlayer){
		if(actvPlayer.currRole == null){
			System.out.println("You are not working on a role. There are no lines to rehearse");
		}
		if(!(actvPlayer.currLocation instanceof Scene)){
                        System.out.println("You are not in a place where you can rehearse");
                        return;
                }
		if(GameManager.getPlayerActed()){
                	System.out.println("I'm sorry. You've already worked this turn");
                        System.out.println("You can only either act or rehearse once per turn");
			return;
                }
                actvPlayer.rehearse();
                System.out.println("You rehearsed your lines");
                System.out.println("You now have " + actvPlayer.rehearseTokens + " rehearsal tokens");
	}

	// Prints info about moving and moves the player to the desired space
	private static void printMove(Player actvPlayer, String input){
		if(GameManager.getPlayerMoved()){
			System.out.println("You have already moved this turn");
			return;
		}
		String newScene;
                newScene = cutFront(input, 1);
                actvPlayer.move(newScene);
	}

	// Prints info and handles upgrade exchanges
	private static void printUpgrade(Player actvPlayer, String input){
		if(!(actvPlayer.currLocation instanceof Casting)){
			System.out.println("I'm sorry. You can't make an exchange here");
                        System.out.println("Please move to the Casting Office to discuss upgrading your rank");
			return;
		}
                String newRank;
                newRank = cutFront(input, 1);
		if(newRank == null){
			System.out.println("It seems you forgot to add a new rank value");
			System.out.println("Remember, it's in the format 'Upgrade + new rank' (Ex 'Upgrade 4')");
			return;
		}
                System.out.println("New rank for upgrade: " + newRank);

                System.out.println("How would you like to pay?");
                System.out.println("(C)redits or (D)ollars?");
                String paymentType = promptInput(terminalIn);
                if(!(paymentType.toLowerCase().equals("c") || paymentType.toLowerCase().equals("d") || paymentType.toLowerCase().startsWith("dollars") || paymentType.toLowerCase().startsWith("credits"))){
                        System.out.println("I'm sorry. " + paymentType + " is a viable form of payment");
			return;
                }
                paymentType = paymentType.toLowerCase();
                boolean usingMoney = (paymentType.equals("d") || paymentType.startsWith("credits"));
                System.out.println("Got it. You're paying with " + paymentType);
                actvPlayer.upgrade((int) Float.parseFloat(newRank), usingMoney);
	}

	// Prints everything the player needs to see in regards to taking a new role
	private static void printRoleChange(Player player, String input){
		String newRole;
                newRole = cutFront(input, 2);
                System.out.println("New role: " + newRole);
		if(player.currLocation instanceof Casting || player.currLocation instanceof Trailers){
			System.out.println("There are no roles at the place you're at");
			return;
		}
		Scene scene = (Scene) player.currLocation;
		if(scene.sceneComplete()){
			System.out.println("This scene has already finished shooting for the day");
			System.out.println("Please try again tomorrow");
			return;
		}
		//Checking scene roles
		Role[] untakenRoles = scene.getUntakenRoles();
		Role foundRole = null;
		for(int i = 0; i < untakenRoles.length; i++){
			if(untakenRoles[i] != null && untakenRoles[i].getTitle().toLowerCase().equals(newRole.toLowerCase())){
				foundRole = untakenRoles[i];
			}
		}
		//Checking card roles
		Card card = scene.getCard();
		untakenRoles = card.getRoles();
		for(int i = 0; i < untakenRoles.length; i++){
			if(untakenRoles[i] != null && !(untakenRoles[i].isTaken()) && untakenRoles[i].getTitle().toLowerCase().equals(newRole.toLowerCase())){
				foundRole = untakenRoles[i];
			}
		}
		if(foundRole == null){
			System.out.println("Either the role you entered is not on this scene or is already taken by another player.");
			System.out.println("Please try again or try to take another role");
			return;
		}
		if(foundRole.getRank() > player.rank){
			System.out.println("You are not experienced enough to take this role");
			System.out.println("You must upgrade your rank in the Casting Office to at least " + foundRole.getRank());
			return;
		}
		player.currRole = foundRole;
		foundRole.setPlayer(player);
		

	}

	// Prints everything the player will need to see whne the turn is ended
	private static void printEndTurn(){
		System.out.println("Next turn...");
                GameManager.changeTurn();
	}

	// Regular prompting for the input, just visually aesthetic 
	private static String promptInput(Scanner input) {
		System.out.print(blink(">>"));
		return input.nextLine();
	}

	// This prints all the info of all the roles that are currently in roleList
	private static void printRoles(Role[] roleList){
		for(int i = 0; i < roleList.length; i++){
                	if(roleList[i] != null){
                        	System.out.println((i+1) + ":" + roleList[i].getTitle() + "");
                                System.out.println("  Level: " + roleList[i].getRank());
                                System.out.println("  '" + roleList[i].getLine() + "'");
                        }
                }
	}

	// Cuts off the first removed words from the input string to get the arguments
	private static String cutFront(String input, int removed){
		if(input.length()-1 == 0){ return null; }
		if(removed == 0){ return input;}
		int i = input.indexOf(" ") + 1;
                return cutFront(input.substring(i, input.length()), removed - 1);
	}

	private static boolean test(int i, boolean ret){
		System.out.println("i before check: " + i);
		System.out.println("boolean at check: " + ret);
		return ret;
	}

	private static String underline(String string){
		return "\u001B[4m" + string + "\u001B[0m";
	}

	private static String bold(String string){
		return "\u001B[1m" + string + "\u001B[0m";
	}

	private static String blink(String string){
		return "\u001B[5m" + string + "\u001B[0m";
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
