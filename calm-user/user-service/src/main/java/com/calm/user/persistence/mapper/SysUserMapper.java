package com.calm.user.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.calm.user.api.dto.SysUserDto;
import com.calm.user.api.entity.SysUser;
import com.calm.user.api.vo.SysUserVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 用户表(SysUser)表数据库访问层
 *
 * @author wangjunming
 * @since 2021-02-15 17:17:55
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser>{


    /**
     * 通过账号查询用户信息
     *
     * @author wangjunming
     * @since 2021/2/15 17:33
     */
    SysUserVo queryByAccount(String account);

    /**
     * 用户分页列表
     *
     * @param page 分页信息
     * @param sysUserDto 前端传参
     * @author wangjunming
     * @since 2021/4/8 14:22
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.calm.user.api.vo.SysUserVo>
     */
    IPage<SysUserVo> page(Page<SysUserVo> page, @Param("qo") SysUserDto sysUserDto);

    /**
     * 通过参数查询唯一用户信息
     *
     * @param sysUserDto 前端传参
     * @author wangjunming
     * @since 2021/4/13 14:30
     * @return com.calm.user.api.vo.SysUserVo
     */
    SysUserVo queryByParams(SysUserDto sysUserDto);

}