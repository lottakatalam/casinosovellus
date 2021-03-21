package model;

import view.RegisterController;

/**
 * Class for testing only!
 */
public class testimain {
    public static void main(String[] args) {
        Logger.setLogLevel(Logger.LogLevel.ALL);
        UserCredentialHandler UModel = UserCredentialHandler.getInstance();
        System.out.println(UModel.getUserController());

    }

}
