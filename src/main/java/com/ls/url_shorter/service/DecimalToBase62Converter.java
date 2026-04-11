package com.ls.url_shorter.service;

public class DecimalToBase62Converter {

    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String convertDecimal(long decimal) {
        if (decimal == 0) return "0";

        StringBuilder sb = new StringBuilder();

        while (decimal > 0) {
            int index = (int) (decimal % 62);
            sb.append(BASE62.charAt(index));
            decimal /= 62;
        }

        return sb.reverse().toString();
    }
}