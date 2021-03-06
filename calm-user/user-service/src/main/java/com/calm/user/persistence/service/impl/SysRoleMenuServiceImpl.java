package com.calm.user.persistence.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.calm.user.api.entity.SysRoleMenu;
import com.calm.user.persistence.mapper.SysRoleMenuMapper;
import com.calm.user.persistence.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色和菜单关联表(SysRoleMenu)表服务实现类
 *
 * @author wangjunming
 * @since 2021-04-04 17:48:46
 */
@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    @Autowired
    private SysRoleMenuMapper mapper;


    /**
     * 根据角色code删除菜单关联表
     *
     * @param code 角色编码
     * @return void
     * @author wangjunming
     * @since 2021/4/7 20:02
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByRoleCode(String code) {
        LambdaQueryWrapper<SysRoleMenu> delWrapper  = new LambdaQueryWrapper<>();
        delWrapper.eq(SysRoleMenu::getRoleCode,code);
        mapper.delete(delWrapper);
    }

    /**
     * 批量插入到中间表
     *
     * @param roleMenuList 角色编码和菜单编码的集合
     * @return java.lang.Boolean
     * @author wangjunming
     * @since 2021/4/8 10:13
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertByList(List<SysRoleMenu> roleMenuList) {
        return mapper.insertByList(roleMenuList) > 0;
    }
}