package model;

import controller.BlackjackController;
import controller.SettingsController;
import view.SettingsViewController;

import java.time.LocalDateTime;

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
    private boolean playerBustedSplit = true;
    private String winner;
    private String splitWinner;
    boolean doublePossibility;
    boolean splitPossibility;
    boolean splitted;
    private SettingsController settingsController;


    CasinoDAO casinoDAO = new CasinoDAO();


    /**
     * Constructor of the round. Draws the first cards and adds them to UI. Sets needed objects for the use of BlackjackRound.
     * Also sets tips if they are ON
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

        addFirstCards();

        addFirstCardsToUI();

        checkDoubleAndSplitPossibility();

        player.getHand().printHand();
        System.out.println("");
        dealer.getHand().printHand();

        setTooltips();
    }

    /**
     * Player draws 2 cards and Dealer draws one at the start of the round
     */
    public void addFirstCards() {
        this.player.getHand().addCard(deck.nextCard());
        this.player.getHand().addCard(deck.nextCard());
        this.dealer.getHand().addCard(deck.nextCard());
    }

    /**
     * Adds cards from addFirstCards()-method to UI
     */
    public void addFirstCardsToUI() {
        this.gameController.setPlayersCardsToUI(this.player.getHand().getHand());
        this.gameController.setDealersCardsToUI(this.dealer.getHand().getHand());
    }

    /**
     * Check if it is possible to split or double in game
     */
    public void checkDoubleAndSplitPossibility() {
        int total = this.player.getHand().calculateTotal();
        doublePossibility = (total >= 9 && total <= 11 && gameController.getPlayer().getCurrency() > player.getBet());
        splitPossibility = checkSplitPossibility();

        /* THIS CAN BE USED TO DEBUG SPLITTING */
        this.gameController.setSplitPossibility(true);
        //this.gameController.setSplitPossibility(splitPossibility);

        /* THIS CAN BE USED TO DEBUG DOUBLING */
        //this.gameController.setDoublePossibility(doublePossibility);
        this.gameController.setDoublePossibility(true);
    }

    /**
     * Called from the UI and controller when player presses "Hit". Adds drawn cards to UI, checks if the player has busted and starts dealer's turn.
     */
    public void playerHit() {
        this.player.getHand().addCard(deck.nextCard());
        this.gameController.setPlayersCardsToUI(player.getHand().getHand());
        player.getHand().printHand();
        if(settingsController.getSelected() && player.getHand().calculateTotal() < 17) {
            gameController.showHitTip();
        }else if(settingsController.getSelected() && player.getHand().calculateTotal() > 16) {
            gameController.showStandTip();
        }
        int total = player.getHand().calculateTotal();
        if (total > 21 && !splitted) {
            playerBusted = true;
            this.start();
        } else if (total == 21 && !splitted) {
            playerBusted = false;
            gameController.disableHit();
            gameController.disableStand();
            this.start();
        } else if (total > 21 || total == 21) {
            gameController.setSplitStatus(true);
            hitToSplittedHand();
        }
    }

    /**
     * Hits a new card to the splitted hand
     */
    public void hitToSplittedHand() {
        this.player.getHand().addCardToSplittedHand(deck.nextCard());
        this.gameController.setPlayersSplittedCardsToUI(player.getHand().getSplittedHand());
        if(settingsController.getSelected() && player.getHand().calculateSplittedTotal() < 17) {
            gameController.showHitTip();
        }else if(settingsController.getSelected() && player.getHand().calculateSplittedTotal() > 16) {
            gameController.showStandTip();
        }
        int splittedTotal = player.getHand().calculateSplittedTotal();

        if (splittedTotal > 21) {
            this.playerBustedSplit = true;
            this.start();
        } else if (splittedTotal == 21) {
            this.playerBustedSplit = false;
            gameController.disableHit();
            gameController.disableStand();
            this.start();
        }
    }

    /**
     * Player doubles (Hits a new card and ends the turn)
     */
    public void playerDouble() {
        this.player.doubleBet();
        playerHit();
        if (!this.isAlive()) {
            playerStay();
        }
    }

    /**
     * Player splits the hand to two hands
     */
    public void playerSplit() {
        splitted = true;
        this.player.getHand().splitHand();
        if(settingsController.getSelected() && player.getHand().calculateTotal() < 17) {
            gameController.showHitTip();
        }else if(settingsController.getSelected() && player.getHand().calculateTotal() > 16) {
            gameController.showStandTip();
        }
    }

    /**
     * Checks if splitting is possible in game
     * @return
     */
    public boolean checkSplitPossibility() {
        return player.getHand().getHand().get(0).getRankString().equals(player.getHand().getHand().get(1).getRankString());
    }


    /**
     * Called from the UI and controller when player presses "Stand". Sets 'busted' status to false and starts the main thread.
     */
    public void playerStay() {
        playerBusted = false;
        this.start();
    }

    public void setTooltips() {
        settingsController = SettingsController.getInstance();

        if(settingsController.getSelected() && player.getHand().calculateTotal() < 17 && !doublePossibility && !splitPossibility) {
            gameController.showHitTip();
        }else if(settingsController.getSelected() && player.getHand().calculateTotal() > 16 && !splitPossibility) {
            gameController.showStandTip();
        }else if(settingsController.getSelected() && doublePossibility) {
            gameController.showDoubleTip();
        }else if(settingsController.getSelected() && splitPossibility) {
            gameController.showSplitTip();
        }
    }

    /**
     * Method to determine the winner and/or the way of winning. Calls the methods to influence player's currency.
     * Also sets data from the round and adds a row of the round to the history table
     * @return winner/end result of the round in String
     * @throws InterruptedException when a thread is waiting, sleeping, or otherwise occupied, and the thread is interrupted, either before or during the activity.
     */
    public String whoWins(int playerTotal) throws InterruptedException {
        History h = new History();
        String winner = "";

        int dealerTotal = dealer.getHand().calculateTotal();
        boolean playerWins = false;

        if (playerTotal == dealerTotal) { //Even if they both get a blackjack
            Logger.log(Logger.LogLevel.PROD, "No one wins");
            this.winner = "nobody";
            h.setResult(History.gameResults.DRAW);
            h.setMethod("Draw");
        } else if (playerTotal == 21){
            playerWins = true;
            this.winner = "Blackjack";
            h.setResult(History.gameResults.WON);
            h.setMethod("Blackjack");
        } else if (playerTotal < 21 && (playerTotal > dealerTotal || dealerTotal > 21)) {
            playerWins = true;
            this.winner = "player";
            h.setResult(History.gameResults.WON);
            h.setMethod("Basic");
            Logger.log(Logger.LogLevel.PROD, "Player wins");
        } else if (playerTotal > 21) {
            this.winner = "busted";
            h.setResult(History.gameResults.LOST);
            h.setMethod("Busted");
            Logger.log(Logger.LogLevel.PROD, "Dealer wins");
        } else if (dealerTotal == 21) {
            this.winner = "dealer";
            h.setResult(History.gameResults.LOST);
            h.setMethod("Blackjack");
            Logger.log(Logger.LogLevel.PROD, "Dealer wins");
        } else {
            this.winner = "dealer";
            h.setResult(History.gameResults.LOST);
            h.setMethod("Basic");
            Logger.log(Logger.LogLevel.PROD, "Dealer wins");
        }

        if (playerWins) {
            player.win();
        } else {
            player.lose();
        }
        gameController.declareWinner(this.winner);

        if (UserCredentialHandler.getInstance().getLoggedInUser() != null) {
            h.setUserID(UserCredentialHandler.getInstance().getLoggedInUser().getUserID());
        }
        h.setDate(LocalDateTime.now());
        h.setBet(player.getBet());
        h.setBalance(player.getCurrency());
        casinoDAO.addHistoryRow(h);

        if (splitted) {
            splitted = false;
            whoWins(player.getHand().calculateSplittedTotal());
        }
        return winner;
    }


    /**
     * This thread is started when the user's turn ends. It starts and plays the dealer's turn if needed and starts checking who won the round.
     */
    public void run() {// end round
        if (!playerBusted || !playerBustedSplit) {
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
            winner = whoWins(player.getHand().calculateTotal());
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
