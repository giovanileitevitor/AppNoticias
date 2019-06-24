package br.applabbs.appnoticias.Utils;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    /**
     * This is used to check the given URL is valid or not.
     * @param url
     * @return true if url is valid, false otherwise.
     */
    public static boolean isValidUrl(String url) {
        Pattern p = Patterns.WEB_URL;
        Matcher m = p.matcher(url.toLowerCase());
        return m.matches();
    }
}
