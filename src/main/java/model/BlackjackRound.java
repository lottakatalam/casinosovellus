package model;

import controller.BlackjackController;
import controller.SettingsController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Logic of Blackjack single round
 */
public class BlackjackRound extends Thread {

    /**
     * Describes the number of rounds played in one game session
     */
    private static int roundNumber = 0;

    /**
     * Controller of game used to communicating with the UI
     */
    private BlackjackController gameController;

    /**
     * Deck object for handling the cards
     */
    private Deck deck;

    /**
     * Player of the round
     */
    private Player player;

    /**
     * Dealer of the round
     */
    private Dealer dealer;

    /**
     * Boolean which describes if the player busts or not
     */
    private boolean playerBusted = false;

    /**
     * Boolean which describes if the player busts or not with a splitted hand
     */
    private boolean playerBustedSplit = true;

    /**
     * The winner of the round in String
     */
    private String winner;

    /**
     * Boolean which describes if there's a possibility to double or not
     */
    boolean doublePossibility;

    /**
     * Boolean which describes if there's a possibility to split or not
     */
    boolean splitPossibility;

    /**
     * Boolean which describes if there's a possibility to insurance or not
     */
    boolean insurancePossibility;

    /**
     * Boolean which describes if there's a possibility to choose even money or not
     */
    boolean evenMoneyPossibility;

    /**
     * Boolean which describes is the player's hand of cards splitted into two or not
     */
    boolean splitted;

    /**
     * Boolean which describes is the player's bet is insured or not
     */
    boolean insured;

    boolean insuranceHit;

    /**
     * Boolean which describes if the player chose even money or not
     */
    boolean evenMoney;

    /**
     * Boolean which describes if the player surrenderd or not
     */
    boolean surrendered;

    /**
     * Controller for communicating with the settings view
     */
    private SettingsController settingsController;

    /**
     * DAO object for DB communication
     */
    CasinoDAO casinoDAO;


    /**
     * Constructor of the round. Draws the first cards and adds them to UI. Sets needed objects for the use of BlackjackRound.
     * Also sets tips if they are ON
     *
     * @param gameController is the controller to allow communication between view and model.
     * @param deck           is used to generate and draw cards.
     * @param player         represents the user. It's methods are called from the UI activity. Wins or loses currency.
     * @param dealer         draws and stands through algorithm.
     */
    public BlackjackRound(Boolean testMode, BlackjackController gameController, CasinoDAO casinoDAO, Deck deck, Player player, Dealer dealer) {
        Logger.log(Logger.LogLevel.PROD, String.format("Round %d started", ++roundNumber));
        this.deck = deck;
        this.player = player;
        this.dealer = dealer;
        this.gameController = gameController;
        this.casinoDAO = casinoDAO;

        deck.shuffleDeck();
        //deck.manipulateDeck(new Card(5,1), new Card(5,3), new Card(1,2));

        /* THIS CAN BE USED TO DEBUG SPECIAL RULES*/
        /*if (testMode) {
            deck.manipulateDeck(new Card(1,1), new Card(10,1), new Card(1,2));
        }*/
        addFirstCards();

        addFirstCardsToUI();

        //checkForBlackJack();
        checkSpecialRulePossibilities();

        player.getHand().printHand();
        System.out.println("");
        dealer.getHand().printHand();

        setTooltips();
    }

    /**
     * Constructor for testing purposes only
     * @param player Player of the round
     * @param dealer Dealer of the round
     * @param gameController Controller for communicating with the UI
     */
    public BlackjackRound(Player player, Dealer dealer, BlackjackController gameController) {
        this.player = player;
        this.dealer = dealer;
        this.gameController = gameController;
    }

