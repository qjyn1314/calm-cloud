package com.calm.core.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <p>
 * Explain:mtbatis-plus的配置信息
 * </p >
 *
 * @author wangjunming
 * @since 2020-01-17 11:50
 */
@Slf4j
@Configuration
//开启密文密码
@EnableEncryptableProperties
//扫描项目中的mapper接口类
@MapperScan(basePackages = {
        "com.calm.*.persistence.mapper",
}, annotationClass = Repository.class)
//开启数据库事物
@EnableTransactionManagement
public class MybatisPlusAutoConfig {

    /**
     * 配置mybatis插件
     *
     * @author wangjunming
     * @since 2020/10/28 17:31
     */
    @Bean
    public MybatisPlusInterceptor plusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        //悲观锁
        mybatisPlusInterceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        //分页配置
        mybatisPlusInterceptor.addInnerInterceptor(paginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }

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