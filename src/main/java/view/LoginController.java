package view;

import java.io.IOException;
import java.sql.SQLException;

import controller.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Window;
import model.User;
import model.UserCredentialHandler;

/**
 *
 * Login Controller for fxml ui
 *
 */
public class LoginController {

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button logInButton;
    private StageManager stageManager;
    private UserController userController = new UserController();


    @FXML private javafx.scene.control.Button backButton;

    @FXML
    public void logInButton(ActionEvent event) throws SQLException {

        Window owner = logInButton.getScene().getWindow();

        System.out.println(usernameTextField.getText());
        System.out.println(passwordTextField.getText());

        if (usernameTextField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your email id");
            return;
        }
        if (passwordTextField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a password");
            return;
        }

        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        if (!userController.login(username,password)) {
            infoBox("Please enter correct Email and Password", null, "Failed");
        } else {
            infoBox("Login Successful!", null, "Success");
        }

        usernameTextField.setText("");
        passwordTextField.setText("");
    }

    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public void backToMainMenu(ActionEvent actionEvent) throws IOException {
        Parent menuParent = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));
        Scene menuScene = new Scene(menuParent);
        stageManager = StageManager.getInstance();
        stageManager.getPrimaryStage().setTitle("The Grand Myllypuro");
        stageManager.getPrimaryStage().setScene(menuScene);
        stageManager.getPrimaryStage().show();

    }


}