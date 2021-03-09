package view;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

import controller.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Window;

/**
 *
 * Register Controller for fxml ui
 *
 */

public class RegisterController {

    public ImageView blackScreen;
    public Text errorText;
    public Button okButton;
    public Text registerText;
    private UserController userController = new UserController();

    @FXML
    public TextField usernameTextField;

    @FXML
    public TextField passwordTextField;

    @FXML
    public TextField repeatTextField;

    @FXML
    public Button submitButton;

    private StageManager stageManager;


    @FXML
    /**
     * Registers a new user
     */
    public void register(ActionEvent event) throws SQLException {

        Window owner = usernameTextField.getScene().getWindow();

        System.out.println(usernameTextField.getText());
        System.out.println(passwordTextField.getText());
        if (usernameTextField.getText().isEmpty()) {
            errorText.setText("Please enter a username");
            blackScreen.setVisible(true);
            errorText.setVisible(true);
            okButton.setVisible(true);
        }
        else if (!isValidUsername(usernameTextField.getText())) {

        }
        else if (passwordTextField.getText().isEmpty() && !usernameTextField.getText().isEmpty()) {
            errorText.setText("Please enter a password");
            blackScreen.setVisible(true);
            errorText.setVisible(true);
            okButton.setVisible(true);
        }
        else if(!isValidPassword(passwordTextField.getText())) {

        }
        else if (repeatTextField.getText().isEmpty() && !usernameTextField.getText().isEmpty() && !passwordTextField.getText().isEmpty()) {
            errorText.setText("Please repeat password");
            blackScreen.setVisible(true);
            errorText.setVisible(true);
            okButton.setVisible(true);
        }
        else if (passwordTextField.getText().equals(repeatTextField.getText()) && !passwordTextField.getText().isEmpty() && !repeatTextField.getText().isEmpty()) {
            blackScreen.setVisible(true);
            registerText.setVisible(true);
            okButton.setVisible(true);
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();

            userController.createNewUser(username, password);
            userController.login(username,password);
        } else {
            errorText.setText("Password does not match");
            blackScreen.setVisible(true);
            errorText.setVisible(true);
            okButton.setVisible(true);
        }


        usernameTextField.setText("");
        passwordTextField.setText("");
        repeatTextField.setText("");
    }

    /**
     * Loads back to Mainmenu
     * @throws IOException
     */
    public void backToMainMenu() throws IOException {
        Parent menuParent = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));
        Scene menuScene = new Scene(menuParent);
        stageManager = StageManager.getInstance();
        stageManager.getPrimaryStage().setTitle("The Grand Myllypuro");
        stageManager.getPrimaryStage().setScene(menuScene);
        stageManager.getPrimaryStage().show();

    }

    /**
     * Validation of Username while registering
     * @param username - Username that is validated
     * @return - If the username is valid or not
     */
    public boolean isValidUsername(String username) {
        boolean isValid = true;
        if (username.length() > 16 || username.length() < 4) {
            String message = "Username must be less than 16 and more than 4 characters in length.";
            System.out.println(message);
            errorText.setText(message);
            blackScreen.setVisible(true);
            errorText.setVisible(true);
            okButton.setVisible(true);
            isValid = false;
        }
        return isValid;
    }

    /**
     * Validation of Password while registering
     * @param password - Password that is validated
     * @return - If the password is valid or not
     */
    public boolean isValidPassword(String password) {
        String message;
        if (password.length() < 6) {
            message = "Password must contain at least 6 characters.";
            System.out.println(message);
            setErrorMessageAboutPassword(message);
            return false;
        }
        String upperCaseChars = "(.*[A-Z].*)";
        if (!password.matches(upperCaseChars)) {
            message = "Password must have atleast one uppercase character";
            System.out.println(message);
            setErrorMessageAboutPassword(message);
            return false;
        }
        String lowerCaseChars = "(.*[a-z].*)";
        if (!password.matches(lowerCaseChars)) {
            message = "Password must have atleast one lowercase character";
            System.out.println(message);
            setErrorMessageAboutPassword(message);
            return false;
        }
        String numbers = "(.*[0-9].*)";
        if (!password.matches(numbers)) {
            message = "Password must have atleast one number";
            System.out.println(message);
            setErrorMessageAboutPassword(message);
            return false;
        }

        return true;
    }

    public void setErrorMessageAboutPassword(String message) {
            errorText.setText(message);
            blackScreen.setVisible(true);
            errorText.setVisible(true);
            okButton.setVisible(true);
    }

    /**
     * Button closes pop up screen and loads to MainMenu if registeration is successed
     * @throws IOException
     */
    public void okButton() throws IOException {
        if (registerText.isVisible()) {
            backToMainMenu();
        }
        blackScreen.setVisible(false);
        errorText.setVisible(false);
        okButton.setVisible(false);
    }
}