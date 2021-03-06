package model;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * A class to handling all the user related stuff
 */
public class UserCredentialHandler {

    private static UserCredentialHandler instance = null;

    private static CasinoDAO casinoDAO;

    private static User loggedInUser;

    public boolean isLoggedIn = false;

    /**
     * Error message concerning registration or password change. Is used in the UI when needed
     */
    private String errorMessage;

    /**
     * Constructor of UserCredentialHandler
     */
    private UserCredentialHandler() {

        //casinoDAO = new CasinoDAO();
    }

    /**
     * Instance of UserCredentialHandler
     *
     * @return - The instance
     */
    public static UserCredentialHandler getInstance() {
        if (instance == null) {
            instance = new UserCredentialHandler();
        }
        return instance;
    }


    /**
     * Sets the casino DAO
     *
     *
     */
    private void setCasinoDAO() {
        if (casinoDAO == null) {
            casinoDAO = new CasinoDAO();
        }
    }

    /**
     * Validates the user credentials which user has given in the registration view.
     * Uses three different methods for validating.
     *
     * @param username  - user input for username
     * @param password1 - user input for password
     * @param password2 - user input for repeated password
     * @return true, if the user credentials can be used for registration
     */
    public boolean validateUserCredentials(String username, String password1, String password2) {

        if (!isValidUsername(username)) {
            return false;
        } else if (!isValidPassword(password1)) {
            return false;
        } else if (!passwordsMatch(password1, password2)) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * Creates a new user and adds it to userTable in Database
     *
     * @param username - Username of added user
     * @param password - Password of added user
     *
     */
    public void createNewUser(String username, String password) {
        setCasinoDAO();
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(hashPassword(password));
        newUser.setBalance(2500);
        newUser.setRounds(1);
        casinoDAO.addUserRow(newUser);
    }

    /**
     * Method for validating the username which user has input.
     * Checks if username is unique and if so, checks if the lenght meets the requirements.
     * If username is not usable, sets an errormessage which is then returned to the UI via controller
     *
     * @param username - String which user has given as an input
     * @return true if username is valid for creating a user
     */
    public boolean isValidUsername(String username) {
        setCasinoDAO();
        String message = "";

        User user = casinoDAO.getUserByUsername(username);
        if (user != null) {
            message = LanguageLoader.getInstance().getString("errorUserNameExists");
            System.out.println(message);
            setErrorMessage(message);
            return false;
        }

        if (username.length() > 16 || username.length() < 4) {
            message = LanguageLoader.getInstance().getString("errorUserNameLenght");
            System.out.println(message);
            setErrorMessage(message);
            return false;
        }
        return true;
    }

    /**
     * Method for validating the password which user has input.
     * If password is not usable, sets an errormessage which is then returned to the UI via controller
     *
     * @param password String which user has given as an input
     * @return true id password is valid for creating a user
     */
    public boolean isValidPassword(String password) {
        String message;
        if (password.length() < 6) {
            message = LanguageLoader.getInstance().getString("errorPasswordLenght");
            System.out.println(message);
            setErrorMessage(message);
            return false;
        }
        String digits = "\\d+";
        if (password.matches(digits)) {
            message = LanguageLoader.getInstance().getString("errorPasswordNoAlphabet");;
            System.out.println(message);
            setErrorMessage(message);
            return false;
        }
        String upperCaseChars = "(.*[A-Z].*)";
        if (!password.matches(upperCaseChars)) {
            message = LanguageLoader.getInstance().getString("errorPasswordNoUpperCaseChar");;
            System.out.println(message);
            setErrorMessage(message);
            return false;
        }
        String lowerCaseChars = "(.*[a-z].*)";
        if (!password.matches(lowerCaseChars)) {
            message = LanguageLoader.getInstance().getString("errorPasswordNoLowerCaseChar");;
            System.out.println(message);
            setErrorMessage(message);
            return false;
        }
        String numbers = "(.*[0-9].*)";
        if (!password.matches(numbers)) {
            message = LanguageLoader.getInstance().getString("errorPasswordNoNumberChar");;
            System.out.println(message);
            setErrorMessage(message);
            return false;
        }

        return true;
    }

    /**
     * Method for checking if the passwords user input match with each other.
     * If passwords do not match, sets an errormessage which is then returned to the UI via controller
     *
     * @param password1 String which user has given as an input
     * @param password2 String which user has given as an input when repeating the password
     * @return true if passwords match
     */
    public boolean passwordsMatch(String password1, String password2) {
        if (password1.equals(password2)) {
            return true;
        }
        String message = LanguageLoader.getInstance().getString("errorPasswordNotMatching_register");;
        setErrorMessage(message);
        return false;
    }

    /**
     * Method for setting an errorMessage for the user
     * Method is possibly used in the username/password validation methods
     *
     * @param message String which is used as an errormessage
     */
    public void setErrorMessage(String message) {
        errorMessage = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Checks the user credentials and if correct, logs the user in
     * @param username given username
     * @param password given password
     * @return true, if the login was successful
     */
    public boolean login(String username, String password) {
        setCasinoDAO();
        User user = casinoDAO.getUserByUsername(username);
        if (user != null) {
            if (validatePassword(password, user.getPassword())) {
                loggedInUser = user;
                isLoggedIn = true;
                return true;
            }
        }
        return false;


    }

    /**
     * Validates if
     * 1. the old password given as input matches the users password
     * 2. the new password given as input is valid for use
     * 3. the new password and repeated new password match with one another
     * @param oldPassword  the old password given
     * @param newPassword the new password given
     * @param newPasswordRepeated repeated new password
     * @return true, if all three inputs are valid
     */
    public boolean validatePassWordChange(String oldPassword, String newPassword, String newPasswordRepeated) {
        setCasinoDAO();
        User user = casinoDAO.getUserByUsername(loggedInUser.getUsername());

        if (!validatePassword(oldPassword, user.getPassword())) {
            errorMessage = LanguageLoader.getInstance().getString("errorOldPassword_passwordChange");
            return false;
        } else if (!isValidPassword(newPassword)) {
            return false;
        } else if (!passwordsMatch(newPassword, newPasswordRepeated)) {
            errorMessage = LanguageLoader.getInstance().getString("errorPasswordsNotMatching_passwordChange");
            return false;
        } else {
            return true;
        }


    }

    /**
     * Changes the users password and gives an errormessage if something goes wrong with the change
     * @param newPassword the new password given
     * @return true, if the password was changed successfully
     */
    public boolean changePassword(String newPassword) {
        setCasinoDAO();
        User user = casinoDAO.getUserByUsername(loggedInUser.getUsername());

        user.setPassword(hashPassword(newPassword));
        if (casinoDAO.updateUser(user)) {
            return true;
        } else {
            errorMessage = LanguageLoader.getInstance().getString("errorDBConnection_changePassword");;
            return false;
        }
    }

    /**
     * Method to hash the users' password in the Database
     *
     * @param password - The password to hash
     * @return the hashed password
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
     *
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
     *
     * @param givenPassword - The password
     * @param passwordHash  - Hashed password
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
     *
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
     *
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
     *
     * @return
     */
    public User getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * Checks if the user is logged in
     *
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
