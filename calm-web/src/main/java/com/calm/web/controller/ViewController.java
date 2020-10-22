package com.calm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping({"/index","/"})
    public String view() {
        return "";
    }

}
