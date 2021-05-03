package model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {


    private static int currency;
    private static Player p;

    @BeforeAll
    static void setTestDB() {
        CasinoDAO.setTestmode(true);
    }

    @AfterAll
    static void setTestDBOFF(){
        CasinoDAO.setTestmode(false);
    }

    @BeforeEach
    public void initializeTests() {
        Logger.setLogLevel(Logger.LogLevel.ALL);
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



    @ParameterizedTest
    @CsvSource({"10, 1010", "200, 1200", "1000, 2000"})
    void win(int bet, int amountOfCurrency) {
            p.setBet(bet);
            p.win("Basic");
            assertEquals(1, p.getWins(), "The amount of wins isn't correct");
            assertEquals(amountOfCurrency, p.getCurrency(), "The currency didn't change correctly after a win");
    }



    @ParameterizedTest
    @ValueSource(ints = {1111, 25, 5000, 250, 87, 23000})
    void setBet(int b) {
        boolean enoughCurrency = p.setBet(b); //Testi toimii jos Player-luokan rivi 112-loggerviestin kommentoi
        if (b <= p.getCurrency()) {
            assertTrue(enoughCurrency, "The bet couldn't be set");
            assertEquals(b, p.getBet(), "The bet was not set correctly");
        } else {
            assertFalse(enoughCurrency, "The bet was bigger than currency and should not be applicable");
        }

    }

}