/*
//	This is the main engine of the game
//	GameManager maintains turn order and the flow of the game
*/

import java.util.Random;
import java.util.Arrays;

public class GameManager{
	private BoardManager bm;
	private UIManager ui;
	private int day;
	private Player[] playerListOrder;
	private int playerAmt;
	private int playerIdx = 0;

        public static void main(String[] args){
                System.out.println("Running GameManager.java");
        }

	//Is called to start the game
	//Creates the objects for the game to play with
	public void startGame(){
		int[] diceReturn = rollDice(5);
		System.out.println("Rolling 5 dice:");
		System.out.println(diceReturn.toString);
	}

	public int getDay(){
		return day;
	}

	public void changeTurn(){
		if(playerIdx+1 == playerAmt){
			playerIdx = 0;
		}else{
			playerIdx++;
		}
	}

	public Player getActivePlayer(){
		return playerListOrder[playerIdx];
	}

	public Player[] getPlayerList(){
		return playerListOrder;
	}

	public void distributeBonuses(Player[] bonusPlayers, int budget){

	}

	public int[] rollDice(int dieAmt){
		Random rand = new Random();
		int[] retVals;
		for(int i = 0; i < dieAmt; i++){
			retVals[i] = rand.nextInt(1, 7);
		}

		return Arrays.sort(retVals);
	}

}
