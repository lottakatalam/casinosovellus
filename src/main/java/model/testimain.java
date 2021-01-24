package model;

public class testimain {
    public static void main(String[] args) {
        Deck deck = new Deck();
        System.out.println(deck.getDeck());
        deck.shuffleDeck();
        System.out.println(deck.getDeck());
        deck.nextCard();
        System.out.println(deck.getDeck());

    }
}
