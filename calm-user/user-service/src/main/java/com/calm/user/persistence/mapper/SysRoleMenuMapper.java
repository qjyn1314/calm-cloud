package com.calm.user.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.calm.user.api.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色和菜单关联表(SysRoleMenu)表数据库访问层
 *
 * @author wangjunming
 * @since 2021-04-04 17:48:46
 */
@Repository
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    int insertByList(@Param("roleMenuList") List<SysRoleMenu> roleMenuList);

}