package view;

import controller.SettingsController;
import controller.UserController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import model.LanguageLoader;

import java.io.IOException;

public class ChangePasswordController {

    public ImageView blackScreen;
    public Text errorText;
    public Button okButton;
    public Text succesfulText;
    public Button volumeOFFbutton;
    public Button volumeONbutton;
    private UserController userController = new UserController();
    public TextField oldPasswordField;
    public TextField newPasswordField;
    public TextField newPasswordRepeatField;
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
     * Changes users password if the userinput meets the requirements
     * If all fields have input, asks the controller to validate the given passwords
     * and tells the user if the change has been made or not
     */
    public void changePassword() {

        if (oldPasswordField.getText().isEmpty()) {
            errorText.setText(LanguageLoader.getInstance().getString("EnterOldPassword"));
            blackScreen.setVisible(true);
            errorText.setVisible(true);
            okButton.setVisible(true);
        } else if (newPasswordField.getText().isEmpty()) {
            errorText.setText(LanguageLoader.getInstance().getString("EnterNewPassword"));
            blackScreen.setVisible(true);
            errorText.setVisible(true);
            okButton.setVisible(true);
        } else if (newPasswordRepeatField.getText().isEmpty()) {
            errorText.setText(LanguageLoader.getInstance().getString("repeatNewPassword"));
            blackScreen.setVisible(true);
            errorText.setVisible(true);
            okButton.setVisible(true);
        } else {
            String oldpassword = oldPasswordField.getText();
            String newPassword = newPasswordField.getText();
            String newPasswordRepeated = newPasswordRepeatField.getText();

            if (userController.validatePasswordChange(oldpassword, newPassword, newPasswordRepeated)) {
                blackScreen.setVisible(true);
                succesfulText.setVisible(true);
                okButton.setVisible(true);
            } else {
                setErrorMessageAboutPassword(userController.getErrorMessage());
            }

        }

        oldPasswordField.setText("");
        newPasswordField.setText("");
        newPasswordRepeatField.setText("");
    }

    /**
     * Loads back to Mainmenu
     *
     * @throws IOException - if .fxml file is not found
     */
    public void backToMainMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/MainMenu.fxml"));
        loader.setResources(LanguageLoader.getInstance().getResourceBundle());
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
     * Sets an error message for user to the UI if the password change was unsuccessful
     * @param message the error message for the user shown in the UI
     */
    public void setErrorMessageAboutPassword(String message) {
        errorText.setText(message);
        blackScreen.setVisible(true);
        errorText.setVisible(true);
        okButton.setVisible(true);
    }

    /**
     * Button closes pop up screen and loads to MainMenu if registeration is successed
     *
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
        stageManager.getMediaPlayer().setVolume(SettingsController.getInstance().getVolume());
    }

    /**
     * Checks is the volume ON or OFF
     */
    public void checkVolume() {
        if(stageManager.getMediaPlayer().getVolume() == 0) {
            volumeOFFbutton.setVisible(false);
            volumeONbutton.setVisible(true);
        }else {
            volumeONbutton.setVisible(false);
            volumeOFFbutton.setVisible(true);
        }
    }
}

