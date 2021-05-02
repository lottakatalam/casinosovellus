package controller;



import model.UserCredentialHandler;


/**
 * Used to control User information
 */
public class UserController {

    /**
     * A string which is created in the UserCredentialHandler
     * This controller forwards the errormessage to viewController to use the message in UI
     */
    private String errorMessage;

    public UserController() {}



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
     * RegisterController uses this method for setting an errormessage related to unsuccessful situations in the UI
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

    /**
     * Asks the UserCredentialHandler to validate the user input for passwordchange
     * If input is valid, asks the UserCredentialHandler to change the users password
     * If input is not valid, gets an error message from UserCredentialHandler
     * @param oldPassword
     * @param newPassword
     * @param newPasswordRepeated
     * @return true, if the password change was successful
     */
    public boolean validatePasswordChange(String oldPassword, String newPassword, String newPasswordRepeated) {
        if (UserCredentialHandler.getInstance().validatePassWordChange(oldPassword, newPassword, newPasswordRepeated)) {
            UserCredentialHandler.getInstance().changePassword(newPassword);
            return true;
        } else {
            errorMessage = UserCredentialHandler.getInstance().getErrorMessage();
            return false;
        }
    }

}
