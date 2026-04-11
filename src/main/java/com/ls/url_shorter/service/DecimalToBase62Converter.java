package com.ls.url_shorter.service;

import io.seruco.encoding.base62.Base62;

public class DecimalToBase62Converter {

    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String convertDecimal(long decimal) {
        StringBuilder stringBuilder = new StringBuilder(1);

        while(decimal%62 != 0) {
            int index = (int)(decimal % 62);
            decimal = decimal / 62;
            char c = BASE62.charAt(index);
            stringBuilder.append(c);
        }
        return stringBuilder.reverse().toString();
    }
}