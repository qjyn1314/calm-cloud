package com.calm.order;

import com.calm.core.datasource.annotation.EnableDynamicDataSource;
import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动类
 * @author wangjunming
 * @since 2020/10/31 20:23
 */
@ComponentScan("com.calm")
@SpringBootApplication
//开启nacos服务注册
@EnableDiscoveryClient
//开启feign服务之间的调用
@EnableFeignClients("com.calm.provider")
//开启动态数据源
@EnableDynamicDataSource
//开启动态数据源代理
@EnableAutoDataSourceProxy
public class CalmOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalmOrderApplication.class, args);
    }

}
