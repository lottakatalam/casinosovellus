package view;

import java.io.IOException;

import controller.SettingsController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Window;


/**
 * Login Controller for fxml ui
 */
public class LoginController extends ViewController {

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
     * Text used for telling the user the login was successful. Text is defined in the fxml-file
     */
    public Text loginText;
    /**
     * Button which turns the music off when pressed
     */
    public Button volumeOFFbutton;
    /**
     * Button which turns the music on when pressed
     */
    public Button volumeONbutton;
    /**
     * Textfield where the user inputs their username
     */
    @FXML
    private TextField usernameTextField;

    /**
     * Textfield where the user inputs their password
     */
    @FXML
    private TextField passwordTextField;

    /**
     * Button that tries to login the user after pressed
     */
    @FXML
    private Button logInButton;


    /**
     * Initialized stageManager
     * Checks the current volume state
     */
    public void initialize(){
        checkVolume();
    }


    @FXML
    /**
     * Login button action that logs user in
     */
    public void logInButton() {

        Window owner = logInButton.getScene().getWindow();

        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        if (!userController.login(username, password)) {
            blackScreen.setVisible(true);
            errorText.setVisible(true);
            okButton.setVisible(true);
        } else {
            blackScreen.setVisible(true);
            loginText.setVisible(true);
            okButton.setVisible(true);
        }

        passwordTextField.setText("");

    }



    /**
     * Closes pop up screen and if log in is successful it loads back to Main menu as a logged in user
     * @throws IOException
     */
    public void okButton() throws IOException {
        if (loginText.isVisible()) {
            showMainMenu();
        }
        blackScreen.setVisible(false);
        errorText.setVisible(false);
        loginText.setVisible(false);
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