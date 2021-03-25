package view;

import controller.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.CasinoDAO;
import model.User;
import model.UserCredentialHandler;

import java.io.IOException;

public class LeaderboardsController {
    public Text loggedUser;
    public Button volumeOFFbutton;
    public Button volumeONbutton;
    public TableView leaderboardTable;
    public TableColumn rankingColumn;
    public TableColumn usernameColumn;
    public TableColumn balanceColumn;
    public TableColumn roundColumn;
    private StageManager stageManager;
    private UserController userController = new UserController();
    CasinoDAO casinoDAO = new CasinoDAO();

    /**
     * Initializes stageManager
     * Checks the current volume state
     */
    public void initialize() {
        stageManager = StageManager.getInstance();
        checkVolume();
        if (UserCredentialHandler.getInstance().getLoggedInUser() != null) {
            loggedUser.setText("Logged in as: " + UserCredentialHandler.getInstance().getLoggedInUser().getUserName());
        }
        setTable();
    }

    /**
     * Loads back to Main menu
     * @throws IOException - if .fxml file is not found
     */
    public void leaderboardsBackButton () throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/MainMenu.fxml"));
        Parent menuParent = loader.load();
        MainMenuController controller = loader.getController();
        if (userController.isUserLoggedIn()) {
            controller.loginButton.setVisible(false);
            controller.registerButton.setVisible(false);
            controller.logoutButton.setVisible(true);
            controller.changePasswordButton.setVisible(true);
        }
        Scene menuScene = new Scene(menuParent);
        stageManager.getPrimaryStage().setTitle("The Grand Myllypuro");
        stageManager.getPrimaryStage().setScene(menuScene);
        stageManager.getPrimaryStage().show();
    }

    /**
     * Sets rankings to the leaderboardsTableView
     */
    public void setTable() {
        rankingColumn.setCellValueFactory(new PropertyValueFactory<>("ranking"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        roundColumn.setCellValueFactory(new PropertyValueFactory<>("round"));
        //leaderboardTable.setItems(getLeaderboards());
    }

    /**
     * Gets every row from Userstable in database to Observablelist
     * @return - Returns Observablelist
     */
    /*public ObservableList<User> getLeaderboards() {
        ObservableList<User> leaderboards = FXCollections.observableArrayList();
        User[] u = casinoDAO.getAllHistoryRows(); // TODO: CasinoDAO:n getAllUserRows()-metodi ja User-luokkaan kolumni pelikierroksille
        for (int i = 0; i < u.length; i++) {

            if (u[i].getUserID() == UserCredentialHandler.getInstance().getLoggedInUser().getUserID()) {

                leaderboards.add(u[i]);
            }
        }

        return leaderboards;
    }*/

    /**
     * Mutes game music
     */
    public void volumeOFF () {
        volumeOFFbutton.setVisible(false);
        volumeONbutton.setVisible(true);
        stageManager.getMediaPlayer().setVolume(0);
    }

    /**
     * Turns game music back ON
     */
    public void volumeON () {
        volumeONbutton.setVisible(false);
        volumeOFFbutton.setVisible(true);
        stageManager.getMediaPlayer().setVolume(0.25);
    }

    /**
     * Checks is the volume ON or OFF
     */
    public void checkVolume () {
        if (stageManager.getMediaPlayer().getVolume() == 0) {
            volumeOFF();
        } else {
            volumeON();
        }
    }
}
