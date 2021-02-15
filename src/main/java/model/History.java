package model;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="history")
public class History {

    public enum gameResults{
        PLAYERWON,
        PLAYERLOST
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int gameNumber;

    @Column
    private int playerID;

    @Column
    private gameResults result;

    @Column
    private String method;

    @Column
    private int bet;

    @Column
    private int balance;

    @Column
    private Date date;

/*
    public History(int gameNumber, String result, int bet, int balance) {
        this.gameNumber = gameNumber;
        this.result = result;
        this.bet = bet;
        this.balance = balance;
    }
*/
    //GETTERS

    public int getGameNumber() {
        return gameNumber;
    }

    public gameResults getResult() {
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

    public void setResult(gameResults result) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }
}
