package com.calm.auth.handle;

import com.calm.common.utils.RequestUtils;
import com.calm.parent.base.JsonResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * explain:
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/24 19:31
 */
public class UnAuthenticationHandler implements AccessDeniedHandler {

    /**
     * 登陆成功后，访问未授权接口
     *
     * @author wangjunming
     * @since 2021/2/23 21:17
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        RequestUtils.setResponse(response, JsonResult.fail("未授权"));
    }
}
