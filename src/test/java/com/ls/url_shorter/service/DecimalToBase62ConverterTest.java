package com.ls.url_shorter.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DecimalToBase62ConverterTest {

    @Test
    void shouldConvertDecimalNumberToBase62() {

        long decimal = 11157L;

        String base62 = DecimalToBase62Converter.convertDecimal(decimal);

        assertEquals("2tx", base62);
    }

    @Test
    void shouldConvertABigDecimalNumberToBase62() {

        long decimal = 356000000000L;

        String base62 = DecimalToBase62Converter.convertDecimal(decimal);

        assertEquals("6GaZids", base62, "Converting 356000000000 number");
    }
}