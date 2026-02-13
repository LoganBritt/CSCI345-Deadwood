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
	private boolean onCard = false;

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
		if(playerHere != null){ return false;}
		if(player.rank <= rank) { return false;}

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

        public int getRank() {
                return rank;
        }

	public String getTitle(){
		return title;
	}

	public String getLine(){
		return line;
	}

	public boolean isOnCard(){
		return onCard;
	}

}
