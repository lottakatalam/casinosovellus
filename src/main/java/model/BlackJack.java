package model;

/**
 *
 *  Logic of Blackjack game
 *
 */

public class BlackJack {

    private int round;

    /**
     * Deck used in a blackjack game
     *
     */
    private Deck deck;

    /**
     * Player playing in a blackjack game
     *
     */
    private Player player;

    //private Dealer dealer;


    /**
     *
     * Initializes new Blackjack game
     *
     */
    public BlackJack() {
        Logger.log(Logger.LogLevel.DEV, "Blackjack game started");
        this.round = 0;
        this.deck = new Deck();
        this.player = new Player();
        //this.dealer = new Dealer();

    }


    /**
     * Initializes new round in a blackjack game:
     * Shuffles deck and deals two cards for player and one for dealer.
     *
     */
    public void initRound() {
        this.round++;
        Logger.log(Logger.LogLevel.DEV, String.format("Round %d begins!\n", round));


        deck.shuffleDeck();
        Card[] playerhand = {deck.nextCard(), deck.nextCard()};
        //Card[] dealerhand = {deck.getCardsInDeck()[2]};
        this.player.setHand(playerhand);
        //this.dealer.setHand(dealerhand);
    }

    // NOTE: This is only for testing
    public void printDeckInGame() {
        this.deck.printCardsInDeck();

    }

}
