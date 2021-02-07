package model;

import controller.BlackjackController;

public class BlackjackRound extends Thread {

    private static int roundNumber = 0;

    private BlackjackController gameController;
    private Deck deck;
    private Player player;
    private Dealer dealer;
    private boolean playerBusted = false;
    private String winner;


    public BlackjackRound(BlackjackController gameController, Deck deck, Player player, Dealer dealer) {
        Logger.log(Logger.LogLevel.PROD, String.format("Round %d started", ++roundNumber));
        this.deck = deck;
        this.player = player;
        this.dealer = dealer;
        this.gameController = gameController;

        deck.shuffleDeck();

        this.player.addCard(deck.nextCard());
        this.player.addCard(deck.nextCard());
        this.dealer.addCard(deck.nextCard());
        player.printHand();
        System.out.println("");
        dealer.printHand();
        this.gameController.setPlayersCardsToUI(this.player.getHand().getHand());
        this.gameController.setDealersCardsToUI(this.dealer.getHand().getHand());
    }

    public void playerHit() throws InterruptedException {
        player.addCard(deck.nextCard());
        player.printHand();
        gameController.setPlayersCardsToUI(this.player.getHand().getHand());


        int total = player.calculateHand();
        if (total > 21) {
            playerBusted = true;
            System.out.println(this.getState());
            this.start();
        }
    }

    public void playerStay() {
        playerBusted = false;
        System.out.println(this.getState());
        this.start();
    }

    public String whoWins() throws InterruptedException {
        String winner = "?";

        int playerTotal = player.calculateHand();
        int dealerTotal = dealer.calculateTotal();
        boolean playerWins = false;

        if (playerTotal == dealerTotal) { //Even if they both get a blackjack
            Logger.log(Logger.LogLevel.PROD, "No one wins");
            this.winner = "nobody";
        } else if (playerTotal <= 21 && (playerTotal > dealerTotal || dealerTotal > 21)) {
            playerWins = true;
            this.winner = "player";
            Logger.log(Logger.LogLevel.PROD, "Player wins");
        } else {
            playerWins = false;
            this.winner = "dealer";
            Logger.log(Logger.LogLevel.PROD, "Dealer wins");
        }

        if (playerWins) {
            player.win();
        } else {
            player.lose();
        }
        gameController.declareWinner(this.winner);
        return winner;
    }


    public void run() {// end round
        if (!playerBusted) {
            System.out.println("Dealer plays\n");
            while (dealer.calculateTotal() <= 16) {
                System.out.println("Dealer has " + dealer.calculateTotal() + " and hits");
                dealer.addCard(deck.nextCard());
                gameController.setDealersCardsToUI(dealer.getHand().getHand());

            }
            if (dealer.calculateTotal() > 21) {
                System.out.println("Dealer busts. ");
            } else {
                System.out.println("Dealer stands at " + dealer.calculateTotal());
            }
        }
        try {
            winner = whoWins();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Logger.log(Logger.LogLevel.PROD, "Round ended");
        player.printHand();
        System.out.println("");
        dealer.printHand();

        Logger.log(Logger.LogLevel.PROD, String.format("The winner is: %s", winner));

        Logger.log(Logger.LogLevel.PROD, String.format("Your saldo: %d", player.getCurrency()));

        Logger.log(Logger.LogLevel.PROD, "The amount of wins: " + player.getWins());
    }
}
