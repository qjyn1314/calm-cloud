package com.calm.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.NestedServletException;

/**
 * <p>
 * explain:
 * </p>
 *
 * @author wangjunming
 * @since 2021/4/17 21:55
 */
@Slf4j
@Controller
public class ErrorController {

    @GetMapping("/error")
    public String handleNestedServletException(NestedServletException e) {
        log.error("统一处理请求的方法不正确异常-异常", e);
        return "/error/404";
    }

}
