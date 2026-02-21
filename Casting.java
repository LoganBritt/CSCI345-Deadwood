/*
//	Casting is a Space that contains the code for the Casting Office space on the board
//	It includes code for making rank exchanges with players 
//      No default values are needed, therefore we decided we don't need an added default constructor
*/

public class Casting extends Space {

	private int[] moneyCostSet = { 4, 10, 18, 28, 40 };
	private int[] creditCostSet = { 5, 10, 15, 20, 25 };
	private int xPos;
	private int yPos;
	private int height;
	private int width;

	// Default constructor with the name of the casting office that is parsed from
	// the card
	Casting(String name) {
		this.name = name;
	}

	// returns the money cost of the selected rank
	public int moneyCost(int rank) {
		return moneyCostSet[rank];
	}

	// returns the credit cost of the selected rank
	public int creditCost(int rank) {
		return creditCostSet[rank];
	}

	// Sets the upgrade to a credit transaction or a dollar transaction and sets the
	// level of the upgrade and the cost needed
	public void setUpgrade(int level, String currency, int amt) {
		if (currency.equals("credit")) {
			creditCostSet[level - 2] = amt;
		} else if (currency.equals("dollar")) {
			moneyCostSet[level - 2] = amt;
		} else {
			System.out.println("There was a problem with the currency");
		}
	}

	//sets the Area of the Casting Office space
	public void setArea(int[] areaVals) {
		xPos = areaVals[0];
		yPos = areaVals[1];
		height = areaVals[2];
		width = areaVals[3];
	}

}
