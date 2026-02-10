/*
//	BoardManager contains the functionality for interacting and changing things on the Board.
//	This is the primary reference for the Board object.
*/

import java.util.ArrayList;
public class BoardManager {
	private static Board board;
	private static Deck deck;
	private static Space trailers;

	public static void main(String[] args) {
		System.out.println("Running BoardManager.java");
	}

	// Assigns the trailer space for easy frequent reference
	public static void setTrailers(Space trailrs) {
		trailers = trailrs;
	}

	//Returns the object that is set to the trailers object
	public static Space getTrailers(){
		return trailers;
	}

	// Returns the amount of cards remianing in the deck
	public static int cardAmt() {
		return deck.getCardAmt();
	}

	// Resets all the cards on the board
	private static void resetCards() {
	}

	// Resets the board for the next day
	public static void resetBoard() {
	}

	// Resets the players' position
	private static void resetPlayers() {
	}

	// Moves a player to another Space on the board
	public static void movePlayer(Player playerToMove, Space spaceToMoveTo) {
	}

	// Removes a card from a space on the board
	public static void removeCard(Space space) {
	}

	public static void createBoard(){
		ArrayList<Space> spaces = new ArrayList<Space>();
		board = new Board(null, spaces);
		for(int i = 0; i < 12; i++){
			if(i == 0){
				spaces.add(new Space(true));
				trailers = spaces.get(0);
			}else if(i == 11){
				spaces.add(new Casting());
			}else{
				spaces.add(new Scene(null, 0));
			}
		}
	}

}
