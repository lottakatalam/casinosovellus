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
 * Controller class for RootLayout.fxml
 */

public class RootLayoutController {

    // Menu's Play-Button loads to InGameView.fxml
    public void playButton(ActionEvent actionEvent) throws IOException {

        Parent gameParent = FXMLLoader.load(getClass().getResource("/InGameView.fxml"));
        Scene gameScene = new Scene(gameParent);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setTitle("Blackjack");
        window.setScene(gameScene);
        window.show();

    }

    // Menu's Settings-Button loads to Settings.fxml
    public void settingsButton(ActionEvent actionEvent) throws IOException {

        Parent settingsParent = FXMLLoader.load(getClass().getResource("/Settings.fxml"));
        Scene settingsScene = new Scene(settingsParent);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setTitle("Settings");
        window.setScene(settingsScene);
        window.show();

    }

    // Menu's Quit-Button closes the program
    @FXML private javafx.scene.control.Button quitButton;
    public void quitButton(){

        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }
}
