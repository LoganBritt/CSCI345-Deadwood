/*
//	Casting is a Space that contains the code for the Casting Office space on the board
//	It includes code for making rank exchanges with players 
//      No default values are needed, therefore we decided we don't need an added default constructor
*/

public class Casting extends Space{

        private int[] moneyCostSet = {4, 10, 18, 28, 40};
        private int[] creditCostSet = {5, 10, 15, 20, 25};
        public static void main(String[] args){
                System.out.println("Running Casting.java");
        }

        // returns the money cost of the selected rank
        public int moneyCost(int rank){
                return 0;
        }

        //returns the credit cost of the selected rank
        public int creditCost(int rank){
                return 0;
        }



}
