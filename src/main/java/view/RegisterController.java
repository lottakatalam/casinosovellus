package view;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Window;

/**
 *
 * Register Controller for fxml ui
 *
 */

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailIdField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button submitButton;

    @FXML
    public void register(ActionEvent event) throws SQLException{

        Window owner = submitButton.getScene().getWindow();

        System.out.println(usernameField.getText());
        System.out.println(emailIdField.getText());
        System.out.println(passwordField.getText());
        if(usernameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your name");
            return;
        }

        if(emailIdField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your email id");
            return;
        }
        if(passwordField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a password");
            return;
        }

        String username = usernameField.getText();
        String emailId = emailIdField.getText();
        String password = passwordField.getText();



        // TODO: JDBCDao for Database operations


         /*

        JdbcDao jdbcDao = new JdbcDao();
        jdbcDao.insertRecord(username, emailId, password);

        */





        showAlert(Alert.AlertType.CONFIRMATION, owner, "Registration Successful!",
                "Welcome " + usernameField.getText());
    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}
