/*
//	This Class represents each of the players playing the game
//	It contains the specific data that belongs to each player
//	Player related actions (Acting, rehearsing moving, etc), are called from here

//  TakeRole is handled in Role, not in Player
*/
import java.util.Random;
public class Player {

	public int dollars;
	public int credits;
	public int rank = 1;
	public int rehearseTokens;
	public Space currLocation;

	public static void main(String[] args) {
		System.out.println("Running Player.java");
	}

	Player(Space trailers){
		currLocation = trailers;
	}

	// Move: Player can move to an adjacent scene
	public void move(Space newLocation) {
		currLocation = newLocation;
	}

	// Act: Player can choose to act, depending on success or fail, the player will
	// revieve rewards for working on/off card
	public void act(boolean onCard) {
		Random r = new Random();
		int roll = r.nextInt(1,7);
		int budget = currLocation.getCard().getBudget();

		if (roll + rehearseTokens >= budget){
			currLocation.setShots(currLocation.getShots() - 1);
			clearTokens();
			if (onCard){
				credits += 2;
			}
			else{
				dollars++;
				credits++;
			}
		}

		else if (!onCard){
			dollars++;
		}

	}

	// Rehearse: Adds practice chip to the die, gives +1 to all die rolls
	public void rehearse() {
		rehearseTokens++;
	}

	// Upgrade: Pay $/Credits to upgrade
	public void upgrade(int newRank) {
		rank = newRank;
	}

	private void clearTokens() {
		rehearseTokens = 0;
	}

}
