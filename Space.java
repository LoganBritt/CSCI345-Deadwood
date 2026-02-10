/*
//	This is an interface that Scenes and Casting inheirit
//	It represents a place on the board where Players can go to when moving on the board
*/

public abstract class Space {
	public boolean isTrailers = false;
	public String name;
	public Space neighborSpaces[] = null;

	public void printNeighbors() {
		System.out.println("Neighbors:");
	}
}
