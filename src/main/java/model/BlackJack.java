package model;

import java.util.Scanner;

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

    private Dealer dealer;


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
        this.dealer = new Dealer();

    }


    /**
     * Initializes new round in a blackjack game:
     * Shuffles deck and deals two cards for player and one for dealer.
     *
     */
    public void initRound() {
        this.round++;
        Logger.log(Logger.LogLevel.DEV, String.format("\nRound %d begins!\n", round));

        deck.shuffleDeck();
        this.player.addCard(deck.nextCard());
        this.player.addCard(deck.nextCard());
        this.dealer.addCard(deck.nextCard());
        player.printHand();
        getUserInput();
    }

    private void getUserInput() {
        System.out.println("\n1. hit, 2. stay\n");
        Scanner scanner = new Scanner(System.in);
        int valinta = Integer.parseInt(scanner.nextLine());
        if (valinta == 1) {
            playerHit();
        } else if(valinta == 2) {
            playerStay();
        }else {
            Logger.log(Logger.LogLevel.ALL, "Error");
        }
    }

    public void playerHit() {
        player.addCard(deck.nextCard());
        player.printHand();
        getUserInput();
    }

    public void playerStay() {
        dealer.dealerPlay(deck);
        endRound();
    }

    private void endRound() {
        Logger.log(Logger.LogLevel.PROD, "Round ended");
        player.clearHand();
        dealer.clearHand();
        initRound();
    }

    // NOTE: This is only for testing
    public void printDeckInGame() {
        this.deck.printCardsInDeck();

    }

}
