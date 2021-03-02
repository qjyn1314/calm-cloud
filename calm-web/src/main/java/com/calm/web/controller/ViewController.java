package com.calm.web.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.calm.auth.CurrentSecurityUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * explain: 用于将页面进行回显，跳转至请求路径相应的页面
 * </p>
 *
 * @author wangjunming
 * @since 2020/9/7 12:12
 */
@Controller
public class ViewController {

    @GetMapping({"/"})
    public String index() {
        boolean notLogin = CurrentSecurityUserUtils.isNotLogin();
        if (notLogin) {
            return "login";
        }
        return "main";
    }

    @GetMapping({"/page/**.html", "/page/*/**.html", "/main",})
    public String initView() {
        boolean notLogin = CurrentSecurityUserUtils.isNotLogin();
        if (notLogin) {
            return "login";
        }
        return handleView();
    }

    private final static String HTML_SUFFIX = ".html";
    @Autowired
    private HttpServletRequest request;

    public String handleView() {
        return request.getRequestURI().substring(1).replace(HTML_SUFFIX, "");
    }

    @ResponseBody
    @GetMapping("/authUser")
    public String authUser(){
        return DateUtil.now() + "-->" + JSONObject.toJSONString(CurrentSecurityUserUtils.authUser());
    }

}
