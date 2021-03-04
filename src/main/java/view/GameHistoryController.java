package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.CasinoDAO;
import model.History;
import model.UserCredentialHandler;

import java.io.IOException;

public class GameHistoryController {

    public Text areYouSure;
    public Button yesButton;
    public Button noButton;
    public ImageView blackScreen;
    public Button clearHistoryButton;
    public TableView<History> historyTable;
    public TableColumn gameColumn;
    public TableColumn resultColumn;
    public TableColumn betColumn;
    public TableColumn balanceColumn;
    public TableColumn methodColumn;
    public TableColumn dateColumn;
    public Button refreshButton;
    public Button okButton;
    public Text errorText;
    public Text loggedUser;
    private StageManager stageManager;

    CasinoDAO casinoDAO = new CasinoDAO();


    /**
     * Loads back to MainMenu.fxml
     * @throws IOException
     */
    public void gameHistoryBackButton() throws IOException {

        Parent menuParent = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));
        Scene menuScene = new Scene(menuParent);
        stageManager = StageManager.getInstance();
        stageManager.getPrimaryStage().setTitle("The Grand Myllypuro");
        stageManager.getPrimaryStage().setScene(menuScene);
        stageManager.getPrimaryStage().show();
    }

    /**
     * Sets Are you sure-screen visible to clear the game history
     */
    public void clearHistory() {
        blackScreen.setVisible(true);
        yesButton.setVisible(true);
        noButton.setVisible(true);
        areYouSure.setVisible(true);
    }

    /**
     * Closes Are you sure-screen
     */
    public void noAction() {
        blackScreen.setVisible(false);
        yesButton.setVisible(false);
        noButton.setVisible(false);
        areYouSure.setVisible(false);
    }

    /**
     * Clears user's game history if user is logged in (Gets every row and deletes every one of them in the loop)
     */
    public void yesAction() {
        if (UserCredentialHandler.getInstance().getLoggedInUser() != null) {
            blackScreen.setVisible(false);
            yesButton.setVisible(false);
            noButton.setVisible(false);
            areYouSure.setVisible(false);
            historyTable.getItems().clear();
            History[] h = casinoDAO.getAllHistoryRows();
            for (int i = 0; i < h.length; i++) {
                casinoDAO.deleteHistoryRow(h[i].getGameNumber());
            }
        }else {
            yesButton.setVisible(false);
            noButton.setVisible(false);
            areYouSure.setVisible(false);
            errorText.setText("Please log in to clear game history");
            blackScreen.setVisible(true);
            errorText.setVisible(true);
            okButton.setVisible(true);
        }
    }

    /**
     * Gets every row from Database to Observablelist
     * @return - Returns Observablelist
     */
    public ObservableList<History> getHistory() {
        ObservableList<History> history = FXCollections.observableArrayList();
        History[] h = casinoDAO.getAllHistoryRows();
        for (int i = 0; i < h.length; i++) {

            if (h[i].getUserID() == UserCredentialHandler.getInstance().getLoggedInUser().getUserID()) {

                history.add(h[i]);
            }
        }

        return history;
    }


    /**
     * Refresh-button makes database visible in the TableView if user is logged in
     */
    public void refresh() {

        gameColumn.setCellValueFactory(new PropertyValueFactory<>("gameNumber"));
        resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));
        methodColumn.setCellValueFactory(new PropertyValueFactory<>("method"));
        betColumn.setCellValueFactory(new PropertyValueFactory<>("bet"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        if (UserCredentialHandler.getInstance().getLoggedInUser() != null) {

            historyTable.setItems(getHistory());

        }else {
            errorText.setText("Please log in to view game history");
            blackScreen.setVisible(true);
            errorText.setVisible(true);
            okButton.setVisible(true);
        }

    }

    public void initialize() {
        if (UserCredentialHandler.getInstance().getLoggedInUser() != null) {
            loggedUser.setText("Logged in as: " + UserCredentialHandler.getInstance().getLoggedInUser().getUserName());
        }
        gameColumn.setCellValueFactory(new PropertyValueFactory<>("gameNumber"));
        resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));
        methodColumn.setCellValueFactory(new PropertyValueFactory<>("method"));
        betColumn.setCellValueFactory(new PropertyValueFactory<>("bet"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        if (UserCredentialHandler.getInstance().getLoggedInUser() != null) {

            historyTable.setItems(getHistory());

        }else {
            errorText.setText("Please log in to view game history");
            blackScreen.setVisible(true);
            errorText.setVisible(true);
            okButton.setVisible(true);
        }
    }

    /**
     * okButton disables "error" message, that you should log in to view game history
     */
    public void okButton() throws IOException {
        blackScreen.setVisible(false);
        errorText.setVisible(false);
        okButton.setVisible(false);
        gameHistoryBackButton();
    }
}
