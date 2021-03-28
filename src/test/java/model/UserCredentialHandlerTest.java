package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class UserCredentialHandlerTest {

    UserCredentialHandler UCH = UserCredentialHandler.getInstance();


    @Test
    void validateUserCredentials() {

    }

    //miten testataan kun aina pitäisi saada uusi uniikki käyttäjänimi annettua?
    @Test
    void createNewUser() {
    }

    @ParameterizedTest
    @CsvSource({"Katala,0", "Moikka,0", "lllllllllllllllll,0", "oi1,0", "pkLhnm3,1", "hgdkjrT87,1"})
    void isValidUsername(String username, int isValid) {
        String message = "";
        if (isValid == 0) {
            assertFalse(UCH.isValidUsername(username), "Invalid username :" + username + " was valid according to the test");
            if (username.equals("Katala") | username.equals("Moikka")) {
                message = "Username already exists. Try a different username";

            } else if (username.equals("lllllllllllllllll") | username.equals("oi1")) {
                message = "Username must be more than 4 and less than 16 characters in length.";
            }
            assertTrue(UCH.getErrorMessage().equals(message), "The error message was not correct");
        } else {
            assertTrue(UCH.isValidUsername(username), "Valid username :" + username + " was not valid according to the test");
        }
    }

    @ParameterizedTest
    @CsvSource({"salas,0", "salasana1,0", "SALASANA1,0", "salaSana,0", "123456,0", "salasanA1,1", "hjrTs7,1"})
    void isValidPassword(String password, int isValid) {
        String message = "";
        if (isValid == 0) {
            assertFalse(UCH.isValidPassword(password), "Invalid password :" + password + " was valid according to the test");
            if (password.equals("salas")) {
                message = "Password must contain at least 6 characters";
            } else if (password.equals("salasana1")) {
                message = "Password must have at least one uppercase character";
            } else if (password.equals("SALASANA1")) {
                message = "Password must have at least one lowercase character";
            } else if (password.equals("salaSana")) {
                message = "Password must have at least one number";
            } else {
                message = "Password must contain at least one alphabetical character";
            }
            assertTrue(UCH.getErrorMessage().equals(message), "The error message was not correct");
        } else {
            assertTrue(UCH.isValidPassword(password), "Valid password: " + password + " was invalid according to the test");
        }
    }

    @ParameterizedTest
    @CsvSource({"salasana, salaSana,0", "testi, test,0", "num3r0, numero,0", "salasana,salasana,1", "test1,test1,1", "samanLainen, samanLainen,1"})
    void passwordsMatch(String password, String anotherPassword, int isValid) {
        String message = "Passwords did not match";
        if (isValid == 0) {
            assertFalse(UCH.passwordsMatch(password, anotherPassword));
            assertTrue(UCH.getErrorMessage().equals(message), "The error message was not correct");
        } else {
            assertTrue(UCH.passwordsMatch(password, anotherPassword));
        }
    }


    @ParameterizedTest
    @CsvSource({"Tämä on errorviesti,1", "Tämä on väärä errorviesti,0", "tämä myös,0"})
    void getErrorMessage(String message, int matches) {
        UCH.setErrorMessage("Tämä on errorviesti");
        if (matches == 0) {
            assertFalse(UCH.getErrorMessage().equals(message));
        } else {
            assertTrue(UCH.getErrorMessage().equals(message));
        }
    }

    @ParameterizedTest
    @CsvSource({"Katala,Katala1,1", "dk78hafy,salasana,0"})
    void login(String username, String password, int isUser) {
        if (isUser == 0) {
            assertFalse(UCH.login(username, password), "Login successful with unregistrated user credentials");
        } else {
            assertTrue(UCH.login(username, password), "Registrated user could not log in");
        }
    }

}

