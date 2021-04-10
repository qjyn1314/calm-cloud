package com.calm.user.persistence.controller;

import cn.hutool.core.date.DateUtil;
import com.calm.common.auth.CurrentSecurityUserUtils;
import com.calm.common.auth.CurrentUser;
import com.calm.common.exception.CalmException;
import com.calm.core.base.BaseController;
import com.calm.parent.base.JsonResult;
import com.calm.user.api.dto.SysUserDto;
import com.calm.user.api.vo.SysUserVo;
import com.calm.user.persistence.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


/**
 * 用户表(SysUser)表控制层
 *
 * @author wangjunming
 * @since 2021-02-15 17:17:55
 */
@RestController
@RequestMapping("/api/v1/sysUser")
public class SysUserController extends BaseController {

    /**
     * 服务对象
     */
    @Autowired
    private SysUserService service;

    private void handleUserDto(SysUserDto sysUserDto) {
        CurrentUser currentUser = CurrentSecurityUserUtils.authUser();
        Date date = DateUtil.date();
        sysUserDto.setCreateTime(date);
        sysUserDto.setUpdateTime(date);
        sysUserDto.setCreateUser(currentUser.getUserId());
        sysUserDto.setUpdateUser(currentUser.getUserId());
    }

    /**
     * 用户分页列表
     *
     * @param sysUserDto 前端传参
     * @author wangjunming
     * @since 2021/4/8 14:09
     * @return com.calm.parent.base.JsonResult
     */
    @GetMapping("/page")
    public JsonResult page(SysUserDto sysUserDto) {
        handleUserDto(sysUserDto);
        return JsonResult.success(service.page(sysUserDto));
    }

    /**
     * 保存用户信息
     *
     * @param sysUserDto 前端传参
     * @author wangjunming
     * @since 2021/4/9 15:09
     * @return com.calm.parent.base.JsonResult
     */
    @PostMapping("/save")
    public JsonResult save(@RequestBody @Validated SysUserDto sysUserDto) {
        handleUserDto(sysUserDto);
        validateUserDto(sysUserDto, SAVE);
        Long id = service.save(sysUserDto);
        return JsonResult.success(id);
    }

    private void validateUserDto(SysUserDto sysUserDto, Integer optionType) {
        String account = sysUserDto.getAccount();
        SysUserVo sysUserVo = service.queryByAccount(account);
        if (SAVE.equals(optionType)) {
            if (null != sysUserVo) {
                throw new CalmException("该登录邮箱已存在。");
            }
        }
    }

}