package controller;


import model.User;
import model.UserCredentialHandler;

public class UserController {

    public UserController() {
    }

    public void createNewUser(String username, String password) {
        UserCredentialHandler.getInstance().createNewUser(username,password);
    }

    public boolean login(String username, String password) {
        return UserCredentialHandler.getInstance().login(username, password);
    }

    public void logout() {
        UserCredentialHandler.getInstance().logout();
    }

    public boolean isUserLoggedIn() {
        return UserCredentialHandler.getInstance().isLoggedIn();
    }
}
