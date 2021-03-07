package com.calm.web.controller;

import com.calm.auth.CurrentSecurityUserUtils;
import com.calm.auth.entity.CurrentUser;
import com.calm.parent.base.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ConcurrentHashMap;

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


    /**
     * 获取当前登录用户信息、权限
     */
    @ResponseBody
    @GetMapping("/currentUser")
    public JsonResult currentUser(){
        CurrentUser currentUser = CurrentSecurityUserUtils.authUser().noPwd();
        final ConcurrentHashMap<String,Object> map = new ConcurrentHashMap<>(8);
        //当前登录用户信息
        map.put("userInfo",currentUser);
        //logo文案
        map.put("logoText","Calm-Web");
        //所属权限
        return JsonResult.success(map);
    }

}
