/*
//	This is the main engine of the game
//	GameManager maintains turn order and the flow of the game
*/

import java.util.Arrays;
import java.util.Random;

public class GameManager {
	private static int day;
	private static Player[] playerListOrder;
	private static int playerAmt;
	private static int playerIdx = 0;
	private static boolean actvPlayerActed = false;
	private static boolean actvPlayerMoved = false;
	private static boolean tookRole = false;

	// Moves the turn order up one
	// Reset index if at the end
	public static void changeTurn() {
		if (playerIdx == playerAmt - 1) {
			playerIdx = 0;
		} else {
			playerIdx++;
		}
		actvPlayerActed = false;
		actvPlayerMoved = false;
		tookRole = false;
	}

	// Gives bonuses to players according to their assigned rank
	// They are distributed highest rank first, to lowest, then back to highest
	// again
	public static void distributeBonuses(Space space) {
		Scene scene = null;
		if (space instanceof Scene) {
			scene = (Scene) space;
		}

		// Bonus for Off Card
		Role[] list = scene.getRoles();
		for (int i = 0; i < list.length; i++) {
			if ((list[i] != null) && (list[i].getPlayer() != null)) {
				list[i].getPlayer().dollars += list[i].getRank();
				System.out.println(
						"$" + list[i].getRank() + " given to player working on the role: " + list[i].getTitle());
			}
		}
		// Bonus for On Card
		Card card = scene.getCard();
		Role[] onCard = card.getRoles();
		int[] diceRolled = rollDice(card.getBudget());
		for (int i = 0, j = 0; i < card.getBudget(); i++, j++) {
			if (j == onCard.length) {
				j = 0;
			}
			if (onCard[j] != null && onCard[j].isTaken()) {
				onCard[j].getPlayer().dollars += diceRolled[diceRolled.length - 1 - i];
				System.out.println("$" + diceRolled[diceRolled.length - 1 - i] + " given to player working on role: "
						+ onCard[j].getTitle());
			} else if (onCard[j] != null) {
			} else {
				i--;
			}
		}
	}

	// Creates an array of random sorted ints between 1 and 6 (Inclusive)
	public static int[] rollDice(int dieAmt) {
		Random rand = new Random();
		int[] retVals = new int[dieAmt];
		for (int i = 0; i < dieAmt; i++) {
			retVals[i] = rand.nextInt(1, 7);
		}
		Arrays.sort(retVals);
		return retVals;
	}

	// returns the Active Player
	public static Player getActivePlayer() {
		return playerListOrder[playerIdx];
	}

	// gets the index of the active player
	public static int getActvPlyrIdx() {
		return playerIdx;
	}

	// returns the Player List
	public static Player[] getPlayerList() {
		return playerListOrder;
	}

	// returns the day
	public static int getDay() {
		return day;
	}

	// sets the number of players to the passed amount
	public static void setPlayerAmt(int amt) {
		playerAmt = amt;
	}

	// checks to see if the player has acted
	public static boolean getPlayerActed() {
		return actvPlayerActed;
	}

	// checks to see if the player has moved
	public static boolean getPlayerMoved() {
		return actvPlayerMoved;
	}

	// checks to see if the player has taken a role
	public static boolean getTookRole() {
		return tookRole;
	}

	// sets the variable to see if the Player has acted to true
	public static void makeActed() {
		actvPlayerActed = true;
	}

	// sets the variable to see if the player has moved to true
	public static void makeMoved() {
		actvPlayerMoved = true;
	}

	// sets the variable to is if the player has taken a role to true
	public static void makeTaken() {
		tookRole = true;
	}

	// Create each player object
	// Also sets the day depending on how many players are playing
	// Precondition: Days is set and the trailers are an object to set to
	public static void createPlayers() {
		playerListOrder = new Player[playerAmt];

		if (playerAmt < 4) {
			day = 3;
		} else {
			day = 4;
		}
		for (int i = 0; i < playerAmt; i++) {
			if (playerAmt < 5) {
				playerListOrder[i] = new Player();
			} else if (playerAmt < 6) {
				playerListOrder[i] = new Player(2, 0);
			} else if (playerAmt < 7) {
				playerListOrder[i] = new Player(4, 0);
			} else {
				playerListOrder[i] = new Player(0, 2);
			}
			playerListOrder[i].currLocation = BoardManager.board.getSpaceByName("trailer");
		}
	}

	// This we are using for testing, it just prints the array that is passed.
	private static void printArray(int[] arr) {
		System.out.print("[");
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i]);
			System.out.print(", ");
		}
		System.out.println("]");
	}

	// Move on to the next day and reset the boards
	public static void endDay() {
		day++;
		BoardManager.resetBoard();
	}

	// Ends the game
	public static void endGame() {
		day = 0;
		System.out.println("Game Over");
		// UIManager.printStats(null);
		for (int i = 0; i < playerListOrder.length; i++) {
			int stats = playerListOrder[i].dollars + playerListOrder[i].credits + (playerListOrder[i].rank * 5);
			System.out.println("Player " + (i + 1) + " total points: " + stats);
		}
		System.out.println("Thanks for playing");
	}
}
