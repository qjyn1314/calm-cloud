package com.calm.user.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * explain:
 * </p>
 *
 * @author wangjunming
 * @since 2021/3/25 11:50
 */
@Data
public class MenuTreeVo implements Serializable {
    /**菜单code*/
    private String id;
    /**菜单名称*/
    private String title;
    /**父级code*/
    private String parentId;
    /**其下的子级*/
    List<MenuTreeVo> children;
}
