package com.calm.user.persistence.controller;

import com.calm.common.auth.CurrentSecurityUserUtils;
import com.calm.common.auth.CurrentUser;
import com.calm.common.exception.CalmException;
import com.calm.core.base.BaseController;
import com.calm.parent.base.JsonResult;
import com.calm.user.api.dto.SysMenuDto;
import com.calm.user.api.vo.SysMenuVo;
import com.calm.user.persistence.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

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

    private void handleMenuDto(SysMenuDto sysMenuDto) {
        Date date = new Date();
        CurrentUser currentUser = CurrentSecurityUserUtils.authUser();
        sysMenuDto.setCreateTime(date);
        sysMenuDto.setUpdateTime(date);
        sysMenuDto.setCreateUser(currentUser.getUserId());
        sysMenuDto.setUpdateUser(currentUser.getUserId());
    }


    /**
     * 菜单分页列表
     *
     * @param sysMenuDto
     * @return com.calm.parent.base.JsonResult
     * @author wangjunming
     * @since 2021/3/25 11:48
     */
    @GetMapping("/page")
    public JsonResult page(SysMenuDto sysMenuDto) {
        handleMenuDto(sysMenuDto);
        return JsonResult.success(sysMenuService.page(sysMenuDto));
    }

    /**
     * 树形菜单的接口
     *
     * @param sysMenuDto 查询传参
     * @return com.calm.parent.base.JsonResult
     * @author wangjunming
     * @since 2021/3/25 11:48
     */
    @GetMapping("/formSelectTree")
    public Map<String, Object> formSelectTree(SysMenuDto sysMenuDto) {
        sysMenuDto.setPcode("-1");
        return JsonResult.success(sysMenuService.formSelectTree(sysMenuDto)).toPermissionsTreeResult();
    }

    /**
     * 保存菜单信息
     *
     * @param sysMenuDto 保存传参
     * @return com.calm.parent.base.JsonResult
     * @author wangjunming
     * @since 2021/3/25 11:48
     */
    @PostMapping("/save")
    public JsonResult save(@RequestBody @Validated SysMenuDto sysMenuDto) {
        handleMenuDto(sysMenuDto);
        validateMenuDto(sysMenuDto, SAVE);
        Long id = sysMenuService.save(sysMenuDto);
        return JsonResult.success(id);
    }

    private void validateMenuDto(SysMenuDto sysMenuDto, Integer optionType) {
        String pcode = sysMenuDto.getPcode();
        String code = sysMenuDto.getCode();
        //父级编码不存在
        if (StringUtils.isBlank(pcode)) {
            throw new CalmException("请选择正确的父级菜单");
        }
        SysMenuVo parentMenu = sysMenuService.selectMenuByCode(pcode);
        if (null == parentMenu) {
            throw new CalmException("请选择正确的父级菜单");
        }
        if (SAVE.equals(optionType)) {
            //菜单编码中已存在此
            if (StringUtils.isBlank(code)) {
                throw new CalmException("已存在此菜单编码，请重新填写");
            }
            SysMenuVo childrenMenu = sysMenuService.selectMenuByCode(code);
            if (null != childrenMenu) {
                throw new CalmException("已存在此菜单编码，请重新填写");
            }
        }
    }

    /**
     * 更新菜单信息
     *
     * @param sysMenuDto 更新传参
     * @return com.calm.parent.base.JsonResult
     * @author wangjunming
     * @since 2021/3/25 11:48
     */
    @PostMapping("/update")
    public JsonResult update(@RequestBody @Validated SysMenuDto sysMenuDto) {
        handleMenuDto(sysMenuDto);
        validateMenuDto(sysMenuDto,UPDATE);
        Long id = sysMenuService.update(sysMenuDto);
        return JsonResult.success(id);
    }

    /**
     * 更新菜单信息
     *
     * @param code 菜单编编码
     * @return com.calm.parent.base.JsonResult
     * @author wangjunming
     * @since 2021/3/25 11:48
     */
    @GetMapping("/selectMenuByCode/{code}")
    public JsonResult selectMenuByCode(@PathVariable String code) {
        return JsonResult.success(sysMenuService.selectMenuByCode(code));
    }


}