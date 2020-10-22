package com.calm.parent.base;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class QueryRequest implements Serializable {

    private int pageSize = 10;
    private int current = 1;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;


}
