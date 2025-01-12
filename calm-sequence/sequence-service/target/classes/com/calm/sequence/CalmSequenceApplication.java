package com.calm.sequence;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

@Slf4j
@RefreshScope
@SpringBootApplication
@ComponentScan("com.calm")
@EnableDiscoveryClient
@EnableFeignClients("com.calm")
@MapperScan(basePackages = "com.calm.*.persistence.mapper", annotationClass = Repository.class)
public class CalmSequenceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalmSequenceApplication.class, args);
    }

}
