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

    /**
     * Initializes a new Deck-object and creates 52 Card-objects for the deck.
     */
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

    /**
     * Returns cards in deck
     * @return array of cards
     */
    public Card[] getCardsInDeck() {
        return deck;
    }

    /**
     * Shuffles cards in deck
     *
     */
    public void shuffleDeck() {
        for (int i=0;i<deck.length;i++) {
            int rnd = (int) (Math.random() * 52);
            Card cardToMove = deck[i];
            deck[i] = deck[rnd];
            deck[rnd] = cardToMove;

        }
    }

    /**
     * Prints all Card-objects in the deck by calling the toString()-method from the Card-class
     */
    public void printCardsInDeck() {
        for (Card card: deck) {
            System.out.println(card.toString());
        }
    }
}
