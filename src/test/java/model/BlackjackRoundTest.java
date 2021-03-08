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

}