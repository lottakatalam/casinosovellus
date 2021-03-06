package view;

import controller.SettingsController;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller class for stage management
 */
public class StageManager {
    public Stage primaryStage;
    private MediaPlayer mediaPlayer;

    /**
     * Constructor of StageManager
     */
    private StageManager() {
        initAudio();

    }

    public static StageManager myInstance;

    /**
     * Gets the instance of StageManager
     *
     * @return instance of StageManager
     */
    public static StageManager getInstance() {
        if (myInstance == null) {
            myInstance = new StageManager();
        }
        return myInstance;
    }

    /**
     * Sets the primaryStage
     * @param primaryStage - The primaryStage
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Gets the primaryStage
     * @return - The primaryStage
     */
    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    /**
     * Initializes game music
     * Sets default volume to 0.25
     */
    private void initAudio(){
        Media sound = new Media(getClass().getResource("/Music/ElegantJazz_edited.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.play();
        });
        mediaPlayer.setVolume(0.25);
        mediaPlayer.play();
        SettingsController.getInstance().setIsPlaying();
    }

    /**
     * Getter for MediaPlayer, which plays the music
     * @return - The MediaPlayer
     */
    public MediaPlayer getMediaPlayer() {
        return this.mediaPlayer;
    }
}