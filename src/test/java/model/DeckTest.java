package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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

    //@Test //tähän voisi tehdä toisen vaihtoehdon
    @ParameterizedTest
    @ValueSource (ints = {1,2})
    void testShuffleDeck(int number) {
        Deck anotherDeck = new Deck();
        if (number ==1) {
            deck.shuffleDeck();
            assertFalse(deck.equals(anotherDeck), "The deck was not shuffled");
        } else {
            assertTrue(deck.equals(anotherDeck), "The deck was shuffled");
        }

    }

    @Test
    void printCardsInDeck() {
    }
}