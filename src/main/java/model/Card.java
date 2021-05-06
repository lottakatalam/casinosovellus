package model;
/**
 * Represents a playing card
 *
 */

public class Card {
    /**
     * Array of suits as String
     */
    public static final String[] suits = {null, "Club", "Diamond", "Spade", "Heart"};

    /**
     * Array of ranks as String
     */
    public static final String[] ranks =
            {null, "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};

    /**
     * Represents the rank of a card. Rank is an integer between 1 and 13.
     */
    private int rank;

    /**
     * Represents the suit of a card. Suit is an integer between 1 and 4.
     */
    private int suit;

    private String value;

    /**
     * Initializes a new Card-object.
     * Note that this constructor takes all integers as parameters.
     * Only the constructor of the Deck-object ensures the values for rank and suit are appropriate.
     * @param rank sets the rank of the Card-object
     * @param suit sets the suit of the Card-object
     */
    public Card(int rank, int suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Sets cards' values
     * @return - The value
     */
    public String getRankString() {
        if (ranks[this.rank].equals("10") || ranks[this.rank].equals("Jack") || ranks[this.rank].equals("Queen") || ranks[this.rank].equals("King")) {
            value = "10";
        } else if (ranks[this.rank].equals("Ace")) {
            value = "Ace";
        } else {
            value = ranks[this.rank];
        }
        return value;
    }

    /**
     * Gets the rank of the card
     * @return - The rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * Gets the suit of the card
     * @return - The suit
     */
    public int getSuit() {
        return suit;
    }

    /**
     * toString()-method to express the card (Rank and suit)
     * @return - The card
     */
    @Override
    public String toString() {
        return ranks[this.rank]+" of "+suits[this.suit];
    }

    /**
     * Used for JUnit tests
     * @param o to compare the Cards
     * @return boolean whether or not the Cards are the same
     */
    public boolean equals(Object o) {
        Card anotherCard = (Card) o;
        if (rank == anotherCard.getRank() && suit == anotherCard.getSuit()) {
            return true;
        }
        return false;
    }
}
