package model;


public class testimain {
    public static void main(String[] args) {
        Logger.setLogLevel(Logger.LogLevel.ALL);
        CasinoDAO casinoDAO = new CasinoDAO();

        PeliTulos p = new PeliTulos();
        p.setPelaajaVoitti(true);
        casinoDAO.lisaaPeliTulos(p);
        System.out.println(casinoDAO.lueKaikkiPeliTulokset()[0].isPelaajaVoitti());
        //BlackJack game = new BlackJack();

        //game.initRound();


    }
}
