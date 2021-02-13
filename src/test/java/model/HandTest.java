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
    }

    @Test
    void clearHand() {
    }
}