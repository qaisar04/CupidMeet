package kz.baltabayev.telegrambotservice.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageUtil {

    public static String getMessage(String key, String language) {
        Locale locale = new Locale(language);
        ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages", locale);
        return bundle.getString(key);
    }
}