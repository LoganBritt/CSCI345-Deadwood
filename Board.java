/*
//	This represents the Board object
//	There really should only ever be one of these created at a time
//	It is the object that contains the spaces on the board
*/

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Board {
	private ArrayList<Space> spaces;
	private BufferedImage background;

	Board(BufferedImage newBackground, ArrayList<Space> spaceSet) {
		spaces = spaceSet;
		background = newBackground;
	}

	public static void main(String[] args) {
		System.out.println("Running Board.java");
	}

	public BufferedImage getBoard() {
		return background;
	}

	public ArrayList<Space> getSpaceList() {
		return spaces;
	}

	 

}
