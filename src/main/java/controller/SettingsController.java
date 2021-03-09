package controller;

import view.SettingsViewController;

/**
 * Used to control Settings from model and model from GUI
 */


public class SettingsController {
    private boolean isSelected;
    SettingsViewController settingsViewController;

    private SettingsController() {

    }
    public static SettingsController myInstance;


    public static SettingsController getInstance() {
        if (myInstance == null) {
            myInstance = new SettingsController();
        }
        return myInstance;
    }



    /**
     * Constructor of SettingsController
     */
    /*public SettingsController(){
        this.isSelected = settingsViewController.turnTips();
    }*/

    /**
     * Gets the current state of isSelected-boolean (which identifies are the tips ON or OFF)
     * @return
     */
    public boolean getSelected() {
        return this.isSelected;
    }


    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public void initializeSettings() {
        SettingsViewController settingsViewController = new SettingsViewController();
        this.isSelected = settingsViewController.getSelected();
    }

}
