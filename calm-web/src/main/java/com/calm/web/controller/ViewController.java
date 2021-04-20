package com.calm.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.calm.common.auth.CurrentSecurityUserUtils;
import com.calm.common.auth.CurrentUser;
import com.calm.common.auth.MenuTree;
import com.calm.parent.base.JsonResult;
import com.calm.user.api.vo.SysUserVo;
import com.calm.web.consumer.WebConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * explain: 用于将页面进行回显，跳转至请求路径相应的页面
 * </p>
 *
 * @author wangjunming
 * @since 2020/9/7 12:12
 */
@Slf4j
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
    @Resource
    private HttpServletRequest httpServletRequest;

    public String handleView() {
        return httpServletRequest.getRequestURI().substring(1).replace(HTML_SUFFIX, "");
    }

    @Autowired
    private WebConsumer webConsumer;

    /**
     * 获取当前登录用户信息
     */
    @ResponseBody
    @GetMapping("/currentUser")
    public JsonResult currentUser(){
        Map<String,Object> map = new HashMap<>(2);
        String code = CurrentSecurityUserUtils.authUser().getCode();
        SysUserVo sysUserVo = webConsumer.selectUserByCode(code);
        //当前登录用户信息
        map.put("userInfo",null != sysUserVo ? sysUserVo : new SysUserVo());
        //logo文案
        map.put("logoText","Calm-Web");
        return JsonResult.success(map);
    }

    /**
     * 获取当前登录用户的菜单
     */
    @ResponseBody
    @GetMapping("/currentUserPermission")
    public List<MenuTree> currentUserPermission(){
        String code = CurrentSecurityUserUtils.authUser().getCode();
        return webConsumer.selectUserByCode(code).getMenuTree();
    }

}
