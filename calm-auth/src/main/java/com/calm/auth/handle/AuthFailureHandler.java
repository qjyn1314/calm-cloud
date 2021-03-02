package com.calm.auth.handle;

import com.calm.common.utils.RequestUtils;
import com.calm.parent.base.JsonResult;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * explain:登录失败处理器
 * </p>
 *
 * @author wangjunming
 * @since 2020/9/21 10:35
 */
@Slf4j
public class AuthFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        log.info("【login fail】" + exception.getMessage());
        RequestUtils.setResponse(response, JsonResult.fail(exception.getMessage()));
    }
}
