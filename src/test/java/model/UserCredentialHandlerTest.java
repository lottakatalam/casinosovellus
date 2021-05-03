package model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class UserCredentialHandlerTest {
    private static int userNameEnding = 0;
    UserCredentialHandler UCH;


    @BeforeAll
    static void setTestDB(){
        CasinoDAO.setTestmode(true);
        UserCredentialHandler.getInstance().createNewUser("Katala","Katala1");
        UserCredentialHandler.getInstance().createNewUser("loputa","Loputa1");
        UserCredentialHandler.getInstance().createNewUser("lopita","Lopita1");
        UserCredentialHandler.getInstance().createNewUser("Moikka","Lopita1");
    }

    @BeforeEach
    void initTests() {
        UCH = UserCredentialHandler.getInstance();
        //CasinoDAO.setTestmode(true);
    }

    @AfterEach
    void logout(){
        UCH.logout();
    }

    @AfterAll
    static void setTestDBOFF(){
        CasinoDAO.setTestmode(false);
    }

    @ParameterizedTest
    @CsvSource({"Katala,0", "Moikka,0", "lllllllllllllllll,0", "oi1,0", "pkLhnm3,1", "hgdkjrT87,1"})
    void isValidUsernameTest(String username, int isValid) {
        String message = "";
        if (isValid == 0) {
            assertFalse(UCH.isValidUsername(username), "Invalid username :" + username + " was valid according to the test");
            if (username.equals("Katala") | username.equals("Moikka")) {
                message = LanguageLoader.getInstance().getString("errorUserNameExists");
            } else if (username.equals("lllllllllllllllll") | username.equals("oi1")) {
                message = LanguageLoader.getInstance().getString("errorUserNameLenght");
            }
            assertTrue(UCH.getErrorMessage().equals(message), "The error message was not correct");
        } else {
            assertTrue(UCH.isValidUsername(username), "Valid username :" + username + " was not valid according to the test");
        }
    }

    @ParameterizedTest
    @CsvSource({"salas,0", "salasana1,0", "SALASANA1,0", "salaSana,0", "123456,0", "salasanA1,1", "hjrTs7,1"})
    void isValidPasswordTest(String password, int isValid) {
        String message = "";
        if (isValid == 0) {
            assertFalse(UCH.isValidPassword(password), "Invalid password :" + password + " was valid according to the test");
            if (password.equals("salas")) {
                message = LanguageLoader.getInstance().getString("errorPasswordLenght");
            } else if (password.equals("salasana1")) {
                message = LanguageLoader.getInstance().getString("errorPasswordNoUpperCaseChar");
            } else if (password.equals("SALASANA1")) {
                message = LanguageLoader.getInstance().getString("errorPasswordNoLowerCaseChar");
            } else if (password.equals("salaSana")) {
                message = LanguageLoader.getInstance().getString("errorPasswordNoNumberChar");
            } else {
                message = LanguageLoader.getInstance().getString("errorPasswordNoAlphabet");
            }
            assertTrue(UCH.getErrorMessage().equals(message), "The error message was not correct");
        } else {
            assertTrue(UCH.isValidPassword(password), "Valid password: " + password + " was invalid according to the test");
        }
    }

    @ParameterizedTest
    @CsvSource({"salasana, salaSana,0", "testi, test,0", "num3r0, numero,0", "salasana,salasana,1", "test1,test1,1", "samanLainen, samanLainen,1"})
    void passwordsMatchTest(String password, String anotherPassword, int isValid) {
        String message =  LanguageLoader.getInstance().getString("errorPasswordNotMatching_register");
        if (isValid == 0) {
            assertFalse(UCH.passwordsMatch(password, anotherPassword),"Passwords should not match");
            assertTrue(UCH.getErrorMessage().equals(message), "The error message was not correct");
        } else {
            assertTrue(UCH.passwordsMatch(password, anotherPassword), "Passwords should have matched");
        }
    }


    @ParameterizedTest
    @CsvSource({"Tämä on errorviesti,1", "Tämä on väärä errorviesti,0", "tämä myös,0"})
    void getErrorMessageTest(String message, int matches) {
        UCH.setErrorMessage("Tämä on errorviesti");
        if (matches == 0) {
            assertFalse(UCH.getErrorMessage().equals(message));
        } else {
            assertTrue(UCH.getErrorMessage().equals(message));
        }
    }

    @ParameterizedTest
    @CsvSource({"Katala,Katala1,1", "dk78hafy,salasana,0"})
    void loginTest(String username, String password, int isUser) {
        if (isUser == 0) {
            assertFalse(UCH.login(username, password), "Login successful with unregistrated user credentials");
        } else {
            assertTrue(UCH.login(username, password), "Registrated user could not log in");
        }
    }

    @ParameterizedTest
    @CsvSource({"Katala,Katala1,Katala1,0", "Mhsjkdfjgh, moikkA1, moikkA1,1","pdhf, moi,moi,0","JDASshfg,Moikka2,Moikka1,0"})
    void validateUserCredentialsTest(String username, String password1, String password2, int isValid){
        if (isValid == 0) {
            assertFalse(UCH.validateUserCredentials(username, password1, password2), "The method should have returned false since the username already exists");
        } else {
            assertTrue(UCH.validateUserCredentials(username, password1, password2), "The method should have returned true");
        }

    }

    @Test
    void createUserTest(){
        String username = "lkjhgf"+String.valueOf(userNameEnding);
        String password = "Lkjhgf1";
        UCH.createNewUser(username,password);
        UCH.login(username,password);
        assertTrue(UCH.getLoggedInUser().getUsername().equals(username), "User not created");
        assertTrue(UCH.getLoggedInUser().getBalance() == 2500, "The balance of the new user was incorrect");
        userNameEnding++;
    }

    void initializePasswordChangeTest(String username, String password){
        UCH.login(username,password);

    }

    @ParameterizedTest
    @CsvSource({"lopita,Lopita1,uusiSalasana1,uusiSalasana1,1","loputa,vaarasalasana,salasanA1,salasanA1,0","lopita,Lopita1,salas,salas,0","lopita,Lopita1,salasanA1,salasanI1,0"})
    void validatePasswordChangeTest(String username, String password, String newPassword1, String newPassword2, int isOK){
        if(username.equals("loputa")){
            this.initializePasswordChangeTest("loputa","Loputa1");
        } else {
            this.initializePasswordChangeTest(username, password);
        }
        String message = "";
        if (isOK == 0) {
            assertFalse(UCH.validatePassWordChange(password, newPassword1, newPassword2), "The method should have returned false");
        } else {
            assertTrue(UCH.validatePassWordChange(password,newPassword1,newPassword2));
        }

    }


}

