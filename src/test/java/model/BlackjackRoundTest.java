package model;

import controller.BlackjackController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlackjackRoundTest {

    BlackjackController controller = new BlackjackController();

    @BeforeAll
    public static void initializeTests() {
        Logger.setLogLevel(Logger.LogLevel.ALL);
    }

    /*@Test
    void isDeckShuffledInConstructor() { //Ei toimi, pitäisi alustaa jotenkin InGameView-kontrolleri
        Deck d1 = controller.getBlackJackGame().getDeck();
        controller.getBlackJackGame().initRound();
        Deck d2 = controller.getBlackJackGame().getDeck();
        assertFalse(d1.equals(d2), "The deck was not shuffled");
    }

    @Test
    void playerHit() {
    }

    @Test
    void playerStay() {
    }

    @Test
    void whoWins() {
    }

    @Test
    void run() {
    }*/
}