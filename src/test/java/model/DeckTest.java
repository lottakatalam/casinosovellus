package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    Deck deck = new Deck();

    @BeforeEach
    public void clearDeck() {
        this.deck.clearDeck();
        deck = new Deck();
    }


    @Test
    void testNextCard() {
        Card card = new Card(1,1);
        assertEquals(card.getRank(), deck.nextCard().getRank(), "The next cards rank wasn't correct");
        assertEquals(card.getSuit(), deck.nextCard().getSuit(), "The next cards suit wasn't correct");
    }

    @Test //En keksi tähän ratkaisua, equals ei toimi korttiolioiden takia
    void testShuffleDeck() {
        Deck anotherDeck = new Deck();
        deck.shuffleDeck();
        assertFalse(deck.equals(anotherDeck), "The deck was not shuffled");
    }

    @Test
    void printCardsInDeck() {
    }
}