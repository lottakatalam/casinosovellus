package model;

/**
 * Represents a deck of cards
 *
 * @author
 * @version
 */
public class Deck {

    /**
     * An array of cards which represents the deck.
     * Can contain maximum of 52 objects.
     */
    private Card[] deck = new Card[52];

    public Deck() {
        int rank = 1;
        int suit = 1;
        for (int i = 0; i < 52; i++) {
            this.deck[i] = new Card(rank, suit);
            rank++;
            if (rank > 13) {
                rank = 1;
                suit++;
            }
        }
    }

    public void printDeck() {
        for (Card card: deck) {
            System.out.println(card.toString());
        }
    }
}
