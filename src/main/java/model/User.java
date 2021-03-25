package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "users")
/**
 * A class for userTable in Database
 */
public class User {

    @Id
    @GenericGenerator(name = "userid", strategy = "increment")
    @GeneratedValue(generator = "userid")
    /**
     * userID column in userTable
     */
    private int userID;

    @Column
    /**
     * username column in userTable
     */
    private String username;

    @Column
    /**
     * password column in userTable
     */
    private String password;

    @Column
    /**
     * balance column in userTable
     */
    private int balance;

    @Column
    /**
     * rounds column in userTable
     */
    private Integer rounds;

    @Column
    /**
     * rounds ranking in userTable
     */
    private Integer ranking;


    /**
     * Gets the userID
     * @return - The userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Sets the userID
     * @param userID - the userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Gets the username
     * @return - The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username
     * @param userName - The username
     */
    public void setUsername(String userName) {
        this.username = userName;
    }

    /**
     * Gets the password
     * @return - The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password
     * @param password - The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the balance
     * @return - The balance
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Sets the balance
     * @param balance - The balance
     */
    public void setBalance(int balance) {
        this.balance = balance;

    }

    /**
     * Gets the rounds
     * @return - The rounds
     */
    public Integer getRounds() {
        return rounds;
    }

    /**
     * Sets the rounds
     * @param rounds - The rounds
     */
    public void setRounds(Integer rounds) {
        this.rounds = rounds;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public void addRound() {
        this.rounds++;
    }
}