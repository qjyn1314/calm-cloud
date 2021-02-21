package com.calm.user.persistence.service;


import com.calm.user.api.vo.SysUserVo;

/**
 * 用户表(SysUser)表服务接口
 *
 * @author wangjunming
 * @since 2021-02-15 17:17:55
 */
public interface SysUserService {

    /**
     * 通过账号查询用户信息
     *
     * @author wangjunming
     * @since 2021/2/15 17:28
     */
    SysUserVo queryByAccount(String account);

}