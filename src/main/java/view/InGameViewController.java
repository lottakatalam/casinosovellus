package view;

import controller.BlackjackController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Card;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Controller class for the InGameView.fxml and Settings.fxml
 */

public class InGameViewController {

    private BlackjackController gameController;
    private ArrayList<Card> playersCards;

    @FXML
    private Label playerCard1;
    @FXML
    private Label playerCard2;
    @FXML
    private Label playerCard3;
    @FXML
    private Label playerCard4;
    @FXML
    private Label playerCurrency;


    /**
     * General initialization method to setup the in-game view
     */
    public void init() {
        showCurrency();
    }

    /** Gamescreen's Menu-Button loads to MainMenu.fxml
     * @param actionEvent
     * @throws IOException
     */
    public void menuButton(ActionEvent actionEvent) throws IOException {

        Parent menuParent = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));
        Scene menuScene = new Scene(menuParent);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setTitle("The Grand Myllypuro");
        window.setScene(menuScene);
        window.show();

    }


    /** Settingscreen's Back-Button loads to MainMenu.fxml
     * @param actionEvent
     * @throws IOException
     */
    public void settingsBackButton(ActionEvent actionEvent) throws IOException {

        Parent menuParent = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));
        Scene menuScene = new Scene(menuParent);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setTitle("The Grand Myllypuro");
        window.setScene(menuScene);
        window.show();

    }

    /** Gamescreen's Instructions-Button loads to Instructions.fxml
     * @param actionEvent
     * @throws IOException
     */
    public void instructionsButton(ActionEvent actionEvent) throws IOException {

        Parent insParent = FXMLLoader.load(getClass().getResource("/Instructions.fxml"));
        Scene insScene = new Scene(insParent);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setTitle("Instructions");
        window.setScene(insScene);
        window.show();

    }

    /** Instructionscreen's Back to game-Button loads to InGameView.fxml
     * @param actionEvent
     * @throws IOException
     */
    public void backToGameButton(ActionEvent actionEvent) throws IOException {

        Parent gameParent = FXMLLoader.load(getClass().getResource("/InGameView.fxml"));
        Scene gameScene = new Scene(gameParent);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setTitle("Blackjack");
        window.setScene(gameScene);
        window.show();

    }

    /**
     * Method for the "Hit" button. Draws a new card and shows it in the application.
     */
    public void hit() {
        gameController.hit();
        this.playersCards = gameController.getPlayersCards();
        switch(playersCards.size()) {
            case 3: playerCard3.setText(playersCards.get(2).toString());
            break;
            case 4: playerCard4.setText(playersCards.get(3).toString());
            break;
        }
    }

    /**
     * Method that is called upon pressing the "Bet" button. Places the bet and draws the first cards
     */
    public void bet() {
        this.playersCards = gameController.getPlayersCards();
        playerCard1.setText(playersCards.get(0).toString());
        playerCard2.setText(playersCards.get(1).toString());
        showCurrency();
    }

    /**
     * Method for the "Stand" button
     */
    public void stand() {
        gameController.stand();
    }

    /**
     * Initializes the starting currency
     */
    public void showCurrency() {
        playerCurrency.setText("\uD83D\uDCB2" + gameController.getPlayer().getCurrency());
    }

    /**
     * Sets up the controller to allow communication into model with it.
     * @param blackjackController
     */
    public void setGameController(BlackjackController blackjackController) {
        gameController = blackjackController;
    }
}
