package com.calm.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.calm")
@SpringBootApplication
//开启nacos服务注册
@EnableDiscoveryClient
//开启feign服务之间的调用
@EnableFeignClients("com.calm.provider")
public class CalmOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalmOrderApplication.class, args);
    }

}
