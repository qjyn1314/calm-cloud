package com.calm.order.persistence.entity;

import java.util.Date;
import java.math.BigDecimal;

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
 * @date 2020-11-05 13:29:15
 */
@Data
@TableName("orders")
@ApiModel(value="Orders对象", description="")
public class Orders implements Serializable {

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
    @TableField("user_id")
    private Integer userId;

    /**
     * 
     */
    @ApiModelProperty(value = "")
    @TableField("product_id")
    private Integer productId;

    /**
     * 
     */
    @ApiModelProperty(value = "")
    @TableField("pay_amount")
    private BigDecimal payAmount;
    /**
     * 
     */
    @ApiModelProperty(value = "")
    @TableField("status")
    private String status;

    /**
     * 
     */
    @ApiModelProperty(value = "")
    @TableField("add_time")
    private Date addTime;

    /**
     * 
     */
    @ApiModelProperty(value = "")
    @TableField("last_update_time")
    private Date lastUpdateTime;

}
