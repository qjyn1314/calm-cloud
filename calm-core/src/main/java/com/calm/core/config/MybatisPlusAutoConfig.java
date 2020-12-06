package com.calm.core.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * <p>
 * Explain:mtbatis-plus的配置信息
 * </p >
 *
 * @author wangjunming
 * @since 2020-01-17 11:50
 */
//开启密文密码
@EnableEncryptableProperties
@Configuration
//扫描项目中的mapper接口类
@MapperScan(basePackages = {
        "com.calm.*.persistence.mapper",
}, annotationClass = Repository.class)
//开启数据库事物
@EnableTransactionManagement
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

//    /**
//     * 在classpath后面的 * 必不可少，缺少型号的话后面的通配符不起作用。**表示可以表示任意多级目录。
//     * 用于寻找mapper类所对应的xml文件，
//     *
//     * @since 2020/10/28 17:38
//     */
//    private static final String COMMON_MAPPER_LOCATION = "classpath*:com/calm/**/*/mapper/xml/*Mapper.xml";
//
//    /**
//     * 手动配置mybatis-plus的数据源事务管理配置
//     *
//     * @author wangjunming
//     * @since 2020/10/28 15:47
//     */
//    @Bean
//    public MybatisSqlSessionFactoryBean mysqlSessionFactory(DataSource dataSource) throws Exception {
//        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource);
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        Resource[] resources = resolver.getResources(COMMON_MAPPER_LOCATION);
//        sqlSessionFactoryBean.setMapperLocations(resources);
//        // 解决此异常：org.springframework.dao.TransientDataAccessResourceException: SqlSessionFactory must be using a SpringManagedTransactionFactory in order to use Spring transaction synchronization
//        //必须使用  SpringManagedTransactionFactory 事务管理器
//        sqlSessionFactoryBean.setTransactionFactory(new SpringManagedTransactionFactory());
//        sqlSessionFactoryBean.setPlugins(plusInterceptor());
//        return sqlSessionFactoryBean;
//    }

    /**
     * 配置mybatis插件
     *
     * @author wangjunming
     * @since 2020/10/28 17:31
     */
    @Bean
    public MybatisPlusInterceptor plusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        //乐观锁
        mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        //分页配置
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }

}