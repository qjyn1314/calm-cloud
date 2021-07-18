package com.calm.execute.config;

import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import com.xxl.job.core.handler.IJobHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * xxl-job config
 * @author wangjunming
 * @since 2021/7/18 20:45
 */
@Data
@Slf4j
@Order(99)
@Configuration
public class XxlJobConfig {

    @Value("${xxl.job.admin.addresses}")
    private String adminAddresses;

    @Value("${xxl.job.accessToken}")
    private String accessToken;

    @Value("${xxl.job.executor.appname}")
    private String executorAppname;

    @Value("${xxl.job.executor.address}")
    private String executorAddress;

    @Value("${xxl.job.executor.ip}")
    private String executorIp;

    @Value("${xxl.job.executor.port}")
    private int executorPort;

    @Value("${xxl.job.executor.logpath}")
    private String executorLogPath;

    @Value("${xxl.job.executor.logretentiondays}")
    private int executorLogRetentionDays;


    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        log.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(adminAddresses);
        xxlJobSpringExecutor.setAppname(executorAppname);
        xxlJobSpringExecutor.setAddress(executorAddress);
        xxlJobSpringExecutor.setIp(executorIp);
        xxlJobSpringExecutor.setPort(executorPort);
        xxlJobSpringExecutor.setAccessToken(accessToken);
        xxlJobSpringExecutor.setLogPath(executorLogPath);
        xxlJobSpringExecutor.setLogRetentionDays(executorLogRetentionDays);
        log.info(">>>>>>>>>>> xxl-job config init.success......");
        return xxlJobSpringExecutor;
    }

    /**所有需要执行的任务类*/
    @Autowired
    private List<IJobHandler> jobHandlerList;

    /**
     *
     * 手动将执行器注册到定时任务管理容器中
     *
     * @author wangjunming
     * @since 2021/7/18 20:54
     */
    @PostConstruct
    public void getHandlers() {
        for (IJobHandler jobHandler : jobHandlerList) {
            log.info("所需要处理的定时任务执行器是：{}", jobHandler);
            String name = jobHandler.getClass().getName();
            log.info("所需要处理的定时任务类名称为是：{}", name);
            int lastIndexOf = name.lastIndexOf(".") + 1;
            name = name.substring(lastIndexOf);
            log.info("所需要处理的定时任务类执行名称为是：{}", name);
            XxlJobExecutor.registJobHandler(name, jobHandler);
        }
    }

    /*
     * 针对多网卡、容器内部署等情况，可借助 "spring-cloud-commons" 提供的 "InetUtils" 组件灵活定制注册IP；
     *
     *      1、引入依赖：
     *          <dependency>
     *             <groupId>org.springframework.cloud</groupId>
     *             <artifactId>spring-cloud-commons</artifactId>
     *             <version>${version}</version>
     *         </dependency>
     *
     *      2、配置文件，或者容器启动变量
     *          spring.cloud.inetutils.preferred-networks: 'xxx.xxx.xxx.'
     *
     *      3、获取IP
     *          String ip_ = inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
     */


}