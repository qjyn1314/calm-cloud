package com.calm.admin.controller;

import com.netflix.client.config.IClientConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 开发测试
 *
 * @author wangjunming@zhichubao.com 2021/9/27 17:51
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("/threadServiceSayHello")
    public String threadServiceSayHello(@RequestParam String username) throws ExecutionException, InterruptedException {
        final CompletableFuture<String> completableFuture = sayHelloAndReturn(username);
        final String re = completableFuture.get();
        log.info("re:{}", re);
        try {
            Thread.sleep(20000);
        } catch (Exception e) {
            log.error("shuimianchaoshi......",e);
        }
        return re;
    }

    @Async(value = "scheduleTaskExecutor")
    public CompletableFuture<String> sayHelloAndReturn(String name) {
        String res = name + "：Say : Hello World!!! ~~";
        log.info(res);
        final AsyncResult<String> asyncResult = new AsyncResult<>(res);
        String asyncResultStr = "";
        try {
            asyncResultStr = asyncResult.get();
        } catch (Exception e) {
            log.error("获取线程池中的结果：{}",asyncResultStr);
        }
        return CompletableFuture.completedFuture(asyncResultStr);
    }


}
