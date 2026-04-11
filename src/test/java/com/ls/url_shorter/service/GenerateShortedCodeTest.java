package com.ls.url_shorter.service;

import io.lettuce.core.RedisCommandExecutionException;
import io.lettuce.core.RedisLoadingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class GenerateShortedCodeTest {

    @Mock
    private StringRedisTemplate stringRedisTemplate;
    @Mock
    private ValueOperations<String, String> valueOperations;

    @InjectMocks
    private GenerateShortedCode generateShortedCode;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void shouldThrowErrorIfRedisDoNotReturnAnyValue() {

        when(valueOperations.get(any())).thenReturn(null);

        assertThrows(RedisLoadingException.class, () -> {
           generateShortedCode.generateShortCode();
        });

    }

    @Test
    void shouldGenerateShortedCodeWithDecimalId() {

        when(valueOperations.get(any())).thenReturn("14000000");
        when(valueOperations.increment(any())).thenReturn(14000001L);

        String shortedCode = generateShortedCode.generateShortCode();

        verify(valueOperations, atLeastOnce()).increment("decimalId");

        assertEquals("wk2S", shortedCode);

    }

    @Test
    void shouldThrowErrorIfNotIncrementedDecimalId() {

        when(valueOperations.get(any())).thenReturn("14000000");
        when(valueOperations.increment(any())).thenReturn(14000000L);

        assertThrows(RedisCommandExecutionException.class, ()->generateShortedCode.generateShortCode());


    }
}