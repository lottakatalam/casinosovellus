package controller;

import view.SettingsViewController;

public class SettingsController {

    SettingsViewController settingsViewController = new SettingsViewController();

    public SettingsController(){
    }

    public boolean getSelected() {
        return this.settingsViewController.getSelected();
    }
}
