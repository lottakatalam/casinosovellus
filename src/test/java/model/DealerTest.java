package model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DealerTest {


    Dealer d = new Dealer();

    @BeforeAll
    static void setTestDB() {
        CasinoDAO.setTestmode(true);
    }

    @AfterAll
    static void setTestDBOFF(){
        CasinoDAO.setTestmode(false);
    }

    @Test
    void testIsBlackJack() {
        d.getHand().addCard(new Card(10,1));
        d.getHand().addCard(new Card(1,1));
        assertTrue(d.isBlackjack(), "Method returned false even though the dealer had a BlackJack");

    }

    @Test
    void testIsNotBlackJack() {
        d.getHand().addCard(new Card(1,1));
        d.getHand().addCard(new Card(2,1));
        assertFalse(d.isBlackjack(), "Returned true even though the dealer did not have a BlackJack");
    }

}