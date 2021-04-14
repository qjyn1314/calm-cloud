package com.calm.user.persistence.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.calm.user.api.dto.SysMenuDto;
import com.calm.user.api.entity.SysMenu;
import com.calm.user.api.vo.DTreeVo;
import com.calm.user.api.vo.SysMenuVo;
import com.calm.user.persistence.mapper.SysMenuMapper;
import com.calm.user.persistence.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 菜单表(SysMenu)表服务实现类
 *
 * @author wangjunming
 * @since 2021-03-13 21:55:51
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper mapper;


    /**
     * 分页列表
     *
     * @param sysMenuDto 查询对象
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.calm.user.api.vo.SysMenuVo>
     * @author wangjunming
     * @since 2021/3/14 14:17
     */
    @Override
    public IPage<SysMenuVo> page(SysMenuDto sysMenuDto) {
        Page<SysMenuVo> page = new Page<>(sysMenuDto.getCurrent(), sysMenuDto.getPageSize());
        return mapper.page(page, sysMenuDto);
    }

    /**
     * 保存菜单信息
     *
     * @param sysMenuDto 前端传参
     * @return java.lang.Long
     * @author wangjunming
     * @since 2021/3/25 10:53
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(SysMenuDto sysMenuDto) {
        SysMenu sysMenu = sysMenuDto.getSysMenu();
        mapper.insert(sysMenu);
        return sysMenu.getMenuId();
    }

    /**
     * 查询树形结构的菜单
     *
     * @param sysMenuDto
     * @return List<MenuTreeVo>
     * @author wangjunming
     * @since 2021/3/25 11:51
     */
    @Override
    public List<DTreeVo> formSelectTree(SysMenuDto sysMenuDto) {
        return mapper.selectByCode(sysMenuDto.getPcode());
    }

    /**
     * 通过菜单编码查询菜单信息
     *
     * @param code 菜单编码
     * @return com.calm.user.api.vo.SysMenuVo
     * @author wangjunming
     * @since 2021/3/30 17:54
     */
    @Override
    public SysMenuVo selectMenuByCode(String code) {
        return mapper.selectMenuByCode(code);
    }

    /**
     * 更新菜单
     *
     * @param sysMenuDto
     * @return java.lang.Long
     * @author wangjunming
     * @since 2021/3/31 11:01
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long update(SysMenuDto sysMenuDto) {
        LambdaQueryWrapper<SysMenu> queryWrapper  = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMenu::getMenuId,sysMenuDto.getMenuId());
        SysMenu sysMenuOne = mapper.selectOne(queryWrapper);
        SysMenu sysMenu = sysMenuDto.getUpdateSysMenu();
        int update = mapper.update(sysMenu, queryWrapper);
        return sysMenuOne.getMenuId();
    }

}