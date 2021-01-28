package model;

import java.util.Scanner;

public class testimain {
    public static void main(String[] args) {
        Logger.setLogLevel(Logger.LogLevel.ALL);
        BlackJack game = new BlackJack();

        game.initRound();
        System.out.println("1. hit, 2. stay");
        Scanner scanner = new Scanner(System.in);

        if (Integer.parseInt(scanner.nextLine()) == 1) {
            game.playerHit();
        } else if (Integer.parseInt(scanner.nextLine()) == 2) {
            game.playerStay();
        }

    }
}
