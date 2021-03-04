package view;

import java.io.IOException;
import java.sql.SQLException;

import controller.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    public ImageView blackScreen;
    public Text errorText;
    public Button okButton;
    public Text loginText;
    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button logInButton;
    private StageManager stageManager;
    private UserController userController = new UserController();


    @FXML
    public void logInButton(ActionEvent event) throws SQLException {

        Window owner = logInButton.getScene().getWindow();

        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        if (!userController.login(username,password)) {
            blackScreen.setVisible(true);
            errorText.setVisible(true);
            okButton.setVisible(true);
        } else {
            blackScreen.setVisible(true);
            loginText.setVisible(true);
            okButton.setVisible(true);
        }

        usernameTextField.setText("");
        passwordTextField.setText("");
    }

    public void backToMainMenu() throws IOException {
        Parent menuParent = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));
        Scene menuScene = new Scene(menuParent);
        stageManager = StageManager.getInstance();
        stageManager.getPrimaryStage().setTitle("The Grand Myllypuro");
        stageManager.getPrimaryStage().setScene(menuScene);
        stageManager.getPrimaryStage().show();

    }


    public void okButton() throws IOException {
        if(loginText.isVisible()) {
            backToMainMenu();
        }
        blackScreen.setVisible(false);
        errorText.setVisible(false);
        loginText.setVisible(false);
        okButton.setVisible(false);
    }
}