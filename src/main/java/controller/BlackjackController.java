package controller;

import model.*;
import view.InGameViewController;

import java.util.ArrayList;

/**
 * Used to control GUI from model and model from GUI
 */
public class BlackjackController {

    private final BlackjackGame blackJackGame;
    private final Player player;
    private final ArrayList<Card> handPlayer;
    private final ArrayList<Card> splittedHandPlayer;
    private final Dealer dealer;
    private final ArrayList<Card> handDealer;
    private InGameViewController inGameViewController;
    private boolean doublePossibility;
    private boolean splitPossibility;
    private boolean insurancePossibility;
    private boolean evenMoneyPossibility;
    private boolean splitIsOn = false;


    /**
     * Constructor of BlackjackController
     */
    public BlackjackController() {
        this.blackJackGame = new BlackjackGame(this);
        this.player = blackJackGame.getPlayer();
        Hand handObjectPlayer = player.getHand();
        this.handPlayer = handObjectPlayer.getHand();
        this.splittedHandPlayer = handObjectPlayer.getSplittedHand();
        this.dealer = blackJackGame.getDealer();
        Hand handObjectDealer = dealer.getHand();
        this.handDealer = handObjectDealer.getHand();
    }


    /**
     * Starts next round in blackjack game
     */
    public void nextRound() {
        this.blackJackGame.initRound();

    }

    /**
     * Player hits and gets a new card
     */
    public void hit() {
        this.blackJackGame.playerHit();
    }

    /**
     * Player stands and then it is Dealer's turn to play
     */
    public void stand() {
        this.blackJackGame.playerStay();
    }

    /**
     * Player sets the bet to the beginning round
     * @param b - Amount of the bet
     * @return - true if the bet is set, false if the player doesn't have enough online cash for setting the bet
     */
    public boolean setBet(int b) {
        return this.player.setBet(b);
    }

    /**
     * Splits the bet to both hands
     * @param b - Amount of the bet
     * @return - Returns the splitted bet
     */
    public boolean setSplitBet(int b) {
        return this.player.setSplitBet(b);
    }

    /**
     * Gets the hand of the Player
     * @return the Arraylist of players cards
     */
    public ArrayList<Card> getPlayersCards() {
        return this.handPlayer;
    }

    /**
     * Gets player's splitted cards
     * @return - Returns splitted cards
     */
    public ArrayList<Card> getPlayersSplittedCards() { return this.splittedHandPlayer;}

    /**
     * Gets the hand of the Dealer
     * @return return the Arraylist of dealer's cards
     */
    public ArrayList<Card> getDealersCards() {
        return this.handDealer;
    }

    /**
     * Sets the player's cards to UI
     * @param playersCards - Cards that the player currently has
     */
    public void setPlayersCardsToUI(ArrayList<Card> playersCards) {
        inGameViewController.setPlayersCards(playersCards);
    }

    /**
     * Sets the player's splitted cards to UI
     * @param playersSplittedCards - Cards that player currently has in his splitted hand
     */
    public void setPlayersSplittedCardsToUI(ArrayList<Card> playersSplittedCards) {
        inGameViewController.setPlayersSplittedCards(playersSplittedCards);
    }

    /**
     * Sets split status
     * @param splitIsOn - Sets split status ON
     */
    public void setSplitStatus(boolean splitIsOn) {
        this.splitIsOn = splitIsOn;
    }

    /**
     * Gets the split status
     * @return - Returns current split status
     */
    public boolean getSplitStatus() {
        return this.splitIsOn;
    }

    /**
     * Hits a new card to splitted hand (second hand)
     */
    public void hitToSplittedHand() {
        this.blackJackGame.hitToSplittedHand();
    }

    /**
     * Sets the dealer's cards to UI
     * @param dealersCards - Cards that the dealer currently has
     */
    public void setDealersCardsToUI(ArrayList<Card> dealersCards) {
        inGameViewController.setDealersCards(dealersCards);
    }

    /**
     * Returns the player
     * @return - The player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Returns the dealer
     * @return - the dealer
     */
    public Dealer getDealer() {
        return this.dealer;
    }

    /**
     * Sets the InGameViewController to allow communication
     * @param inGameViewController - Game window controller
     */
    public void setInGameViewController(InGameViewController inGameViewController) {
        this.inGameViewController = inGameViewController;
    }

    /**
     * Declares the winner of the round
     * @param winner - Winner of the round
     * @throws InterruptedException when a thread is waiting, sleeping, or otherwise occupied, and the thread is interrupted, either before or during the activity
     */
    public void declareWinner(String winner) throws InterruptedException {
        inGameViewController.declareWinner(winner);
    }

    /**
     * Disables hit button if the player hits a blackjack (21)
     */
    public void disableHit() {
        inGameViewController.disableHit();
    }

    /**
     * For testing purposes only
     * @return BlackJackGame-object created in the constructor
     */
    public BlackjackGame getBlackJackGame() {
        return blackJackGame;
    }

    /**
     * Gets double possibility
     * @return - Returns true or false/ is doubling possible or not
     */
    public boolean getDoublePossibility() {
        return this.doublePossibility;
    }

    /**
     * Sets the double possibility
     * @param doublePossibility - Current doubling possibility
     */
    public void setDoublePossibility(boolean doublePossibility) {
        this.doublePossibility = doublePossibility;
    }

    /**
     * Player doubles in game (Hits a new card and ends player's turn. Also doubles the current bet)
     * Only possible if the total of first two cards is between 9 and 11
     */
    public void playerDouble() {
        this.blackJackGame.playerDouble();
    }

    /**
     * Disables stand-button when it can't be used
     */
    public void disableStand() {
        inGameViewController.disableStand();
    }

    /**
     * Sets the split possibility
     * @param splitPossibility - Current splitting possibility
     */
    public void setSplitPossibility(boolean splitPossibility) {
        this.splitPossibility = splitPossibility;
    }

    /**
     * Sets the insurance possibility
     * @param insurancePossibility - Current insurance possibility
     */
    public void setInsurancePossibility(boolean insurancePossibility) { this.insurancePossibility = insurancePossibility; }

    /**
     * Gets the insurance possibility
     * @return - Returns true or false/ is insuring possible or not
     */
    public boolean getInsurancePossibility() { return this.insurancePossibility; }

    /**
     * Asks the Game-class to insure the players round
     */
    public void playerInsure() { this.blackJackGame.playerInsure(); }


    /**
     * Gets the split possibility
     * @return - Returns true or false/ is splitting possible or not
     */
    public boolean getSplitPossibility() {
        return this.splitPossibility;
    }

    /**
     * Player's game splits to two hands (First hand and splitted hand)
     */
    public void playerSplit() {
        this.blackJackGame.playerSplit();
    }


    /**
     * Ends game with even money rule
     */
    public void playerSurrender() {this.blackJackGame.playerSurrender();}


    /**
     * Sets the even money possibility
     * @param evenMoneyPossibility - Current even money possibility
     */
    public void setEvenMoneyPossibility(boolean evenMoneyPossibility) {this.evenMoneyPossibility = evenMoneyPossibility;}

    /**
     * Gets the even money possibility
     * @return - Returns true or false/ is even money possible or not
     */
    public boolean getEvenMoneyPossibility() {return evenMoneyPossibility;}

    /**
     * Ends game with even money rule
     */
    public void playerEvenMoney() {this.blackJackGame.playerEvenMoney();}

    /**
     * Asks the views fx-controller to show the suitable tip for player
     * @param tipType the type of the tip which is showcased in the UI when hoovering the mouse
     */
    public void showTip(String tipType) { inGameViewController.showTip(tipType); }


}
