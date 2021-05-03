package model;

import controller.BlackjackController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlackjackRoundTest {

    private static int currency;
    private static Player p;
    private static Dealer d;
    private static Deck deck;
    private static BlackjackRound round;
    private static BlackjackController ctrl;

    @BeforeEach
    public void initializeTests() {
        Logger.setLogLevel(Logger.LogLevel.ALL);
        currency = 1000;
        p = new Player(currency);
        d = new Dealer();
        deck = new Deck();
        ctrl = new BlackjackController();
        round = new BlackjackRound(p, d,deck, ctrl);
    }


    @Test
    void addFirstCardsTest(){
        round.addFirstCards();
        assertEquals(2,p.getHand().getNumberOfCards(),"The amount of cards is not correct");
        assertEquals(1,d.getHand().getNumberOfCards(),"The amount of cards is not correct");
    }


}