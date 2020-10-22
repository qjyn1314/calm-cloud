package com.calm.order.persistence.entity;

import java.util.Date;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 用户表 Entity
 *
 * @author Mr.Wang
 * @date 2020-10-12 13:48:42
 */
@Data
@TableName("hulunbuir_user")
@ApiModel(value="HulunbuirUser对象", description="用户表")
public class HulunbuirUser implements Serializable {

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    @TableField("avatar")
    private String avatar;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    @TableField("create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField("create_date")
    private Date createDate;

    /**
     * 删除标志 0:未删除 1:删除
     */
    @ApiModelProperty(value = "删除标志 0:未删除 1:删除")
    @TableField("del_flag")
    private String delFlag;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    @TableField("description")
    private String description;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    private String email;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 最近访问时间
     */
    @ApiModelProperty(value = "最近访问时间")
    @TableField("last_login_time")
    private Date lastLoginTime;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @TableField("phone")
    private String phone;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @TableField("remarks")
    private String remarks;

    /**
     * 性别 0男 1女 2保密
     */
    @ApiModelProperty(value = "性别 0男 1女 2保密")
    @TableField("sex")
    private String sex;

    /**
     * 状态 0锁定 1有效
     */
    @ApiModelProperty(value = "状态 0锁定 1有效")
    @TableField("status")
    private String status;

    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者")
    @TableField("update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @TableField("update_date")
    private Date updateDate;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    @TableField("user_name")
    private String userName;

}
