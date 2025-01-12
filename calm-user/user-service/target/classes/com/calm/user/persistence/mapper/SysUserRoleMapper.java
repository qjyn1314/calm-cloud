package com.calm.user.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.calm.user.api.entity.SysRoleMenu;
import com.calm.user.api.entity.SysUserRole;
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
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    int insertByList(@Param("userRoleList") List<SysUserRole> userRoleList);

}