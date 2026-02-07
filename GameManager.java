/*
//	This is the main engine of the game
//	GameManager maintains turn order and the flow of the game
*/

import java.util.Arrays;
import java.util.Random;

public class GameManager {
	private static BoardManager bm;
	private static UIManager ui;
	private static int day;
	private static Player[] playerListOrder;
	private static int playerAmt;
	private static int playerIdx = 0;

	public static void main(String[] args) {
		System.out.println("Running GameManager.java");
	}

	// Moves the turn order up one
	// Reset index if at the end
	public static void changeTurn() {
		if (playerIdx + 1 == playerAmt) {
			playerIdx = 0;
		} else {
			playerIdx++;
		}
	}

	// Gives bonuses to players according to their assigned rank
	// They are distributed highest rank first, to lowest, then back to highest
	// again
	public static void distributeBonuses(Player[] bonusPlayers, Space space) {
		// Bonus for Off Card
		Role[] list = space.getTakenRoles();
		for (int i = 0; i < list.length; i++) {
			list[i].getPlayer().dollars += list[i].getRank();
		}
		// Bonus for On Card
		Card card = space.getCard();
		Role[] onCard = card.getRoles();
		int[] diceRolled = rollDice(card.getBudget());
		for (int i = 0, j = 0; i < card.getBudget(); i++, j++) {
			if (j == onCard.length) {
				j = 0;
			}
			if (onCard[j].isTaken()) {
				onCard[j].getPlayer().dollars += diceRolled[i];
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

	public static Player getActivePlayer() {
		return playerListOrder[playerIdx];
	}

	public static Player[] getPlayerList() {
		return playerListOrder;
	}

	public static int getDay() {
		return day;
	}

	// This we are using for testing
	private static void printArray(int[] arr) {
		System.out.print("[");
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i]);
			System.out.print(", ");
		}
		System.out.println("]");
	}

	public void endDay() {
		day++;
		BoardManager.resetBoard();
	}
}
