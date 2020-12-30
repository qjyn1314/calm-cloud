package com.calm.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

//开启nacos的动态配置文件的刷新功能
@RefreshScope
//扫描包中所配置的bean
@ComponentScan("com.calm")
@SpringBootApplication
//开启nacos服务注册
@EnableDiscoveryClient
//开启feign服务之间的调用，进行扫描包
@EnableFeignClients("com.calm.provider")
public class CalmGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalmGatewayApplication.class, args);
    }

}
