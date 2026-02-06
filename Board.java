/*
//	This represents the Board object
//	There really should only ever be one of these created at a time
//	It is the object that contains the spaces on the board
*/

import java.awt.image.BufferedImage;

public class Board {
	private Space[] spaces;
	private BufferedImage background;

	Board(BufferedImage newBackground, Space[] spaceSet) {
		spaces = spaceSet;
		background = newBackground;
	}

	public static void main(String[] args) {
		System.out.println("Running Board.java");
	}

	public BufferedImage getBoard() {
		return background;
	}

	public Space[] getSpaceList() {
		return spaces;
	}

}
