/*
//	This is an interface that Scenes and Casting inheirit
//	It represents a place on the board where Players can go to when moving on the board
*/

public abstract class Space {
	public boolean isTrailers = false;
	public String name;
	public Space neighborSpaces[] = null;
	public int xPos;
	public int yPos;
	public int height;
	public int width;

	public void printNeighbors() {
		System.out.println("Neighbors:");
		for(int i = 0; i < neighborSpaces.length; i++){
			System.out.println(neighborSpaces[i].name);
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
			Space newSpace = BoardManager.board.getSpaceByName(neighborNames[i]);
			if(newSpace == null){
				if(neighborNames[i].equals("trailers")){
					newSpace = new Trailers();
				}else if(neighborNames[i].equals("office")){
					newSpace = new Casting();
				}else{
					newSpace = new Scene(neighborNames[i]);
				}
				BoardManager.board.getSpaceList().add(newSpace);
			}
			neighborSpaces[i] = newSpace;
		}
	}
}
