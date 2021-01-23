import com.sun.javafx.iio.ImageLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class fxml extends Application{
    @Override
    public void start(Stage primaryStage) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/InGameView.fxml"));
            Scene gameScene = new Scene(root);
            primaryStage.setScene(gameScene);
            primaryStage.setTitle("Blackjack");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
