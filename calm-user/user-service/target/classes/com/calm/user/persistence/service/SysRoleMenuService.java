package com.calm.user.persistence.service;

import com.calm.user.api.entity.SysRoleMenu;

import java.util.List;

/**
 * 角色和菜单关联表(SysRoleMenu)表服务接口
 *
 * @author wangjunming
 * @since 2021-04-04 17:48:46
 */
public interface SysRoleMenuService{

    /**
     * 根据角色code删除菜单关联表
     *
     * @param code 角色编码
     * @author wangjunming
     * @since 2021/4/7 20:02
     * @return void
     */
    void deleteByRoleCode(String code);

    /**
     * 批量插入到中间表
     *
     * @param roleMenuList 角色编码和菜单编码的集合
     * @author wangjunming
     * @since 2021/4/8 10:13
     * @return java.lang.Boolean
     */
    Boolean insertByList(List<SysRoleMenu> roleMenuList);

}