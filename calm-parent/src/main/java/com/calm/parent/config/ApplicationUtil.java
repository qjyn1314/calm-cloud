package com.calm.parent.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * explain: spring中的工具类，用于获取ioc容器中所创建的bean,
 * </p>
 *
 * @author wangjunming
 * @since 2020/8/27 10:39
 */
@Configuration
public class ApplicationUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public ApplicationUtil() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        assertApplicationContext();
        return applicationContext;
    }

    public static <T> T getBean(String beanName) {
        assertApplicationContext();
        return (T) applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> requiredType) {
        assertApplicationContext();
        return applicationContext.getBean(requiredType);
    }

    public static void autowireBean(Object bean) {
        applicationContext.getAutowireCapableBeanFactory().autowireBean(bean);
    }

    private static void assertApplicationContext() {
        if (applicationContext == null) {
            throw new RuntimeException("applicaitonContext属性为null,请检查是否注入了SpringContextHolder!");
        }
    }


}
