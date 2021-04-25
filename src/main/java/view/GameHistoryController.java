package view;

import controller.SettingsController;
import controller.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import model.CasinoDAO;
import model.History;
import model.LanguageLoader;
import model.UserCredentialHandler;

import java.io.IOException;

/**
 * Controller class for Game History
 */
public class GameHistoryController extends ViewController {

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
    public Button volumeOFFbutton;
    public Button volumeONbutton;
    CasinoDAO casinoDAO;


    /**
     * Loads back to MainMenu
     * @throws IOException - if .fxml file is not found
     */
    public void gameHistoryBackButton() throws IOException {
        showMainMenu();
    }

    /**
     * Sets Are you sure-screen visible to clear the game history
     * Yes-action clears history and No-action closes the screen
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
     * If user is not logged in, clearing isn't possible
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
            errorText.setText(LanguageLoader.getInstance().getString("GamehistoryClearError"));
            blackScreen.setVisible(true);
            errorText.setVisible(true);
            okButton.setVisible(true);
        }
    }

    /**
     * Gets every row from Historytable in database to Observablelist
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
     * Refresh-button refreshes the TableView if user is logged in
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
            errorText.setText(LanguageLoader.getInstance().getString("GamehistoryEntryError"));
            blackScreen.setVisible(true);
            errorText.setVisible(true);
            okButton.setVisible(true);
        }

    }

    /**
     * For initializing table in game history
     * Checks the current volume state
     * Refreshes the tableView
     * Sets empty table's message to correct language
     */
    public void initialize() {
        casinoDAO = new CasinoDAO();
        checkVolume();
        historyTable.setPlaceholder(new Label(LanguageLoader.getInstance().getString("TableViewText")));
        if (UserCredentialHandler.getInstance().getLoggedInUser() != null) {
            loggedUser.setText(LanguageLoader.getInstance().getString("LoggedInUser") + UserCredentialHandler.getInstance().getLoggedInUser().getUsername());
        }
        refresh();
    }

    /**
     * okButton disables "error" message, that you should log in to view game history
     * and puts user back to main menu
     */
    public void okButton() throws IOException {
        blackScreen.setVisible(false);
        errorText.setVisible(false);
        okButton.setVisible(false);
        gameHistoryBackButton();
    }

    /**
     * Mutes game music
     */
    public void volumeOFF() {
        volumeOFFbutton.setVisible(false);
        volumeONbutton.setVisible(true);
        stageManager.getMediaPlayer().setVolume(0);
    }

    /**
     * Turns game music back ON
     */
    public void volumeON() {
        volumeONbutton.setVisible(false);
        volumeOFFbutton.setVisible(true);
        stageManager.getMediaPlayer().setVolume(SettingsController.getInstance().getVolume());
    }

    /**
     * Checks is the volume ON or OFF
     */
    public void checkVolume() {
        if(stageManager.getMediaPlayer().getVolume() == 0) {
            volumeOFFbutton.setVisible(false);
            volumeONbutton.setVisible(true);
        }else {
            volumeONbutton.setVisible(false);
            volumeOFFbutton.setVisible(true);
        }
    }
}
