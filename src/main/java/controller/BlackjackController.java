package controller;

import model.BlackJack;
import model.Card;

public class BlackjackController {

    private BlackJack blackJackGame;

    public BlackjackController() {

    }

    public void newBlackjackGame() {
        this.blackJackGame = new BlackJack();
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
}
