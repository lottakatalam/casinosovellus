package view;

import controller.SettingsController;
import controller.UserController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import model.LanguageLoader;


import java.io.IOException;

public abstract class ViewController {


    /**
     * userController is used for passing on information between the model-package class UserCredentialHandler and this viewController
     */
    static final UserController userController = new UserController();

    static final StageManager stageManager = StageManager.getInstance();

    /**
     * Button which turns the music off when pressed
     */
    @FXML
    public Button volumeOFFbutton;

    /**
     * Button which turns the music on when pressed
     */
    @FXML
    public Button volumeONbutton;


    /**
     * Loads Mainmenu view
     *
     *
     */
    @FXML
    void showMainMenu() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/MainMenu.fxml"));
        loader.setResources(LanguageLoader.getInstance().getResourceBundle());
        Parent menuParent = null;
        try {
            menuParent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
     * Mutes game music
     */
    public void volumeOFF() {
        this.volumeOFFbutton.setVisible(false);
        this.volumeONbutton.setVisible(true);
        stageManager.getMediaPlayer().setVolume(0);

    }

    /**
     * Turns game music back ON
     */
    public void volumeON() {
        this.volumeONbutton.setVisible(false);
        this.volumeOFFbutton.setVisible(true);
        stageManager.getMediaPlayer().setVolume(SettingsController.getInstance().getVolume());
    }

    /**
     * Checks is the volume ON or OFF
     */
    public void checkVolume() {
        if (stageManager.getMediaPlayer().getVolume() == 0) {
            this.volumeOFFbutton.setVisible(false);
            this.volumeONbutton.setVisible(true);
        } else {
            this.volumeONbutton.setVisible(false);
            this.volumeOFFbutton.setVisible(true);
        }
    }

}
