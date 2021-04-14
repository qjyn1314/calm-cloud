package com.calm.user.persistence.controller;

import cn.hutool.core.date.DateUtil;
import com.calm.common.auth.CurrentSecurityUserUtils;
import com.calm.common.auth.CurrentUser;
import com.calm.common.exception.CalmException;
import com.calm.core.base.BaseController;
import com.calm.parent.base.JsonResult;
import com.calm.user.api.dto.SysUserDto;
import com.calm.user.api.enums.UserStatus;
import com.calm.user.api.vo.SysUserVo;
import com.calm.user.persistence.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
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
     * @return com.calm.parent.base.JsonResult
     * @author wangjunming
     * @since 2021/4/8 14:09
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
     * @return com.calm.parent.base.JsonResult
     * @author wangjunming
     * @since 2021/4/9 15:09
     */
    @PostMapping("/save")
    public JsonResult save(@RequestBody @Validated SysUserDto sysUserDto) {
        handleUserDto(sysUserDto);
        validateUserDto(sysUserDto, SAVE);
        Long id = service.save(sysUserDto);
        return JsonResult.success(id);
    }

    private SysUserVo validateUserDto(SysUserDto sysUserDto, Integer optionType) {
        SysUserVo sysUserVo = null;
        if (SAVE.equals(optionType)) {
            String account = sysUserDto.getAccount();
            sysUserVo = service.queryByAccount(account);
            if (null != sysUserVo) {
                throw new CalmException("该登录邮箱已存在。");
            }
        }
        if (UPDATE.equals(optionType)) {
            String code = sysUserDto.getCode();
            sysUserVo = service.queryByCode(code);
            if (null == sysUserVo) {
                throw new CalmException("未查到该邮箱注册的用户信息。");
            }
        }
        return sysUserVo;
    }

    /**
     * 更新用户信息
     *
     * @param sysUserDto 前端传参
     * @return com.calm.parent.base.JsonResult
     * @author wangjunming
     * @since 2021/4/9 15:09
     */
    @PostMapping("/update")
    public JsonResult update(@RequestBody @Validated SysUserDto sysUserDto) {
        handleUserDto(sysUserDto);
        validateUserDto(sysUserDto, UPDATE);
        return JsonResult.success(service.update(sysUserDto));
    }

    /**
     * 用户审核通过
     *
     * @param code 用户编码
     * @return com.calm.parent.base.JsonResult
     * @author wangjunming
     * @since 2021/4/9 15:09
     */
    @PostMapping("/audit/{code}/{status}")
    public JsonResult audit(@PathVariable String code, @PathVariable Integer status) {
        SysUserDto sysUserDto = new SysUserDto();
        handleUserDto(sysUserDto);
        sysUserDto.setCode(code);
        sysUserDto.setStatus(status);
        if (!UserStatus.THE_APPROVED.getCode().equals(status)) {
            throw new CalmException("参数不正确，请重新传参。");
        }
        SysUserVo sysUserVo = validateUserDto(sysUserDto, UPDATE);
        if (!UserStatus.TO_AUDIT.getCode().equals(sysUserVo.getStatus())) {
            throw new CalmException("请确认该用户的状态为待审核。");
        }
        return JsonResult.success(service.updateStatus(sysUserDto));
    }

    /**
     * 用户停用
     *
     * @param code 用户编码
     * @return com.calm.parent.base.JsonResult
     * @author wangjunming
     * @since 2021/4/9 15:09
     */
    @PostMapping("/disable/{code}/{status}")
    public JsonResult disable(@PathVariable String code, @PathVariable Integer status) {
        SysUserDto sysUserDto = new SysUserDto();
        handleUserDto(sysUserDto);
        sysUserDto.setCode(code);
        sysUserDto.setStatus(status);
        if (!UserStatus.DISABLE.getCode().equals(status)) {
            throw new CalmException("参数不正确，请重新传参。");
        }
        SysUserVo sysUserVo = validateUserDto(sysUserDto, UPDATE);
        if (!UserStatus.THE_APPROVED.getCode().equals(sysUserVo.getStatus())) {
            throw new CalmException("请确认该用户的状态为已审核。");
        }
        return JsonResult.success(service.updateStatus(sysUserDto));
    }

    /**
     * 用户分配角色
     *
     * @param sysUserDto 前端传参
     * @return com.calm.parent.base.JsonResult
     * @author wangjunming
     * @since 2021/4/9 15:09
     */
    @PostMapping("/userDistributionRole")
    public JsonResult userDistributionRole(@RequestBody SysUserDto sysUserDto) {
        if (StringUtils.isBlank(sysUserDto.getCode()) || (null == sysUserDto.getRoleCodes() || sysUserDto.getRoleCodes().length <= 0)) {
            throw new CalmException("请选择新的分配的菜单。");
        }
        SysUserVo sysUserVo = validateUserDto(sysUserDto, UPDATE);
        if (!UserStatus.THE_APPROVED.getCode().equals(sysUserVo.getStatus())) {
            throw new CalmException("待审核用户不可分配菜单。");
        }
        return JsonResult.success(service.userDistributionRole(sysUserDto));
    }

}