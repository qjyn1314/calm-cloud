package com.calm.user.persistence.controller;

import cn.hutool.core.date.DateUtil;
import com.calm.common.auth.CurrentSecurityUserUtils;
import com.calm.common.auth.CurrentUser;
import com.calm.common.exception.CalmException;
import com.calm.core.base.BaseController;
import com.calm.parent.base.JsonResult;
import com.calm.user.api.dto.SysRoleDto;
import com.calm.user.api.vo.SysRoleVo;
import com.calm.user.persistence.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * 角色表(SysRole)表控制层
 *
 * @author wangjunming
 * @since 2021-04-04 16:54:56
 */
@RestController
@RequestMapping("/api/v1/sysRole")
public class SysRoleController extends BaseController {

    /**
     * 服务对象
     */
    @Autowired
    private SysRoleService sysRoleService;

    private void handleMenuDto(SysRoleDto sysRoleDto) {
        CurrentUser currentUser = CurrentSecurityUserUtils.authUser();
        Date date = DateUtil.date();
        sysRoleDto.setCreateTime(date);
        sysRoleDto.setUpdateTime(date);
        sysRoleDto.setCreateUser(currentUser.getUserId());
        sysRoleDto.setUpdateUser(currentUser.getUserId());
    }

    /**
     * 角色分页列表
     *
     * @param sysRoleDto 前端传参
     * @return com.calm.parent.base.JsonResult
     * @author wangjunming
     * @since 2021/4/7 19:26
     */
    @GetMapping("/page")
    public JsonResult page(SysRoleDto sysRoleDto) {
        handleMenuDto(sysRoleDto);
        return JsonResult.success(sysRoleService.page(sysRoleDto));
    }

    /**
     * 保存角色信息
     *
     * @param sysRoleDto 前端传参
     * @return com.calm.parent.base.JsonResult
     * @author wangjunming
     * @since 2021/4/7 19:27
     */
    @PostMapping("/save")
    public JsonResult save(@RequestBody @Validated SysRoleDto sysRoleDto) {
        handleMenuDto(sysRoleDto);
        validateRoleDto(sysRoleDto, SAVE);
        Long id = sysRoleService.save(sysRoleDto);
        return JsonResult.success(id);
    }

    private void validateRoleDto(SysRoleDto sysRoleDto, Integer optionType) {
        SysRoleVo sysRoleVo = sysRoleService.selectByCode(sysRoleDto.getCode());
        if (SAVE.equals(optionType)) {
            if (null != sysRoleVo) {
                throw new CalmException("此角色编码已存在，请重新输入。");
            }
        }
        if (UPDATE.equals(optionType)) {
            if (null == sysRoleVo) {
                throw new CalmException("此角色信息不存在，请重新选择需要修改的角色。");
            }
        }
    }

    /**
     * 更新角色信息
     *
     * @param sysRoleDto 前端传参
     * @return com.calm.parent.base.JsonResult
     * @author wangjunming
     * @since 2021/4/7 19:27
     */
    @PostMapping("/update")
    public JsonResult update(@RequestBody @Validated SysRoleDto sysRoleDto) {
        handleMenuDto(sysRoleDto);
        validateRoleDto(sysRoleDto, UPDATE);
        Long id = sysRoleService.update(sysRoleDto);
        return JsonResult.success(id);
    }

    /**
     * 角色分配菜单
     *
     * @param sysRoleDto 前端传参
     * @return com.calm.parent.base.JsonResult
     * @author wangjunming
     * @since 2021/4/7 19:27
     */
    @PostMapping("/distributionMenu")
    public JsonResult distributionMenu(@RequestBody @Validated SysRoleDto sysRoleDto) {
        handleMenuDto(sysRoleDto);
        validateRoleDto(sysRoleDto, UPDATE);
        if (null == sysRoleDto.getPermissionCode() || sysRoleDto.getPermissionCode().length <= 0) {
            throw new CalmException("请选择菜单。");
        }
        Boolean flag = sysRoleService.distributionMenu(sysRoleDto);
        return JsonResult.success(flag);
    }

    /**
     * 角色树
     *
     * @return com.calm.parent.base.JsonResult
     * @author wangjunming
     * @since 2021/4/7 19:27
     */
    @GetMapping("/roleTree")
    public Map<String, Object> roleTree(SysRoleDto sysRoleDto) {
        return JsonResult.success(sysRoleService.roleTree(sysRoleDto)).toDTreeResult();
    }

}