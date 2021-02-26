package model;

import controller.BlackjackController;

/**
 * Logic of Blackjack single round
 */
public class BlackjackRound extends Thread {

    private static int roundNumber = 0;

    private final BlackjackController gameController;
    private final Deck deck;
    private final Player player;
    private final Dealer dealer;
    private boolean playerBusted = false;
    private String winner;


    /**
     * Constructor of the round. Draws the first cards and adds them to UI. Sets needed objects for the use of BlackjackRound.
     * @param gameController is the controller to allow communication between view and model.
     * @param deck is used to generate and draw cards.
     * @param player represents the user. It's methods are called from the UI activity. Wins or loses currency.
     * @param dealer draws and stands through algorithm.
     */
    public BlackjackRound(BlackjackController gameController, Deck deck, Player player, Dealer dealer) {
        Logger.log(Logger.LogLevel.PROD, String.format("Round %d started", ++roundNumber));
        this.deck = deck;
        this.player = player;
        this.dealer = dealer;
        this.gameController = gameController;

        deck.shuffleDeck();

        this.player.getHand().addCard(deck.nextCard());
        this.player.getHand().addCard(deck.nextCard());
        this.dealer.getHand().addCard(deck.nextCard());
        player.getHand().printHand();
        System.out.println("");
        dealer.getHand().printHand();
        this.gameController.setPlayersCardsToUI(this.player.getHand().getHand());
        this.gameController.setDealersCardsToUI(this.dealer.getHand().getHand());
    }

    /**
     * Called from the UI and controller when player presses "Hit". Adds drawn cards to UI, checks if the player has busted and starts dealer's turn.
     */
    public void playerHit() {
        this.player.getHand().addCard(deck.nextCard());
        this.gameController.setPlayersCardsToUI(player.getHand().getHand());
        player.getHand().printHand();
        int total = player.getHand().calculateTotal();
        if (total > 21) {
            playerBusted = true;
            this.start();
        } else if (total == 21) {
            playerBusted = false;
            gameController.disableHit();
            this.start();
        }
    }

    /**
     * Called from the UI and controller when player presses "Stand". Sets 'busted' status to false and starts the main thread.
     */
    public void playerStay() {
        playerBusted = false;
        this.start();
    }

    /**
     * Method to determine the winner and/or the way of winning. Calls the methods to influence player's currency.
     * @return winner/end result of the round in String
     * @throws InterruptedException when a thread is waiting, sleeping, or otherwise occupied, and the thread is interrupted, either before or during the activity.
     */
    public String whoWins() throws InterruptedException {
        String winner = "";

        int playerTotal = player.getHand().calculateTotal();
        int dealerTotal = dealer.getHand().calculateTotal();
        boolean playerWins = false;

        if (playerTotal == dealerTotal) { //Even if they both get a blackjack
            Logger.log(Logger.LogLevel.PROD, "No one wins");
            this.winner = "nobody";
        } else if (playerTotal == 21){
            playerWins = true;
            this.winner = "Blackjack";
        } else if (playerTotal < 21 && (playerTotal > dealerTotal || dealerTotal > 21)) {
            playerWins = true;
            this.winner = "player";
            Logger.log(Logger.LogLevel.PROD, "Player wins");
        } else if (playerTotal > 21) {
            this.winner = "busted";
            Logger.log(Logger.LogLevel.PROD, "Dealer wins");
        } else if (dealerTotal == 21) {
            this.winner = "dealer";
            Logger.log(Logger.LogLevel.PROD, "Dealer wins");
        } else {
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


    /**
     * This thread is started when the user's turn ends. It starts and plays the dealer's turn if needed and starts checking who won the round.
     */
    public void run() {// end round
        if (!playerBusted) {
            System.out.println("Dealer plays\n");
            while (dealer.getHand().calculateTotal() <= 16) {
                System.out.println("Dealer has " + dealer.getHand().calculateTotal() + " and hits");
                dealer.getHand().addCard(deck.nextCard());
                gameController.setDealersCardsToUI(dealer.getHand().getHand());

            }
            if (dealer.getHand().calculateTotal() > 21) {
                System.out.println("Dealer busts. ");
            } else {
                System.out.println("Dealer stands at " + dealer.getHand().calculateTotal());
            }
        } else {
            gameController.setPlayersCardsToUI(this.player.getHand().getHand());
        }
        try {
            winner = whoWins();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Logger.log(Logger.LogLevel.PROD, "Round ended");
        player.getHand().printHand();
        System.out.println("");
        dealer.getHand().printHand();

        Logger.log(Logger.LogLevel.PROD, String.format("The winner is: %s", winner));

        Logger.log(Logger.LogLevel.PROD, String.format("Your saldo: %d", player.getCurrency()));

        Logger.log(Logger.LogLevel.PROD, "The amount of wins: " + player.getWins());
    }
}
