package com.calm.user.persistence.service;

import com.calm.user.api.entity.SysUserRole;

import java.util.List;

/**
 * 用户与角色关联表(SysUserRole)表服务接口
 *
 * @author wangjunming
 * @since 2021-04-04 17:48:46
 */
public interface SysUserRoleService {

    /**
     * 根据用户code删除角色关联表
     *
     * @param code 用户编码
     * @author wangjunming
     * @since 2021/4/7 20:02
     * @return void
     */
    void deleteByUserCode(String code);

    /**
     * 批量插入到中间表
     *
     * @param userRoleList 用户编码和角色编码的集合
     * @author wangjunming
     * @since 2021/4/8 10:13
     * @return java.lang.Boolean
     */
    Boolean insertByList(List<SysUserRole> userRoleList);

}