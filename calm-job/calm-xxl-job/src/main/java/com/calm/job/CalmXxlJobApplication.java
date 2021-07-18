package com.calm.job;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@RefreshScope
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@ComponentScan("com.calm")
@EnableDiscoveryClient
@EnableFeignClients("com.calm")
public class CalmXxlJobApplication {

	public static void main(String[] args) {
        SpringApplication.run(CalmXxlJobApplication.class, args);
	}

}