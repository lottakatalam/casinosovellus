package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HandTest2 {

    private Hand hand1 = new Hand();
    hand1.addCard(new Card(1,1));

    @Test
    void TestCalculateTotal() {
    }
}