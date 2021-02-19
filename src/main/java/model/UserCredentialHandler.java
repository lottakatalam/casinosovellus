package model;

public class UserCredentialHandler {

    private static UserCredentialHandler instance = null;

    private CasinoDAO casinoDAO;


    private UserCredentialHandler(){
        casinoDAO = new CasinoDAO();
    }

    public static UserCredentialHandler getInstance() {
        if (instance == null) {
            instance = new UserCredentialHandler();
        }
        return instance;
    }

    public void setCasinoDAO(CasinoDAO casinoDAO) {
        this.casinoDAO = casinoDAO;
    }

    public boolean createNewUser(String username, String password) {

        User newUser = new User();
        newUser.setUserName(username);
        newUser.setPassword(password);

        casinoDAO.addUserRow(newUser);



        return true;
    }
}
