package com.ls.url_shorter.config;

import org.apache.juli.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisKeyManagementConfig {

    private static Logger LOG = LoggerFactory.getLogger(RedisKeyManagementConfig.class);

    @Bean
    public ApplicationRunner initRedis(StringRedisTemplate redisTemplate) {
        return args -> {
            Boolean created = redisTemplate.opsForValue()
                    .setIfAbsent("decimalId", "14000000");

            if (Boolean.TRUE.equals(created)) {
               LOG.info("[INFO] Key {} Created", "decimalId");
            } else {
                LOG.info("[INFO] Last value incremented: {}", redisTemplate.opsForValue().get("decimalId"));
            }
        };
    }
}