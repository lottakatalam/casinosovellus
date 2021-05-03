package model;

import org.hibernate.annotations.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

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

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 6, 9, 11, 34})
    void clearHand(int max) {
        for (int i=0; i < max; i++) {
            hand.addCard(new Card(i, 1));
        }
        hand.clearHand();
        assertEquals(0, hand.getNumberOfCards(), "Hand was not cleared correctly");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 6, 9, 11, 34})
    void clearSplittedHand(int max) {
        for (int i=0; i < max; i++) {
            hand.addCardToSplittedHand(new Card(i, 1));
        }
        hand.clearSplittedHand();
        assertEquals(0, hand.getNumberOfCards(), "Hand was not cleared correctly");
    }

    @Test
    void printHandTest(){
        assertTrue(hand.printHand());
    }
    @Test
    void printSlplittedHandTest(){
        assertTrue(hand.printSplittedHand());
    }

}