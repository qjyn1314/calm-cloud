package com.calm.common.exception;

import com.calm.parent.base.JsonResult;
import com.netflix.client.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.NestedServletException;

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
public class GlobRestHandleException {
    /**
     * 统一异常
     *
     * @author wangjunming
     * @since 2021/2/12 23:34
     */
    @ExceptionHandler(value = Exception.class)
    public JsonResult handleGlobException(Exception e) {
        log.error("系统异常，请检查代码", e);
        return JsonResult.fail("系统异常");
    }

    @ExceptionHandler(value = RuntimeException.class)
    public JsonResult handleGlobRuntimeException(RuntimeException e) {
        log.error("系统异常，请检查代码", e);
        return JsonResult.fail("系统异常");
    }

    @ExceptionHandler(value = ClientException.class)
    public JsonResult handleGlobRuntimeException(ClientException e) {
        log.error("系统异常，请检查代码", e);
        return JsonResult.fail("系统异常");
    }

    /**
     * 业务异常
     *
     * @author wangjunming
     * @since 2021/2/12 23:34
     */
    @ExceptionHandler(value = CalmException.class)
    public JsonResult handleHulunBuirException(CalmException e) {
        log.error("业务异常，请检查代码", e);
        return JsonResult.fail(e.getMessage());
    }


}
