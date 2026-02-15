/*
//	Scenes are also Spaces that contain extra functionality for having roles and cards
//	They contain Roles and is where Players can work on Roles
*/

public class Scene extends Space {
	private Role untakenRoles[] = null;
	private Role takenRoles[] = null;
	private Card card = null;
	private int shots = 0;
	private int[][] takeAreasList = new int[3][];
	Scene(Card setCard, int shotAmt) {
		card = setCard;
		shots = shotAmt;
	}

	Scene(String name){
		this.name = name;
	}

	public static void main(String[] args) {
		System.out.println("Running Scene.java");
	}

	// Sets the card to a new card
	public void setCard(Card newCard) {
		card = newCard;
	}

	// Changes a role from the untaken array to the taken array
	public void changeToTaken(Role roleToChange) {
	}

	// Returns the untaken role list
	public Role[] getUntakenRoles() {
		return untakenRoles;
	}

	// Returns the taken role list
	public Role[] getTakenRoles() {
		return takenRoles;
	}

	// Returns whether the scene is complete or not (card is null)
	public boolean sceneComplete() {
		return true;
	}

	// Returns the shot amount
	public int getShots() {
		return shots;
	}

	public Card getCard() {
		return card;
	}

	public void setShots(int newShots) {
		shots = newShots;
	}

	public String getName() {
		return name;
	}

	public void setName(String newName){
		name = newName;
	}

	public void setTakeArea(int number, int[] areaVals){
		takeAreasList[number - 1] = areaVals;
	}

	public void createRoleLists(int size){
		untakenRoles = new Role[size];
		takenRoles = new Role[size];
	}
}
