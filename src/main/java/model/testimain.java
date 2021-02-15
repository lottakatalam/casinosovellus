package model;


public class testimain {
    public static void main(String[] args) {

        Deck d = new Deck();
        Deck b = new Deck();
        b.shuffleDeck();
        System.out.println(d.equals(b));
    }
}
