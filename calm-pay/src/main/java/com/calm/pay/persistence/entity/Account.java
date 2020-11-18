package com.calm.pay.persistence.entity;

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
 *  Entity
 *
 * @author wangjunming
 * @date 2020-11-07 11:04:10
 */
@Data
@TableName("account")
@ApiModel(value="Account对象", description="")
public class Account implements Serializable {

    /**
     * 
     */
    @ApiModelProperty(value = "")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @ApiModelProperty(value = "")
    @TableField("balance")
    private Double balance;

    /**
     * 
     */
    @ApiModelProperty(value = "")
    @TableField("last_update_time")
    private Date lastUpdateTime;

}
