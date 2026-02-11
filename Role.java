/*
//	This is the Class for creating Role objects
//	These will belong to Cards and Scenes
//	It contians info related to that specfic role
*/

public class Role {

        private int rank = 1;
        private Player playerHere;
	private String title = "nameless extra";
	private String line = "indistinct murmuring";

        public static void main(String[] args) {
                System.out.println("Running Role.java");
        }

        Role(int newRank) {
                rank = newRank;
        }

	Role(int newRank, String newTitle, String newLine){
		rank = newRank;
		title = newTitle;
		line = newLine;
	}

        // checks to see if a player can take the role
        public boolean canTake(Player player) {
                return true;
        }

        // compares the rank of the role to the rank of the player
        // helper method to canTake()
        private boolean levelCompare(int compareRank) {
                return true;
        }

        // checks to see if the role is taken
        public boolean isTaken() {
                return true;
        }

        // sets the playerHere to player
        public void setPlayer(Player player) {

        }

        // returns the playerHere
        public Player getPlayer() {
                return playerHere;
        }

        public int getRank() {
                return rank;
        }

	public String getTitle(){
		return title;
	}

	public String getLine(){
		return line;
	}

}
