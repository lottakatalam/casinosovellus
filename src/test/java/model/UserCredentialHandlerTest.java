package model;

import controller.UserController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import view.RegisterController;

import static org.junit.jupiter.api.Assertions.*;

class UserCredentialHandlerTest {

    UserCredentialHandler UCHandler = UserCredentialHandler.getInstance();
    //Miten alustetaan registerController?


    @Test
    void getInstance() {

    }

    /*@Test
    void linkController() {
        UserController userController = new UserController(rg);
        assertNotNull(UserCredentialHandler.getInstance().getUserController(),"The user controller-object was not created.");
    }*/

    @Test
    void validateUserCredentials() {

    }

    //might not work is username already exists?
    @ParameterizedTest
    @CsvSource({"lottaliina, Lol123", "pelaaja, pelaaja234", "lol, lol"})
    void createNewUser(String username, String password) {
        CasinoDAO casdao = UCHandler.getCasinoDAO();
        UCHandler.createNewUser(username,password);
        User user = casdao.getUserByUsername(username);
        assertTrue(user.getUsername().equals(username), "The user "+username+"  was not created");
    }


    /*@ParameterizedTest
    @ValueSource (strings = {"ai", "mit", "mmmmmmmmmmmmmmmmm"})
    void isValidUsername(String username) {
        UserController userController = new UserController(new RegisterController());
        assertFalse(UCHandler.isValidUsername(username), "The method returned true, should have been false");
    }*/

    @Test
    void isValidPassword() {
    }

    @Test
    void passwordsMatch() {
    }

    @Test
    void setErrorMessage() {
    }

    @Test
    void login() {
    }

    @Test
    void changePassword() {
    }

    @Test
    void getLoggedInUser() {
    }

    @Test
    void isLoggedIn() {
    }

    @Test
    void logout() {
    }
}