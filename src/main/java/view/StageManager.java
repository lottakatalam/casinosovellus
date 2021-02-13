package view;

import javafx.stage.Stage;

public class StageManager {
    public Stage primaryStage;

    private StageManager() {

    }
    public static StageManager myInstance;

    public static StageManager getInstance() {
        if (myInstance == null) {
            myInstance = new StageManager();
        }
        return myInstance;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }
}
