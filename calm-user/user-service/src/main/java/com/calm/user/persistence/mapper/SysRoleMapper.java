package com.calm.user.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.calm.user.api.dto.SysRoleDto;
import com.calm.user.api.entity.SysRole;
import com.calm.user.api.vo.SysRoleVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 角色表(SysRole)表数据库访问层
 *
 * @author wangjunming
 * @since 2021-04-04 16:54:56
 */
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {

    IPage<SysRoleVo> page(Page<SysRoleVo> page, @Param("qo") SysRoleDto sysRoleDto);

    SysRoleVo selectByCode(@Param("code") String code);
}