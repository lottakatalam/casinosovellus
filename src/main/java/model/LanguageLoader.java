package model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class LanguageLoader {

    private static LanguageLoader instance = null;

    private static Locale locale;

    private static Properties textResources;

    public static LanguageLoader getInstance() {
        if(instance == null){
            instance = new LanguageLoader();
        }
        return instance;
    }

    private LanguageLoader() {
        setLocale(Locale.getDefault());
    }

    public ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle("TextResources", locale);
    }

    public void setLocale(String language, String country) {
        locale = new Locale(language, country);
        getTextResources();
    }

    private void setLocale(Locale locale) {
        this.locale = locale;
        getTextResources();
    }

    public Locale getLocale() {
        return locale;
    }

    private void getTextResources() {
        textResources = new Properties();
        String TRFile = String.format("TextResources_%s_%s.properties", locale.getLanguage(),locale.getCountry());
        try {
            textResources.load(getClass().getClassLoader().getResourceAsStream(TRFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getString(String key) {
        return textResources.getProperty(key);
    }


}
