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
		Scene scene = null;
		if(space instanceof Scene){ scene = (Scene) space;}

		// Bonus for Off Card
		Role[] list = scene.getTakenRoles();
		for (int i = 0; i < list.length; i++) {
			list[i].getPlayer().dollars += list[i].getRank();
		}
		// Bonus for On Card
		Card card = scene.getCard();
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

	public static int getActvPlyrIdx(){
		return playerIdx;
	}

	public static Player[] getPlayerList() {
		return playerListOrder;
	}

	public static int getDay() {
		return day;
	}

	public static void setPlayerAmt(int amt){
		playerAmt = amt;
	}

	//Create each player object
	//Also sets the day depending on how many players are playing
	//Precondition: Days is set and the trailers are an object to set to
	public static void createPlayers(){
		playerListOrder = new Player[playerAmt];

		if(playerAmt < 4){ day = 3;}else{day = 4;}
		for(int i = 0; i < playerAmt; i++){
			if(playerAmt < 5){
				playerListOrder[i] = new Player();
			}else if(playerAmt < 6){
				playerListOrder[i] = new Player(2, 0);
			}else if(playerAmt < 7){
				playerListOrder[i] = new Player(4, 0);
			}else{
				playerListOrder[i] = new Player(0, 2);
			}
		}
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

	//Move on to the next day
	public static void endDay() {
		day++;
		BoardManager.resetBoard();
	}

	//Ends the game
	public static void endGame(){
		day = 0;
		System.out.println("Game Over");
		UIManager.printStats(null);
		System.out.println("Thanks for playing");
	}
}
