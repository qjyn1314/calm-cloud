package com.calm.common.exception;

import com.calm.parent.base.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
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
@ControllerAdvice
public class GlobHandleException {
    /**
     * 统一异常
     *
     * @author wangjunming
     * @since 2021/2/12 23:34
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public JsonResult handleGlobleException(Exception e) {
        log.error("系统异常，请检查代码", e);
        return JsonResult.fail("系统异常");
    }

    /**
     * 业务异常
     *
     * @author wangjunming
     * @since 2021/2/12 23:34
     */
    @ResponseBody
    @ExceptionHandler(value = CalmException.class)
    public JsonResult handleHulunBuirException(CalmException e) {
        log.error("业务异常，请检查代码", e);
        return JsonResult.fail(e.getMessage());
    }

    /**
     * 统一处理请求的方法不正确异常
     *
     * @author wangjunming
     * @since 2020/2/12 18:05
     */
    @ExceptionHandler(value = NestedServletException.class)
    public String handleNestedServletException(NestedServletException e) {
        log.error("统一处理请求的方法不正确异常-异常", e);
        return "/error/404";
    }

}
