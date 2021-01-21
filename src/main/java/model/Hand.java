package model;

/**
 * Represents a hand of cards.
 *
 * @author
 * @version
 */
public class Hand {
    /**
     * Integer which represents the maximum amount of cards in one hand.
     * Created so the concept of hand-object can be used in other card games as well.
     */
    private int maxCards;
    /**
     * An array of Card-objects.
     */
    private Card[] hand;

    /**
     * Initializes a new Hand-object.
     * @param maxCards determines the maximum amount of Card-objects the Hand-object can store.
     */
    public Hand(int maxCards) {
        this.maxCards = maxCards;
        this.hand = new Card[this.maxCards];
    }

    /**
     * Sets cards in hand
     * @param cards array of cards
     */
    public void setHand(Card[] cards) {
        this.hand = cards;
    }

    public void printHand() {
        for (Card card: hand) {
            System.out.println(card.toString());
        }
    }

}
