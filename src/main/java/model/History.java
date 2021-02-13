package model;

public class History {

    private int gameNumber;
    private String result;
    private String method;
    private int bet;
    private int balance;


    public History(int gameNumber, String result, int bet, int balance) {
        this.gameNumber = gameNumber;
        this.result = result;
        this.bet = bet;
        this.balance = balance;
    }

    //GETTERS

    public int getGameNumber() {
        return gameNumber;
    }

    public String getResult() {
        return result;
    }

    public String getMethod() {
        return method;
    }

    public int getBet() {
        return bet;
    }

    public int getBalance() {
        return balance;
    }

    //SETTERS
    public void setGameNumber(int gameNumber) {
        this.gameNumber = gameNumber;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
