/*
//	This represents the Board object
//	There really should only ever be one of these created at a time
//	It is the object that contains the spaces on the board
*/

import java.util.ArrayList;

public class Board {
	private ArrayList<Space> spaces;
	private Trailers trailers;
	private Casting casting;

	Board(ArrayList<Space> spaceSet) {
		spaces = spaceSet;
	}

	public static void main(String[] args) {
		System.out.println("Running Board.java");
	}

	public ArrayList<Space> getSpaceList() {
		return spaces;
	}

	public Trailers getTrailers(){
		return trailers;
	}

	public Casting getCasting(){
		return casting;
	}

	public void setCasting(Casting newCasting){
		casting = newCasting;
	}

	public void setCasting(Space newCasting){
		casting = (Casting) newCasting;
	}

	public void setTrailers(Trailers newTrailers){
                trailers = newTrailers;
        }

        public void setTrailers(Space newTrailers){
                trailers = (Trailers) newTrailers;
        }

	public Space getSpaceByName(String name){
		for(int i = 0; i < spaces.size(); i++){
			if(spaces.get(i).name.equals(name)){
				return spaces.get(i);
			}
		}
		return null;
	}
}
