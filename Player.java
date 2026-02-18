
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
	public void move(String newLocationName) {
		if(GameManager.getPlayerMoved()) return;
		Space[] neighborList = currLocation.neighborSpaces;
		for(int i = 0; i < neighborList.length; i++){
//			System.out.println("Testing '" + newLocationName + "' against '" + neighborList[i].name);
			if(newLocationName.equals((neighborList[i].name).toLowerCase())){
				currLocation = neighborList[i];
				GameManager.getPlayerMoved();
				System.out.println("Moved to " + newLocationName);
				System.out.println("Type 'Space' to see details");
				return;
			}
		}
		System.out.println(newLocationName  + " is not a neighbor of " + currLocation.name);
		System.out.println("To see " + currLocation.name + "'s neighbors, type 'Space'");
	}

	// Act: Player can choose to act, depending on success or fail, the player will
	// revieve rewards for working on/off card
	public boolean act(boolean onCard) {
		boolean success = false;
		Scene activeScene = null;
		if (currLocation instanceof Scene) {
			activeScene = (Scene) currLocation;
		}
		if (activeScene != null) {
			GameManager.makeActed();
			Random r = new Random();
                	int roll = r.nextInt(1, 7);
                	int budget = activeScene.getCard().getBudget();
			if (roll + rehearseTokens >= budget) {
				success = true;
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
		return success;
	}

	// Rehearse: Adds practice chip to the die, gives +1 to all die rolls
	public void rehearse() {
		if(currRole == null) return;
		if(!(currLocation instanceof Scene)) return;
		if(GameManager.getPlayerActed()) return;
		GameManager.makeActed();
		rehearseTokens++;
	}

	// Upgrade: Pay $/Credits to upgrade
	public void upgrade(int newRank, boolean useDollars) {
		if(!(currLocation instanceof Casting)){
			System.out.println("Sorry, you can't make an exchange here.");
			System.out.println("Please move to the Casting Office to make an exchange");
			return;
		}
		Casting casting = (Casting) currLocation;
		int exchangeRef = 0;
		if(useDollars){
			exchangeRef = casting.moneyCost(newRank - 2);
			if(exchangeRef <= dollars){
				System.out.println("Making exchange...");
				dollars -= exchangeRef;
				rank = newRank;
			}else{
				System.out.println("I'm sorry. You do not have enough dollars to afford that upgrade");
				System.out.println("Please try again when you have " + exchangeRef + " dollars");
			}
		}else{
			exchangeRef = casting.creditCost(newRank - 2);
                        if(exchangeRef <= credits){
                                System.out.println("Making exchange...");
                                credits -= exchangeRef;
                                rank = newRank;
                        }else{
                                System.out.println("I'm sorry. You do not have enough credits to afford that upgrade");
                                System.out.println("Please try again when you have " + exchangeRef + " credits");
                        }
		}
	}

	private void clearTokens() {
		rehearseTokens = 0;
	}

}
