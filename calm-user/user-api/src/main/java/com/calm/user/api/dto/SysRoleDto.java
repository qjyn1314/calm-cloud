package com.calm.user.api.dto;

import com.calm.parent.base.BaseDto;
import com.calm.user.api.entity.SysRole;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色表(SysRole)表实体类
 *
 * @author wangjunming
 * @since 2021-04-04 16:54:56
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysRoleDto  extends BaseDto implements Serializable {

    /**主键id*/
    private Long roleId;
    /**角色编码*/
    @Length(min = 1,message = "请输入角色编码")
    private String code;
    /**角色名称*/
    @Length(min = 1,message = "请输入角色名称")
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
    /**分配的权限*/
    private String[] permissionCode;

    public SysRole getSysRole() {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(this, sysRole);
        return sysRole;
    }

    public SysRole getUpdateSysRole() {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(this, sysRole, "createUser", "createTime");
        return sysRole;
    }

}