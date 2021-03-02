package com.calm.auth.handle;

import com.calm.common.utils.RequestUtils;
import com.calm.parent.base.JsonResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * explain: 未登录拦截器和未授权拦截器
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/23 21:06
 */
public class UnAuthenticationEntryPoint implements AuthenticationEntryPoint{

    /**
     * 未登录时访问授权接口
     *
     * @author wangjunming
     * @since 2021/2/23 21:17
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        RequestUtils.setResponse(response, JsonResult.fail("请登录。"));
    }


}
