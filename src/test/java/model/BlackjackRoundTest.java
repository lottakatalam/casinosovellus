package model;

import controller.BlackjackController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlackjackRoundTest {

    private BlackjackController gameController;
    private BlackjackGame game;

    @BeforeAll
    static void initializeTests() {

        Logger.setLogLevel(Logger.LogLevel.ALL);
    }

    @BeforeEach
    void initializeTest() {
        gameController = new BlackjackController();
        if (!gameController.getBlackJackGame().toggleTestMode()) {
            fail("Test mode is not enabled");
        }
    }



    @Test
    void gameResultIsEvenMoney() {
        System.out.println("Test in progress");
    }

}