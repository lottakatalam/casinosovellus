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
    public TableColumn useridColumn;
    public TableColumn methodColumn;
    public TableColumn dateColumn;
    public Button refreshButton;

    CasinoDAO casinoDAO = new CasinoDAO();


    /**
     * Loads back to MainMenu.fxml
     * @param actionEvent
     * @throws IOException
     */
    public void gameHistoryBackButton(ActionEvent actionEvent) throws IOException {

        Parent menuParent = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));
        Scene menuScene = new Scene(menuParent);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setTitle("The Grand Myllypuro");
        window.setScene(menuScene);
        window.show();
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
     * Clears user's game history (Gets every row and deletes every one of them in the loop)
     */
    public void yesAction() {
        blackScreen.setVisible(false);
        yesButton.setVisible(false);
        noButton.setVisible(false);
        areYouSure.setVisible(false);
        historyTable.getItems().clear();
        History[] h = casinoDAO.getAllHistoryRows();
        for (int i = 0; i < h.length; i++) {
            casinoDAO.deleteHistoryRow(h[i].getGameNumber());
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

            history.add(h[i]);
        }

        return history;
    }


    /**
     * Refresh-button makes database visible in the TableView
     */
    public void refresh() {

        // For Testing
        History h = new History();

        h.setPlayerID(casinoDAO.getUserByUsername("Pelaaja"));
        h.setResult(History.gameResults.WON);
        h.setMethod("Basic");
        h.setBet(1000);
        h.setBalance(2500);
        //h.setDate("2013-09-04");
        casinoDAO.addHistoryRow(h);

        ////////////////////////////////////////

        gameColumn.setCellValueFactory(new PropertyValueFactory<>("gameNumber"));
        //useridColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));
        methodColumn.setCellValueFactory(new PropertyValueFactory<>("method"));
        betColumn.setCellValueFactory(new PropertyValueFactory<>("bet"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        historyTable.setItems(getHistory());

    }
}
