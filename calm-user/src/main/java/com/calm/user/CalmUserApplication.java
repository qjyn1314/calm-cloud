package com.calm.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@RefreshScope
@SpringBootApplication
@ComponentScan("com.calm")
@EnableDiscoveryClient
@EnableFeignClients("com.calm.provider")
public class CalmUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalmUserApplication.class, args);
    }

}
