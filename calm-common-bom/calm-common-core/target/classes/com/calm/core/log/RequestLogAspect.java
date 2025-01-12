package com.calm.core.log;

import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Enumeration;

/**
 * 2019/1/9
 * 创建人：Wjunming
 */
@Slf4j
@Aspect
@Component
public class RequestLogAspect {

    /**
     * 定义切入点，切入点为com.example.aop下的所有函数com.example.jack.controller
     */
    @Pointcut("@annotation(com.calm.core.log.ReqLog)")
    public void webLog() {
    }

    /**
     * 定义前置通知，拿到前端请求的的参数日志
     * 切入点表达式：
     * <p>
     * 参考：
     * http://www.manongjc.com/detail/7-bvryvfphgqlbijl.html
     * <p>
     * https://blog.csdn.net/xiao190128/article/details/82181769
     * <p>
     * https://blog.csdn.net/weixin_39986856/article/details/82657750
     *
     * @author wangjunming
     * @since 2020/12/25 11:22
     */
    @Before("webLog() && @annotation(reqLog)")
    public void before(JoinPoint joinPoint, ReqLog reqLog) {

        log.info("reqLog-->{}", reqLog.value());
        //获取方法名
        String name = joinPoint.getSignature().getName();
        //获取参数列表
        log.info("进入的方法名：-->" + name + "->的参数:--" + JSONUtil.toJsonStr(joinPoint.getArgs()));
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        final HttpServletRequest request = attributes.getRequest();
        log.info("URL：--" + request.getRequestURI());
        log.info("HTTP_METHOD:--" + request.getMethod());
        log.info("IP：--" + request.getRemoteAddr());
        Enumeration<String> parameter = request.getParameterNames();
        while (parameter.hasMoreElements()) {
            String element = parameter.nextElement();
            log.info("key:{" + element + "},value:{" + request.getParameter(element) + "}");
        }
    }

    @After("webLog()")
    public void after(JoinPoint joinPoint) {
        //获取方法名
        String name = joinPoint.getSignature().getName();
        log.info("{}-->方法结束", name);
    }

    @AfterReturning(returning = "result", pointcut = "webLog()")
    public void afterReturn(JoinPoint joinPoint, Object result) {
        //获取方法名
        String name = joinPoint.getSignature().getName();
        log.info(name + "方法的结果是" + result);
        //获取参数列表
        String args = Arrays.toString(joinPoint.getArgs());
        log.info("进入的方法名：--" + name + "的参数:--" + args);
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("URL：--" + request.getRequestURI());
        log.info("HTTP_METHOD:--" + request.getMethod());
        log.info("IP：--" + request.getRemoteAddr());
        Enumeration<String> parameter = request.getParameterNames();
        while (parameter.hasMoreElements()) {
            String element = parameter.nextElement();
            log.info("key:{" + element + "},value:{" + request.getParameter(element) + "}");
        }
        try {
            log.info("执行过后返回的结果是：--“{}”", JSONUtil.toJsonStr(result));
        } catch (Exception e) {
            log.error("打印结果异常->", e);
        }
    }

    @AfterThrowing(value = "webLog()", throwing = "ex", pointcut = "webLog()")
    public void afterThrowing(JoinPoint joinPoint, Exception ex) {
        //获取方法名
        String name = joinPoint.getSignature().getName();
        log.error("方法-“{}”，抛出异常：", name, ex);
    }
}
