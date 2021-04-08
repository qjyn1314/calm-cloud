package com.calm.user.api.entity;

import lombok.Data;
import java.io.Serializable;

/**
 * 角色和菜单关联表(SysRoleMenu)表实体类
 *
 * @author wangjunming
 * @since 2021-04-04 17:48:46
 */
@Data
public class SysRoleMenu implements Serializable {

    /**主键*/
    private Long relationId;
    /**菜单编码*/
    private String menuCode;
    /**角色编码*/
    private String roleCode;

}