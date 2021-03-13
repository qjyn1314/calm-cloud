package com.calm.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@RefreshScope
@EnableAdminServer
@SpringBootApplication
@ComponentScan("com.calm")
@EnableDiscoveryClient
@EnableFeignClients("com.calm")
public class CalmAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalmAdminApplication.class, args);
    }

}