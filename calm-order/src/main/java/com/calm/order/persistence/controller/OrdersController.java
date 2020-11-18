package com.calm.order.persistence.controller;

import com.calm.core.base.BaseController;
import com.calm.order.persistence.entity.Orders;
import com.calm.order.persistence.service.IOrdersService;
import com.calm.parent.base.JsonResult;
import com.calm.parent.base.QueryRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  Controller
 * </p>
 *
 * @author wangjunming
 * @since 2020-11-05 13:29:15
 */
@Slf4j
@Api(tags = " Controller")
@RestController
@RequestMapping("/orders")
public class OrdersController extends BaseController {

    @Autowired
    private IOrdersService service;

    @ApiOperation("分页列表")
    @GetMapping("/page")
    public JsonResult page(QueryRequest queryRequest, Orders orders){
        return JsonResult.success(getDataTable(service.page(queryRequest,orders)));
    }

    @ApiOperation("添加")
    @PostMapping("/save")
    public JsonResult save(Orders orders){
        return JsonResult.success(service.save(orders));
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public JsonResult update(Orders orders){
        return JsonResult.success(service.update(orders));
    }

    @ApiOperation("获取")
    @GetMapping("/selOne")
    public JsonResult selOne(Orders orders){
        return JsonResult.success(service.selOne(orders));
    }

}
