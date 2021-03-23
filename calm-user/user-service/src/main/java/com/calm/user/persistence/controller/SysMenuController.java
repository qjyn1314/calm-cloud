package com.calm.user.persistence.controller;

import com.calm.common.auth.CurrentSecurityUserUtils;
import com.calm.common.auth.CurrentUser;
import com.calm.core.base.BaseController;
import com.calm.parent.base.JsonResult;
import com.calm.user.api.dto.SysMenuDto;
import com.calm.user.persistence.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单表(SysMenu)表控制层
 *
 * @author wangjunming
 * @since 2021-03-13 21:55:51
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/sysMenu")
public class SysMenuController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("/page")
    public JsonResult page(SysMenuDto sysMenuDto) {
        CurrentUser currentUser = CurrentSecurityUserUtils.authUser();
        return JsonResult.success(sysMenuService.page(sysMenuDto));
    }

    @PostMapping("/save")
    public JsonResult save() {
        CurrentUser currentUser = CurrentSecurityUserUtils.authUser();
        log.info("当前登录用户信息是：{}", currentUser);
        return JsonResult.success();
    }

}