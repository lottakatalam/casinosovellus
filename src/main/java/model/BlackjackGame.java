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
    private final Deck deck;

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
     * Initializes new Blackjack game
     */
    public BlackjackGame(BlackjackController gameController) {
        Logger.log(Logger.LogLevel.DEV, "Blackjack game started");
        this.deck = new Deck();
        this.player = new Player(2500);
        this.dealer = new Dealer();
        this.gameController = gameController;

    }


    /**
     * Initializes new round in a blackjack game:
     * Shuffles deck and deals two cards for player and one for dealer.
     */
    public void initRound() {
        player.getHand().clearHand();
        dealer.getHand().clearHand();

        this.round = new BlackjackRound(this.gameController, this.deck, this.player, this.dealer);

    }

    /**
     * Passes call of a playerHit method to ongoing round
     */
    public void playerHit() {
            round.playerHit();
    }

    /**
     * Passes call of a playerStay method to ongoing round
     */
    public void playerStay() {
        round.playerStay();
    }


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

    public Deck getDeck() {
        return deck;
    }

    public BlackjackRound getRound() {
        return round;
    }


    public BlackjackController getGameController() {
        return gameController;
    }
}
