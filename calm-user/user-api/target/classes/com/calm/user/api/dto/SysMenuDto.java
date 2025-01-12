package com.calm.user.api.dto;

import com.calm.parent.base.BaseDto;
import com.calm.user.api.entity.SysMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
    @Length(min = 1,message = "请填写菜单编号")
    private String code;
    /**菜单父编号*/
    @Length(min = 1,message = "请选择父级菜单")
    private String pcode;
    /**菜单名称*/
    @Length(min = 1,message = "请填写菜单名称")
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
    /**逗号分隔的角色编码*/
    List<String> roleCode;

    /**传参转换*/
    public SysMenu getSysMenu() {
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(this, sysMenu);
        return sysMenu;
    }

    /**传参转换为需要更新的菜单*/
    public SysMenu getUpdateSysMenu() {
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(this, sysMenu,"createTime","createUser");
        return sysMenu;
    }

}