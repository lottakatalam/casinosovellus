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
    public void register(ActionEvent event) throws SQLException {

        Window owner = usernameTextField.getScene().getWindow();

        System.out.println(usernameTextField.getText());
        System.out.println(passwordTextField.getText());
        if (usernameTextField.getText().isEmpty()) {
            errorText.setText("Please enter username");
            blackScreen.setVisible(true);
            errorText.setVisible(true);
            okButton.setVisible(true);
        }
        else if (passwordTextField.getText().isEmpty() && !usernameTextField.getText().isEmpty()) {
            errorText.setText("Please enter password");
            blackScreen.setVisible(true);
            errorText.setVisible(true);
            okButton.setVisible(true);
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


    public void backToMainMenu() throws IOException {
        Parent menuParent = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));
        Scene menuScene = new Scene(menuParent);
        stageManager = StageManager.getInstance();
        stageManager.getPrimaryStage().setTitle("The Grand Myllypuro");
        stageManager.getPrimaryStage().setScene(menuScene);
        stageManager.getPrimaryStage().show();

    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public static boolean isValidUsername(String username) {
        boolean isValid = true;
        if (username.length() > 20 || username.length() < 6) {
            System.out.println("Username must be less than 20 and more than 6 characters in length.");
            isValid = false;
        }
        return isValid;
    }

    public static boolean isValidPassword(String password) {
        boolean isValid = true;
        if (password.length() > 20 || password.length() < 6) {
            System.out.println("Password must be less than 20 and more than 6 characters in length.");
            isValid = false;
        }
        String upperCaseChars = "(.*[A-Z].*)";
        if (!password.matches(upperCaseChars)) {
            System.out.println("Password must have atleast one uppercase character");
            isValid = false;
        }
        String lowerCaseChars = "(.*[a-z].*)";
        if (!password.matches(lowerCaseChars)) {
            System.out.println("Password must have atleast one lowercase character");
            isValid = false;
        }
        String numbers = "(.*[0-9].*)";
        if (!password.matches(numbers)) {
            System.out.println("Password must have atleast one number");
            isValid = false;
        }
        return isValid;
    }

    public void okButton() throws IOException {
        if (registerText.isVisible()) {
            backToMainMenu();
        }
        blackScreen.setVisible(false);
        errorText.setVisible(false);
        okButton.setVisible(false);
    }
}