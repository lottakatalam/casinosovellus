package view;

import controller.SettingsController;
import controller.UserController;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.effect.*;
import javafx.scene.text.Text;
import model.LanguageLoader;
import model.UserCredentialHandler;

import java.io.IOException;

/**
 * Controller class for Settings.fxml
 */
public class SettingsViewController extends ViewController {

    public RadioButton onRadio;
    public RadioButton offRadio;
    public Slider volumeSlider;
    public Text loggedUser;
    public Text volumeText;
    public Button finnishButton;
    public Button englishButton;
    public Button volumeOFFbutton;
    public Button volumeONbutton;
    private boolean isSelected = true;
    private SettingsController settingsController = SettingsController.getInstance();
    MainMenuController mainMenuController = null;
    private UserController userController = new UserController();

    /**
     * Setting screen's Back-Button loads to MainMenu.fxml
     *
     * @throws IOException - if .fxml file is not found
     */
    public void settingsBackButton() throws IOException {
        showMainMenu();
    }

    /**
     * Initializes Logged in as-text at the top corner
     * Checks the current volume state
     * Initializes settings
     */
    public void initialize() {
        checkVolume();
        if (UserCredentialHandler.getInstance().getLoggedInUser() != null) {
            loggedUser.setText(LanguageLoader.getInstance().getString("LoggedInUser") + UserCredentialHandler.getInstance().getLoggedInUser().getUsername());
        }
        if(settingsController.getSelected()) {
            offRadio.setSelected(false);
            onRadio.setSelected(true);
        }else {
            offRadio.setSelected(true);
            onRadio.setSelected(false);
        }
        initSettings();
    }

    /**
     * Turns tips ON or OFF
     */
    public void turnTips() {
        if (offRadio.isSelected()) {
            this.isSelected = false;
            settingsController.setSelected(false);
        } else if (onRadio.isSelected()) {
            this.isSelected = true;
            settingsController.setSelected(true);
        }
    }

    /**
     * Checks if the tips are ON or OFF
     *
     * @return - Returns true if tips are ON and false if they are OFF
     */
    public boolean getSelected() {
        return this.isSelected;
    }

    /**
     * Initializes settings in the settings screen
     */
    public void initSettings() {
        volumeSlider.setValue(SettingsController.getInstance().getVolume() * 100);
        volumeText.setText(SettingsController.getInstance().getVolume() * 100 +" %");
        volumeSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            volumeText.setText(Math.floor((Double) newValue)+" %");
            stageManager.getMediaPlayer().setVolume(((Double) newValue) / 100);
            SettingsController.getInstance().setVolume(Math.floor((Double) newValue) / 100);
        });
        if (LanguageLoader.getInstance().getLocale().getLanguage() == "fi") {
            selectFinnish();
        }else {
            selectEnglish();
        }
    }

    /**
     * Sets mainMenuController as the object of class MainMenuController
     * @param mainMenuController object of the MainMenuController
     */
    public void setMainMenuController(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
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

    /**
     * Changes language to English
     */
    public void selectEnglish() {

        LanguageLoader.getInstance().setLocale("en","GB");
        englishButton.setScaleX(1.2);
        englishButton.setScaleY(1.2);
        finnishButton.setScaleX(0.8);
        finnishButton.setScaleY(0.8);
        englishButton.setEffect(new DropShadow());
        finnishButton.setEffect(null);
    }

    /**
     * Changes language to Finnish
     */
    public void selectFinnish() {
        LanguageLoader.getInstance().setLocale("fi", "FI");
        englishButton.setScaleX(0.8);
        englishButton.setScaleY(0.8);
        finnishButton.setScaleX(1.2);
        finnishButton.setScaleY(1.2);
        finnishButton.setEffect(new DropShadow());
        englishButton.setEffect(null);
    }
}
