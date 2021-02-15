package model;


public class testimain {
    public static void main(String[] args) {
        Logger.setLogLevel(Logger.LogLevel.ALL);
        CasinoDAO casinoDAO = new CasinoDAO();

        History h = new History();
        h.setResult(History.gameResults.PLAYERLOST);
        h.setBet(2000);
        h.setPlayerID(1);
        casinoDAO.addHistoryRow(h);
        System.out.println("Player id: " + casinoDAO.getAllHistoryRows()[0].getPlayerID());
        //BlackJack game = new BlackJack();

        //game.initRound();
        /*
        Hand hand = new Hand();
        hand.addCard(new Card(1, 1));
        hand.addCard(new Card(2, 2));
        hand.addCard(new Card(13, 3));

        hand.calculateTotal();

         */

    }
}
