package com.calm.redis;

import com.calm.redis.config.RedisAutoConfig;
import com.calm.redis.service.RedisHelper;
import com.calm.redis.service.impl.RedisHelperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;


public class CalmRedisAutoConfig {


    @Bean
    public RedisAutoConfig redisAutoConfig() {
        return new RedisAutoConfig();
    }

    @Bean
    public RedisHelper redisHelper(RedisTemplate redisTemplate) {
        return new RedisHelperImpl(redisTemplate);
    }


}
