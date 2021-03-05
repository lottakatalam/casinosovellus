package view;

import controller.BlackjackController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Card;

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
    public Text setValidBet;
    public Button OKBetButton;
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
    public Button instructionsButton;
    public Button menuButton;
    public Text currencyText;
    public Text yourHandText;
    public Text dealersHandText;
    public Button doubleButton;
    public Button surrenderButton;
    public Button insuranceButton;
    public Button evenMoneyButton;
    public Text instructionsText;
    public VBox instructionsBox;
    private BlackjackController gameController;
    private ArrayList<Card> playersCards;
    private ArrayList<Card> dealerCards;
    private int bet;
    private StageManager stageManager;
    boolean instructionsOn;
    public ImageView playerCardImage1;
    public ImageView playerCardImage2;
    public ImageView playerCardImage3;
    public ImageView playerCardImage4;
    public ImageView dealerCardImage1;
    public ImageView dealerCardImage2;
    public ImageView dealerCardImage3;
    public ImageView dealerCardImage4;

    /**
     * Sets Are you sure-screen visible to get back to Menu
     */
    public void menuButton() {

        winnerScreen.setVisible(true);
        yesButton.setVisible(true);
        noButton.setVisible(true);
        areYouSure.setVisible(true);

    }

    /**
     * Loads to MainMenu.fxml
     *
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
     * Game screen's Instructions-Button loads to Instructions.fxml (Opens another window)
     *
     * @throws IOException
     */
    public void instructionsButton() {
        instructionsOn = true;
        playerCardImage1.setVisible(false);
        playerCardImage2.setVisible(false);
        playerCardImage3.setVisible(false);
        playerCardImage4.setVisible(false);
        dealerCardImage1.setVisible(false);
        dealerCardImage2.setVisible(false);
        dealerCardImage3.setVisible(false);
        dealerCardImage4.setVisible(false);
        playerCurrency.setVisible(false);
        playerTotal.setVisible(false);
        dealerTotal.setVisible(false);
        currentBet.setVisible(false);
        betField.setVisible(false);
        splitButton.setVisible(false);
        standButton.setVisible(false);
        hitButton.setVisible(false);
        dealButton.setVisible(false);
        instructionsButton.setVisible(false);
        menuButton.setVisible(false);
        currencyText.setVisible(false);
        yourHandText.setVisible(false);
        dealersHandText.setVisible(false);
        doubleButton.setVisible(false);
        surrenderButton.setVisible(false);
        insuranceButton.setVisible(false);
        evenMoneyButton.setVisible(false);
        instructionsText.setVisible(true);
        instructionsBox.setVisible(true);
        closebutton.setVisible(true);
    }

    /**
     * Instruction screen's Close-Button closes the instructions window
     */
    public void closeButton() {
        instructionsOn = false;
        playerCardImage1.setVisible(true);
        playerCardImage2.setVisible(true);
        playerCardImage3.setVisible(true);
        playerCardImage4.setVisible(true);
        dealerCardImage1.setVisible(true);
        dealerCardImage2.setVisible(true);
        dealerCardImage3.setVisible(true);
        dealerCardImage4.setVisible(true);
        playerCurrency.setVisible(true);
        playerTotal.setVisible(true);
        dealerTotal.setVisible(true);
        currentBet.setVisible(true);
        betField.setVisible(true);
        splitButton.setVisible(true);
        standButton.setVisible(true);
        hitButton.setVisible(true);
        dealButton.setVisible(true);
        instructionsButton.setVisible(true);
        menuButton.setVisible(true);
        currencyText.setVisible(true);
        yourHandText.setVisible(true);
        dealersHandText.setVisible(true);
        doubleButton.setVisible(true);
        surrenderButton.setVisible(true);
        insuranceButton.setVisible(true);
        evenMoneyButton.setVisible(true);
        instructionsText.setVisible(false);
        instructionsBox.setVisible(false);
        closebutton.setVisible(false);
    }

    /**
     * Player hits and updates the total counter
     *
     * @throws InterruptedException
     */
    public void hit() throws InterruptedException {
        gameController.hit();
        disableDouble();
        updateTotalResult();
    }

    /**
     * Deal-button sets bet, updates player's currency, Sets round to start, enables hit and stand buttons,
     * shows first cards in UI and updates the total counter
     */
    public void deal() {
        if (setBet()) {
            updateBalance();
            gameController.nextRound();
            dealButton.setDisable(true);
            hitButton.setDisable(false);
            standButton.setDisable(false);
            this.playersCards = gameController.getPlayersCards();
            this.dealerCards = gameController.getDealersCards();
            updateTotalResult();
            checkForDouble();
        }
    }

    public void setCardImage(String card, int cardNumber) {
        Image cardImage= new Image(getClass().getResource(card).toExternalForm());

        switch(cardNumber) {
            case 1:
                playerCardImage1.setImage(cardImage);
                break;
            case 2:
                playerCardImage2.setImage(cardImage);
                break;
            case 3:
                playerCardImage3.setImage(cardImage);
                break;
            case 4:
                playerCardImage4.setImage(cardImage);
                break;
        }
    }

    public void setDealerCardImage(String card, int cardNumber) {
        Image cardImage= new Image(getClass().getResource(card).toExternalForm());

        switch(cardNumber) {
            case 1:
                dealerCardImage1.setImage(cardImage);
                break;
            case 2:
                dealerCardImage2.setImage(cardImage);
                break;
            case 3:
                dealerCardImage3.setImage(cardImage);
                break;
            case 4:
                dealerCardImage4.setImage(cardImage);
                break;
        }
    }

    public String getImage(Card card) {
        String suit = "";
        String value = "";
        String file = "";
        switch (card.getSuit()) {
            case 1:
                suit = "C";
                break;
            case 2:
                suit = "D";
                break;
            case 3:
                suit = "S";
                break;
            case 4:
                suit = "H";
                break;
        }
        if (card.getRank() <= 10 && card.getRank() != 1) {
            value = ""+card.getRank();
        } else {
            switch (card.getRank()) {
                case 11: value = "J"; break;
                case 12: value = "Q"; break;
                case 13: value = "K"; break;
                case 1: value = "A"; break;
            }
        }
        file = value + suit + ".png";

        return file;
    }

    /**
     * Player stands and updates the total counter
     *
     * @throws InterruptedException
     */
    public void stand() throws InterruptedException {
        disableHit();
        disableStand();
        gameController.stand();
        updateTotalResult();
    }

    /**
     * Declares winner of the round and enables winnerScreen, where winner is displayed in UI
     * Switch case defines which String is set to the UI
     * Updates player's currency
     * Clears the table
     *
     * @param winner - Winner of the round
     * @throws InterruptedException
     */
    public void declareWinner(String winner) throws InterruptedException {
        if (!instructionsOn) {
            winnerScreen.setVisible(true);
            declareWinner.setVisible(true);
        }
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
        clearTable();
        sleep(4000);
        winnerScreen.setVisible(false);
        declareWinner.setText("");
        currentBet.setText("\uD83D\uDCB2" + bet);
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
     *
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
        instructionsBox.setVisible(false);
        instructionsText.setVisible(false);
        closebutton.setVisible(false);
    }


    /**
     * Disables the 'Hit' button if Blackjack has been gained immediately from the first two cards
     */
    public void disableHit() {
        hitButton.setDisable(true);
    }

    public void disableStand() {
        standButton.setDisable(true);
        disableDouble();
    }

    public void disableDouble() {
        doubleButton.setDisable(true);
    }

    public void doublePressed() {
        doubleButton.setDisable(true);
        gameController.playerDouble();
        currentBet.setText("\uD83D\uDCB2" + gameController.getPlayer().getBet());
        updateBalance();
    }

    public void checkForDouble() {
        if (gameController.getDoublePossibility()) {
            doubleButton.setDisable(false);
        }
    }

    /**
     * Sets the bet from a textfield in the UI. Validates that the amount doesn't exceed player's balance.
     */
    public boolean setBet() {
        try {
            bet = Integer.parseInt(betField.getText());
            if (gameController.setBet(bet)) {
                currentBet.setText("\uD83D\uDCB2" + bet);
                updateBalance();
                return true;
            } else {
                String message = "Your bet can not be greater than your balance";
                setValidBetView(message);
            }
        } catch (NumberFormatException e) {
            String message = "Place a valid bet";
            setValidBetView(message);
        }
        return false;

    }

    private void setValidBetView(String message) {
        winnerScreen.setVisible(true);
        setValidBet.setText(message);
        setValidBet.setVisible(true);
        OKBetButton.setVisible(true);
    }

    /*public void OKBetButton() {
        winnerScreen.setVisible(false);
        setValidBet.setVisible(false);
        setBet();

    }*/

    /**
     * OKBetButton-action
     */
    public void backToSettingBetAction() {
        winnerScreen.setVisible(false);
        setValidBet.setVisible(false);
        OKBetButton.setVisible(false);
    }

    /**
     * Updates the balance shown in the UI
     */
    public void updateBalance() {
        playerCurrency.setText("\uD83D\uDCB2" + gameController.getPlayer().getCurrency());
    }

    /**
     * Sets players cards to the UI based on the amount of cards.
     *
     * @param playersCards is an array that contains objects of the player's cards
     */
    public void setPlayersCards(ArrayList<Card> playersCards) {
        switch (playersCards.size()) {
            case 1:
                setCardImage(getImage(playersCards.get(0)), 1);
                break;
            case 2:
                setCardImage(getImage(playersCards.get(0)), 1);
                setCardImage(getImage(playersCards.get(1)), 2);
                break;
            case 3:
                setCardImage(getImage(playersCards.get(0)), 1);
                setCardImage(getImage(playersCards.get(1)), 2);
                setCardImage(getImage(playersCards.get(2)), 3);
                break;
            case 4:
                setCardImage(getImage(playersCards.get(0)), 1);
                setCardImage(getImage(playersCards.get(1)), 2);
                setCardImage(getImage(playersCards.get(2)), 3);
                setCardImage(getImage(playersCards.get(3)), 4);
                break;
        }
        updateTotalResult();
    }

    /**
     * Sets dealers cards to the UI based on the amount of cards.
     *
     * @param dealerCards is an array that contains objects of the dealer's cards
     */
    public void setDealersCards(ArrayList<Card> dealerCards) {
        switch (dealerCards.size()) {
            case 1:
                setDealerCardImage(getImage(dealerCards.get(0)), 1);
                break;
            case 2:
                setDealerCardImage(getImage(dealerCards.get(0)), 1);
                setDealerCardImage(getImage(dealerCards.get(1)), 2);
                break;
            case 3:
                setDealerCardImage(getImage(dealerCards.get(0)), 1);
                setDealerCardImage(getImage(dealerCards.get(1)), 2);
                setDealerCardImage(getImage(dealerCards.get(2)), 3);
                break;
            case 4:
                setDealerCardImage(getImage(dealerCards.get(0)), 1);
                setDealerCardImage(getImage(dealerCards.get(1)), 2);
                setDealerCardImage(getImage(dealerCards.get(2)), 3);
                setDealerCardImage(getImage(dealerCards.get(3)), 4);
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
        dealerCardImage1.setImage(null);
        dealerCardImage2.setImage(null);
        dealerCardImage3.setImage(null);
        dealerCardImage4.setImage(null);
        playerCardImage1.setImage(null);
        playerCardImage2.setImage(null);
        playerCardImage3.setImage(null);
        playerCardImage4.setImage(null);
    }

}

