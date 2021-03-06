package model;

import java.util.ArrayList;

/**
 * Represents a player of the game
 */
public class Player {

    /**
     * Hand of a player
     */
    private Hand hand;

    private Hand splittedHand;

    private int amountOfWins = 0;
    /**
     * An integer which describes the total amount of onlinecash the player has
     */
    private int currency;
    /**
     * An integer which describes the bet the player has in one BlackJack-round
     */
    private int bet;

    /**
     * Initializes new Player-object
     */
    public Player(int currency) {
        this.hand = new Hand();
        this.currency = currency;
    }

    public Hand getHand() {
        return this.hand;
    }

    /**
     * A method which adds a card-object to players hand-object
     *
     * @param card the card-object to be added
     */
    /*public void addCard(Card card) {
        hand.addCard(card);
    }*/

    /**
     * Sets cards to players hand and prints the hand.
     *
     * @param cardsToHand Array of cards
     */
    /*
    public void setHand(Card[] cardsToHand) { //tätäkään ei taideta tarvita?
        hand.setHand(cardsToHand);
        Logger.log(Logger.LogLevel.DEV, "Players hand:");
        hand.printHand();
    }*/

    /**
     * Clears the players hand-object completely
     */
    /*
    public void clearHand() {
        hand.clearHand();
    }*/

    /**
     * Prints the players hand-object by calling the printHand-method from Hand-class
     */
    /*
    public void printHand() {
        Logger.log(Logger.LogLevel.DEV, "Players hand:");
        hand.printHand();
    }*/

    /**
     * Calculates the total value (sum of card ranks) of the players hand-object
     *
     * @return the total sum of card-objects ranks in a hand
     */
    /*
    public int calculateHand() {
        return hand.calculateTotal();
    }*/

    /**
     * Adds one win to players amountOfWins-attribute and increases the amount of online cash the player has
     */
    public void win() {
        amountOfWins++;
        this.currency += (bet * 2);
        if (UserCredentialHandler.getInstance().isLoggedIn()) {
            UserCredentialHandler.getInstance().getLoggedInUser().setBalance(this.currency);
        }
    }

    /**
     * Decreases the amount of online cash the player has
     */
    public void lose() {
        //this.currency -= bet;
        if (UserCredentialHandler.getInstance().isLoggedIn()) {
            UserCredentialHandler.getInstance().getLoggedInUser().setBalance(this.currency);
        }
    }

    public int getWins() {
        return amountOfWins;
    }


    public int getCurrency() {
        return this.currency;
    }

    /**
     * Sets the bet-attribute to a player if the player has enough online cash
     *
     * @param b integer which describes the value of a bet
     * @return true if the bet is set, false if the player doesn't have enough online cash for setting the bet
     */
    public boolean setBet(int b) {
        if (this.currency < b) {
            Logger.log(Logger.LogLevel.PROD, "Saldo ("+currency+") ei riitä panoksen ("+b+") asettamiseen.");
            return false;
        } else {
            this.bet = b;
            this.currency -= bet;
            return true;
        }
    }

    public void doubleBet() {
        this.currency -= bet;
        this.bet = bet * 2;
    }

    public int getBet() {
        return bet;
    }
}
