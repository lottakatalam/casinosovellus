package model;


public class testimain {
    public static void main(String[] args) {
        Logger.setLogLevel(Logger.LogLevel.ALL);
        Player p = new Player(2500);
        System.out.println(p.getHand().getNumberOfCards());
        System.out.println(p.getHand());
    }
}
