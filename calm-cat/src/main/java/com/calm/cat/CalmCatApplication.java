package com.calm.cat;

import com.calm.auth.datasource.annotation.EnableDynamicDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("com.calm")
@EnableDiscoveryClient
@EnableFeignClients("com.calm.provider")
@EnableDynamicDataSource
public class CalmCatApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalmCatApplication.class, args);
    }

}
