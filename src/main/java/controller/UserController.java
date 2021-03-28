package controller;



import model.User;
import model.UserCredentialHandler;
import view.RegisterController;

/**
 * Used to control User information
 */
public class UserController {

    private RegisterController registerController;

    private String errorMessage;


    /**
     * Constructor of UserController
     */
    public UserController() {

    }


    /**
     *  Calls for registration validation
     * @param username - New user's username
     * @param password1 - New user's password
     * @param password2 - User's repeated password
     * returns true if new user is created
     */
    public boolean validateCredentials(String username, String password1, String password2) {
        if (UserCredentialHandler.getInstance().validateUserCredentials(username,password1, password2)) {
            UserCredentialHandler.getInstance().createNewUser(username, password1);
            return true;
        } else {
            errorMessage = UserCredentialHandler.getInstance().getErrorMessage();
            return false;
        }

    }

    /**
     * RegisterController uses this method for setting an errormessage related to unsuccessful registration
     * The string is possibly set in the validateCredentials-method by calling the UserCredentialHandler for the right errormessage
     * @return errorMessage, which specifies the reason for the unsuccessful registration
     */
    public String getErrorMessage() {
        return errorMessage;
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
