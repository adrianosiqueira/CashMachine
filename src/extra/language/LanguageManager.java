package extra.language;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageManager {
    private static final String BASE_NAME = "extra/language/language";

    private static ResourceBundle bundle = ResourceBundle.getBundle(BASE_NAME, Locale.getDefault());

    public static String get(String key) {
        return bundle.getString(key);
    }

    public static void update(Locale locale) {
        bundle = ResourceBundle.getBundle(BASE_NAME, locale);
    }
}
