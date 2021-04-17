package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    UserCredentialHandler UCH = UserCredentialHandler.getInstance();
    User testUser;

    @BeforeEach
    void initializeTest(){
        UCH.login("katala","Katala1");
        testUser = UCH.getLoggedInUser();
    }

    @AfterEach
    void endTest(){
        UCH.logout();
    }

    @Test
    void getUserID() {
        testUser.setUserID(1);
        assertEquals(1,testUser.getUserID(),"User ID not what expected");
    }

    @Test
    void setUserID() {
        testUser.setUserID(567);
        assertEquals(567, testUser.getUserID(),"User ID setting failed");
    }

    @Test
    void getUsername() {
        assertEquals("Katala",testUser.getUsername(), "username not matching");
    }

    @Test
    void setUsername() {
        testUser.setUsername("kattila");
        assertEquals("kattila",testUser.getUsername(),"Username setting failed");
        testUser.setUsername("Katala");
    }


    @Test
    void setPassword() {
        testUser.setPassword("testiSalasana");
        assertEquals("testiSalasana",testUser.getPassword(),"Password setting failed");
        UCH.changePassword("Katala1");
    }

    @Test
    void getBalance() {
        testUser.setBalance(199988);
        assertEquals(199988,testUser.getBalance(),"Get balance failed");
    }

    @Test
    void setBalance() {
        testUser.setBalance(5634);
        assertEquals(5634,testUser.getBalance(),"Get balance failed");
    }

    @Test
    void getRounds() {
        testUser.setRounds(3);
        assertEquals(3,testUser.getRounds(),"Amount of rounds not correct");
    }

    @Test
    void setRounds() {
        testUser.setRounds(5);
        assertEquals(5,testUser.getRounds(),"Amount of rounds not correct");
    }

    @Test
    void getRanking() {
        testUser.setRanking(999);
        assertEquals(999,testUser.getRanking(),"Ranking not correct");
    }

    @Test
    void setRanking() {
        testUser.setRanking(654);
        assertEquals(654,testUser.getRanking(),"Ranking not correct");
    }

    @Test
    void addRound() {
        int expected = testUser.getRounds()+1;
        testUser.addRound();
        assertEquals(expected,testUser.getRounds(),"Adding round failed");
    }
}