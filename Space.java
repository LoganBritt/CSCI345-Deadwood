/*
//	This is an interface that Scenes and Casting inheirit
//	It represents a place on the board where Players can go to when moving on the board
*/

import java.util.ArrayList;
public abstract class Space {
	public boolean isTrailers = false;
	public String name;
	public Space neighborSpaces[] = null;
	public int xPos;
	public int yPos;
	public int height;
	public int width;

	public void printNeighbors() {
		System.out.println("\u001b[4mNeighbors:\u001b[0m");
		for(int i = 0; i < neighborSpaces.length; i++){
			if(neighborSpaces[i] != null){
				System.out.println("  * " + neighborSpaces[i].name);
			}
		}
	}

	public void setVals(int[] vals){
		xPos = vals[0];
		yPos = vals[1];
		height = vals[2];
		width = vals[3];
	}

	public void setNeighbors(String[] neighborNames){
		neighborSpaces = new Space[neighborNames.length];
		for(int i = 0; i < neighborNames.length; i++){
			if(neighborNames[i] != null){
				Space newSpace = BoardManager.board.getSpaceByName(neighborNames[i]);
				neighborSpaces[i] = newSpace;
			}

		}
	}

}
