package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GenericGenerator(name = "userid", strategy = "increment")
    @GeneratedValue(generator = "userid")
    private int userID;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private int balance;


    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
        //UserCredentialHandler.getInstance().getCasinoDAO().updateBalance(this);
        CasinoDAO casinoDAO = new CasinoDAO();
        casinoDAO.updateBalance(this);
    }
}