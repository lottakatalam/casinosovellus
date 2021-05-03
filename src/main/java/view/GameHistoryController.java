package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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
    CasinoDAO casinoDAO;


    /**
     * Loads back to MainMenu
     */
    public void gameHistoryBackButton() {
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

                /* Date formating for both languages*/
                if (LanguageLoader.getInstance().getLocale().toString().equals("fi_FI")) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH.mm.ss");
                    String date = h[i].getDate().format(formatter);
                    h[i].setDateString(date);
                    h[i].setBalanceString(String.format("%,d",h[i].getBalance()));
                    h[i].setBetString(String.format("%,d",h[i].getBet()));
                }else if(LanguageLoader.getInstance().getLocale().toString().equals("en_GB")) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
                    String date = h[i].getDate().format(formatter);
                    h[i].setDateString(date);
                    h[i].setBalanceString(String.format(Locale.UK, "%,d",h[i].getBalance()));
                    h[i].setBetString(String.format(Locale.UK,"%,d",h[i].getBet()));
                }
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
        betColumn.setCellValueFactory(new PropertyValueFactory<>("betString"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balanceString"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateString"));

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
}
