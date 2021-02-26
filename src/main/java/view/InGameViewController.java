package view;

import controller.BlackjackController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Card;
import model.Logger;
import model.Player;

import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

/**
 * Controller class for the InGameView.fxml and Settings.fxml
 */

public class InGameViewController {

    public Text playerCard1;
    public Text playerCard2;
    public Text playerCard3;
    public Text playerCard4;
    public Text dealerCard1;
    public Text dealerCard2;
    public Text dealerCard3;
    public Text dealerCard4;
    public Text playerCurrency;
    public Text playerTotal;
    public Text dealerTotal;
    public Text currentBet;
    public TextField betField;
    public Button splitButton;
    public Button standButton;
    public Button hitButton;
    public Button dealButton;
    public ImageView winnerScreen;
    public Text declareWinner;
    public Text areYouSure;
    public Button yesButton;
    public Button noButton;
    public Button closebutton;
    private BlackjackController gameController;
    private ArrayList<Card> playersCards;
    private ArrayList<Card> dealerCards;
    private int bet;
    private StageManager stageManager;


    /**
     * Sets Are you sure-screen visible to get back to Menu
     */
    public void menuButton(){

        winnerScreen.setVisible(true);
        yesButton.setVisible(true);
        noButton.setVisible(true);
        areYouSure.setVisible(true);

    }

