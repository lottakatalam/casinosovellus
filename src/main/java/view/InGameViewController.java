package view;

import controller.BlackjackController;
import controller.SettingsController;
import controller.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Card;
import model.LanguageLoader;
import model.UserCredentialHandler;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

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
    public Button volumeOFFbutton;
    public Button volumeONbutton;
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
    public ImageView playerCardImage5;
    public ImageView playerCardImage6;
    public ImageView playerSplit1;
    public ImageView playerSplit2;
    public ImageView playerSplit3;
    public ImageView playerSplit4;
    public ImageView playerSplit5;
    public ImageView playerSplit6;
    public ImageView dealerCardImage1;
    public ImageView dealerCardImage2;
    public ImageView dealerCardImage3;
    public ImageView dealerCardImage4;
    public ImageView dealerCardImage5;
    public ImageView dealerCardImage6;
    private boolean splitted = false;
    public Button yesMoneyButton;
    public Button noMoneyButton;
    public Text outOfMoney;
    private UserController userController = new UserController();
    LanguageLoader texts = LanguageLoader.getInstance();

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
     * Loads to MainMenu
     *
     * @param actionEvent
     * @throws IOException - if .fxml file is not found
     */
    public void yesAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/MainMenu.fxml"));
        loader.setResources(LanguageLoader.getInstance().getResourceBundle());
        Parent menuParent = loader.load();
        MainMenuController controller = loader.getController();
        if (userController.isUserLoggedIn()) {
            controller.loginButton.setVisible(false);
            controller.registerButton.setVisible(false);
            controller.logoutButton.setVisible(true);
            controller.changePasswordButton.setVisible(true);
        }
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
     * Initializes stageManager
     * Check the current volume state
     */
    public void initialize() {
        stageManager = StageManager.getInstance();
        checkVolume();
    }

    /**
     * Game screen's Instructions-Button makes instructions visible top of the game screen
     *
     * @throws IOException
     */
    public void instructionsButton() {
        instructionsOn = true;
        playerCardImage1.setVisible(false);
        playerCardImage2.setVisible(false);
        playerCardImage3.setVisible(false);
        playerCardImage4.setVisible(false);
        playerCardImage5.setVisible(false);
        playerCardImage6.setVisible(false);
        dealerCardImage1.setVisible(false);
        dealerCardImage2.setVisible(false);
        dealerCardImage3.setVisible(false);
        dealerCardImage4.setVisible(false);
        dealerCardImage5.setVisible(false);
        dealerCardImage6.setVisible(false);
        playerSplit1.setVisible(false);
        playerSplit2.setVisible(false);
        playerSplit3.setVisible(false);
        playerSplit4.setVisible(false);
        playerSplit5.setVisible(false);
        playerSplit6.setVisible(false);
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
     * Instruction screen's Close-Button closes the instructions
     */
    public void closeButton() {
        instructionsOn = false;
        playerCardImage1.setVisible(true);
        playerCardImage2.setVisible(true);
        playerCardImage3.setVisible(true);
        playerCardImage4.setVisible(true);
        playerCardImage5.setVisible(true);
        playerCardImage6.setVisible(true);
        dealerCardImage1.setVisible(true);
        dealerCardImage2.setVisible(true);
        dealerCardImage3.setVisible(true);
        dealerCardImage4.setVisible(true);
        dealerCardImage5.setVisible(true);
        dealerCardImage6.setVisible(true);
        playerSplit1.setVisible(true);
        playerSplit2.setVisible(true);
        playerSplit3.setVisible(true);
        playerSplit4.setVisible(true);
        playerSplit5.setVisible(true);
        playerSplit6.setVisible(true);
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
     */
    public void hit() {
        splitButton.setDisable(true);
        insuranceButton.setDisable(true);
        if (gameController.getSplitStatus()) {
            gameController.hitToSplittedHand();
        } else {
            gameController.hit();
        }
        disableDouble();
        updateTotalResult();
    }

    /**
     * Deal-button sets bet, updates player's currency, Sets round to start, enables hit and stand buttons,
     * shows first cards in UI and updates the total counter
     */
    public void deal() {
        if (gameController.getPlayer().getCurrency() == 0) {
            checkBalance();
        } else {
            if (setBet()) {
                updateBalance();
                gameController.nextRound();
                dealButton.setDisable(true);
                hitButton.setDisable(false);
                standButton.setDisable(false);
                surrenderButton.setDisable(false);
                this.playersCards = gameController.getPlayersCards();
                this.dealerCards = gameController.getDealersCards();
                Image cardImage= new Image(getClass().getResource("/Cards/red_back.png").toExternalForm());
                dealerCardImage2.setImage(cardImage);
                updateTotalResult();
                //checkForBlackJack();
                checkSpecialRules();
            }
        }

    }

    /**
     * Sets the image of the player's card to UI
     * @param card - Card set to UI
     * @param cardNumber - Number of that card
     */
    public void setCardImage(String card, int cardNumber) {
        Image cardImage= new Image(getClass().getResource("/Cards/"+card).toExternalForm());

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
            case 5:
                playerCardImage5.setImage(cardImage);
                break;
            case 6:
                playerCardImage6.setImage(cardImage);
                break;
        }
    }

    /**
     * Sets the image of the player's card to UI (splitted hand)
     * @param card - Card set to UI
     * @param cardNumber - Number of that card
     */
    public void setSplittedCardImage(String card, int cardNumber) {
        Image cardImage= new Image(getClass().getResource("/Cards/"+card).toExternalForm());

        switch(cardNumber) {
            case 1:
                playerSplit1.setImage(cardImage);
                break;
            case 2:
                playerSplit2.setImage(cardImage);
                break;
            case 3:
                playerSplit3.setImage(cardImage);
                break;
            case 4:
                playerSplit4.setImage(cardImage);
                break;
            case 5:
                playerSplit5.setImage(cardImage);
                break;
            case 6:
                playerSplit6.setImage(cardImage);
                break;
        }
    }

    /**
     * Sets image of dealer's card to UI
     * @param card - Card set to UI
     * @param cardNumber - Number of that card
     */
    public void setDealerCardImage(String card, int cardNumber) {
        Image cardImage= new Image(getClass().getResource("/Cards/"+card).toExternalForm());

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
            case 5:
                dealerCardImage5.setImage(cardImage);
                break;
            case 6:
                dealerCardImage6.setImage(cardImage);
                break;
        }
    }

    /**
     * Gets the image of card
     * @param card - The card
     * @return
     */
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
     */
    public void stand() {
        splitButton.setDisable(true);
        insuranceButton.setDisable(true);
        if (gameController.getSplitStatus() && splitted) {
            disableHit();
            disableStand();
            gameController.stand();
        } else if (!gameController.getSplitStatus() && splitted) {
            gameController.setSplitStatus(true);
            gameController.hitToSplittedHand();
        } else {
            disableHit();
            disableStand();
            gameController.stand();
        }
        updateTotalResult();
    }

    public String formatBet() {
        if (LanguageLoader.getInstance().getLocale().toString().equals("en_GB")) {
            return texts.getString("Currency") + " " + bet;
        } else {
            return bet + " " + texts.getString("Currency");
        }
    }

    public String formatInsurance(int insurance) {
        if (LanguageLoader.getInstance().getLocale().toString().equals("en_GB")) {
            return texts.getString("Currency") + " " + insurance;
        } else {
            return insurance + " " + texts.getString("Currency");
        }
    }

    /**
     * Player splits his hand to two hands
     */
    public void split() {
        if (gameController.setSplitBet(bet)) {
            currentBet.setText(formatBet() + " + " + formatBet());
            updateBalance();
            this.splitted = true;
            gameController.playerSplit();
            splitHandInUI();
            updateTotalResult();
            splitButton.setDisable(true);
            disableDouble();
        } else {
            setValidBetView("Insufficient balance to split the hand");
        }
    }

    /**
     * Player insures
     */
    public void insure() {
        int insurance = this.bet / 2;
        if (gameController.getInsurancePossibility()) {
            currentBet.setText(formatBet() + "(" + formatInsurance(insurance) + ")");
            gameController.playerInsure();
            updateBalance();
            splitButton.setDisable(true);
            insuranceButton.setDisable(true);
            disableDouble();
        }
    }

    /**
     * Player surrenders
     */
    public void surrender() {
        int surrender = this.bet / 2;
        if (gameController.getSurrenderPossibility()) {
            currentBet.setText(formatBet() + "(" + formatInsurance(surrender) + ")");
            gameController.playerSurrender();
            updateBalance();
            splitButton.setDisable(true);
            surrenderButton.setDisable(true);
            disableDouble();
        }
    }

    /**
     * Player calls even money
     */
    public void handleEvenMoney() {
        gameController.playerEvenMoney();
        evenMoneyButton.setDisable(true);
    }

    public void checkForBlackJack() {
        if (gameController.getPlayer().getHand().calculateTotal() == 21) {
            disableHit();
            disableStand();
        }
    }


    /**
     * Sets splitted hand to UI when player has splitted
     */
    public void splitHandInUI() {
        playerCardImage2.setImage(null);
        setSplittedCardImage(getImage(gameController.getPlayersSplittedCards().get(0)), 1);
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
                declareWinner.setText(texts.getString("YouWin"));
                break;
            case "dealer":
                declareWinner.setText(texts.getString("DealerWins"));
                break;
            case "nobody":
                declareWinner.setText(texts.getString("Draw"));
                break;
            case "Blackjack":
                declareWinner.setText("BLACKJACK!");
                break;
            case "busted":
                declareWinner.setText(texts.getString("Busted"));
                break;
            case "insured":
                declareWinner.setText(texts.getString("InsurancePayback"));
                break;
            case "surrendered":
                declareWinner.setText(texts.getString("Surrendered"));
                break;
            case "EvenMoney":
                declareWinner.setText(texts.getString("EvenMoney"));

        }
        updateBalance();
        System.out.println(winner);
        sleep(4000);
        if (gameController.getSplitStatus()) {
            gameController.setSplitStatus(false);
            declareWinner.setText("");
        } else {
            winnerScreen.setVisible(false);
            declareWinner.setText("");
            currentBet.setText(formatBet());
            clearTable();
            splitted = false;
            checkBalance();
        }

        if(UserCredentialHandler.getInstance().isLoggedIn()) {
            UserCredentialHandler.getInstance().getLoggedInUser().addRound();
        }
    }

    /**
     * If player runs out of money, the game will give them an option to get money back
     * Opens up a message with "Yes" and "No" actions
     */
    public void checkBalance() {
        if (gameController.getPlayer().getCurrency() == 0) {
            winnerScreen.setVisible(true);
            outOfMoney.setVisible(true);
            outOfMoney.setText(texts.getString("OutOfMoney"));
            yesMoneyButton.setVisible(true);
            noMoneyButton.setVisible(true);
        }
    }

    /**
     * Player declines the money offered and continues with 0 balance
     */
    public void noMoney() {
        winnerScreen.setVisible(false);
        outOfMoney.setVisible(false);
        yesMoneyButton.setVisible(false);
        noMoneyButton.setVisible(false);
    }

    /**
     * Player wants more money and sets new balance for him
     */
    public void yesMoney() {
        gameController.getPlayer().setNewBalance();
        winnerScreen.setVisible(false);
        outOfMoney.setVisible(false);
        yesMoneyButton.setVisible(false);
        noMoneyButton.setVisible(false);
        updateBalance();
    }

    /**
     * Updates the total counter
     */
    public void updateTotalResult() {
        if (splitted) {
            playerTotal.setText("" + gameController.getPlayer().getHand().calculateTotal() + " & " + gameController.getPlayer().getHand().calculateSplittedTotal());
        } else {
            playerTotal.setText("" + gameController.getPlayer().getHand().calculateTotal());
        }
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
        playerCurrency.setText(formatBalance());
    }

    public String formatBalance() {
        if (LanguageLoader.getInstance().getLocale().toString().equals("en_GB")) {
            return texts.getString("Currency") + " " + gameController.getPlayer().getCurrency();
        } else {
            return gameController.getPlayer().getCurrency() + " " + texts.getString("Currency");
        }
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

    /**
     * Disables the 'Stand' button
     */
    public void disableStand() {
        standButton.setDisable(true);
        disableDouble();
    }

    /**
     * Disables 'Double' button
     */
    public void disableDouble() {
        doubleButton.setDisable(true);
    }

    public void doublePressed() {
        splitButton.setDisable(true);
        doubleButton.setDisable(true);
        hitButton.setDisable(true);
        standButton.setDisable(true);
        gameController.playerDouble();
        currentBet.setText(formatBet() + " + " + formatBet());
        updateBalance();
    }

    /**
     * Checks if doubling is possible and if its possible, it enables 'Double' button
     */
    public void checkForDouble() {
        if (gameController.getDoublePossibility()) {
            doubleButton.setDisable(false);
        }
    }

    /**
     * Checks if splitting is possible and if its possible, it enables 'Split' button
     */
    public void checkForSplit() {
        if(gameController.getSplitPossibility()) {
            splitButton.setDisable(false);
        }
    }

    /**
     * Checks if insuring is possible and if its possible, it enables 'Insurance' button
     */
    public void checkForInsurance() {
        if(gameController.getInsurancePossibility()) {
            insuranceButton.setDisable(false);
        }
    }
    public void checkForSurrender() {
        if (gameController.getSurrenderPossibility()) {
            surrenderButton.setDisable(false);
        }
    }

    public void checkForEvenMoney() {
            if (gameController.getEvenMoneyPossibility()) {
            evenMoneyButton.setDisable(false);
            }
    }
    /**
     * Checks for all the special rules
     */
    public void checkSpecialRules() {
        checkForSplit();
        checkForDouble();
        checkForInsurance();
        checkForSurrender();
        checkForEvenMoney();
    }

    /**
     * Sets the bet from a textfield in the UI. Validates that the amount doesn't exceed player's balance.
     */
    public boolean setBet() {
        try {
            bet = Integer.parseInt(betField.getText());
            if (gameController.setBet(bet)) {
                currentBet.setText(formatBet());
                updateBalance();
                return true;
            } else {
                String message = texts.getString("BetGreaterThanBalance");
                setValidBetView(message);
            }
        } catch (NumberFormatException e) {
            String message = texts.getString("PlaceAValidBet");
            setValidBetView(message);
        }
        return false;

    }

    /**
     * If bet is not valid, error message pops up
     * @param message - Message that is shown in UI
     */
    private void setValidBetView(String message) {
        winnerScreen.setVisible(true);
        setValidBet.setText(message);
        setValidBet.setVisible(true);
        OKBetButton.setVisible(true);
    }

    /**
     * OKBetButton-action
     */
    public void backToSettingBetAction() {
        winnerScreen.setVisible(false);
        setValidBet.setVisible(false);
        OKBetButton.setVisible(false);
    }

    /**
     * Shows a tip to the player that recommends to hit
     */
    public void showHitTip() {
        Tooltip hitTip = new Tooltip();
        hitTip.setText(texts.getString("HitTip"));
        hitButton.setTooltip(hitTip);
        standButton.setTooltip(hitTip);
        doubleButton.setTooltip(hitTip);
        splitButton.setTooltip(hitTip);
        surrenderButton.setTooltip(hitTip);
        insuranceButton.setTooltip(hitTip);
        evenMoneyButton.setTooltip(hitTip);
    }

    /**
     * Shows a tip to the player that recommends to stand
     */
    public void showStandTip() {
        Tooltip standTip = new Tooltip();
        standTip.setText(texts.getString("StandTip"));
        hitButton.setTooltip(standTip);
        standButton.setTooltip(standTip);
        doubleButton.setTooltip(standTip);
        splitButton.setTooltip(standTip);
        surrenderButton.setTooltip(standTip);
        insuranceButton.setTooltip(standTip);
        evenMoneyButton.setTooltip(standTip);
    }

    /**
     * Shows a tip to the player that recommends to double
     */
    public void showDoubleTip() {
        Tooltip doubleTip = new Tooltip();
        doubleTip.setText(texts.getString("DoubleTip"));
        hitButton.setTooltip(doubleTip);
        standButton.setTooltip(doubleTip);
        doubleButton.setTooltip(doubleTip);
        splitButton.setTooltip(doubleTip);
        surrenderButton.setTooltip(doubleTip);
        insuranceButton.setTooltip(doubleTip);
        evenMoneyButton.setTooltip(doubleTip);
    }

    /**
     * Shows a tip to the player that recommends to split
     */
    public void showSplitTip() {
        Tooltip splitTip = new Tooltip();
        splitTip.setText(texts.getString("SplitTip"));
        hitButton.setTooltip(splitTip);
        standButton.setTooltip(splitTip);
        doubleButton.setTooltip(splitTip);
        splitButton.setTooltip(splitTip);
        surrenderButton.setTooltip(splitTip);
        insuranceButton.setTooltip(splitTip);
        evenMoneyButton.setTooltip(splitTip);
    }

    /**
     * Shows a tip to the player that recommends to surrender
     */
    public void showSurrenderTip() {
        Tooltip surrenderTip = new Tooltip();
        surrenderTip.setText(texts.getString("SurrenderTip"));
        hitButton.setTooltip(surrenderTip);
        standButton.setTooltip(surrenderTip);
        doubleButton.setTooltip(surrenderTip);
        splitButton.setTooltip(surrenderTip);
        surrenderButton.setTooltip(surrenderTip);
        insuranceButton.setTooltip(surrenderTip);
        evenMoneyButton.setTooltip(surrenderTip);
    }

    /**
     * Shows a tip to the player that recommends to insure
     */
    public void showInsuranceTip() {
        Tooltip insuranceTip = new Tooltip();
        insuranceTip.setText(texts.getString("InsuranceTip"));
        hitButton.setTooltip(insuranceTip);
        standButton.setTooltip(insuranceTip);
        doubleButton.setTooltip(insuranceTip);
        splitButton.setTooltip(insuranceTip);
        surrenderButton.setTooltip(insuranceTip);
        insuranceButton.setTooltip(insuranceTip);
        evenMoneyButton.setTooltip(insuranceTip);
    }

    /**
     * Shows a tip to the player that recommends to take even money
     */
    public void showEvenMoneyTip() {
        Tooltip evenMoneyTip = new Tooltip();
        evenMoneyTip.setText(texts.getString("EvenMoneyTip"));
        hitButton.setTooltip(evenMoneyTip);
        standButton.setTooltip(evenMoneyTip);
        doubleButton.setTooltip(evenMoneyTip);
        splitButton.setTooltip(evenMoneyTip);
        surrenderButton.setTooltip(evenMoneyTip);
        insuranceButton.setTooltip(evenMoneyTip);
        evenMoneyButton.setTooltip(evenMoneyTip);
    }

    /**
     * Mutes game music
     */
    public void volumeOFF() {
        volumeOFFbutton.setVisible(false);
        volumeONbutton.setVisible(true);
        stageManager.getMediaPlayer().setVolume(0);
    }

    /**
     * Turns game music back ON
     */
    public void volumeON() {
        volumeONbutton.setVisible(false);
        volumeOFFbutton.setVisible(true);
        stageManager.getMediaPlayer().setVolume(SettingsController.getInstance().getVolume());
    }

    /**
     * Checks is the volume ON or OFF
     */
    public void checkVolume() {
        if(stageManager.getMediaPlayer().getVolume() == 0) {
            volumeOFFbutton.setVisible(false);
            volumeONbutton.setVisible(true);
        }else {
            volumeONbutton.setVisible(false);
            volumeOFFbutton.setVisible(true);
        }
    }

    /**
     * Updates the balance shown in the UI
     */
    public void updateBalance() {
        playerCurrency.setText(formatBalance());
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
            case 5:
                setCardImage(getImage(playersCards.get(0)), 1);
                setCardImage(getImage(playersCards.get(1)), 2);
                setCardImage(getImage(playersCards.get(2)), 3);
                setCardImage(getImage(playersCards.get(3)), 4);
                setCardImage(getImage(playersCards.get(4)), 5);
                break;
            case 6:
                setCardImage(getImage(playersCards.get(0)), 1);
                setCardImage(getImage(playersCards.get(1)), 2);
                setCardImage(getImage(playersCards.get(2)), 3);
                setCardImage(getImage(playersCards.get(3)), 4);
                setCardImage(getImage(playersCards.get(4)), 5);
                setCardImage(getImage(playersCards.get(5)), 6);
        }
        updateTotalResult();
    }

    /**
     * Sets players' splitted cards to the UI based on the amount of cards.
     * @param playersSplittedCards
     */
    public void setPlayersSplittedCards(ArrayList<Card> playersSplittedCards) {
        switch (playersSplittedCards.size()) {
            case 1:
                setSplittedCardImage(getImage(playersSplittedCards.get(0)), 1);
                break;
            case 2:
                setSplittedCardImage(getImage(playersSplittedCards.get(0)), 1);
                setSplittedCardImage(getImage(playersSplittedCards.get(1)), 2);
                break;
            case 3:
                setSplittedCardImage(getImage(playersSplittedCards.get(0)), 1);
                setSplittedCardImage(getImage(playersSplittedCards.get(1)), 2);
                setSplittedCardImage(getImage(playersSplittedCards.get(2)), 3);
                break;
            case 4:
                setSplittedCardImage(getImage(playersSplittedCards.get(0)), 1);
                setSplittedCardImage(getImage(playersSplittedCards.get(1)), 2);
                setSplittedCardImage(getImage(playersSplittedCards.get(2)), 3);
                setSplittedCardImage(getImage(playersSplittedCards.get(3)), 4);
                break;
            case 5:
                setSplittedCardImage(getImage(playersSplittedCards.get(0)), 1);
                setSplittedCardImage(getImage(playersSplittedCards.get(1)), 2);
                setSplittedCardImage(getImage(playersSplittedCards.get(2)), 3);
                setSplittedCardImage(getImage(playersSplittedCards.get(3)), 4);
                setSplittedCardImage(getImage(playersSplittedCards.get(4)), 5);
                break;
            case 6:
                setSplittedCardImage(getImage(playersSplittedCards.get(0)), 1);
                setSplittedCardImage(getImage(playersSplittedCards.get(1)), 2);
                setSplittedCardImage(getImage(playersSplittedCards.get(2)), 3);
                setSplittedCardImage(getImage(playersSplittedCards.get(3)), 4);
                setSplittedCardImage(getImage(playersSplittedCards.get(4)), 5);
                setSplittedCardImage(getImage(playersSplittedCards.get(5)), 6);
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
            case 5:
                setDealerCardImage(getImage(dealerCards.get(0)), 1);
                setDealerCardImage(getImage(dealerCards.get(1)), 2);
                setDealerCardImage(getImage(dealerCards.get(2)), 3);
                setDealerCardImage(getImage(dealerCards.get(3)), 4);
                setDealerCardImage(getImage(dealerCards.get(4)), 5);
                break;
            case 6:
                setDealerCardImage(getImage(dealerCards.get(0)), 1);
                setDealerCardImage(getImage(dealerCards.get(1)), 2);
                setDealerCardImage(getImage(dealerCards.get(2)), 3);
                setDealerCardImage(getImage(dealerCards.get(3)), 4);
                setDealerCardImage(getImage(dealerCards.get(4)), 5);
                setDealerCardImage(getImage(dealerCards.get(5)), 6);
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
     * TODO: nappien disablointi
     */
    public void clearTable() {
        hitButton.setDisable(true);
        standButton.setDisable(true);
        surrenderButton.setDisable(true);
        dealButton.setDisable(false);
        dealerCardImage1.setImage(null);
        dealerCardImage2.setImage(null);
        dealerCardImage3.setImage(null);
        dealerCardImage4.setImage(null);
        dealerCardImage5.setImage(null);
        dealerCardImage6.setImage(null);
        playerCardImage1.setImage(null);
        playerCardImage2.setImage(null);
        playerCardImage3.setImage(null);
        playerCardImage4.setImage(null);
        playerCardImage5.setImage(null);
        playerCardImage6.setImage(null);

        playerSplit1.setImage(null);
        playerSplit2.setImage(null);
        playerSplit3.setImage(null);
        playerSplit4.setImage(null);
        playerSplit5.setImage(null);
        playerSplit6.setImage(null);

        playerTotal.setText("");
        dealerTotal.setText("");
    }
}

