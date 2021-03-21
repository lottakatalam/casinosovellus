package view;

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

import java.io.IOException;

public class ChangePasswordController {

    public ImageView blackScreen;
    public Text errorText;
    public Button okButton;
    public Text succesfulText;
    public Button volumeOFFbutton;
    public Button volumeONbutton;
    private UserController userController = new UserController();

    @FXML
    public TextField oldPasswordField;

    @FXML
    public TextField newPasswordField;

    @FXML
    public TextField newPasswordRepeatField;

    @FXML
    public Button submitButton;

    private StageManager stageManager;

    /**
     * Initializes stageManager
     * Checks the current volume state
     */
    public void initialize() {
        stageManager = StageManager.getInstance();
        checkVolume();
    }

    /**
     * Changes users password
     */
    public void changePassword() {

        if (oldPasswordField.getText().isEmpty() ) {
            errorText.setText("Please enter old password");
            blackScreen.setVisible(true);
            errorText.setVisible(true);
            okButton.setVisible(true);
        }
        else if(newPasswordField.getText().isEmpty()) {
            errorText.setText("Please enter new password");
            blackScreen.setVisible(true);
            errorText.setVisible(true);
            okButton.setVisible(true);
        }
        else if(!isValidPassword(newPasswordField.getText())) {
        }
        else if (newPasswordRepeatField.getText().isEmpty()) {
            errorText.setText("Please repeat new password");
            blackScreen.setVisible(true);
            errorText.setVisible(true);
            okButton.setVisible(true);
        }
        else if (newPasswordField.getText().equals(newPasswordRepeatField.getText()) && !newPasswordField.getText().isEmpty() && !newPasswordRepeatField.getText().isEmpty()) {
            if(userController.changeUserPassword(newPasswordField.getText(), oldPasswordField.getText())) {
                blackScreen.setVisible(true);
                succesfulText.setVisible(true);
                okButton.setVisible(true);
            } else {
                errorText.setText("Please check that old password is written correctly");
                blackScreen.setVisible(true);
                errorText.setVisible(true);
                okButton.setVisible(true);
            }


        } else {
            errorText.setText("Password does not match");
            blackScreen.setVisible(true);
            errorText.setVisible(true);
            okButton.setVisible(true);
        }


        oldPasswordField.setText("");
        newPasswordField.setText("");
        newPasswordRepeatField.setText("");
    }

    /**
     * Loads back to Mainmenu
     * @throws IOException - if .fxml file is not found
     */
    public void backToMainMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/MainMenu.fxml"));
        Parent menuParent = loader.load();
        MainMenuController controller = loader.getController();
        if (userController.isUserLoggedIn()) {
            controller.loginButton.setVisible(false);
            controller.registerButton.setVisible(false);
            controller.logoutButton.setVisible(true);
            controller.changePasswordButton.setVisible(true);
        }
        Scene menuScene = new Scene(menuParent);
        stageManager.getPrimaryStage().setTitle("The Grand Myllypuro");
        stageManager.getPrimaryStage().setScene(menuScene);
        stageManager.getPrimaryStage().show();

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
        if (succesfulText.isVisible()) {
            backToMainMenu();
        }
        blackScreen.setVisible(false);
        errorText.setVisible(false);
        okButton.setVisible(false);
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
        stageManager.getMediaPlayer().setVolume(0.25);
    }

    /**
     * Checks is the volume ON or OFF
     */
    public void checkVolume() {
        if(stageManager.getMediaPlayer().getVolume() == 0) {
            volumeOFF();
        }else {
            volumeON();
        }
    }
}

