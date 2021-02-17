package model;


import com.sun.istack.NotNull;
import org.hibernate.annotations.GenericGenerator;

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
    @GenericGenerator(name = "gameid", strategy = "increment")
    @GeneratedValue(generator = "gameid")
    private int gameNumber;

    @ManyToOne
    @JoinColumn(name = "userID")
    @NotNull
    private User user;

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

    public User getPlayerID() {
        return user;
    }

    public void setPlayerID(User user) {
        this.user = user;
    }
}
