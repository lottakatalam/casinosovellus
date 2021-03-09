package view;
import controller.BlackjackController;
import controller.SettingsController;
import controller.UserController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

import static javafx.application.Application.launch;

/**
 * Main class of the app GUI. Initializes the views, variables and controller
 */
public class GUImain extends Application {
    private Stage primaryStage;
    private AnchorPane rootLayout;
    private BlackjackController ctrl;
    private UserController userController;
    private StageManager stageManager;

    /**
     * main function to launch the app
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Constructor of GUImain
     */
    public GUImain() {

    }

    /**
     * Function that is called from launch. Sets the views and initializes the stage.
     * @param primaryStage is the primary stage set for the view
     */
    public void start(Stage primaryStage) {
        try {

            primaryStage.setOnCloseRequest(new EventHandler<>() {
                @Override
                public void handle(WindowEvent t) {
                    Platform.exit();
                    System.exit(0);
                }
            });
            this.primaryStage = primaryStage;
            this.primaryStage.setTitle("The Grand Myllypuro");
            stageManager = StageManager.getInstance();
            stageManager.setPrimaryStage(primaryStage);
            SettingsController.getInstance().initializeSettings();
            Image icon= new Image(getClass().getResource("chip.png").toExternalForm());
            primaryStage.getIcons().add(icon);
            initRootLayout();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads up the root layout and sets the controller, shows the primary stage
     */
    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GUImain.class.getResource("/MainMenu.fxml"));
            rootLayout = loader.load();
            MainMenuController controller = loader.getController();
            BlackjackController ctrl = new BlackjackController();
            userController = new UserController();

            controller.setGameController(ctrl);
            controller.setUserController(userController);
            controller.setStageManager(this.stageManager);
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
