package model;

import model.Logger;
import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SurrenderTest {
    private static int currency;
    private static Player p;
    private static BlackjackRound round;


    @BeforeEach
    public void initializeTests() {
        Logger.setLogLevel(Logger.LogLevel.ALL);
        currency = 1000;
        p = new Player(currency);
    }
}
