package com.calm.user.persistence.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.calm.user.api.dto.SysRoleDto;
import com.calm.user.api.vo.SysRoleVo;

/**
 * 角色表(SysRole)表服务接口
 *
 * @author wangjunming
 * @since 2021-04-04 16:54:56
 */
public interface SysRoleService{

    /**
     * 角色分页
     *
     * @param sysRoleDto 前端传参
     * @author wangjunming
     * @since 2021/4/4 17:57
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.calm.user.api.vo.SysRoleVo>
     */
    IPage<SysRoleVo> page(SysRoleDto sysRoleDto);


    /**
     * 保存角色
     * 
     * @param sysRoleDto 前端传参
     * @author wangjunming
     * @since 2021/4/5 19:43
     * @return java.lang.Long
     */
    Long save(SysRoleDto sysRoleDto);

    /**
     * 根据角色编码code查询角色信息
     *
     * @param code 角色编码
     * @author wangjunming
     * @since 2021/4/6 15:34
     * @return com.calm.user.api.vo.SysRoleVo
     */
    SysRoleVo selectByCode(String code);

    /**
     * 更新角色
     *
     * @param sysRoleDto 前端传参
     * @author wangjunming
     * @since 2021/4/6 16:11
     * @return java.lang.Long
     */
    Long update(SysRoleDto sysRoleDto);

    /**
     * 角色分配菜单
     *
     * @param sysRoleDto 前端传参
     * @author wangjunming
     * @since 2021/4/7 19:33
     * @return java.lang.Long
     */
    Boolean distributionMenu(SysRoleDto sysRoleDto);

}