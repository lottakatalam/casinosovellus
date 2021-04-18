package controller;

import view.SettingsViewController;

/**
 * Used to control Settings from model and model from GUI
 */


public class SettingsController {
    /**
     * Boolean which describes are the automated instructions for play in the UI on
     * True if the instructions are ON, false if the instructions are OFF
     */
    private boolean isSelected;

    /**
     * Boolean which describes is the background music playing or not
     * True if the music is ON, false if the music is OFF
     */
    private boolean isPlaying;

    /**
     * Double which describes the volume
     */
    private double volume;

    /**
     * A private constuctor for a singleton-object
     */
    private SettingsController() {

    }

    /**
     * A singleton object of class
     */
    public static SettingsController myInstance;

    /**
     * Instance of SettingsController
     * First time called the method creates a new singleton object
     * After the object is created returns the same exact object
     * @return SettingsController-object
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
        this.volume = 0.25;
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
