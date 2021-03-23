package com.calm.user.api.dto;

import com.calm.parent.base.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 菜单表(SysMenu)表实体类
 *
 * @author wangjunming
 * @since 2021-03-13 21:40:08
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenuDto extends BaseDto implements Serializable {

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
    /**url地址*/
    private String url;
    /**菜单排序号*/
    private Integer sort;
    /**菜单层级*/
    private Integer levels;
    /**是否是菜单：1-是；0-否*/
    private Boolean menuFlag;
    /**备注*/
    private String description;
    /**菜单状态：1-启用；0-停用*/
    private String status;
    /**是否打开新页面的标识：1-是；0-否*/
    private String newPageFlag;
    /**是否打开：1-是；0-否*/
    private String openFlag;
    /**系统分类：1-后台管理系统；2-前台管理系统*/
    private String systemType;
    /**创建时间*/
    private Date createTime;
    /**创建人*/
    private Long createUser;

}