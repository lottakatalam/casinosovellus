package model;

import controller.BlackjackController;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoubleTest {

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
    @CsvSource({"10, 20", "200, 400", "500, 1000"})
    void doublePressed(int bet, int newBet) {
        p.setBet(bet);
        p.doubleBet();
        assertEquals(newBet, p.getBet(), "The amount of new bet is not correct");
    }

    @ParameterizedTest
    @CsvSource({"4, 3, 5, 1, true, 1, 4", "6, 3, 6, 4, false, 3, 2", "5, 1, 5, 2, true, 2, 1"})
    void doublingIsPossible(int rankFirstCard, int suitFirstCard, int rankSecondCard, int suitSecondCard, boolean isPossible, int dealerRankFirst, int dealerSuitFirst) {
        p.getHand().addCard(new Card(rankFirstCard, suitFirstCard));
        p.getHand().addCard(new Card(rankSecondCard, suitSecondCard));
        d.getHand().addCard(new Card(dealerRankFirst, dealerSuitFirst));
        round.checkSpecialRulePossibilities();
        assertEquals(isPossible, ctrl.getDoublePossibility(), "Double possibility is not set correctly");
    }
}
