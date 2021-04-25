package view;

import controller.SettingsController;
import controller.UserController;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import model.LanguageLoader;

import java.io.IOException;

public class ChangePasswordController extends ViewController {

    /**
     * A dark, slightly see-through ImageView which is used when the user is informed and their attention is needed
     */
    public ImageView blackScreen;

    /**
     * Text used for presenting possible errormessages to user
     */
    public Text errorText;

    /**
     * Button is used after messages to user to confirm the user has understood the message
     */
    public Button okButton;

    /**
     * Text used for telling the user the password change was successful. Text is defined in the fxml-file
     */
    public Text succesfulText;


    /**
     * userController is used for passing on information between the model-package class UserCredentialHandler and this viewController
     */
    private UserController userController = new UserController();

    /**
     * Textfield where the user inputs the old password
     */
    public TextField oldPasswordField;

    /**
     * Textfield where the user inputs the new password
     */
    public TextField newPasswordField;

    /**
     * Textfield where the user inputs the new password again
     */
    public TextField newPasswordRepeatField;


    /**
     * Initializes stageManager
     * Checks the current volume state
     */
    public void initialize() {
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
            showMainMenu();
        }
        blackScreen.setVisible(false);
        errorText.setVisible(false);
        okButton.setVisible(false);
    }


}

