import model.CasinoDAO;
import model.LanguageLoader;
import model.Logger;
import view.GUImain;

/**
 * Main class of the app.
 */
public class main {


    /**
     * main function to launch the app
     * @param args program arguments
     */
    public static void main(String[] args) {
        Logger.setLogLevel(Logger.LogLevel.PROD);
        GUImain.main(args);

    }

}