    /**
     * Loads to MainMenu.fxml
     * @param actionEvent
     * @throws IOException
     */
    public void yesAction(ActionEvent actionEvent) throws IOException {
        Parent menuParent = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));
        Scene menuScene = new Scene(menuParent);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setTitle("The Grand Myllypuro");
        window.setScene(menuScene);
        window.show();
    }

    /**
     * Closes Are you sure-screen
     */
    public void noAction() {
        winnerScreen.setVisible(false);
        yesButton.setVisible(false);
        noButton.setVisible(false);
        areYouSure.setVisible(false);
    }


    /**
     * Setting screen's Back-Button loads to MainMenu.fxml
     *
     * @param actionEvent
     * @throws IOException
     */
    public void settingsBackButton(ActionEvent actionEvent) throws IOException {
        Parent menuParent = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));
        Scene menuScene = new Scene(menuParent);

        stageManager = StageManager.getInstance();
        stageManager.getPrimaryStage().setTitle("The Grand Myllypuro");
        stageManager.getPrimaryStage().setScene(menuScene);
        stageManager.getPrimaryStage().show();

    }

    /**
     * Game screen's Instructions-Button loads to Instructions.fxml (Opens another window)
     *
     * @throws IOException
     */
    public void instructionsButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Instructions.fxml"));
        Parent instructionsParent = fxmlLoader.load();
        Scene instructionsScene = new Scene(instructionsParent);

        stageManager = StageManager.getInstance();
        stageManager.getPrimaryStage().setTitle("Instructions");
        stageManager.getPrimaryStage().setScene(instructionsScene);
        stageManager.getPrimaryStage().show();
    }

    /**
     * Instruction screen's Close-Button closes the instructions window
     */
    public void closeButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUImain.class.getResource("/InGameView.fxml"));
        Parent gameParent = loader.load();

        InGameViewController controller = loader.getController();
        BlackjackController gameController = new BlackjackController();
        controller.setGameController(gameController);
        gameController.setInGameViewController(controller);
        controller.init();
        Scene gameScene = new Scene(gameParent);

        stageManager = StageManager.getInstance();
        stageManager.getPrimaryStage().setTitle("The Grand Myllypuro");
        stageManager.getPrimaryStage().setScene(gameScene);
        stageManager.getPrimaryStage().show();
    }

    /**
     * Player hits and updates the total counter
     * @throws InterruptedException
     */
    public void hit() throws InterruptedException {
        gameController.hit();
        updateTotalResult();
    }

    /**
     * Deal-button sets bet, updates player's currency, Sets round to start, enables hit and stand buttons,
     * shows first cards in UI and updates the total counter
     */
    public void deal() {
        setBet();
        updateBalance();
        gameController.nextRound();
        dealButton.setDisable(true);
        hitButton.setDisable(false);
        standButton.setDisable(false);
        this.playersCards = gameController.getPlayersCards();
        this.dealerCards = gameController.getDealersCards();
        playerCard1.setText(playersCards.get(0).toString());
        playerCard2.setText(playersCards.get(1).toString());
        dealerCard1.setText(dealerCards.get(0).toString());
        updateTotalResult();
    }

    /**
     * Player stands and updates the total counter
     * @throws InterruptedException
     */
    public void stand() throws InterruptedException {
        gameController.stand();
        updateTotalResult();
    }

    /**
     * Declares winner of the round and enables winnerScreen, where winner is displayed in UI
     * Switch case defines which String is set to the UI
     * Updates player's currency
     * Clears the table
     * @param winner - Winner of the round
     * @throws InterruptedException
     */
    public void declareWinner(String winner) throws InterruptedException {
        winnerScreen.setVisible(true);
        declareWinner.setVisible(true);
        switch (winner) {
            case "player":
                declareWinner.setText("You win!");
                break;
            case "dealer":
                declareWinner.setText("Dealer wins");
                break;
            case "nobody":
                declareWinner.setText("Draw");
                break;
            case "Blackjack":
                declareWinner.setText("BLACKJACK!");
                break;
            case "busted":
                declareWinner.setText("Busted! Dealer wins.");
                break;
        }
        updateBalance();
        sleep(4000);
        clearTable();
        winnerScreen.setVisible(false);
    }

    /**
     * Updates the total counter
     */
    public void updateTotalResult() {
        playerTotal.setText("" + gameController.getPlayer().getHand().calculateTotal());
        dealerTotal.setText("" + gameController.getDealer().getHand().calculateTotal());
    }

    /**
     * Set gameController as the object of class BlackjackController
     * @param blackjackController - Object of the BlackjackController
     */
    public void setGameController(BlackjackController blackjackController) {
        gameController = blackjackController;
    }

    /**
     * Sets starting currency to the player in UI
     */

    public void showCurrency() {
        playerCurrency.setText("\uD83D\uDCB2" + gameController.getPlayer().getCurrency());
    }

    /**
     * Initializes the starting currency to the UI
     */
    public void init() {
        showCurrency();
    }


    /**
     * Disables the 'Hit' button if Blackjack has been gained immediately from the first two cards
     */
    public void disableHit() {
        hitButton.setDisable(true);
    }

    /**
     * Sets the bet from a textfield in the UI. Validates that the amount doesn't exceed player's balance.
     */
    public void setBet() { // TODO: Asetetun panoksen validointi
        bet = Integer.parseInt(betField.getText());
        if (gameController.setBet(bet)) {
            currentBet.setText("\uD83D\uDCB2" + bet);
            updateBalance();
        } else {

            //Jos saldo ei riitä, mitä tehdään käyttöliittymässä?
        }
    }

    /**
     * Updates the balance shown in the UI
     */
    public void updateBalance() {
        playerCurrency.setText("\uD83D\uDCB2" + gameController.getPlayer().getCurrency());
    }

    /**
     * Sets players cards to the UI based on the amount of cards.
     * @param playersCards is an array that contains objects of the player's cards
     */
    public void setPlayersCards(ArrayList<Card> playersCards) {
        switch (playersCards.size()) {
            case 1:
                playerCard1.setText(playersCards.get(0).toString());
                break;
            case 2:
                playerCard1.setText(playersCards.get(0).toString());
                playerCard2.setText(playersCards.get(1).toString());
                break;
            case 3:
                playerCard1.setText(playersCards.get(0).toString());
                playerCard2.setText(playersCards.get(1).toString());
                playerCard3.setText(playersCards.get(2).toString());
                break;
            case 4:
                playerCard1.setText(playersCards.get(0).toString());
                playerCard2.setText(playersCards.get(1).toString());
                playerCard3.setText(playersCards.get(2).toString());
                playerCard4.setText(playersCards.get(3).toString());
                break;
        }

        updateTotalResult();
    }

    /**
     * Sets dealers cards to the UI based on the amount of cards.
     * @param dealerCards is an array that contains objects of the dealer's cards
     */
    public void setDealersCards(ArrayList<Card> dealerCards) {
        switch (dealerCards.size()) {
            case 1:
                System.out.println("yksi kortti");
                dealerCard1.setText(dealerCards.get(0).toString());
                break;
            case 2:
                System.out.println("2 korttia" + dealerCards.get(1).toString());
                dealerCard1.setText(dealerCards.get(0).toString());
                dealerCard2.setText(dealerCards.get(1).toString());
                System.out.println(dealerCard2.getText());
                break;
            case 3:
                dealerCard1.setText(dealerCards.get(0).toString());
                dealerCard2.setText(dealerCards.get(1).toString());
                dealerCard3.setText(dealerCards.get(2).toString());
                break;
            case 4:
                dealerCard1.setText(dealerCards.get(0).toString());
                dealerCard2.setText(dealerCards.get(1).toString());
                dealerCard3.setText(dealerCards.get(2).toString());
                dealerCard4.setText(dealerCards.get(3).toString());
                break;
        }
        updateTotalResult();
        try {
            sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clears the table to end the round
     */
    public void clearTable() {
        hitButton.setDisable(true);
        standButton.setDisable(true);
        dealButton.setDisable(false);
        dealerCard1.setText("");
        dealerCard2.setText("");
        dealerCard3.setText("");
        dealerCard4.setText("");
        playerCard1.setText("");
        playerCard2.setText("");
        playerCard3.setText("");
        playerCard4.setText("");
        dealerTotal.setText("");
        playerTotal.setText("");
        declareWinner.setText("");
    }

}

