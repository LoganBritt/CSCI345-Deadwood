/*
//	This is an interface that Scenes and Casting inheirit
//	It represents a place on the board where Players can go to when moving on the board
*/


public abstract class Space{
	public boolean isTrailers = false;
	public Space neighborSpaces[] = null;

	public abstract void setShots(int newShots);
	public abstract int getShots();
	public abstract Card getCard();
}
