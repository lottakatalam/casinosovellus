package controller;

import view.SettingsViewController;

/**
 * Used to control Settings from model and model from GUI
 */


public class SettingsController {
    private boolean isSelected;
    private boolean isPlaying;
    private double volume;

    private SettingsController() {

    }
    public static SettingsController myInstance;

    /**
     * Instance of SettingsController
     * @return
     */
    public static SettingsController getInstance() {
        if (myInstance == null) {
            myInstance = new SettingsController();
        }
        return myInstance;
    }

    /**
     * Gets the current state of isSelected-boolean (which identifies are the tips ON or OFF)
     * @return
     */
    public boolean getSelected() {
        return this.isSelected;
    }

    /**
     * Sets the state of isSelected-boolean (which identifies are the tips ON or OFF)
     * @param isSelected
     */
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    /**
     * Initializes settings
     */
    public void initializeSettings() {
        SettingsViewController settingsViewController = new SettingsViewController();
        this.isPlaying = false;
        this.volume = 25;
        this.isSelected = settingsViewController.getSelected();
    }

    /**
     * Sets that the music is playing
     */
    public void setIsPlaying() {
        this.isPlaying = !this.isPlaying;
    }

    /**
     * Gets the current value of the volume
     * @return - the current value of volume
     */
    public double getVolume() {
        return this.volume;
    }

    /**
     * Sets the volume to wanted value
     * @param volume - Wanted volume value
     */
    public void setVolume(double volume) {
        this.volume = volume;
    }

}
