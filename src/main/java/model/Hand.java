package model;


public class Hand {
    /**
     * Integer which represents the maximum amount of cards in hand.
     * Created so the concept of hand-object can be used in other card games as well.
     */
    private int maxCards;
    /**
     * An array of Card-objects.
     */
    private Card[] hand;

    public Hand(int maxCards) {
        this.maxCards = maxCards;
        this.hand = new Card[this.maxCards];
    }

}
