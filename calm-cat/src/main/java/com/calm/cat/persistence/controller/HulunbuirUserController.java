package com.calm.cat.persistence.controller;

import com.calm.cat.persistence.entity.HulunbuirUser;
import com.calm.cat.persistence.service.IHulunbuirUserService;
import com.calm.auth.base.BaseController;
import com.calm.parent.base.QueryRequest;
import com.calm.parent.base.Result;
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
 * 用户表 Controller
 * </p>
 *
 * @author Mr.Wang
 * @since 2020-10-12 13:48:42
 */
@Slf4j
@Api(tags = "用户表 Controller")
@RestController
@RequestMapping("/hulunbuirUser")
public class HulunbuirUserController extends BaseController {

    @Autowired
    private IHulunbuirUserService service;

    @ApiOperation("用户表分页列表")
    @GetMapping("/page")
    public Result page(QueryRequest queryRequest, HulunbuirUser hulunbuirUser){
        return Result.ofSuccess(getDataTable(service.page(queryRequest,hulunbuirUser)));
    }

    @ApiOperation("用户表添加")
    @PostMapping("/save")
    public Result save(HulunbuirUser hulunbuirUser){
        return Result.ofSuccess(service.save(hulunbuirUser));
    }

    @ApiOperation("用户表修改")
    @PostMapping("/update")
    public Result update(HulunbuirUser hulunbuirUser){
        return Result.ofSuccess(service.update(hulunbuirUser));
    }

    @ApiOperation("用户表获取")
    @GetMapping("/selOne")
    public Result selOne(HulunbuirUser hulunbuirUser){
        return Result.ofSuccess(service.selOne(hulunbuirUser));
    }

}
