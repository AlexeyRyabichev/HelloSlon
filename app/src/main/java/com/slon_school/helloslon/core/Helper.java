package com.slon_school.helloslon.core;

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
}
