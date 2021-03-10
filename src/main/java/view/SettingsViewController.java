package view;

import controller.SettingsController;
import controller.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;
import model.UserCredentialHandler;

import java.io.IOException;

/**
 * Controller class for Settings.fxml
 */
public class SettingsViewController {

    public RadioButton onRadio;
    public RadioButton offRadio;
    public Slider volumeSlider;
    public Text loggedUser;
    public Text volumeText;
    private StageManager stageManager;
    private boolean isSelected = false;
    private SettingsController settingsController = SettingsController.getInstance();
    MainMenuController mainMenuController = null;
    private UserController userController = new UserController();

    /**
     * Setting screen's Back-Button loads to MainMenu.fxml
     *
     * @param actionEvent
     * @throws IOException
     */
    public void settingsBackButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/MainMenu.fxml"));
        Parent menuParent = loader.load();
        MainMenuController controller = loader.getController();
        if (userController.isUserLoggedIn()) {
            controller.loginButton.setVisible(false);
            controller.registerButton.setVisible(false);
            controller.logoutButton.setVisible(true);
        }
        Scene menuScene = new Scene(menuParent);
        stageManager = StageManager.getInstance();
        stageManager.getPrimaryStage().setTitle("The Grand Myllypuro");
        stageManager.getPrimaryStage().setScene(menuScene);
        stageManager.getPrimaryStage().show();

    }

    /**
     * Initializes Logged in as-text at the top corner
     */
    public void initialize() {
        if (UserCredentialHandler.getInstance().getLoggedInUser() != null) {
            loggedUser.setText("Logged in as: " + UserCredentialHandler.getInstance().getLoggedInUser().getUserName());
        }
        turnTips();
        volumeSlider.setValue(SettingsController.getInstance().getVolume());
        volumeText.setText(""+SettingsController.getInstance().getVolume());
        volumeSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            volumeText.setText(""+Math.floor((Double) newValue));
            mainMenuController.getMediaPlayer().setVolume(((Double) newValue) / 100);
            SettingsController.getInstance().setVolume(Math.floor((Double) newValue));
        });
    }

    /**
     * Turns tips ON or OFF
     */
    public void turnTips(){
        if(offRadio.isSelected()) {
            this.isSelected = false;
            settingsController.setSelected(false);
            System.out.println("Tips are now OFF");
        }else if(onRadio.isSelected()) {
            this.isSelected = true;
            settingsController.setSelected(true);
            System.out.println("Tips are now ON");
        }
    }

    /**
     * Checks if the tips are ON or OFF
     * @return - Returns true if tips are ON and false if they are OFF
     */
    public boolean getSelected() {
        return this.isSelected;
    }

    public void setMainMenuController(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }
}
