/*
//	This is the Class that holds 40 Card objects
//	It also includes methods to draw the cards and remove them from the deck
*/
import java.util.Random;
public class Deck {

        private int remainingCards = 0;
        private Card[] cardSet = new Card[40];

        //adds a card to the deck
        public void addCard(Card card){
               cardSet[remainingCards] = card;
               remainingCards++;
        }

        // takes a card from the deck
        public Card takeCard() {
		Random r = new Random();
		int rIdx = r.nextInt(0, remainingCards);
                return cardSet[rIdx];
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
