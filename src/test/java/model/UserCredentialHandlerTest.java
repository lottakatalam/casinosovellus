package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class UserCredentialHandlerTest {

    UserCredentialHandler UCH = UserCredentialHandler.getInstance();

   /* @Test
    void getInstance() {

    }

    @Test
    void setCasinoDAO() {
    }*/


    @Test
    void validateUserCredentials() {

    }

    @Test
    void createNewUser() {
    }

    @ParameterizedTest
    @CsvSource({"Katala,0", "Moikka,0","lllllllllllllllll,0","oi1,0","pkLhnm3,1","hgdkjrT87,1"})
    void isValidUsername(String username, int isValid) {
        String message = "";
        if (isValid == 0) {
            assertFalse(UCH.isValidUsername(username), "Invalid username :"+username+" was valid according to the test");
            if (username.equals("Katala")|username.equals("Moikka")) {
                message = "Username already exists. Try a different username";

            } else if (username.equals("lllllllllllllllll")|username.equals("oi1")){
                message = "Username must be more than 4 and less than 16 characters in length.";
            }
            assertTrue(UCH.getErrorMessage().equals(message), "The error message was not correct");
        } else {
            assertTrue(UCH.isValidUsername(username), "Valid username :"+username+" was not valid according to the test");
        }
    }

    @ParameterizedTest
    @CsvSource({"123456,1","salas,0","salasana1,0","SALASANA1,0","salaSana,0","salasanA1,1","hjrTs7,1",})
    void isValidPassword(String password, int isValid) {
        String message = "";
        if (isValid==0) {
            assertFalse(UCH.isValidPassword(password),"Invalid password :"+password+" was valid according to the test");
        } else {
            assertTrue(UCH.isValidPassword(password), "Valid password: "+password+" was invalid according to the test");
        }
    }

    @Test
    void passwordsMatch() {
    }

    @Test
    void setErrorMessage() {
    }

    @Test
    void getErrorMessage() {
    }

    @Test
    void login() {
    }

    @Test
    void validatePassWordChange() {
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

    @Test
    void getCasinoDAO() {
    }
}