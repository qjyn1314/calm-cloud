package com.calm.user.persistence.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.calm.common.auth.MenuTree;
import com.calm.user.api.dto.SysMenuDto;
import com.calm.user.api.entity.SysMenu;
import com.calm.user.api.enums.MenuStatus;
import com.calm.user.api.enums.MenuSystemType;
import com.calm.user.api.vo.DTreeVo;
import com.calm.user.api.vo.SysMenuVo;
import com.calm.user.persistence.mapper.SysMenuMapper;
import com.calm.user.persistence.service.SysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMenu::getMenuId, sysMenuDto.getMenuId());
        SysMenu sysMenuOne = mapper.selectOne(queryWrapper);
        SysMenu sysMenu = sysMenuDto.getUpdateSysMenu();
        int update = mapper.update(sysMenu, queryWrapper);
        return sysMenuOne.getMenuId();
    }

    /**
     * 通过角色编码查询已启用的后台系统菜单树
     *
     * @param roleCode 逗号分隔的角色编码
     * @return java.util.List<com.calm.user.api.vo.MenuTree>
     * @author wangjunming
     * @since 2021/4/16 11:09
     */
    @Override
    public List<MenuTree> selectMenuTreeByRoleCodes(String roleCode) {
        if(StringUtils.isBlank(roleCode)){
            return new ArrayList<>();
        }
        SysMenuDto sysMenuDto = new SysMenuDto();
        sysMenuDto.setRoleCode(Arrays.asList(roleCode.split(",")));
        sysMenuDto.setSystemType(MenuSystemType.BACKSTAGE.getCode());
        sysMenuDto.setStatus(MenuStatus.ENABLE.getCode());
        return selectMenuTreeByDto(sysMenuDto);
    }

    private List<MenuTree> selectMenuTreeByDto(SysMenuDto sysMenuDto) {
        List<MenuTree> menuTrees = mapper.selectMenuTreeByRoleCodes(sysMenuDto);
        List<MenuTree> finalMenuTrees = menuTrees;
        menuTrees = menuTrees.stream().filter(item -> "0".equals(item.getPcode())).peek(item -> item.setChildren(getChildrens(item, finalMenuTrees))).collect(Collectors.toList());
        return menuTrees;
    }

    private List<MenuTree> getChildrens(MenuTree root, List<MenuTree> allList) {
        return allList.stream().filter(item -> item.getPcode().equals(root.getCode()))
                .peek(item -> item.setChildren(getChildrens(item, allList))).collect(Collectors.toList());
    }

}