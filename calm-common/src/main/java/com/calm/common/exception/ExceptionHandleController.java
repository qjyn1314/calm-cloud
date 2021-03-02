package com.calm.common.exception;

import com.calm.parent.base.JsonResult;
import com.calm.parent.config.CalmProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * explain: springmvc处理filter中抛出的异常信息
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/25 14:43
 */
@Slf4j
@RestController
public class ExceptionHandleController extends BasicErrorController {
    public ExceptionHandleController() {
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }

    /**
     * 只要filter中发生错误就会返回失败信息，而不是代码信息
     * @param request 当前请求信息
     * @return
     */
    @Override
    @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
//        if(CalmProperties.isAdminService()){
//            return super.error(request);
//        }
        //ErrorAttributeOptions.Include.STACK_TRACE 表示 会将 异常信息且包括堆栈跟踪属性 信息拿到
        Map<String, Object> body = getErrorAttributes(request, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.STACK_TRACE));
        HttpStatus status = getStatus(request);
        log.error("请求发生错误并返回的状态：{}", status);
        log.error("返回的body中的信息是：{}", body);
        String trace = null != body.get("trace") ? body.get("trace").toString() : "";
        if (StringUtils.isNotBlank(trace)) {
            status = HttpStatus.FORBIDDEN;
            return new ResponseEntity<>(JsonResult.fail("系统异常，请稍后再试。").toMap(), status);
        }
        return new ResponseEntity<>(body, status);
    }
}
