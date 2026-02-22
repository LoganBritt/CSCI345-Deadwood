/*
//	This is the Class for creating Role objects
//	These will belong to Cards and Scenes
//	It contians info related to that specfic role
*/

public class Role {

	private int rank = 1;
	private Player playerHere = null;
	private String title = "nameless extra";
	private String line = "indistinct murmuring";
	private boolean onCard = false;
	private int xPos;
	private int yPos;
	private int height;
	private int width;

	// default contructor that takes a rank
	Role(int newRank) {
		rank = newRank;
	}

	// default constructor that takes a rank, title, and line
	Role(int newRank, String newTitle, String newLine) {
		rank = newRank;
		title = newTitle;
		line = newLine;
	}

	// default contrstuctor that takes a rank, title, line, and area values
	Role(int newRank, String newTitle, String newLine, int[] areaVals) {
		rank = newRank;
		title = newTitle;
		line = newLine;
		xPos = areaVals[0];
		yPos = areaVals[1];
		height = areaVals[2];
		width = areaVals[3];
	}

	// checks to see if a player can take the role
	public boolean canTake(Player player) {
		if (playerHere != null) {
			return false;
		}
		if (player.rank <= rank) {
			return false;
		}

		return true;
	}

	// checks to see if the role is taken
	public boolean isTaken() {
		return (playerHere != null);
	}

	// sets the playerHere to player
	public void setPlayer(Player player) {
		playerHere = player;
	}

	// returns the playerHere
	public Player getPlayer() {
		return playerHere;
	}

	// gets the rank of the role
	public int getRank() {
		return rank;
	}

	// gets the title of the role
	public String getTitle() {
		return title;
	}

	// gets the line of the role
	public String getLine() {
		return line;
	}

	// function to make the role on card
	public void makeOnCard() {
		onCard = true;
	}

	// checks to see if role is on the card
	public boolean isOnCard() {
		return onCard;
	}

}
