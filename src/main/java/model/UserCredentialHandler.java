package model;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * A class to handling all the user related stuff
 */
public class UserCredentialHandler {

    private static UserCredentialHandler instance = null;

    private CasinoDAO casinoDAO;

    private static User loggedInUser;

    public boolean isLoggedIn = false;

    /**
     * Constructor of UserCredentialHandler
     */
    private UserCredentialHandler() {
        casinoDAO = new CasinoDAO();
    }

    /**
     * Instance of UserCredentialHandler
     * @return - The instance
     */
    public static UserCredentialHandler getInstance() {
        if (instance == null) {
            instance = new UserCredentialHandler();
        }
        return instance;
    }

    /**
     * Sets the casino Database
     * @param casinoDAO - The Database
     */
    public void setCasinoDAO(CasinoDAO casinoDAO) {
        this.casinoDAO = casinoDAO;
    }


    public boolean login(String username, String password) {
        User user = casinoDAO.getUserByUsername(username);
        if (user != null ) {
            if (validatePassword(password, user.getPassword())) {
                user.setPassword("");
                loggedInUser = user;
                isLoggedIn = true;
                return true;
            }
        }
            return false;


    }

    /**
     * Creates a new user and adds it to userTable in Database
     * @param username - Username of added user
     * @param password - Password of added user
     * @return - True if user creation is successed
     */
    public boolean createNewUser(String username, String password) {
        User newUser = new User();
        newUser.setUserName(username);
        newUser.setPassword(hashPassword(password));
        newUser.setBalance(2500);

        casinoDAO.addUserRow(newUser);


        return true;
    }

    public boolean changePassword(String newPassword, String oldPassword) {

        User user = casinoDAO.getUserByUsername(loggedInUser.getUserName());
        if (user != null ) {
            if (validatePassword(oldPassword, user.getPassword())) {
                user.setPassword(hashPassword(newPassword));
                casinoDAO.updateUser(user);

                return true;
            }
        }


        return false;
    }

    /**
     * Method to hash the users' password in the Database
     * @param password - The password to hash
     * @return
     */
    private String hashPassword(String password) {
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        byte[] hash = null;
        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = secretKeyFactory.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    /**
     * Hashes the password
     * @return - The hashed password
     */
    private byte[] getSalt() {
        SecureRandom secureRandom = null;
        try {
            secureRandom = SecureRandom.getInstance("SHA1PRNG");

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }

    /**
     * Validates the password
     * @param givenPassword - The password
     * @param passwordHash - Hashed password
     * @return
     */
    private boolean validatePassword(String givenPassword, String passwordHash) {
        String[] splitedHash = passwordHash.split(":");

        int iterations = Integer.parseInt(splitedHash[0]);
        byte[] salt = fromHex(splitedHash[1]);
        byte[] hash = fromHex(splitedHash[2]);

        PBEKeySpec spec = new PBEKeySpec(givenPassword.toCharArray(), salt, iterations, hash.length * 8);
        byte[] hashToTest = null;
        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hashToTest = secretKeyFactory.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
        }


        int diff = hash.length ^ hashToTest.length;
        for (int i = 0; i < hash.length && i < hashToTest.length; i++) {
            diff |= hash[i] ^ hashToTest[i];
        }
        return diff == 0;
    }

    /**
     * From string to Hex
     * @param array
     * @return
     */
    private String toHex(byte[] array) {
        BigInteger bigInteger = new BigInteger(1, array);
        String hex = bigInteger.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    /**
     * From Hex to string
     * @param hex
     * @return
     */
    private byte[] fromHex(String hex) {

        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    /**
     * Gets the currently logged in user
     * @return
     */
    public User getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * Checks if the user is logged in
     * @return
     */
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    /**
     * Logs the user out
     */
    public void logout() {
        loggedInUser = null;
        isLoggedIn = false;
    }
}
