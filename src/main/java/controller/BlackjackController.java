package controller;

import model.BlackJack;
import model.Card;
import model.Hand;
import model.Player;
import view.InGameViewController;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class BlackjackController {

    private BlackJack blackJackGame;
    private Player player;
    private Hand handObject;
    private ArrayList<Card> hand;

    /**
     * Initializes the controller and game objects for it's usage
     */
    public BlackjackController() {
        this.blackJackGame = new BlackJack();
        this.player = blackJackGame.getPlayer();
        this.handObject = player.getHand();
        this.hand = handObject.getHand();
    }


    public void nextRound() {
        this.blackJackGame.initRound();

    }

    public void hit() {
        this.blackJackGame.playerHit();
    }
    public void stand() {
        this.blackJackGame.playerStay();
    }
    public Player getPlayer() {
        return this.player;
    }

    public ArrayList<Card> getPlayersCards() {
        return this.hand;
    }

}
