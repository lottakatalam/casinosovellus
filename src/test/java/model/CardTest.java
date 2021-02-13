package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(RandomParametersExtension.class)
class CardTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 20, 87, 4, 0, 12, 567, 8765})
    public void testRank(int rank) {
        Card card = new Card(rank,4);
        assertEquals(rank, card.getRank(), "Rank was not what expected.");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 20, 87, 4, 6, 12, 567, 8765})
    public void testSuit(int suit) {
        Card card = new Card(1,suit);
        assertEquals(suit, card.getSuit(), "Suit was not what expected.");
    }

}