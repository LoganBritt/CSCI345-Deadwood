/*
//	Scenes are also Spaces that contain extra functionality for having roles and cards
//	They contain Roles and is where Players can work on Roles
*/

public class Scene extends Space {
	private Role[] roleList = null;
	private Card card = null;
	private int baseShots = 0;
	private int shots = 0;
	private int[][] takeAreasList = new int[3][];

	// default constructor that takes a card and a shot counter
	Scene(Card setCard, int shotAmt) {
		card = setCard;
		baseShots = shotAmt;
		shots = baseShots;
	}

	// default constructor that takes a name for the scene
	Scene(String name) {
		this.name = name;
	}

	// Sets the card to a new card
	public void setCard(Card newCard) {
		card = newCard;
	}

	// Returns the list of roles
	public Role[] getRoles() {
		return roleList;
	}

	// Returns whether the scene is complete or not (card is null)
	public boolean sceneComplete() {
		return card == null;
	}

	// Returns the shot amount
	public int getShots() {
		return shots;
	}

	// Reverts the actve shot count to the normal amount
	public void resetShots() {
		shots = baseShots;
	}

	// gets the Card at the scene
	public Card getCard() {
		return card;
	}

	// sets the shot counter for the scene
	public void setShots(int newShots) {
		shots = newShots;
	}

	// sets the initial shot counter for the scene
	public void setBaseShots(int newShots) {
		baseShots = newShots;
	}

	// returns the same of the scene
	public String getName() {
		return name;
	}

	// sets the same for the scene
	public void setName(String newName) {
		name = newName;
	}

	// sets the area for the takes
	public void setTakeArea(int number, int[] areaVals) {
		takeAreasList[number - 1] = areaVals;
	}

	// creates a Role List for the scene
	public void createRoleLists(int size) {
		roleList = new Role[size];
	}

	// clears the Player's role
	public void clearRoles() {
		Role[] roles = roleList;
		for (int i = 0; i < roles.length; i++) {
			if (roles[i] != null && roles[i].getPlayer() != null) {
				roles[i].getPlayer().currRole = null;
				roles[i].setPlayer(null);
			}
		}
		roles = card.getRoles();
		for (int i = 0; i < roles.length; i++) {
			if (roles[i] != null && roles[i].getPlayer() != null) {
				roles[i].getPlayer().currRole = null;
				roles[i].setPlayer(null);
			}
		}
	}
}
