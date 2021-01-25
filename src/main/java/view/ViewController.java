package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller class for the InGameView.fxml and Settings.fxml
 */

public class ViewController {

    /** Gamescreen's Menu-Button loads to RootLayout.fxml
     * @param actionEvent
     * @throws IOException
     */
    public void menuButton(ActionEvent actionEvent) throws IOException {

        Parent menuParent = FXMLLoader.load(getClass().getResource("/RootLayout.fxml"));
        Scene menuScene = new Scene(menuParent);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setTitle("The Grand Myllypuro");
        window.setScene(menuScene);
        window.show();

    }

    /** Settingscreen's Back-Button loads to RootLayout.fxml
     * @param actionEvent
     * @throws IOException
     */
    public void settingsBackButton(ActionEvent actionEvent) throws IOException {

        Parent menuParent = FXMLLoader.load(getClass().getResource("/RootLayout.fxml"));
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
}
