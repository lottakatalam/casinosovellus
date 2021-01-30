package view;

import controller.BlackjackController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Card;

import java.io.IOException;
import java.util.ArrayList;

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
    public Label playerCurrency;
    private BlackjackController gameController;
    private ArrayList<Card> playersCards;
    private ArrayList<Card> dealerCards;


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

    public void bet() {
        this.playersCards = gameController.getPlayersCards();
        this.dealerCards = gameController.getDealersCards();
        playerCard1.setText(playersCards.get(0).toString());
        playerCard2.setText(playersCards.get(1).toString());
        dealerCard1.setText(dealerCards.get(0).toString());
    }

    public void stand() {
        gameController.stand();
        this.playersCards = gameController.getPlayersCards();
        this.dealerCards = gameController.getDealersCards();
        playerCard1.setText("");
        playerCard2.setText("");
        playerCard3.setText("");
        playerCard4.setText("");
        dealerCard1.setText("");
        dealerCard2.setText("");
        dealerCard3.setText("");
        dealerCard4.setText("");

    }

    public void setGameController(BlackjackController blackjackController) {
        gameController = blackjackController;
    }
    public void showCurrency() {
        playerCurrency.setText("\uD83D\uDCB2" + gameController.getPlayer().getCurrency());
    }
    public void init() {
        showCurrency();
    }
    //
}
