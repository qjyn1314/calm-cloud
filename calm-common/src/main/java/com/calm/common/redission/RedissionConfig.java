package com.calm.common.redission;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RedissionConfig {

    @Autowired
    private RedisProperties redisProperties;

    /**
     * 单机模式自动装配
     */
    @Bean
    public RedissonClient redissonSingle() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379")
                .setTimeout(5000)
                .setConnectionPoolSize(3)
                .setConnectionMinimumIdleSize(1);
        RedissonClient redissonClient = Redisson.create(config);
        log.info("RedissonClient-初始化成功后：{}",redissonClient);
        return redissonClient;
    }

}
