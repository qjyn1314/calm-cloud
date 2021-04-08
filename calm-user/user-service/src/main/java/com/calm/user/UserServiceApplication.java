package com.calm.user;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.calm.parent.config.ApplicationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import javax.sql.DataSource;
import java.util.Map;

@Slf4j
@RefreshScope
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@ComponentScan("com.calm")
@EnableDiscoveryClient
@EnableFeignClients("com.calm")
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
        final Map<String, DataSource> beforeCurrentDataSources = ApplicationUtil.getBean(DynamicRoutingDataSource.class).getCurrentDataSources();
        log.info("当前SpringIoc中的动态数据源有：{},",beforeCurrentDataSources);
    }

}
