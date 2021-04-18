package view;

import java.io.IOException;
import java.sql.SQLException;

import controller.SettingsController;
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
import model.LanguageLoader;
/**
 *
 * Register Controller for fxml ui
 *
 */

public class RegisterController {

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
     * Button which turns the music off when pressed
     */
    public Button volumeOFFbutton;
    /**
     * Button which turns the music on when pressed
     */
    public Button volumeONbutton;
    /**
     * userController is used for passing on information between the model-package class UserCredentialHandler and this viewController
     */
    private UserController userController = new UserController();

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
     * Loads back to Mainmenu
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