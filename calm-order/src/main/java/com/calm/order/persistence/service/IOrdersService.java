package com.calm.order.persistence.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.calm.order.persistence.entity.Orders;
import com.calm.parent.base.QueryRequest;
import com.calm.provider.entitys.OrderFeignEntity;

/**
 *  Service接口
 *
 * @author wangjunming
 * @since 2020-11-05 13:29:15
 */
public interface IOrdersService {

    /**
     * 分页列表
     *
     * @author wangjunming
     * @since 2020-11-05 13:29:15
     */
    IPage<Orders> page(QueryRequest queryRequest, Orders orders);

    /**
     * 保存
     *
     * @author wangjunming
     * @since 2020-11-05 13:29:15
     */
     boolean save(Orders orders);

    /**
     * 修改
     *
     * @author wangjunming
     * @since 2020-11-05 13:29:15
     */
     boolean update(Orders orders);


    /**
     * 获取单个
     *
     * @author wangjunming
     * @since 2020-11-05 13:29:15
     */
    Orders selOne(Orders orders);


    /**
     * 测试分布式事务
     * @param orderFeignEntity 订单信息
     * @return boolean
     */
    boolean testDistributedTransaction(OrderFeignEntity orderFeignEntity);

}
