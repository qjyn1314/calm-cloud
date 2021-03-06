package com.calm.user.api.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * 菜单表(SysMenu)表实体类
 *
 * @author wangjunming
 * @since 2021-03-13 21:40:08
 */
@Data
public class SysMenu implements Serializable {

    /**主键id*/
    private Long menuId;
    /**菜单编号*/
    private String code;
    /**菜单父编号*/
    private String pcode;
    /**菜单名称*/
    private String name;
    /**菜单图标*/
    private String icon;
    /**菜单样式*/
    private String fontFamily;
    /**url地址*/
    private String url;
    /**菜单类型：1-菜单；2-目录*/
    private Integer menuType;
    /**菜单状态：1-启用；0-停用*/
    private Integer status;
    /**是否打开：1-是；0-否*/
    private Integer openFlag;
    /**系统分类：1-后台管理系统；2-前台管理系统*/
    private Integer systemType;
    /**菜单排序号*/
    private Integer sort;
    /**备注*/
    private String description;
    /**创建时间*/
    private Date createTime;
    /**修改时间*/
    private Date updateTime;
    /**创建人*/
    private Long createUser;
    /**修改人*/
    private Long updateUser;

}