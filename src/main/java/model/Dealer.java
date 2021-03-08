package model;

import java.io.Serializable;

/**
 * Represents a dealer of the game
 */
public class Dealer implements Serializable {

    private Hand hand;

    /**
     * Constructor of Dealer
     */
    public Dealer() {
        this.hand = new Hand();
    }

    /**
     * Gets the dealer's hand
     * @return - The dealer's hand
     */
    public Hand getHand() {
        return this.hand;
    }

    /**
     * Determines if dealer has a blackjack
     */

    public boolean isBlackjack() {
        return hand.calculateTotal() == 21;
    }
}
