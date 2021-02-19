package controller;


import model.UserCredentialHandler;

public class UserController {

    public UserController() {
    }

    public void createNewUser(String username, String password) {
        UserCredentialHandler.getInstance().createNewUser(username,password);
    }

}
