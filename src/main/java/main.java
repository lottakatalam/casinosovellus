import model.*;
public class main {

    public static void main(String[] args) {
        //Deck deck = new Deck();
        //deck.printDeck();
        Logger.setLogLevel(Logger.LogLevel.ALL);
        BlackJack blackJack = new BlackJack();
        //blackJack.printDeckInGame();
        blackJack.initRound();
        blackJack.initRound();
        blackJack.initRound();
    }

}
