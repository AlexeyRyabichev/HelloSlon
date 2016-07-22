package com.slon_school.helloslon.core;

import android.util.Pair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class Helper {
    public static boolean isHttpLink(final String string) {
        return string.matches("http://([a-zA-z0-9-])+\\.([a-zA-Z])+(/|)");
    }

    public static boolean isDecimalNumber(final String string) {
        return string.matches("(\\d)+");
    }

    public static long string2long(final String string) {
        long result = 0;
        long multiple = 1;
        long tmp;
        for (int i = string.length() - 1; i >= 0; --i) {
            tmp = multiple * char2long(string.charAt(i));
            result += tmp;
            multiple *= 10;
        }
        return result;
    }

    public static long char2long(final char ch) {
        return (long) (ch - '0');
    }

    public static Pair<String,Boolean> getStringFromWeb(final String URL_GET, final String FIND_PATTERN, final String CHARSET) throws Exception {
        String line;
        URL url = new URL(URL_GET);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), CHARSET));
        while (true) {
            line = reader.readLine();
            if (line == null) {
                break;
            }
            if (line.contains(FIND_PATTERN)) {
                return new Pair<>(line,true);
            }
        }
        return new Pair<>(null,false);
    }

    public static Pair<ArrayList<String>,Boolean> getStringsFromWeb(final String URL_GET, final String FIND_PATTERN, final String CHARSET) throws Exception {
        String line; //TODO implement this method (or not?)
        ArrayList<String> strings = new ArrayList<>();
        URL url = new URL(URL_GET);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), CHARSET));
        while (true) {
            line = reader.readLine();
            if (line == null) {
                break;
            }
            if (line.contains(FIND_PATTERN)) {
                strings.add(line);
            }
        }
        return strings.size() == 0 ? new Pair<>(strings,false) : new Pair<>(strings,true);
    }

    public interface additionalInterface {
        boolean FINISH_SESSION = false;
    }
}
