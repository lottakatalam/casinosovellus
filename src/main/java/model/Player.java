package model;

import java.util.ArrayList;

/**
 * Represents a player of the game
 *
 */
public class Player {

    /**
     * Hand of a player
     */
    private Hand hand;

    /**
     * Initializes new Player-object
     *
     */
    public Player() {
        this.hand = new Hand();
    }

    /**
     * Sets cards to players hand and prints the hand.
     * @param cardsToHand Array of cards
     *
     */
    public void setHand(Card [] cardsToHand) {
        hand.setHand(cardsToHand);
        Logger.log(Logger.LogLevel.DEV, "Players hand:");
        hand.printHand();
    }
}
