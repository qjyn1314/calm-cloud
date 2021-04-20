package com.calm.common.auth;

import lombok.Data;

import java.util.List;

/**
 * <p>
 * explain: 当前登录用户的菜单列表
 * </p>
 *
 * @author wangjunming
 * @since 2021/4/15 14:48
 */
@Data
public class MenuTree {
    /**菜单编号*/
    private String code;
    /**菜单父编号*/
    private String pcode;
    /**菜单名称*/
    private String title;
    /**菜单url*/
    private String href;
    /**菜单字体类型*/
    private String fontFamily;
    /**菜单图标样式*/
    private String icon;
    /**是否展开*/
    private Boolean spread;
    /**是否选中*/
    private Boolean isCheck;
    /**菜单类型：1-菜单；2-目录*/
    private Integer menuType;
    /**菜单状态：1-启用；0-停用*/
    private Integer status;
    private List<MenuTree> children;

}
