package com.slon_school.helloslon.core;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by I. Dmitry on 14.07.2016.
 */
public class Helper {

    public static boolean isValidMobilePhoneNumber(String mobileNumber) {
        mobileNumber = deleteUnwantedCharacters("[\\(- \\)]",mobileNumber);
        //final String mobileNumberRegex = "\\d\\((\\d){3}\\)(\\d){3}-(\\d){2}-(\\d){2}";
        mobileNumber = mobileNumber.replace(" ","");
        final String mobileNumberRegex = "(\\d){11}";
        return mobileNumber.matches(mobileNumberRegex);
    }

    private static String deleteUnwantedCharacters(String regex, String string) {
        string = string.replaceAll(regex,"");
        return string;
    }

    public static void BTS(Integer id) {
        Toast.makeText(new Activity(), "Упс, ошибочка №" + id.toString() + ". Пожалуйста, сообщи о ней разрабу", Toast.LENGTH_LONG).show();
    }
}
