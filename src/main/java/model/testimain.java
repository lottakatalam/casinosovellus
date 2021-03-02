package model;


public class testimain {
    public static void main(String[] args) {
        Logger.setLogLevel(Logger.LogLevel.ALL);
        /*
        Player p = new Player(2500);
        System.out.println(p.getHand().getNumberOfCards());
        System.out.println(p.getHand());
*/
        CasinoDAO casinoDAO = new CasinoDAO();



        UserCredentialHandler.getInstance().createNewUser("testi", "salasana");

        History h = new History();

        //h.setPlayerID(casinoDAO.getUserByUsername("pelaaja"));
        h.setResult(History.gameResults.WON);
        h.setBet(10000);
        h.setBalance(25000);
        casinoDAO.addHistoryRow(h);


    }

}
