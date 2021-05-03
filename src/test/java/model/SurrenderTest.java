package model;

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

public class SurrenderTest {
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

    @ParameterizedTest
    @CsvSource({"10, 995", "200, 900", "500, 750"})
    void surrender(int bet, int newBalance) {
        p.setBet(bet);
        p.lose("Surrender");
        assertEquals(newBalance, p.getCurrency(), "The amount of new balance is not correct");
    }
}
