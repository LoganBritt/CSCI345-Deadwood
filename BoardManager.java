/*
//	BoardManager contains the functionality for interacting and changing things on the Board.
//	This is the primary reference for the Board object.
*/

import java.util.ArrayList;

public class BoardManager {
	public static Board board;
	private static Deck deck;
	private static Space trailers;

	// Assigns the trailer space for easy frequent reference
	public static void setTrailers(Space trailrs) {
		trailers = trailrs;
	}

	// Returns the object that is set to the trailers object
	public static Space getTrailers() {
		return trailers;
	}

	// Returns the amount of cards remianing in the deck
	public static int cardAmt() {
		return deck.getCardAmt();
	}

	// Resets all the cards on the board
	private static void resetCards() {
		ArrayList<Space> spaces = board.getSpaceList();
		for (int i = 0; i < spaces.size(); i++) {
			if (spaces.get(i) instanceof Scene) {
				Scene scene = (Scene) spaces.get(i);
				scene.setCard(deck.takeCard());
			}
		}
	}

	// Resets the board for the next day
	public static void resetBoard() {
		resetCards();
		if (GameManager.getPlayerList() != null) {
			resetPlayers();
		}
	}

	// Resets the players' position
	private static void resetPlayers() {
		Player[] playerList = GameManager.getPlayerList();
		for (int i = 0; i < playerList.length; i++) {
			playerList[0].move("trailer");
		}
	}

	// Moves a player to another Space on the board
	public static void movePlayer(Player playerToMove, Space spaceToMoveTo) {
	}

	// Removes a card from a space on the board
	public static void removeCard(Space space) {
		if (space instanceof Scene) {
			Scene scene = (Scene) space;
			scene.setCard(null);
		}
	}

	// creates a new deck for the gameplay, this also calls the parser for said
	// deck.
	public static void createDeck() {
		Deck newDeck = new Deck();
		for (int i = 0; i < 40; i++) {
			Card newCard = new Card(3, new Role[4]);
			newDeck.addCard(newCard);
		}
		deck = newDeck;
		Parse.parseCard(deck);
	}

	// this creates the board for the game
	public static void createBoard() {
		ArrayList<Space> spaces = new ArrayList<Space>();
		board = new Board(spaces);
		Parse.parseBoard(board);
		resetBoard();
	}

	// this Prints all of the spaces and all of their neighbors
	public static void printAllSpaces() {
		System.out.println();
		System.out.println("******************************************");
		for (int i = 0; i < board.getSpaceList().size(); i++) {
			Space workingSpace = board.getSpaceList().get(i);
			if (workingSpace instanceof Scene) {
				workingSpace = (Scene) workingSpace;
			} else if (workingSpace instanceof Casting) {
				workingSpace = (Casting) workingSpace;
			} else if (workingSpace instanceof Trailers) {
				workingSpace = (Trailers) workingSpace;
			}
			System.out.println(workingSpace.name + " info:");
			System.out.println("Neighbor names:");
			for (int j = 0; j < workingSpace.neighborSpaces.length; j++) {
				if (workingSpace.neighborSpaces[j] != null) {
					System.out.println("Neighbor " + (j + 1) + ": " + workingSpace.neighborSpaces[j].name);
				} else {
					System.out.println("Neighbor " + (j + 1) + ": null");
				}
			}
			System.out.println();
		}
		System.out.println("******************************************");
		System.out.println();
	}
}
