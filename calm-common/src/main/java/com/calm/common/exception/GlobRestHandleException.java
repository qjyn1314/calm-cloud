package com.calm.common.exception;

import com.calm.parent.base.JsonResult;
import com.netflix.client.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
        return JsonResult.fail("未知异常");
    }

    /**
     * 运行时异常处理
     *
     * @param e
     * @return com.calm.parent.base.JsonResult
     * @author wangjunming
     * @since 2021/3/30 17:31
     */
    @ExceptionHandler(value = RuntimeException.class)
    public JsonResult handleGlobRuntimeException(RuntimeException e) {
        log.error("系统业务异常，请检查代码", e);
        return JsonResult.fail("系统业务异常，请稍后重试。");
    }

    /**
     * 网管请求异常
     *
     * @param e
     * @return com.calm.parent.base.JsonResult
     * @author wangjunming
     * @since 2021/3/30 17:31
     */
    @ExceptionHandler(value = ClientException.class)
    public JsonResult handleGlobRuntimeException(ClientException e) {
        log.error("系统异常，请检查代码", e);
        return JsonResult.fail("网关异常，请稍后重试。");
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

    /**
     * 传参异常，源自于 HibernateValidator
     *
     * @param e
     * @return com.calm.parent.base.JsonResult
     * @author wangjunming
     * @since 2021/3/30 17:33
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public JsonResult handleHulunBuirException(MethodArgumentNotValidException e) {
        log.error("传参异常，请检查效验逻辑", e);
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder sb = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getDefaultMessage());
        }
        return JsonResult.fail(sb.toString());
    }

    @ExceptionHandler(value = InterruptedException.class)
    public JsonResult handleInterruptedException(InterruptedException e) {
        log.error("关闭admin服务异常", e);
        return JsonResult.fail("关闭admin服务异常，请稍后重试。");
    }

    @ExceptionHandler(value = AuthenticationServiceException.class)
    public JsonResult handleAuthenticationServiceException(AuthenticationServiceException e) {
        log.error("用户授权异常", e);
        return JsonResult.fail(e.getMessage());
    }


}
