package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {

    Hand hand = new Hand();


    @BeforeEach
    public void clear() {
        hand.clearHand();
        hand = new Hand();
    }


    @Test
    void addCard() {
        hand.addCard(new Card(1,1));
        assertEquals(1, hand.getNumberOfCards(), "Adding the card did not succeed");
    }

    @Test
    void clearHand() {
        hand.addCard(new Card(1,1));
        hand.clearHand();
        assertEquals(0, hand.getNumberOfCards(), "Hand was not cleared correctly");
    }
}