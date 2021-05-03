package model;

import controller.BlackjackController;



/**
 * Logic of Blackjack game
 */

public class BlackjackGame extends Thread {

    private BlackjackRound round;

    /**
     * Deck used in a blackjack game
     */
    private Deck deck;

    /**
     * Player playing in a blackjack game
     */
    private final Player player;
    /**
     * Dealer in a BlackJack-game
     */
    private final Dealer dealer;

    /**
     * Controller of the application (MVC-model)
     */
    private final BlackjackController gameController;

    /**
     * DAO object for the DB communication
     */
    private CasinoDAO casinoDAO;

    /**
     * Boolean which is set true when testing
     */
    private boolean testMode = false;


    /**
     * Initializes new Blackjack game
     */
    public BlackjackGame(BlackjackController gameController) {
        this.casinoDAO = new CasinoDAO();
        Logger.log(Logger.LogLevel.DEV, "Blackjack game started");
        this.deck = new Deck();
        if (UserCredentialHandler.getInstance().isLoggedIn()) {
            this.player = new Player(UserCredentialHandler.getInstance().getLoggedInUser().getBalance(), this.casinoDAO);
        } else {
            this.player = new Player(2500, this.casinoDAO);
        }
        this.dealer = new Dealer();
        this.gameController = gameController;

    }


    /**
     * Initializes new round in a blackjack game:
     * Shuffles deck and deals two cards for player and one for dealer.
     */
    public void initRound() {
        player.getHand().clearHand();
        player.getHand().clearSplittedHand();
        dealer.getHand().clearHand();

        if(this.deck.getDeck().size() < 18) {
            this.deck = new Deck();
            Logger.log(Logger.LogLevel.PROD, "Getting a new deck...");
        }

        this.round = new BlackjackRound(this.testMode, this.gameController, this.casinoDAO, this.deck, this.player, this.dealer);

    }

    /**
     * Passes call of a playerHit method to ongoing round
     */
    public void playerHit() {
        round.playerHit();
    }

    /**
     * Player splits the hand to two hands
     */

    public void playerSplit() {
        round.playerSplit();
    }

    /**
     * Player doubles (Hits a new card and ends the turn)
     */
    public void playerDouble() {
        round.playerDouble();
    }

    /**
     * Hits a new card to the splitted hand
     */
    public void hitToSplittedHand() {
        round.hitToSplittedHand();
    }

    /**
     * Passes call of a playerStay method to ongoing round
     */
    public void playerStay() {
        round.playerStay();
    }

    /**
     * Passes call for player wanting to insure for ongoing roun
     */
    public void playerInsure() { round.playerInsure(); }

    /**
     * Passes call for player wanting to surrender for ongoing round
     */
    public void playerSurrender() { round.playerSurrender(); }

    /**
     * Passes call for player wanting to choose even money for ongoing round
     */
    public void playerEvenMoney() { round.playerEvenMoney();}

    /**
     * Returns player of the game
     * @return Player-object
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Returns dealer of the game
     * @return Dealer-object
     */
    public Dealer getDealer() {
        return this.dealer;
    }


    public boolean toggleTestMode() {
        testMode = !testMode;
        return testMode;
    }
}
