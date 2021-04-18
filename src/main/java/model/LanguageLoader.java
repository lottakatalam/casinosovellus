package model;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Class for managing the language options
 */
public class LanguageLoader {
    /**
     * The singleton object Language loader.
     * Attribute is null if the object is not yet created
     */
    private static LanguageLoader instance = null;

    /**
     * Represents a specific linguistic region
     * Holds information of the language and country
     * Locale option defines the language which is used in UI
     */
    private static Locale locale;

    /**
     * A property list
     */
    private static Properties textResources;

    /**
     * Method which enables to work with a singleton object Language loader.
     * When called the first time, creates a Language Loader-object.
     * After the object is created, always returns the same exact object.
     * @return Language Loader object
     */
    public static LanguageLoader getInstance() {
        if(instance == null){
            instance = new LanguageLoader();
        }
        return instance;
    }

    /**
     * A private constructor of the singleton object
     * Set's the locale used default to english (United Kingdom)
     */
    private LanguageLoader() {
        setLocale("en", "GB");
    }

    /**
     * Recource bundle contains locale-specific objects
     * Gets a resource bundle using the specified base name and locale
     * @return the recource bundle
     */
    public ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle("TextResources", locale);
    }

    /**
     * Creates a new locale-object and set's the language which is used in UI
     * Loads the textResources where the language options are hold
     * @param language the language wanted
     * @param country the specific country
     */
    public void setLocale(String language, String country) {
        locale = new Locale(language, country);
        getTextResources();
    }


    private void setLocale(Locale locale) {
        LanguageLoader.locale = locale;
        getTextResources();
    }

    public Locale getLocale() {
        return locale;
    }

    /**
     * Creates a new properties object
     * Loads the resources where the language options are hold
     */
    private void getTextResources() {
        textResources = new Properties();
        String TRFile = String.format("TextResources_%s_%s.properties", locale.getLanguage(),locale.getCountry());
        try {
            textResources.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(TRFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Used for all text in the UI. Get's the string needed from the resources
     * @param key the key which holds all available language options for a specific String
     * @return the wanted String in the language which is set in locale
     */
    public String getString(String key) {
        return textResources.getProperty(key);
    }


}
