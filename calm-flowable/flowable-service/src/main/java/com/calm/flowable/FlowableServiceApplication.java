package com.calm.flowable;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@RefreshScope
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class,proxyBeanMethods = false)
@ComponentScan("com.calm")
@EnableDiscoveryClient
@EnableFeignClients("com.calm")
public class FlowableServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowableServiceApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner init(final RepositoryService repositoryService,
//                                  final RuntimeService runtimeService,
//                                  final TaskService taskService) {
//
//        return strings -> {
//            System.out.println("Number of process definitions : "
//                    + repositoryService.createProcessDefinitionQuery().count());
//            System.out.println("Number of tasks : " + taskService.createTaskQuery().count());
//            runtimeService.startProcessInstanceByKey("oneTaskProcess");
//            System.out.println("Number of tasks after process start: "
//                    + taskService.createTaskQuery().count());
//        };
//    }

}
