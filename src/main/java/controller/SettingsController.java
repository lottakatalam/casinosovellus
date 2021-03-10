package controller;

import view.SettingsViewController;

/**
 * Used to control Settings from model and model from GUI
 */


public class SettingsController {
    private boolean isSelected;
    private boolean isPlaying;
    private double volume;
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
        this.isPlaying = false;
        this.volume = 3;
        this.isSelected = settingsViewController.getSelected();
    }

    public boolean getIsPlaying() {
        return this.isPlaying;
    }

    public void setIsPlaying() {
        this.isPlaying = !this.isPlaying;
    }

    public double getVolume() {
        return this.volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

}