    /**
     * Constructor for testing purposes only
     * @param player
     * @param dealer
     * @param deck;
     * @param gameController
     */
    public BlackjackRound(Player player, Dealer dealer, Deck deck, BlackjackController gameController) {
        this.player = player;
        this.dealer = dealer;
        this.deck = deck;
        this.gameController = gameController;
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
    public void checkSpecialRulePossibilities() {
        int total = this.player.getHand().calculateTotal();
        doublePossibility = (total >= 9 && total <= 11 && gameController.getPlayer().getCurrency() > player.getBet());
        splitPossibility = player.getHand().getHand().get(0).getRankString().equals(player.getHand().getHand().get(1).getRankString());
        insurancePossibility = dealer.getHand().getHand().get(0).getRankString().equals("Ace") && gameController.getPlayer().getCurrency() > player.getBet() / 2;
        evenMoneyPossibility = dealer.getHand().getHand().get(0).getRankString().equals("Ace") && player.getHand().calculateTotal() == 21;


        /* THIS CAN BE USED TO DEBUG SPLITTING */
        //this.gameController.setSplitPossibility(true);
        this.gameController.setSplitPossibility(splitPossibility);

        /* THIS CAN BE USED TO DEBUG DOUBLING */
        this.gameController.setDoublePossibility(doublePossibility);
        //this.gameController.setDoublePossibility(true);

        /* THIS CAN BE USED TO DEBUG INSURANCE */
        this.gameController.setInsurancePossibility(insurancePossibility);
        //this.gameController.setInsurancePossibility(true);

        /*  Although to truly debug insuring you also have to set insuranceHit to true and make a new if -statement in
         * whoWins with only condition insuranceHit == true */

        /* THIS CAN BE USED TO DEBUG EVEN MONEY */
        this.gameController.setEvenMoneyPossibility(evenMoneyPossibility);
        //this.gameController.setEvenMoneyPossibility(true);
    }

    /**
     * Called from the UI and controller when player presses "Hit". Adds drawn cards to UI, checks if the player has busted and starts dealer's turn.
     */
    public void playerHit() {
        this.player.getHand().addCard(deck.nextCard());
        this.gameController.setPlayersCardsToUI(player.getHand().getHand());
        player.getHand().printHand();
        if (settingsController.getSelected() && player.getHand().calculateTotal() < 17) {
            gameController.showTip("HitTip");
        } else if (settingsController.getSelected() && player.getHand().calculateTotal() > 16) {
            gameController.showTip("StandTip");
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
        if (settingsController.getSelected() && player.getHand().calculateSplittedTotal() < 17) {
            gameController.showTip("HitTip");
        } else if (settingsController.getSelected() && player.getHand().calculateSplittedTotal() > 16) {
            gameController.showTip("StandTip");
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
     * Sets the insurance status to true and takes the insuring money from player
     */

    public void playerInsure() {
        this.insured = true;
        this.player.insure();
    }

    /**
     * Player splits the hand to two hands
     */
    public void playerSplit() {
        splitted = true;
        this.player.getHand().splitHand();
        if (settingsController.getSelected() && player.getHand().calculateTotal() < 17) {
            gameController.showTip("HitTip");
        } else if (settingsController.getSelected() && player.getHand().calculateTotal() > 16) {
            gameController.showTip("StandTip");
        }
    }

    /**
     * Player surrenders (Round ends)
     */
    public void playerSurrender() {
        surrendered = true;
        this.start();
    }

    /**
     * Player calls even money (Round ends)
     */
    public void playerEvenMoney() {
        evenMoney = true;
        this.start();
    }


    /**
     * Called from the UI and controller when player presses "Stand". Sets 'busted' status to false and starts the main thread.
     */
    public void playerStay() {
        playerBusted = false;
        this.start();
    }

    /**
     * Sets possible tooltips according to the current game situation
     */
    public void setTooltips() {
        settingsController = SettingsController.getInstance();

        if (settingsController.getSelected() && player.getHand().calculateTotal() < 17 && !doublePossibility && !splitPossibility && !insurancePossibility && !evenMoneyPossibility) {
            gameController.showTip("HitTip");
        } else if (settingsController.getSelected() && player.getHand().calculateTotal() > 16 && !splitPossibility && !insurancePossibility && !evenMoneyPossibility) {
            gameController.showTip("StandTip");
        } else if (settingsController.getSelected() && doublePossibility) {
            gameController.showTip("DoubleTip");
        } else if (settingsController.getSelected() && splitPossibility) {
            gameController.showTip("SplitTip");
        } else if(settingsController.getSelected() && insurancePossibility && !evenMoneyPossibility) {
            gameController.showTip("InsuranceTip");
        }else if(settingsController.getSelected() && evenMoneyPossibility) {
            gameController.showTip("EvenMoneyTip");
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
        String method = "";

        int dealerTotal = dealer.getHand().calculateTotal();
        boolean playerWins = false;

        if (surrendered){
            this.winner = "surrender";
            h.setResult(History.gameResults.LOST);
            method = "Surrender";
            h.setMethod(method);
            Logger.log(Logger.LogLevel.PROD, "Surrendered");
        }
        else if (evenMoney) {
            playerWins = true;
            this.winner = "EvenMoney";
            h.setResult(History.gameResults.WON);
            method = "EvenMoney";
            h.setMethod(method);
            Logger.log(Logger.LogLevel.PROD, "Even money called");

        } else if (playerTotal == dealerTotal) { //Even if they both get a blackjack
            Logger.log(Logger.LogLevel.PROD, "No one wins");
            this.winner = "nobody";
            h.setResult(History.gameResults.DRAW);
            method = "Draw";
            h.setMethod(method);
        } else if (playerTotal == 21) {
            playerWins = true;
            this.winner = "Blackjack";
            h.setResult(History.gameResults.WON);
            method = "Blackjack";
            h.setMethod(method);
        } else if (playerTotal < 21 && (playerTotal > dealerTotal || dealerTotal > 21)) {
            playerWins = true;
            this.winner = "player";
            h.setResult(History.gameResults.WON);
            method = "Basic";
            h.setMethod(method);
            Logger.log(Logger.LogLevel.PROD, "Player wins");
        } else if (playerTotal > 21) {
            this.winner = "busted";
            h.setResult(History.gameResults.LOST);
            method = "Busted";
            h.setMethod(method);
            Logger.log(Logger.LogLevel.PROD, "Dealer wins");
        } else if (dealerTotal == 21 && !insuranceHit) {
            this.winner = "dealer";
            h.setResult(History.gameResults.LOST);
            method = "Blackjack";
            h.setMethod(method);
            Logger.log(Logger.LogLevel.PROD, "Dealer wins");
        } else if (dealerTotal == 21 && insuranceHit) {
            this.winner = "insured";
            h.setResult(History.gameResults.INSURED);
            method = "Insurance";
            h.setMethod(method);
            Logger.log(Logger.LogLevel.PROD, "Hand insured");
        } else {
            this.winner = "dealer";
            h.setResult(History.gameResults.LOST);
            method = "Basic";
            h.setMethod(method);
            Logger.log(Logger.LogLevel.PROD, "Dealer wins");
        }


        if (playerWins) {
            player.win(method);
        } else {
            player.lose(method);
        }
        gameController.declareWinner(this.winner);

        if (UserCredentialHandler.getInstance().

                getLoggedInUser() != null) {
            h.setUserID(UserCredentialHandler.getInstance().getLoggedInUser().getUserID());
        }

        LocalDateTime date = LocalDateTime.now();
        h.setDate(date);
        h.setBet(player.getBet());
        h.setBalance(player.getCurrency());
        casinoDAO.addHistoryRow(h);

        if (splitted) {
            splitted = false;
            whoWins(player.getHand().calculateSplittedTotal());
        }

        if (insured) {
            insured = false;
            insuranceHit = false;
        }
        return winner;
    }


    /**
     * This thread is started when the user's turn ends. It starts and plays the dealer's turn if needed and starts checking who won the round.
     */
    public void run() { // End round
        System.out.println(evenMoney);
        if ((!playerBusted || !playerBustedSplit) && !evenMoney && !surrendered) {
            System.out.println("Dealer plays\n");
            while (dealer.getHand().calculateTotal() <= 16) {
                System.out.println("Dealer has " + dealer.getHand().calculateTotal() + " and hits");
                dealer.getHand().addCard(deck.nextCard());
                if (dealer.getHand().calculateTotal() == 21 && dealer.getHand().getNumberOfCards() == 2 && insured) {
                    this.insuranceHit = true;
                }
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
