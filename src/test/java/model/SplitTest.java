package model;

import controller.BlackjackController;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SplitTest {

    private static int currency;
    private static Player p;
    private static BlackjackRound round;
    private static BlackjackController ctrl;
    private static Dealer d;

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
        d = new Dealer();
        ctrl = new BlackjackController();
        round = new BlackjackRound(p, d, ctrl);
    }

    @ParameterizedTest
    @CsvSource({"5,3, 5,2", "10,1, 10,2", "11,1, 11,2"})
    void splitHand(int rankFirstCard, int suitFirstCard, int rankSecondCard, int suitSecondCard) {
        p.getHand().addCard(new Card(rankFirstCard, suitFirstCard));
        p.getHand().addCard(new Card(rankSecondCard, suitSecondCard));
        String cardToBeSplit = p.getHand().getHand().get(1).toString();
        p.getHand().splitHand();
        assertEquals(cardToBeSplit, p.getHand().getSplittedHand().get(0).toString(), "Hand didn't split successfully");
    }

    @ParameterizedTest
    @CsvSource({"5, 3, 5, 1, true, 1, 4", "10, 3, 6, 4, false, 3, 2", "11, 1, 11, 2, true, 2, 1"})
    void splittingIsPossible(int rankFirstCard, int suitFirstCard, int rankSecondCard, int suitSecondCard, boolean isPossible, int dealerRankFirst, int dealerSuitFirst) {
        p.getHand().addCard(new Card(rankFirstCard, suitFirstCard));
        p.getHand().addCard(new Card(rankSecondCard, suitSecondCard));
        d.getHand().addCard(new Card(dealerRankFirst, dealerSuitFirst));
        round.checkSpecialRulePossibilities();
        assertEquals(isPossible, ctrl.getSplitPossibility(), "Split possibility is not set correctly");
    }
}
