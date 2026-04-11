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
        Long id = redisTemplate.opsForValue().increment(REDIS_KEY);

        if (id == null) {
            throw new RedisCommandExecutionException("Error incrementing id");
        }
        String shortCode = DecimalToBase62Converter.convertDecimal(id);
        LOG.info("[INFO] id={} shortCode={}", id, shortCode);
        return shortCode;
    }
}