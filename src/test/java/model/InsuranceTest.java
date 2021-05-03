package model;

import controller.BlackjackController;
import model.Logger;
import model.Player;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InsuranceTest {


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
    @CsvSource({"10, 985", "200, 700", "500, 250"})
    void insure(int bet, int newBalance) {
        p.setBet(bet);
        p.insure();
        assertEquals(newBalance, p.getCurrency(), "The amount of insurance is not correct");
    }

    @ParameterizedTest
    @CsvSource({"100, 1000", "200, 1000", "325, 1000"})
    void insurancePayback(int bet, int newBalance) {
        p.setBet(bet);
        p.insure();
        p.lose("Insurance");
        assertEquals(newBalance, p.getCurrency(), "The amount of insurance payback is not correct");
    }

    @ParameterizedTest
    @CsvSource({"100, 850", "200, 700", "325, 513"})
    void loseWithInsurance(int bet, int newBalance) {
        p.setBet(bet);
        p.insure();
        p.lose("Basic");
        assertEquals(newBalance, p.getCurrency(), "The amount of new balance after losing is not correct");
    }

    @ParameterizedTest
    @CsvSource({"100, 1050", "200, 1100", "325, 1163"})
    void winWithInsurance(int bet, int newBalance) {
        p.setBet(bet);
        p.insure();
        p.win("Basic");
        assertEquals(newBalance, p.getCurrency(), "The amount of new balance after winning is not correct");
    }

    @ParameterizedTest
    @CsvSource({"100, 850", "200, 700", "325, 513"})
    void drawWithInsurance(int bet, int newBalance) {
        p.setBet(bet);
        p.insure();
        p.lose("Draw");
        assertEquals(newBalance, p.getCurrency(), "The amount of new balance after drawing is not correct");
    }

    @ParameterizedTest
    @CsvSource({"5, 3, 5, 1, true, 1, 4", "10, 3, 6, 4, false, 3, 2", "11, 1, 11, 2, true, 1, 1"})
    void insuringIsPossible(int rankFirstCard, int suitFirstCard, int rankSecondCard, int suitSecondCard, boolean isPossible, int dealerRankFirst, int dealerSuitFirst) {
        p.getHand().addCard(new Card(rankFirstCard, suitFirstCard));
        p.getHand().addCard(new Card(rankSecondCard, suitSecondCard));
        d.getHand().addCard(new Card(dealerRankFirst, dealerSuitFirst));
        round.checkSpecialRulePossibilities();
        assertEquals(isPossible, ctrl.getInsurancePossibility(), "Insurance possibility is not set correctly");
    }
}