package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.CasinoDAO;
import model.LanguageLoader;
import model.User;
import model.UserCredentialHandler;
import java.util.Arrays;
import java.util.Comparator;

public class LeaderboardsController extends ViewController {
    public Text loggedUser;
    public TableView<User> leaderboardTable;
    public TableColumn rankingColumn;
    public TableColumn usernameColumn;
    public TableColumn balanceColumn;
    public TableColumn roundColumn;
    CasinoDAO casinoDAO;

    /**
     * Initializes stageManager
     * Checks the current volume state
     * Sets empty table's message to correct language
     */
    public void initialize() {
        casinoDAO = new CasinoDAO();
        checkVolume();
        leaderboardTable.setPlaceholder(new Label(LanguageLoader.getInstance().getString("TableViewText")));
        if (UserCredentialHandler.getInstance().getLoggedInUser() != null) {
            loggedUser.setText(LanguageLoader.getInstance().getString("LoggedInUser") + UserCredentialHandler.getInstance().getLoggedInUser().getUsername());
        }
        setTable();
    }

    /**
     * Loads back to Main menu
     *
     */
    public void leaderboardsBackButton () {
        showMainMenu();
    }

    /**
     * Sets rankings to the leaderboardsTableView
     */
    public void setTable() {
        rankingColumn.setCellValueFactory(new PropertyValueFactory<>("ranking"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        roundColumn.setCellValueFactory(new PropertyValueFactory<>("rounds"));

        leaderboardTable.setItems(getLeaderboards());
    }

    /**
     * Gets every row from UsersTable in database to Observablelist
     * @return - Returns Observablelist
     */
    public ObservableList<User> getLeaderboards() {
        ObservableList<User> leaderboards = FXCollections.observableArrayList();
        User[] u = casinoDAO.getAllUserRows();
        leaderboards.addAll(Arrays.asList(u));

        Comparator<User> comparator = Comparator.comparingInt(User::getBalance);
        comparator = comparator.reversed();
        FXCollections.sort(leaderboards, comparator);

        for (int i=0; i<leaderboards.size(); i++ ) {
            leaderboards.get(i).setRanking(i+1);
            casinoDAO.updateUser(leaderboards.get(i));
        }

        return leaderboards;
    }


}
