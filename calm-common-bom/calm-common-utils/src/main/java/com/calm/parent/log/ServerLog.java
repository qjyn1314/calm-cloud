package com.calm.parent.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * explain: service层的日志注解
 * </p>
 *
 * @author wangjunming
 * @since 2020/12/18 15:37
 */
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ServerLog {

    String value() default "";

}
