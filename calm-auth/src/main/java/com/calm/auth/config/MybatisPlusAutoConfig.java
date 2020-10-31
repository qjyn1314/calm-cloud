package com.calm.auth.config;

import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Explain:mtbatis-plus的配置信息
 * </p >
 *
 * @author wangjunming
 * @since 2020-01-17 11:50
 */
@EnableEncryptableProperties
@Configuration
@MapperScan(basePackages = {
        "com.calm.*.persistence.mapper",
}, annotationClass = Repository.class)
public class MybatisPlusAutoConfig {

    /**
     * mybatis-plus分页插件
     *
     * @author wangjunming
     * @since 2020/10/31 22:10
     */
    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        return new PaginationInnerInterceptor();
    }

}
