package view;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

import controller.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Window;

/**
 *
 * Register Controller for fxml ui
 *
 */

public class RegisterController {

    private UserController userController = new UserController();

    @FXML
    public TextField usernameTextField;

    @FXML
    public TextField passwordTextField;

    @FXML
    public TextField repeatTextField;

    @FXML
    public Button submitButton;

    private StageManager stageManager;

    

    @FXML
    public void register(ActionEvent event) throws SQLException{

        Window owner = usernameTextField.getScene().getWindow();

        System.out.println(usernameTextField.getText());
        System.out.println(passwordTextField.getText());
        if(usernameTextField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your name");
            return;
        }

        if(passwordTextField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a password");
            return;
        }

        if(passwordTextField.getText().equals(repeatTextField.getText())){
            System.out.println("Password successfully created.");
        } else {
            System.out.println("Passwords do not match.");
        }


        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String confirmpassword = repeatTextField.getText();


        userController.createNewUser(username, password);

        showAlert(Alert.AlertType.CONFIRMATION, owner, "Registration Successful!",
                "Welcome " + usernameTextField.getText());

        usernameTextField.setText("");
        passwordTextField.setText("");
        repeatTextField.setText("");
    }

    // TODO: Password reapeat (check)

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public void backToMainMenu(ActionEvent actionEvent) throws IOException {
        Parent menuParent = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));
        Scene menuScene = new Scene(menuParent);
        stageManager = StageManager.getInstance();
        stageManager.getPrimaryStage().setTitle("The Grand Myllypuro");
        stageManager.getPrimaryStage().setScene(menuScene);
        stageManager.getPrimaryStage().show();

    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }
   
    
    
}
