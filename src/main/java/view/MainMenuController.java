package view;

import controller.BlackjackController;
import controller.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.LanguageLoader;
import model.UserCredentialHandler;

import java.io.IOException;

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
    public Button volumeOFFbutton;
    public Button volumeONbutton;
    public Button changePasswordButton;
    public Button leaderboardsButton;
    private BlackjackController gameController;
    private static UserController userController;
    private StageManager stageManager;

    /** Menu's Play-Button loads to InGameView.fxml
     * @throws IOException - if .fxml file is not found
     */
    public void playButton() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUImain.class.getResource("/FXML/InGameView.fxml"));
        Parent gameParent = loader.load();

        InGameViewController controller = loader.getController();
        BlackjackController gameController = new BlackjackController();
        controller.setGameController(gameController);
        gameController.setInGameViewController(controller);
        controller.init();
        Scene gameScene = new Scene(gameParent);

        stageManager.getPrimaryStage().setTitle("Blackjack");
        stageManager.getPrimaryStage().setScene(gameScene);
        stageManager.getPrimaryStage().show();

    }

    /**
     * Initializes Welcome-message for logged in user and music
     * Checks the current volume state
     */
    public void initialize() {
        stageManager = StageManager.getInstance();
        checkVolume();
        if (UserCredentialHandler.getInstance().isLoggedIn()) {
            loggedUser.setText("Welcome, \n" + UserCredentialHandler.getInstance().getLoggedInUser().getUsername());
        }
    }

    /**
     * Menu's Game History-button loads to GameHistory.fxml
     * @throws IOException - if .fxml file is not found
     */
    public void gameHistoryButton() throws IOException {
        Parent gameHistoryParent = FXMLLoader.load(getClass().getResource("/FXML/GameHistory.fxml"));
        Scene gameHistoryScene = new Scene(gameHistoryParent);

        stageManager.getPrimaryStage().setTitle("Game History");
        stageManager.getPrimaryStage().setScene(gameHistoryScene);
        stageManager.getPrimaryStage().show();
    }

    /** Menu's Settings-Button loads to Settings.fxml
     * @throws IOException - if .fxml file is not found
     */
    public void settingsButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/Settings.fxml"));
        Parent settingsParent = loader.load();
        SettingsViewController settingsViewController = loader.getController();
        settingsViewController.setMainMenuController(this);
        Scene settingsScene = new Scene(settingsParent);
        System.out.println(LanguageLoader.getInstance().getString("settings"));
        stageManager.getPrimaryStage().setTitle(LanguageLoader.getInstance().getString("settings"));
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
     * @throws IOException - if .fxml file is not found
     */
    public void logInButton() throws IOException {
        Parent logInParent = FXMLLoader.load(getClass().getResource("/FXML/LogInView.fxml"));
        Scene logInScene = new Scene(logInParent);

        stageManager.getPrimaryStage().setTitle("Log In");
        stageManager.getPrimaryStage().setScene(logInScene);
        stageManager.getPrimaryStage().show();


    }

    /**
     * Logs user out and makes login and register buttons visible again
     */
    public void logOutButton() {
        userController.logout();
        logoutButton.setVisible(false);
        loginButton.setVisible(true);
        changePasswordButton.setVisible(false);
        registerButton.setVisible(true);
        loggedUser.setText("Logged out");
    }

    /**
     * Loads to RegisterView.fxml
     * @throws IOException - if .fxml file is not found
     */
    public void registerButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/RegisterView.fxml"));

        Parent registerParent = loader.load();
        RegisterController controller = loader.getController();

        Scene registerScene = new Scene(registerParent);
        stageManager.getPrimaryStage().setTitle("Register");
        stageManager.getPrimaryStage().setScene(registerScene);
        stageManager.getPrimaryStage().show();
    }

    /**
     * Loads to Leaderboards.fxml
     * @throws IOException - if .fxml file is not found
     */
    public void leaderboardsButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/Leaderboards.fxml"));

        Parent leaderboardsParent = loader.load();
        LeaderboardsController controller = loader.getController();

        Scene leaderboardsScene = new Scene(leaderboardsParent);
        stageManager.getPrimaryStage().setTitle("Leaderboards");
        stageManager.getPrimaryStage().setScene(leaderboardsScene);
        stageManager.getPrimaryStage().show();
    }

    /**
     * Loads to ChangePassword.fxml
     * @throws IOException - if .fxml file is not found
     */
    public void handleChangePassword() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/ChangePassword.fxml"));

        Parent changePasswordView = loader.load();
        ChangePasswordController controller = loader.getController();

        Scene changePasswordScene = new Scene(changePasswordView);
        stageManager = StageManager.getInstance();
        stageManager.getPrimaryStage().setTitle("Change password");
        stageManager.getPrimaryStage().setScene(changePasswordScene);
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
}
