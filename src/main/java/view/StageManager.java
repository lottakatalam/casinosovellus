package view;

import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Controller class for stage management
 */
public class StageManager {
    public Stage primaryStage;

    /**
     * Constructor of StageManager
     */
    private StageManager() {

    }
    public static StageManager myInstance;

    /**
     * Gets the instance of StageManager
     * @return
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
}
