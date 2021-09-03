package com.calm.gen.config;

import com.baomidou.dynamic.datasource.creator.DruidDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.druid.DruidConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;

/**
 * <p>
 * explain:
 * </p>
 *
 * @author wangjunming
 * @since 2021/7/17 15:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MybatisGenConfig {

    private DbMessageInfo dbMessageInfo;

    private DataSource genDataSource() {
        DruidConfig druidConfig = new DruidConfig();
        DruidDataSourceCreator druidDataSourceCreator = new DruidDataSourceCreator(druidConfig);
        DataSourceProperty sourceProperty = new DataSourceProperty();
        sourceProperty.setDriverClassName(dbMessageInfo.driverClassName);
        sourceProperty.setUrl(dbMessageInfo.url);
        sourceProperty.setUsername(dbMessageInfo.username);
        sourceProperty.setPassword(dbMessageInfo.password);
        return druidDataSourceCreator.createDataSource(sourceProperty);
    }

    @SafeVarargs
    public final <T> SqlSessionFactory initMybatisSqlSessionFactory(Class<T>... classList) {
        DataSource dataSource = genDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        for (Class<T> mapperClass : classList) {
            configuration.addMapper(mapperClass);
        }
        return new SqlSessionFactoryBuilder().build(configuration);
    }


}
