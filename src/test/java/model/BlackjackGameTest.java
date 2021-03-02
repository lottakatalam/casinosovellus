package model;

import controller.BlackjackController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static model.Logger.*;
import static org.junit.jupiter.api.Assertions.*;

class BlackjackGameTest {

    BlackjackController controller = new BlackjackController();

    @BeforeAll
    public static void initializeTests() {
        Logger.setLogLevel(Logger.LogLevel.ALL);
    }


    @Test
    void testSetCurrencyForPlayer() {
        assertEquals(2500, controller.getBlackJackGame().getPlayer().getCurrency(), "The currency of the player wasn't set correctly");
    }


    @Test
    void initRound() {
        assertEquals(0, controller.getBlackJackGame().getPlayer().getHand().getNumberOfCards(), "The hand of the player wasn't cleared");
        assertEquals(0, controller.getBlackJackGame().getDealer().getHand().getNumberOfCards(), "The hand of the dealer wasn't cleared");

    }
}