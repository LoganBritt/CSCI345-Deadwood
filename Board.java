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

	// Default contructor that sets the spaces to the spaceSet
	Board(ArrayList<Space> spaceSet) {
		spaces = spaceSet;
	}

	// this gets the list of spaces
	public ArrayList<Space> getSpaceList() {
		return spaces;
	}

	// this gets the Trailer
	public Trailers getTrailers() {
		return trailers;
	}

	// this gets the Casting Office
	public Casting getCasting() {
		return casting;
	}

	// this sets the casting office to the Casting office
	public void setCasting(Casting newCasting) {
		casting = newCasting;
	}

	// this sets any space object to the Casting Office
	public void setCasting(Space newCasting) {
		casting = (Casting) newCasting;
	}

	// this sets the trailers to Trailers
	public void setTrailers(Trailers newTrailers) {
		trailers = newTrailers;
	}

	// this sets any space object to the Trailers
	public void setTrailers(Space newTrailers) {
		trailers = (Trailers) newTrailers;
	}

	// this looks through all of the space objects and returns the Space with passed
	// name
	public Space getSpaceByName(String name) {
		for (int i = 0; i < spaces.size(); i++) {
			if (spaces.get(i) != null && spaces.get(i).name != null && name != null
					&& spaces.get(i).name.equals(name)) {
				return spaces.get(i);
			}
		}
		return null;
	}
}
