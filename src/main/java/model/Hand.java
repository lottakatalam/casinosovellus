package model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a hand of cards.
 */
public class Hand {
    /**
     * An arraylist of Card-objects.
     */
    private ArrayList<Card> hand;
    private ArrayList<Card> splittedHand;

    /**
     * Initializes a new Hand-object which stores Card-obejcts in an arraylist
     */
    public Hand() {
        this.hand = new ArrayList<>();
        this.splittedHand = new ArrayList<>();
    }

    /**
     * @return integer which represents the amount of Card-objects in hand
     */
    public int getNumberOfCards() {
        return this.hand.size();
    }

    /**
     * Gets the hand
     * @return - The hand
     */
    public ArrayList<Card> getHand() {
        return hand;
    }

    /**
     * Gets the splitted hand
     * @return - The splitted hand
     */
    public ArrayList<Card> getSplittedHand() {
        return splittedHand;
    }

    /**
     * Splits the hand to two hands
     */
    public void splitHand() {
        Card splittedCard = this.hand.get(1);
        splittedHand.add(splittedCard);
        this.hand.remove(1);
    }

    /**
     * Prints all of the card-objects (in their own rows) in a hand by calling the toString()-method in Card-class
     */
    public void printHand() {
        for (Card card : hand) {
            System.out.println(card.toString());
        }
    }

    /**
     * Prints the splitted hand
     */
    public void printSplittedHand() {
        for (Card card : splittedHand) {
            System.out.println(card.toString());
        }
    }


    /**
     * Calculates the sum of card values in hand
     * Decides whether ace is 1 or 11
     *
     * @return the total value of hand
     */
    public int calculateTotal() {
        int total = 0;
        boolean aceFlag = false;
        for (int i = 0; i < hand.size(); i++) {
            int value = hand.get(i).getRank();
            if (value > 10) {
                value = 10;
            } else if (value == 1) {
                aceFlag = true;
            }
            total += value;
        }
        if (aceFlag && total + 10 <= 21) { // Only for dealer. Player decides the value of ace.
            total += 10;
        }
        return total;
    }

    /**
     * Calculates the sum of card values in splitted hand
     * @return - the total value of the splitted hand
     */
    public int calculateSplittedTotal() {
        int total = 0;
        boolean aceFlag = false;
        for (int i = 0; i < splittedHand.size(); i++) {
            int value = splittedHand.get(i).getRank();
            if (value > 10) {
                value = 10;
            } else if (value == 1) {
                aceFlag = true;
            }
            total += value;
        }
        if (aceFlag && total + 10 <= 21) { // Only for dealer. Player decides the value of ace.
            total += 10;
        }
        return total;
    }

    /**
     * Adds a card-object to a hand
     *
     * @param card the object to be added to a hand
     */
    public void addCard(Card card) {
        this.hand.add(card);
    }

    /**
     * Adds a card-object to a splitted hand
     * @param card - the object to be added to a splitted hand
     */
    public void addCardToSplittedHand(Card card) {
        this.splittedHand.add(card);
    }

    /**
     * Clears the hand-object / deletes all the card-objects in a hand
     */
    public void clearHand() {
        this.hand.clear();
    }

    /**
     * Clears the hand-object / deletes all the card-objects in a splitted hand
     */
    public void clearSplittedHand() {
        this.splittedHand.clear();
    }

}
