package com.calm.core.exception;

import com.calm.parent.base.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <p>
 * explain:异常处理公共类
 * </p>
 *
 * @author wangjunming
 * @since 2020/11/14 10:43
 */
@Slf4j
@RestControllerAdvice
public class GlobHandleException {

    @ExceptionHandler(value = CalmException.class)
    public JsonResult handleHulunBuirException(CalmException e) {
        log.error("业务异常，请检查代码", e);
        return JsonResult.fail(e.getMessage());
    }

}
