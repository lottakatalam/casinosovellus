package model;


public class testimain {
    public static void main(String[] args) {
        Logger.setLogLevel(Logger.LogLevel.ALL);
        /*
        Player p = new Player(2500);
        System.out.println(p.getBet());
        System.out.println(p.getHand());
*/
        CasinoDAO casinoDAO = new CasinoDAO();

        User newUser = new User();
        newUser.setUserName("testi");
        newUser.setPassword("testisalasana");
        newUser.setBalance(2500);
        casinoDAO.addUserRow(newUser);

        History h = new History();

        h.setPlayerID(casinoDAO.getUser(1));
        h.setBet(2000);
        casinoDAO.addHistoryRow(h);
    }

}
