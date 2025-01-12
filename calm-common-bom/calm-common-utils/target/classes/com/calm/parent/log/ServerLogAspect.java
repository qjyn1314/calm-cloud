package com.calm.parent.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Order(5)
@Component
public class ServerLogAspect {

    @Pointcut("@annotation(com.calm.parent.log.ServerLog)")
    public void serviceLog() {
    }

    @Before("serviceLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 记录下请求内容
        log.debug("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.debug("ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "ret", pointcut = "serviceLog()")
    public void doAfterReturning(Object ret) {
        // 处理完请求，返回内容
        log.debug("RETURN : " + ret);
    }
}
