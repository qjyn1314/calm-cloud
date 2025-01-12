package com.calm.user;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
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
public class CalmUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalmUserApplication.class, args);
        DynamicRoutingDataSource bean = SpringUtil.getBean(DynamicRoutingDataSource.class);
        log.info("当前SpringIoc中的动态数据源有：{},", bean.getGroupDataSources().keySet());
    }

}
