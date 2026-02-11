
/*
//	This Class represents each of the players playing the game
//	It contains the specific data that belongs to each player
//	Player related actions (Acting, rehearsing moving, etc), are called from here

//  TakeRole is handled in Role, not in Player
*/
import java.util.Random;

public class Player {
	public int dollars = 0;
	public int credits = 0;
	public int rank = 1;
	public int rehearseTokens = 0;
	public Space currLocation;
	public Role currRole;

	public static void main(String[] args) {
		System.out.println("Running Player.java");
	}

	// For 2-4 player games
	Player() {
		currLocation = BoardManager.getTrailers();
	}

	// For 5-8 player games
	Player(int creditUpgrade, int rankUpgrade) {
		currLocation = BoardManager.getTrailers();
		credits = creditUpgrade;
		rank = rankUpgrade;
	}

	// Move: Player can move to an adjacent scene
	public void move(Space newLocation) {
		currLocation = newLocation;
	}

	// Act: Player can choose to act, depending on success or fail, the player will
	// revieve rewards for working on/off card
	public void act(boolean onCard) {
		Scene activeScene = null;
		if (currLocation instanceof Scene) {
			activeScene = (Scene) currLocation;
		}
		if (activeScene != null) {
			Random r = new Random();
                	int roll = r.nextInt(1, 7);
                	int budget = activeScene.getCard().getBudget();
			if (roll + rehearseTokens >= budget) {
				activeScene.setShots(activeScene.getShots() - 1);
				clearTokens();
				if (onCard) {
					credits += 2;
				} else {
					dollars++;
					credits++;
				}
			}

			else if (!onCard) {
				dollars++;
			}
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
