package com.slon_school.helloslon.core;

import android.app.Activity;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by I. Dmitry on 14.07.2016.
 */
public class Helper {
    public static void BTS(Integer id) {
        Toast.makeText(new Activity(), "Упс, ошибочка №" + id.toString() + ". Пожалуйста, сообщи о ней разрабу HelloSlom", Toast.LENGTH_LONG).show();
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

    public interface additionalInterface {
        boolean FINISH_SESSION = false;
        ArrayList<Key> keys = new ArrayList<>();
    }
}
