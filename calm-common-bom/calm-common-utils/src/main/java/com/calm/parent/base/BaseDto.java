package com.calm.parent.base;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * explain:
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/15 14:40
 */
@Data
public class BaseDto implements Serializable {

    /**每页显示记录数*/
    private int pageSize = 10;
    /**当前页*/
    private int current = 1;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;


}
