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
        this.player = new Player(2500);
        this.dealer = new Dealer();
    }


    /**
     * Initializes new round in a blackjack game:
     * Shuffles deck and deals two cards for player and one for dealer.
     *
     */
    public void initRound() {
        player.clearHand();
        dealer.clearHand();
        /*System.out.println("Set the bet:");
        Scanner scanner = new Scanner(System.in);
        int bet = Integer.parseInt(scanner.nextLine());
        System.out.println("Your bet: "+bet);*/

        this.round++;
        Logger.log(Logger.LogLevel.DEV, String.format("\nRound %d begins!\n", round));

        deck.shuffleDeck();
        this.player.addCard(deck.nextCard());
        this.player.addCard(deck.nextCard());
        this.dealer.addCard(deck.nextCard());
        player.printHand();
        System.out.println("");
        dealer.printHand();
        //getUserInput();
    }

    /*private void getUserInput() {
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
    }*/

    //30.1.
    public void playerHit() {
        player.addCard(deck.nextCard());
        player.printHand();

        int total = player.calculateHand();
        if (total > 21) {
            endRound();
        }
        //getUserInput();
    }

    public void playerStay() {
        //dealer.dealerPlay(deck);
        //testi 31.1. alkaa
        System.out.println("Dealer plays\n");
        while (dealer.calculateTotal() <= 16) {
            System.out.println("Dealer has " + dealer.calculateTotal()+ " and hits");
            dealer.addCard(deck.nextCard());
            //System.out.println("Dealer " + this.getHandString(true, false));
        }
        if (dealer.calculateTotal() > 21) {
            System.out.println("Dealer busts. ");// + this.getHandString(true, false));
        } else {
            System.out.println("Dealer stands at " + dealer.calculateTotal());// + this.getHandString(true, false));
        }
        //testi 31.1. loppuu
        endRound();
    }


    private void endRound() {

        Logger.log(Logger.LogLevel.PROD, "Round ended");
        player.printHand();
        System.out.println("");
        dealer.printHand();

        Object winner = whoWins();
        System.out.println("The winner is: "+winner.toString());

        System.out.println("Your saldo: "+player.getCurrency());

        Logger.log(Logger.LogLevel.PROD, "The amount of wins: "+player.getWins());
        initRound();
    }

    // NOTE: This is only for testing
    public void printDeckInGame() {
        this.deck.printCardsInDeck();

    }

    public Player getPlayer() {
        return this.player;
    }

    public Dealer getDealer() {
        return this.dealer;
    }

    public Object whoWins() {
        Object winner = null;

        int playerTotal = player.calculateHand();
        int dealerTotal = dealer.calculateTotal();
        boolean playerWins = false;

        if (playerTotal == dealerTotal) { //Even if they both get a blackjack
            Logger.log(Logger.LogLevel.PROD, "No one wins");
        } else if (playerTotal <= 21 && (playerTotal > dealerTotal || dealerTotal > 21)) {
            playerWins = true;
            winner = player;
            Logger.log(Logger.LogLevel.PROD, "Player wins");
        }
        else {
            playerWins = false;
            winner = dealer;
            Logger.log(Logger.LogLevel.PROD, "Dealer wins");
        }

        if (playerWins == true) {
            player.win();
        } else {
            player.lose();
        }
        return winner;
    }

}
