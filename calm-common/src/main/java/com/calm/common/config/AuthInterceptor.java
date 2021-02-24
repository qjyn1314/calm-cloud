package com.calm.common.config;

import com.alibaba.fastjson.JSON;
import com.calm.parent.base.JsonResult;
import com.calm.parent.config.CalmProperties;
import com.calm.parent.config.ForwardAccessService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <p>
 * explain:
 * 1、SpringCloud确保服务只能通过gateway转发访问，禁止直接调用接口访问
 * 参考： https://blog.csdn.net/Hpsyche/article/details/102926010
 * </p>
 *
 * @author wangjunming
 * @since 2020/12/30 14:23
 */
@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        log.info("请求路径是：{}", request.getRequestURI());
        log.info("请求的服务是：{}", CalmProperties.getApplicationName());
        if (CalmProperties.isNotWebService()) {
            String forwardAccessHeaderValue = request.getHeader(ForwardAccessService.HEADER_KEY);
            log.info("请求头默认值：{}", forwardAccessHeaderValue);
            if (StringUtils.isBlank(forwardAccessHeaderValue) || !ForwardAccessService.HEADER_VALUE.equals(forwardAccessHeaderValue)) {
                response.setContentType("application/json; charset=utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(JsonResult.fail("网关异常，请确认请求路径。")));
                return false;
            }
        }
        return true;
    }

}
