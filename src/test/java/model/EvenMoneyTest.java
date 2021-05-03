package model;

import controller.BlackjackController;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EvenMoneyTest {
    private static int currency;
    private static Player p;
    private static Dealer d;
    private static BlackjackRound round;
    private static BlackjackController ctrl;

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
    @CsvSource({"10, 1010", "200, 1200", "500, 1500"})
    void evenMoney(int bet, int newBalance) {
        p.setBet(bet);
        p.win("EvenMoney");
        assertEquals(newBalance, p.getCurrency(), "The amount of new balance is not correct");
    }

    @ParameterizedTest
    @CsvSource({"10, 3, 1, 1, true, 1, 2", "5, 3, 8, 4, false, 1, 4", "1, 1, 10, 1, false, 5, 1"})
    void evenMoneyIsPossible(int rankFirstCard, int suitFirstCard, int rankSecondCard, int suitSecondCard, boolean isPossible, int dealerRankFirst, int dealerSuitFirst) {
        p.getHand().addCard(new Card(rankFirstCard, suitFirstCard));
        p.getHand().addCard(new Card(rankSecondCard, suitSecondCard));
        d.getHand().addCard(new Card(dealerRankFirst, dealerSuitFirst));
        round.checkSpecialRulePossibilities();
        assertEquals(isPossible, ctrl.getEvenMoneyPossibility(), "Even money possibility is not set correctly");
    }
}
