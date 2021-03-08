package controller;

import view.SettingsViewController;

/**
 * Used to control Settings from model and model from GUI
 */
public class SettingsController {

    SettingsViewController settingsViewController = new SettingsViewController();

    /**
     * Constructor of SettingsController
     */
    public SettingsController(){
    }

    /**
     * Gets the current state of isSelected-boolean (which identifies are the tips ON or OFF)
     * @return
     */
    public boolean getSelected() {
        return this.settingsViewController.getSelected();
    }
}
