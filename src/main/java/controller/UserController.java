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
     * Alternative constructor to be used in registerController
     */
    public UserController(RegisterController rg) {
        registerController = rg;
        UserCredentialHandler.getInstance().linkController(this);
    }

    /**
     *  Calls for registration validation
     * @param username - New user's username
     * @param password1 - New user's password
     * @param password2 - User's repeated password
     * returns true if new user is created
     */
    public boolean createNewUser(String username, String password1, String password2) {
        return UserCredentialHandler.getInstance().validateUserCredentials(username,password1, password2);
    }

    /**
     * Sets a new error message to registration view
     * @param message - string to display as an error message for user
     * @return message which is displayed in the UI
     */
    public String setErrorMessageToView(String message){
        registerController.setErrorMessage(message);
        return message;
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
