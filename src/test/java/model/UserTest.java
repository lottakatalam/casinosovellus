package model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    UserCredentialHandler UCH = UserCredentialHandler.getInstance();
    User testUser;

    @BeforeAll
    static void setTestDB() {
        CasinoDAO.setTestmode(true);
        UserCredentialHandler.getInstance().createNewUser("katala","Arghkspfopss45");
        UserCredentialHandler.getInstance().login("katala","Arghkspfopss45");

    }

    @BeforeEach
    void initializeTest(){
        System.out.println(UCH.login("katala","Arghkspfopss45"));
        testUser = UCH.getLoggedInUser();

    }

    @AfterEach
    void endTest(){
        UCH.logout();
    }

    @AfterAll
    static void setTestDBOFF(){
        CasinoDAO.setTestmode(false);
    }

    @Test
    void getUserID() {
        UCH.createNewUser("Lotta","laksfjhsY7");
        UCH.login("Lotta","laksfjhsY7");
        testUser = UCH.getLoggedInUser();
        testUser.setUserID(1);
        assertEquals(1,testUser.getUserID(),"User ID not what expected");
    }

    @Test
    void setUserID() {
        UCH.createNewUser("Lotta","laksfjhsY7");
        UCH.login("Lotta","laksfjhsY7");
        testUser = UCH.getLoggedInUser();
        testUser.setUserID(567);
        assertEquals(567, testUser.getUserID(),"User ID setting failed");
    }

    @Test
    void getUsername() {
        UCH.createNewUser("Lotta","laksfjhsY7");
        UCH.login("Lotta","laksfjhsY7");
        testUser = UCH.getLoggedInUser();
        assertEquals("Lotta",testUser.getUsername(), "username not matching");
    }

    @Test
    void setUsername() {
        UCH.createNewUser("Lotta","laksfjhsY7");
        UCH.login("Lotta","laksfjhsY7");
        testUser = UCH.getLoggedInUser();
        testUser.setUsername("kattila");
        assertEquals("kattila",testUser.getUsername(),"Username setting failed");
        testUser.setUsername("katala");
    }


    @Test
    void setPassword() {
        UCH.createNewUser("Lotta","laksfjhsY7");
        UCH.login("Lotta","laksfjhsY7");
        testUser = UCH.getLoggedInUser();
        testUser.setPassword("testiSalasana");
        assertEquals("testiSalasana",testUser.getPassword(),"Password setting failed");
        UCH.changePassword("laksfjhsY7");
    }

    @Test
    void getBalance() {
        UCH.createNewUser("Lotta","laksfjhsY7");
        UCH.login("Lotta","laksfjhsY7");
        testUser = UCH.getLoggedInUser();
        testUser.setBalance(199988);
        assertEquals(199988,testUser.getBalance(),"Get balance failed");
    }

    @Test
    void setBalance() {
        UCH.createNewUser("Lotta","laksfjhsY7");
        UCH.login("Lotta","laksfjhsY7");
        testUser = UCH.getLoggedInUser();
        testUser.setBalance(5634);
        assertEquals(5634,testUser.getBalance(),"Get balance failed");
    }

    @Test
    void getRounds() {
        UCH.createNewUser("Lotta","laksfjhsY7");
        UCH.login("Lotta","laksfjhsY7");
        testUser = UCH.getLoggedInUser();
        testUser.setRounds(3);
        assertEquals(3,testUser.getRounds(),"Amount of rounds not correct");
    }

    @Test
    void setRounds() {
        UCH.createNewUser("Lotta","laksfjhsY7");
        UCH.login("Lotta","laksfjhsY7");
        testUser = UCH.getLoggedInUser();
        testUser.setRounds(5);
        assertEquals(5,testUser.getRounds(),"Amount of rounds not correct");
    }

    @Test
    void getRanking() {
        UCH.createNewUser("Lotta","laksfjhsY7");
        UCH.login("Lotta","laksfjhsY7");
        testUser = UCH.getLoggedInUser();
        testUser.setRanking(999);
        assertEquals(999,testUser.getRanking(),"Ranking not correct");
    }

    @Test
    void setRanking() {
        UCH.createNewUser("Lotta","laksfjhsY7");
        UCH.login("Lotta","laksfjhsY7");
        testUser = UCH.getLoggedInUser();
        testUser.setRanking(654);
        assertEquals(654,testUser.getRanking(),"Ranking not correct");
    }

    @Test
    void addRound() {
        UCH.createNewUser("Lotta","laksfjhsY7");
        UCH.login("Lotta","laksfjhsY7");
        testUser = UCH.getLoggedInUser();
        int expected = testUser.getRounds()+1;
        testUser.addRound();
        assertEquals(expected,testUser.getRounds(),"Adding round failed");
    }
}