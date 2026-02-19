/*
//	Scenes are also Spaces that contain extra functionality for having roles and cards
//	They contain Roles and is where Players can work on Roles
*/

public class Scene extends Space {
	private Role[] roleList = null;
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

	//Returns the list of roles
	public Role[] getRoles(){
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
		roleList = new Role[size];
	}

	public void clearRoles(){
		Role[] roles = roleList;
		for (int i = 0; i < roles.length; i++){
			if (roles[i].getPlayer() != null){
				System.out.println("Initial Player Role: " + roles[i].getPlayer().currRole);
				roles[i].getPlayer().currRole = null;
				roles[i].setPlayer(null);
				System.out.println("Updated Player Role " + roles[i].getPlayer().currRole);
			}
		}
		roles = card.getRoles();
		for (int i = 0; i < roles.length; i++){
			if (roles[i].getPlayer() != null){
				System.out.println("Initial card role: " + roles[i].getPlayer().currRole);
				roles[i].getPlayer().currRole = null;
				roles[i].setPlayer(null);
				System.out.println("Updated card Role" + roles[i].getPlayer().currRole);
			}
		}
	}
}
