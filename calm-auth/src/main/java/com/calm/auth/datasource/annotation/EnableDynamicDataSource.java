package com.calm.auth.datasource.annotation;

import com.calm.auth.datasource.DynamicDataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启动态数据源注解，在需要数据源的服务中，在启动类中添加此注解，即可开启数据源
 *
 * @author wangjunming
 * @since 2020/10/31 20:17
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(DynamicDataSourceAutoConfiguration.class)
public @interface EnableDynamicDataSource {

}
