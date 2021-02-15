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
    public Button refreshButton;

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
     * Clears user's game history
     */
    public void yesAction() {
        blackScreen.setVisible(false);
        yesButton.setVisible(false);
        noButton.setVisible(false);
        areYouSure.setVisible(false);
        historyTable.getItems().clear();
    }
/*
    public ObservableList<History> getHistory() {
        ObservableList<History> history = FXCollections.observableArrayList();
        history.add(new History(1,"Won",100,2600));
        history.add(new History(2,"Lost",100,2500));
        history.add(new History(3,"Doubled",100,2900));
        history.add(new History(4,"Insured",200,2900));
        return history;
    }
    */


    public void refresh() {
        gameColumn.setCellValueFactory(new PropertyValueFactory<>("gameNumber"));
        resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));
        betColumn.setCellValueFactory(new PropertyValueFactory<>("bet"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        //historyTable.setItems(getHistory());

    }
}
