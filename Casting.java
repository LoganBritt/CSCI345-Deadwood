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
        public static void main(String[] args) {
                System.out.println("Running Casting.java");
        }

        // returns the money cost of the selected rank
        public int moneyCost(int rank) {
                return moneyCostSet[rank];
        }

        // returns the credit cost of the selected rank
        public int creditCost(int rank) {
                return creditCostSet[rank];
        }

	public void setUpgrade(int level, String currency, int amt){
		if(currency.equals("credit")){
			creditCostSet[level - 2] = amt;
		}else if(currency.equals("dollar")){
			moneyCostSet[level - 2] = amt;
		}else{
			System.out.println("There was a problem with the currency");
		}
	}

	public void setArea(int[] areaVals){
		xPos = areaVals[0];
		yPos = areaVals[1];
		height = areaVals[2];
		width = areaVals[3];
	}

}
