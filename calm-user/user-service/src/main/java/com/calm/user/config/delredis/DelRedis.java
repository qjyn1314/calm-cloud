package com.calm.user.config.delredis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * explain: 根据注解中的值进行删除redis中的数据
 * </p>
 *
 * @author wangjunming
 * @since 2020/12/18 15:37
 */
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface DelRedis {

    String value();

}
