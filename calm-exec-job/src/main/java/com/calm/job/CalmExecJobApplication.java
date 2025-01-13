package com.calm.job;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@EnableFeignClients("com.calm")
@ComponentScan("com.calm")
@SpringBootApplication
public class CalmExecJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalmExecJobApplication.class, args);
    }

}