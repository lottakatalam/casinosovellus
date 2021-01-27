package model;

/**
 * Represents a player of the game
 *
 */
public class Player {

    /**
     * Hand of a player
     */
    private Hand hand;

    private int amountOfWins = 0;

    /**
     * Initializes new Player-object
     *
     */
    public Player() {
        this.hand = new Hand();
    }

    public void addCard(Card card) {
        hand.addCard(card);
    }

    /**
     * Sets cards to players hand and prints the hand.
     * @param cardsToHand Array of cards
     *
     */
   /* public void setHand(Card [] cardsToHand) {
        hand.setHand(cardsToHand);
        Logger.log(Logger.LogLevel.DEV, "Players hand:");
        hand.printHand();
    }*/

    public void clearHand() {
        hand.clearHand();
    }

    public void printHand() {
        Logger.log(Logger.LogLevel.DEV, "Players hand: ");
        hand.printHand();
    }

    public int calculateHand() {
        return hand.calculateTotal();
    }

    public void addWin() {
        amountOfWins++;
    }

    public int getWins() {
        return amountOfWins;
    }
}
