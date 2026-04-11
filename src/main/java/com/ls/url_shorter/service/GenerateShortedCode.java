package com.ls.url_shorter.service;

import io.lettuce.core.RedisCommandExecutionException;
import io.lettuce.core.RedisLoadingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class GenerateShortedCode {

    private static final Logger LOG = LoggerFactory.getLogger(GenerateShortedCode.class);
    private static final String REDIS_KEY = "decimalId";

    private final StringRedisTemplate redisTemplate;

    public GenerateShortedCode(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String generateShortCode() {
        String redisId = redisTemplate.opsForValue().get(REDIS_KEY);

        if (redisId == null || redisId.isEmpty()) {
            LOG.error("[ERROR] No value found for decimalId");
            throw new RedisLoadingException("No value found for decimalId");
        }

        long decimal = Long.parseLong(redisId);

        String shortedCode =  DecimalToBase62Converter.convertDecimal(decimal);

        long newValue = redisTemplate.opsForValue().increment(REDIS_KEY);

        if (newValue == decimal) {
            LOG.error("[ERROR] Error incrementing id actual: {} | new: {}", decimal, newValue);
            throw new RedisCommandExecutionException("Error incrementing id");
        }

        return shortedCode;
    }
}