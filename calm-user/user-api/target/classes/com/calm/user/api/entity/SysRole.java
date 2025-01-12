package com.calm.user.api.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色表(SysRole)表实体类
 *
 * @author wangjunming
 * @since 2021-04-04 16:54:56
 */
@Data
public class SysRole implements Serializable {

    /**主键id*/
    private Long roleId;
    /**角色编码*/
    private String code;
    /**角色名称*/
    private String name;
    /**提示*/
    private String description;
    /**序号*/
    private Integer sort;
    /**乐观锁*/
    private Integer version;
    /**创建时间*/
    private Date createTime;
    /**修改时间*/
    private Date updateTime;
    /**创建用户*/
    private Long createUser;
    /**修改用户*/
    private Long updateUser;

}