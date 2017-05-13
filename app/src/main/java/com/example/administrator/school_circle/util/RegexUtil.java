package com.example.administrator.school_circle.util;

import java.util.regex.Pattern;

/**
 * Created by BigGod on 2017-05-06.
 */
public class RegexUtil {
    public static final String PASSWORD_REGEX = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
    public static final String ACCOUNT_REGEX = "^[a-zA-z][a-zA-Z0-9_]{2,9}$";
    public static final String EMAIL_REGEX="^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    public static final String TELEPHONE_REGEX="^1(3[0-9]|4[57]|5[0-35-9]|7[01678]|8[0-9])\\\\d{8}$";

    public static boolean strCorrect(String str,String regex) {
        Pattern pattern = Pattern.compile(regex);

        return pattern.matcher(str).find();
    }

}
