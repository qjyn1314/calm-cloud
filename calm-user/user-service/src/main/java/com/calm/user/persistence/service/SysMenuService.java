package com.calm.user.persistence.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.calm.common.auth.MenuTree;
import com.calm.user.api.dto.SysMenuDto;
import com.calm.user.api.vo.DTreeVo;
import com.calm.user.api.vo.SysMenuVo;

import java.util.List;

/**
 * 菜单表(SysMenu)表服务接口
 *
 * @author wangjunming
 * @since 2021-03-13 21:55:51
 */
public interface SysMenuService{

    /**
     * 分页列表
     *
     * @param sysMenuDto 查询对象
     * @author wangjunming
     * @since 2021/3/14 14:17
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.calm.user.api.vo.SysMenuVo>
     */
    IPage<SysMenuVo> page(SysMenuDto sysMenuDto);

    /**
     * 保存菜单信息
     *
     * @param sysMenuDto
     * @author wangjunming
     * @since 2021/3/25 10:53
     * @return java.lang.Long
     */
    Long save(SysMenuDto sysMenuDto);

    /**
     * 查询树形结构的菜单
     *
     * @param sysMenuDto
     * @author wangjunming
     * @since 2021/3/25 11:51
     * @return List<MenuTreeVo>
     */
    List<DTreeVo> formSelectTree(SysMenuDto sysMenuDto);

    /**
     * 通过菜单编码查询菜单信息
     *
     * @param code 菜单编码
     * @author wangjunming
     * @since 2021/3/30 17:54
     * @return com.calm.user.api.vo.SysMenuVo
     */
    SysMenuVo selectMenuByCode(String code);


    /**
     * 更新菜单
     *
     * @param sysMenuDto
     * @author wangjunming
     * @since 2021/3/31 11:01
     * @return java.lang.Long
     */
    Long update(SysMenuDto sysMenuDto);

    /**
     * 通过角色编码查询菜单树
     * 
     * @param roleCode 逗号分隔的角色编码
     * @author wangjunming
     * @since 2021/4/16 11:09
     * @return java.util.List<com.calm.user.api.vo.MenuTree>
     */
    List<MenuTree> selectMenuTreeByRoleCodes(String roleCode);
    
}