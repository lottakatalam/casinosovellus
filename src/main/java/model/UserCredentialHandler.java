package model;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class UserCredentialHandler {

    private static UserCredentialHandler instance = null;

    private CasinoDAO casinoDAO;

    private static User loggedInUser;

    public boolean isLoggedIn = false;


    private UserCredentialHandler() {
        casinoDAO = new CasinoDAO();
    }

    public static UserCredentialHandler getInstance() {
        if (instance == null) {
            instance = new UserCredentialHandler();
        }
        return instance;
    }

    public void setCasinoDAO(CasinoDAO casinoDAO) {
        this.casinoDAO = casinoDAO;
    }


    public boolean login(String username, String password) {
        User user = casinoDAO.getUserByUsername(username);
        if (user != null ) {
            if (validatePassword(password, user.getPassword())) {
                //user.setPassword("");
                loggedInUser = user;
                isLoggedIn = true;
                return true;
            }
        }
            return false;


    }


    public boolean createNewUser(String username, String password) {

        User newUser = new User();
        newUser.setUserName(username);
        newUser.setPassword(hashPassword(password));
        newUser.setBalance(2500);

        casinoDAO.addUserRow(newUser);


        return true;
    }

    private String hashPassword(String pasword) {
        int iterations = 1000;
        char[] chars = pasword.toCharArray();
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

    private byte[] fromHex(String hex) {

        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }
}
