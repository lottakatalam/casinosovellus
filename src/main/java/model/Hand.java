package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Represents a hand of cards.
 *
 * @author
 */
public class Hand {
    /**
     * An arraylist of Card-objects.
     */
    private ArrayList<Card> hand;

    /**
     * Integer which represents the amount of Card-objects in hand
     */
    private int numberOfCards;

    /**
     * Initializes a new Hand-object which stores Card-obejcts in an arraylist
     */
    public Hand() {
        this.hand = new ArrayList<>();
        this.numberOfCards = this.hand.size();
    }

    /**
     *
     * @return integer which represents the amount of Card-objects in hand
     */
    public int getNumberOfCards() {
        return numberOfCards;
    }


    public ArrayList<Card> getHand() {
        return hand;
    }

    /**
     * Sets cards in hand
     *
     * @param cards array of cards
     */
    public void setHand(Card [] cards) {

        Collections.addAll(this.hand, cards);
    }


    public void printHand() {
        for (Card card : hand) {
            System.out.println(card.toString());
        }
    }

    // Calculates the total of a hand and also decides whether ace is 1 or 11
    public int calculateTotal() {
        int total = 0;
        boolean aceFlag = false;
        for (int i = 0; i < hand.size(); i++) {
            //int value = theHand[i].getValue();
            int value = hand.get(i).getRank();
            if (value > 10) {
                value = 10;
            } else if (value == 1) {
                aceFlag = true;
            }
            total += value;
        }
        if (aceFlag && total + 10 <= 21) {
            total += 10;
        }
        return total;
    }

    public void addCard(Card card){
        this.hand.add(card);
    }

    public void clearHand(){
        this.hand.clear();
    }

    public boolean dealerPeek() {
        int value = hand.get(1).getRank();
        return value == 1 || value >= 10;
    }


}
