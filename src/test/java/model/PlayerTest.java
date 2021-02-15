package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {


    private static int currency;
    private static Player p;

    @BeforeEach
    public void initalizeTests() {
        currency = 1000;
        p = new Player(currency);
    }

    @Test
    void testConstructor() {
        assertEquals(currency, p.getCurrency(), "The currency should be "+currency);
        assertNotNull(p.getHand(), "The hand was not created");
        assertEquals(0, p.getBet(), 0, "The bet should be 0");
        assertEquals(0, p.getWins(), "The amount of wins should be 0");
    }



    /*@ParameterizedTest
    @CsvSource({"1, 1020", "2, 1040"})
    void win() {
        p.setBet(10);
        p.win();
        assertEquals(1, );
    }*/

    @Test
    void lose() {
    }

    @Test
    void getWins() {
    }

    @Test
    void getHand() {
    }

    @Test
    void getCurrency() {
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 25, 5000, 250, 87, 23000})
    void setBet(int b) {
        boolean enoughCurrency = p.setBet(b);
        if (b <= p.getCurrency()) {
            assertTrue(enoughCurrency, "The bet couldn't be set");
            assertEquals(b, p.getBet(), "The bet was not set correctly");
        } else {
            assertFalse(enoughCurrency, "The bet was bigger than currency and should not be applicable");
        }

    }

    @Test
    void getBet() {
    }
}