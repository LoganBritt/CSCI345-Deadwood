/*
//	This is the Class that holds 40 Card objects
//	It also includes methods to draw the cards and remove them from the deck
*/

public class Deck {

        private int remainingCards = 0;
        private Card[] cardSet = new Card[40];

        public static void main(String[] args) {
                System.out.println("Running Deck.java");
        }

        //adds a card to the deck
        public void addCard(Card card){
               cardSet[remainingCards] = card;
               remainingCards++;
        }

        // takes a card from the deck
        public Card takeCard() {
                return null;
        }

        // Returns the amount of remaining cards
        public int getCardAmt() {
                return remainingCards;
        }

        //gets the CardSet
        public Card[] getCardSet(){
                return cardSet;
        }

}
