package view;

import controller.BlackjackController;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.Card;
import model.LanguageLoader;
import model.UserCredentialHandler;
import java.util.ArrayList;
import static java.lang.Thread.sleep;

/**
 * Controller class for the InGameView.fxml and Settings.fxml
 */

public class InGameViewController extends ViewController {

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
    private int bet;
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
    private MediaPlayer mediaPlayer;
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
     *
     */
    public void yesAction() {
        showMainMenu();
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
        checkVolume();
    }

    /**
     * Game screen's Instructions-Button makes instructions visible top of the game screen
     *
     *
     */
    public void instructionsButton() {
        instructionsOn = true;
        hideOrShowContent(false);
    }

    /**
     * Instruction screen's Close-Button closes the instructions
     */
    public void closeButton() {
        instructionsOn = false;
        hideOrShowContent(true);
    }

    public void hideOrShowContent(boolean show) {
        playerCardImage1.setVisible(show);
        playerCardImage2.setVisible(show);
        playerCardImage3.setVisible(show);
        playerCardImage4.setVisible(show);
        playerCardImage5.setVisible(show);
        playerCardImage6.setVisible(show);
        dealerCardImage1.setVisible(show);
        dealerCardImage2.setVisible(show);
        dealerCardImage3.setVisible(show);
        dealerCardImage4.setVisible(show);
        dealerCardImage5.setVisible(show);
        dealerCardImage6.setVisible(show);
        playerSplit1.setVisible(show);
        playerSplit2.setVisible(show);
        playerSplit3.setVisible(show);
        playerSplit4.setVisible(show);
        playerSplit5.setVisible(show);
        playerSplit6.setVisible(show);
        playerCurrency.setVisible(show);
        playerTotal.setVisible(show);
        dealerTotal.setVisible(show);
        currentBet.setVisible(show);
        betField.setVisible(show);
        splitButton.setVisible(show);
        standButton.setVisible(show);
        hitButton.setVisible(show);
        dealButton.setVisible(show);
        instructionsButton.setVisible(show);
        menuButton.setVisible(show);
        currencyText.setVisible(show);
        yourHandText.setVisible(show);
        dealersHandText.setVisible(show);
        doubleButton.setVisible(show);
        surrenderButton.setVisible(show);
        insuranceButton.setVisible(show);
        evenMoneyButton.setVisible(show);
        instructionsText.setVisible(!show);
        instructionsBox.setVisible(!show);
        closebutton.setVisible(!show);
    }

    /**
     * Player hits and updates the total counter
     *
     */
    public void hit() {
        disableSpecialRules();
        if (gameController.getSplitStatus()) {
            gameController.hitToSplittedHand();
        } else {
            gameController.hit();
        }
        //playSFX("dealingCard");
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
                ArrayList<Card> playersCards = gameController.getPlayersCards();
                ArrayList<Card> dealerCards = gameController.getDealersCards();
                Image cardImage= new Image(getClass().getResource("/Cards/red_back.png").toExternalForm());
                dealerCardImage2.setImage(cardImage);
                updateTotalResult();
                //checkForBlackJack();
                playSFX("chips");
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
     * @return filename of image
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
        disableSpecialRules();
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

    /**
     * Set's the format of the bet in the UI by the choice of language
     * @return the String which is used to show the bet in the UI
     */
    public String formatBet() {
        if (LanguageLoader.getInstance().getLocale().toString().equals("en_GB")) {
            return texts.getString("Currency") + " " + bet;
        } else {
            return bet + " " + texts.getString("Currency");
        }
    }

    /**
     * Set's the format of the amount of insurance in the UI by the choice of language
     * @return the String which is used to show the amount of insurance in the UI
     */
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
            playSFX("split");
            updateTotalResult();
            disableSpecialRules();
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
            disableSpecialRules();
        }
    }

    /**
     * Player surrenders
     */
    public void surrender() {
            disableSpecialRules();
            gameController.playerSurrender();
            updateBalance();
    }

    /**
     * Player calls even money
     */
    public void handleEvenMoney() {
        gameController.playerEvenMoney();
        evenMoneyButton.setDisable(true);
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
     * @throws InterruptedException sleep
     */
    public void declareWinner(String winner) throws InterruptedException {
        if (!instructionsOn) {
            winnerScreen.setVisible(true);
            declareWinner.setVisible(true);
        }
        switch (winner) {
            case "player":
                declareWinner.setText(texts.getString("YouWin"));
                playSFX("youWin");
                break;
            case "dealer":
                declareWinner.setText(texts.getString("DealerWins"));
                playSFX("lose");
                break;
            case "nobody":
                declareWinner.setText(texts.getString("Draw"));
                playSFX("lose");
                break;
            case "Blackjack":
                declareWinner.setText("BLACKJACK!");
                playSFX("trumpet");
                break;
            case "busted":
                declareWinner.setText(texts.getString("Busted"));
                playSFX("lose");
                break;
            case "insured":
                declareWinner.setText(texts.getString("InsurancePayback"));
                playSFX("youWin");
                break;
            case "surrender":
                declareWinner.setText(texts.getString("Surrendered"));
                playSFX("lose");
                break;
            case "EvenMoney":
                declareWinner.setText(texts.getString("EvenMoney"));
                playSFX("lose");
                break;

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

    /**
     * Method to play SFX sounds
     * @param file determines the file path of the SFX sound
     */
    public void playSFX(String file) {
        Media sound = new Media(getClass().getResource("/SFX/" + file + ".mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(0.5);
        mediaPlayer.play();
    }

    /**
     * Set's the format of the player's balance in the UI by the choice of language
     * @return the String which is used to show the balance in the UI
     */
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
    }

    /**
     * Does the changes needed if the player decides to double.
     * Disables the buttons that should not be used after doubling and tells the controller the player wants to double
     * Updates the player's balance
     */
    public void doublePressed() {
        disableSpecialRules();
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

    /**
     * Checks if it's possible for the player to choose even money-option and enables to press even money-button
     */
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
        checkForEvenMoney();
    }

    /**
     * Sets the bet from a textfield in the UI. Validates that the amount doesn't exceed player's balance.
     */
    public boolean setBet() {
        try {
            bet = Integer.parseInt(betField.getText());
            if (gameController.setBet(bet) && bet > 0) {
                currentBet.setText(formatBet());
                updateBalance();
                return true;
            } else if (bet < 0) {
                String message = texts.getString("PlaceAValidBet");
                setValidBetView(message);
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
     * Shows a strategical tip on buttons depending on the game situation
     * @param tipType is defined in class BlackjackRound and decides what kind of tip is given to the player
     */

    public void showTip(String tipType) {
        Tooltip toolTip = new Tooltip();
        toolTip.setText((texts.getString(tipType)));
        hitButton.setTooltip(toolTip);
        standButton.setTooltip(toolTip);
        doubleButton.setTooltip(toolTip);
        splitButton.setTooltip(toolTip);
        insuranceButton.setTooltip(toolTip);
        evenMoneyButton.setTooltip(toolTip);
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
        playSFX("dealingCard");
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
     * @param playersSplittedCards splitted cards
     */
    public void setPlayersSplittedCards(ArrayList<Card> playersSplittedCards) {
        playSFX("dealingCard");
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
        playSFX("dealingCard");
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
     * Method to disable all the special rule buttons at once
     */

    public void disableSpecialRules() {
        doubleButton.setDisable(true);
        splitButton.setDisable(true);
        insuranceButton.setDisable(true);
        surrenderButton.setDisable(true);
        evenMoneyButton.setDisable(true);
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