package controller;



import model.User;
import model.UserCredentialHandler;
import view.RegisterController;

/**
 * Used to control User information
 */
public class UserController {

    private RegisterController registerController;
    /**
     * Constructor of UserController
     */
    public UserController() {

    }

    /**
     * Alternative constructor for registerController
     */
    public UserController(RegisterController rg) {
        registerController = rg;
        UserCredentialHandler.getInstance().linkController(this);
    }

    /**
     * Creates new user
     * @param username - New user's username
     * @param password - New user's password
     */
    public void createNewUser(String username, String password) {
        UserCredentialHandler.getInstance().createNewUser(username,password);
    }

    public String setErrorMessagetoView(String message){
        return "moi";
    }

    /**
     * Logs the user in
     * @param username - User's username
     * @param password - User's password
     * @return - Returns User
     */
    public boolean login(String username, String password) {
        return UserCredentialHandler.getInstance().login(username, password);
    }

    /**
     * Logs the current user out
     */
    public void logout() {
        UserCredentialHandler.getInstance().logout();
    }

    /**
     * Checks if user is logged in
     * @return - Returns current user
     */
    public boolean isUserLoggedIn() {
        return UserCredentialHandler.getInstance().isLoggedIn();
    }

    public boolean changeUserPassword(String newPassword, String oldPassword) {
        return UserCredentialHandler.getInstance().changePassword(newPassword, oldPassword);
    }
}
