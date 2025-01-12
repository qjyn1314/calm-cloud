package com.calm.user.persistence.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.calm.user.api.dto.SysUserDto;
import com.calm.user.api.vo.SysUserVo;

/**
 * 用户表(SysUser)表服务接口
 *
 * @author wangjunming
 * @since 2021-02-15 17:17:55
 */
public interface SysUserService {

    /**
     * 用户分页列表
     *
     * @param sysUserDto 前端传参
     * @author wangjunming
     * @since 2021/4/8 14:10
     * @return java.lang.Object
     */
    IPage<SysUserVo> page(SysUserDto sysUserDto);

    /**
     * 保存用户信息
     *
     * @param sysUserDto 前端传参
     * @author wangjunming
     * @since 2021/4/9 15:09
     * @return java.lang.Long
     */
    Long save(SysUserDto sysUserDto);

    /**
     * 更新用户信息
     *
     * @param sysUserDto 前端传参
     * @author wangjunming
     * @since 2021/4/12 17:04
     * @return java.lang.Long
     */
    Boolean update(SysUserDto sysUserDto);

    /**
     * 更新用户的状态
     *
     * @param sysUserDto 前端传参
     * @author wangjunming
     * @since 2021/4/13 12:07
     * @return java.lang.Long
     */
    Boolean updateStatus(SysUserDto sysUserDto);


    /**
     * 通过用户编码查询用户信息，用于更新操作中的数据查询
     *
     * @param code 用户编码
     * @author wangjunming
     * @since 2021/4/13 14:27
     * @return com.calm.user.api.vo.SysUserVo
     */
    SysUserVo queryByCode(String code);

    /**
     * 通过用户登录账号查询用户信息，没有密码和盐值，没有角色信息，用于效验是否已存在此邮箱
     *
     * @param account 用户登录账号
     * @author wangjunming
     * @since 2021/4/13 14:27
     * @return com.calm.user.api.vo.SysUserVo
     */
    SysUserVo queryByAccountNps(String account);

    /**
     * 用户分配角色
     *
     * @param sysUserDto 前端传参
     * @author wangjunming
     * @since 2021/4/14 15:33
     * @return java.lang.Boolean
     */
    Boolean userDistributionRole(SysUserDto sysUserDto);

    /**
     * 授权服务调用，效验密码是否正确
     *
     * @param account 登录账号
     * @param password 用户输入的密码
     * @author wangjunming
     * @since 2021/4/16 10:28
     * @return com.calm.user.api.vo.SysUserVo
     */
    SysUserVo validatePassword(String account, String password);


    /**
     * 通过用户编码查询当前登录用户信息
     *
     * @param code 用户编码
     * @author wangjunming
     * @since 2021/4/17 21:38
     * @return com.calm.user.api.vo.SysUserVo
     */
    SysUserVo selectByCode(String code);

    /**
     * 更新密码
     *
     * @param sysUserDto 前端传参
     * @author wangjunming
     * @since 2021/4/18 17:38
     * @return java.lang.Boolean
     */
    Boolean changePwd(SysUserDto sysUserDto);


}