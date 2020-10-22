package com.calm.order.persistence.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.calm.order.persistence.entity.HulunbuirUser;
import com.calm.parent.base.QueryRequest;

/**
 * 用户表 Service接口
 *
 * @author Mr.Wang
 * @since 2020-10-12 13:48:42
 */
public interface IHulunbuirUserService {

    /**
     * 用户表分页列表
     *
     * @author Mr.Wang
     * @since 2020-10-12 13:48:42
     */
    IPage<HulunbuirUser> page(QueryRequest queryRequest, HulunbuirUser hulunbuirUser);

    /**
     * 保存
     *
     * @author Mr.Wang
     * @since 2020-10-12 13:48:42
     */
     boolean save(HulunbuirUser hulunbuirUser);

    /**
     * 修改
     *
     * @author Mr.Wang
     * @since 2020-10-12 13:48:42
     */
     boolean update(HulunbuirUser hulunbuirUser);


    /**
     * 获取单个
     *
     * @author Mr.Wang
     * @since 2020-10-12 13:48:42
     */
    HulunbuirUser selOne(HulunbuirUser hulunbuirUser);


}
