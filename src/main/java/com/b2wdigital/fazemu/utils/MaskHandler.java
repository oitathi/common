package com.b2wdigital.fazemu.utils;

public class MaskHandler {

    private static String strToNumber(String str) {
        str = str.replaceAll("\\D", "");
        return str;
    }

    public static long strToLong(String str) {
        return Long.parseLong(strToNumber(str));
    }

    public static int strToInt(String str) {
        return Integer.parseInt(strToNumber(str));
    }

}
