package com.calm.user.persistence.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.calm.user.api.dto.SysRoleDto;
import com.calm.user.api.entity.SysRole;
import com.calm.user.api.entity.SysRoleMenu;
import com.calm.user.api.vo.SysRoleVo;
import com.calm.user.persistence.mapper.SysRoleMapper;
import com.calm.user.persistence.service.SysRoleMenuService;
import com.calm.user.persistence.service.SysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 角色表(SysRole)表服务实现类
 *
 * @author wangjunming
 * @since 2021-04-04 16:54:56
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper mapper;

    @Autowired
    private SysRoleMenuService roleMenuService;

    /**
     * 角色分页
     *
     * @param sysRoleDto 前端传参
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.calm.user.api.vo.SysRoleVo>
     * @author wangjunming
     * @since 2021/4/4 17:57
     */
    @Override
    public IPage<SysRoleVo> page(SysRoleDto sysRoleDto) {
        Page<SysRoleVo> page = new Page<>(sysRoleDto.getCurrent(), sysRoleDto.getPageSize());
        return mapper.page(page, sysRoleDto);
    }

    /**
     * 保存角色
     *
     * @param sysRoleDto 前端传参
     * @return java.lang.Long
     * @author wangjunming
     * @since 2021/4/5 19:43
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(SysRoleDto sysRoleDto) {
        SysRole sysRole = sysRoleDto.getSysRole();
        int insert = mapper.insert(sysRole);
        return sysRole.getRoleId();
    }

    /**
     * 根据角色编码code查询角色信息
     *
     * @param code 角色编码
     * @return com.calm.user.api.vo.SysRoleVo
     * @author wangjunming
     * @since 2021/4/6 15:34
     */
    @Override
    public SysRoleVo selectByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return mapper.selectByCode(code);
    }

    /**
     * 更新角色
     *
     * @param sysRoleDto 前端传参
     * @return java.lang.Long
     * @author wangjunming
     * @since 2021/4/6 16:11
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long update(SysRoleDto sysRoleDto) {
        SysRole sysRole = sysRoleDto.getUpdateSysRole();
        LambdaQueryWrapper<SysRole> queryWrapper  = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRole::getCode,sysRoleDto.getCode());
        int update = mapper.update(sysRole, queryWrapper);
        return sysRole.getRoleId();
    }

    /**
     * 角色分配菜单
     *
     * @param sysRoleDto 前端传参
     * @return java.lang.Long
     * @author wangjunming
     * @since 2021/4/7 19:33
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean distributionMenu(SysRoleDto sysRoleDto) {
        String roleCode = sysRoleDto.getCode();
        //将此角色原有的菜单删除
        roleMenuService.deleteByRoleCode(roleCode);
        List<SysRoleMenu> roleMenuList = CollUtil.newArrayList();
        List<String> permission = Arrays.asList(sysRoleDto.getPermissionCode());
        permission.forEach(perCode -> {
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setRoleCode(roleCode);
            roleMenu.setMenuCode(perCode);
            roleMenuList.add(roleMenu);
        });
        //添加此角色新的菜单
        return roleMenuService.insertByList(roleMenuList);
    }
}