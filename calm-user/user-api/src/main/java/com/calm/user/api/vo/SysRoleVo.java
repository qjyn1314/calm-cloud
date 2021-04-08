package com.calm.user.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class SysRoleVo implements Serializable {

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
    /**菜单编码*/
    private String menuCodes;
    /**菜单名称*/
    private String menuNames;
    /**创建时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**修改时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}