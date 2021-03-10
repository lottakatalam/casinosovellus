package view;

import controller.BlackjackController;
import controller.SettingsController;
import controller.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.UserCredentialHandler;
import org.w3c.dom.css.CSS2Properties;

import java.io.IOException;
import java.util.Set;

/**
 * Controller class for MainMenu.fxml
 */

public class MainMenuController {

    public ImageView blackScreen;
    public Text areYouSure;
    public Button noButton;
    public Button yesButton;
    public Text loggedUser;
    public Button loginButton;
    public Button logoutButton;
    public Button registerButton;
    private BlackjackController gameController;
    private static UserController userController;
    public Stage primaryStage;
    private StageManager stageManager;
    MediaPlayer mediaPlayer;

    /** Menu's Play-Button loads to InGameView.fxml
     * @param actionEvent
     * @throws IOException
     */
    public void playButton(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUImain.class.getResource("/InGameView.fxml"));
        Parent gameParent = loader.load();

        InGameViewController controller = loader.getController();
        BlackjackController gameController = new BlackjackController();
        controller.setGameController(gameController);
        gameController.setInGameViewController(controller);
        controller.init();
        Scene gameScene = new Scene(gameParent);

        stageManager = StageManager.getInstance();
        stageManager.getPrimaryStage().setTitle("Blackjack");
        stageManager.getPrimaryStage().setScene(gameScene);
        stageManager.getPrimaryStage().show();

    }

    /**
     * Initializes Welcome-message for logged in user and music
     */
    public void initialize() {
        if (!SettingsController.getInstance().getIsPlaying()) {
            Media sound = new Media(getClass().getResource("/Music/ElegantJazz.mp3").toExternalForm());
            mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.play();
        });
            mediaPlayer.setVolume(0.03);
            mediaPlayer.play();
            SettingsController.getInstance().setIsPlaying();
        }

        if (UserCredentialHandler.getInstance().isLoggedIn()) {
            loggedUser.setText("Welcome, \n" + UserCredentialHandler.getInstance().getLoggedInUser().getUserName());
        }
    }

    /**
     * Menu's Game History-button loads to GameHistory.fxml
     * @param actionEvent
     * @throws IOException
     */
    public void gameHistoryButton(ActionEvent actionEvent) throws IOException {
        Parent gameHistoryParent = FXMLLoader.load(getClass().getResource("/GameHistory.fxml"));
        Scene gameHistoryScene = new Scene(gameHistoryParent);

        stageManager = StageManager.getInstance();
        stageManager.getPrimaryStage().setTitle("Game History");
        stageManager.getPrimaryStage().setScene(gameHistoryScene);
        stageManager.getPrimaryStage().show();
    }

    /** Menu's Settings-Button loads to Settings.fxml
     * @param actionEvent
     * @throws IOException
     */
    public void settingsButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Settings.fxml"));
        Parent settingsParent = loader.load();
        SettingsViewController settingsViewController = loader.getController();
        settingsViewController.setMainMenuController(this);
        Scene settingsScene = new Scene(settingsParent);

        stageManager = StageManager.getInstance();
        stageManager.getPrimaryStage().setTitle("Settings");
        stageManager.getPrimaryStage().setScene(settingsScene);
        stageManager.getPrimaryStage().show();
    }

    /** Sets Are you sure-screen visible to close the program
     */
    @FXML private javafx.scene.control.Button quitButton;
    public void quitButton(){
        blackScreen.setVisible(true);
        areYouSure.setVisible(true);
        yesButton.setVisible(true);
        noButton.setVisible(true);

    }

    /**
     * Loads to LogInView.fxml
     * @param actionEvent
     * @throws IOException
     */
    public void logInButton(ActionEvent actionEvent) throws IOException {
        Parent logInParent = FXMLLoader.load(getClass().getResource("/LogInView.fxml"));
        Scene logInScene = new Scene(logInParent);

        stageManager = StageManager.getInstance();
        stageManager.getPrimaryStage().setTitle("Log In");
        stageManager.getPrimaryStage().setScene(logInScene);
        stageManager.getPrimaryStage().show();


    }

    /**
     * Logs user out and makes login and register buttons visible again
     * @param actionEvent
     */
    public void logOutButton(ActionEvent actionEvent) {
        userController.logout();
        logoutButton.setVisible(false);
        loginButton.setVisible(true);
        registerButton.setVisible(true);
        loggedUser.setText("Logged out");
    }

    /**
     * Loads to RegisterView.fxml
     * @param actionEvent
     * @throws IOException
     */
    public void registerButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/RegisterView.fxml"));

        Parent registerParent = loader.load();
        RegisterController controller = loader.getController();

        Scene registerScene = new Scene(registerParent);
        stageManager = StageManager.getInstance();
        stageManager.getPrimaryStage().setTitle("Register");
        stageManager.getPrimaryStage().setScene(registerScene);
        stageManager.getPrimaryStage().show();
    }

    /**
     * Closes the program
     */
    public void yesAction() {
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Closes Are you sure-screen
     */
    public void noAction() {
        blackScreen.setVisible(false);
        areYouSure.setVisible(false);
        yesButton.setVisible(false);
        noButton.setVisible(false);
    }

    /**
     * Sets gameController as the object of class BlackjackController
     * @param blackjackController object of the BlackjackController
     */
    public void setGameController(BlackjackController blackjackController) {
        gameController = blackjackController;
    }

    /**
     * Sets userController as the object of class UserController
     * @param userController object of the UserController
     */
    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    /**
     * Sets stageManager as the object of class StageManager
     * @param stageManager object of the StageManager
     */
    public void setStageManager(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    public MediaPlayer getMediaPlayer() {
        return this.mediaPlayer;
    }
}
