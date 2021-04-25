package view;

import java.io.IOException;
import controller.SettingsController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Window;
import model.LanguageLoader;
/**
 *
 * Register Controller for fxml ui
 *
 */

public class RegisterController extends ViewController {

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
     * Text used for telling the user the registration was successful. Text is defined in the fxml-file
     */
    public Text registerText;

    /**
     * Textfield where the user inputs their username
     */
    @FXML
    public TextField usernameTextField;
    /**
     * Textfield where the user inputs their password
     */
    @FXML
    public TextField passwordTextField;
    /**
     * Textfield where the user repeats their password
     */
    @FXML
    public TextField repeatTextField;

    /**
     * Button that registers user credentials after pressed
     */
    @FXML
    public Button submitButton;


    /**
     * Initializes stageManager
     * Checks the current volume state
     */
    public void initialize() {
        checkVolume();
    }
    /**
     * Button that changes user password after pressed
     */
    @FXML
    private Button changePasswordButton;


    @FXML
    /**
     * Registers a new user if the user input meets the requirements
     * If all fields have input, asks the controller to pass on the information to validation
     * and informs the user if the registration was successful or not. If not, tells more specific information
     */

    public void register() {

        Window owner = usernameTextField.getScene().getWindow();
        System.out.println(usernameTextField.getText());
        System.out.println(passwordTextField.getText());

        if (usernameTextField.getText().isEmpty()) {
            setErrorMessage(LanguageLoader.getInstance().getString("RegErrUser"));
        }
        else if (passwordTextField.getText().isEmpty()) {
            setErrorMessage(LanguageLoader.getInstance().getString("RegErrPwd"));
        }
        else if (repeatTextField.getText().isEmpty()) {
            setErrorMessage(LanguageLoader.getInstance().getString("RegErrRepeatPwd"));
        }
        else  {
            String username = usernameTextField.getText();
            String password1 = passwordTextField.getText();
            String password2 = repeatTextField.getText();

            if (userController.validateCredentials(username, password1, password2)) {

                userController.login(username, password1);

                blackScreen.setVisible(true);
                registerText.setVisible(true);
                okButton.setVisible(true);
            } else {
                setErrorMessage(userController.getErrorMessage());
            }
        }

        passwordTextField.setText("");
        repeatTextField.setText("");

    }

    /**
     * Set's an error message on display in UI
     * @param message
     */
    public void setErrorMessage(String message) {
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
            showMainMenu();
        }
        blackScreen.setVisible(false);
        errorText.setVisible(false);
        okButton.setVisible(false);
    }

}