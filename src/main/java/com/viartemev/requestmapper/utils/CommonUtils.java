package com.viartemev.requestmapper.utils;

public class CommonUtils {

    public static String unquote(String s) {
        return s != null && s.length() >= 2 && s.charAt(0) == '\"'
                ? s.substring(1, s.length() - 1)
                : s;
    }

}
