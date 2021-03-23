package com.calm.user.persistence.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.calm.user.api.dto.SysMenuDto;
import com.calm.user.api.vo.SysMenuVo;

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

}