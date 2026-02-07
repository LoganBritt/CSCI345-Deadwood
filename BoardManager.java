/*
//	BoardManager contains the functionality for interacting and changing things on the Board.
//	This is the primary reference for the Board object.
*/

public class BoardManager {
	private Board board;
	private UIManager ui;
	private Deck deck;
	private Space trailers;

	BoardManager(UIManager uiSet, Board newBoard, Deck newDeck) {
		ui = uiSet;
		board = newBoard;
		deck = newDeck;
	}

	public static void main(String[] args) {
		System.out.println("Running BoardManager.java");
	}

	// Assigns the trailer space for easy frequent reference
	public void setTrailers(Space trailrs) {
		trailers = trailrs;
	}

	// Returns the amount of cards remianing in the deck
	public int cardAmt() {
		return deck.getCardAmt();
	}

	// Resets all the cards on the board
	private void resetCards() {
	}

	// Resets the board for the next day
	public static void resetBoard() {
	}

	// Resets the players' position
	private void resetPlayers() {
	}

	// Moves a player to another Space on the board
	public void movePlayer(Player playerToMove, Space spaceToMoveTo) {
	}

	// Removes a card from a space on the board
	public void removeCard(Space space) {
	}

}
