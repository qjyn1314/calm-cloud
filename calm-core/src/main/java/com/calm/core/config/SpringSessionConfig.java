package com.calm.core.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * <p>
 * explain:  springboot+springSession+redis登录 已实现session共享
 *
 * </p>
 *
 * @author wangjunming
 * @since 2020/12/30 21:01
 */
@EnableCaching
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 300)
@Configuration
public class SpringSessionConfig {

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        cookieSerializer.setCookieName("clam");
        cookieSerializer.setCookieMaxAge(300);
        cookieSerializer.setDomainName("hulunbuir.com");
        return cookieSerializer;
    }

    @Bean
    public ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }

}