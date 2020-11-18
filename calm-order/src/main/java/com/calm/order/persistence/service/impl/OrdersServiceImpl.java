package com.calm.order.persistence.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.calm.order.persistence.entity.Orders;
import com.calm.order.persistence.mapper.OrdersMapper;
import com.calm.order.persistence.service.IOrdersService;
import com.calm.parent.base.QueryRequest;
import com.calm.provider.entitys.OrderFeignEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
 *  Service实现
 *
 * @author wangjunming
 * @since 2020-11-05 13:29:15
 */
@Slf4j
@Service
public class OrdersServiceImpl implements IOrdersService {

    @Autowired
    private OrdersMapper ordersMapper;

   /**
    * 分页列表
    *
    * @param queryRequest
    * @param orders
    * @author wangjunming
    * @since 2020-11-05 13:29:15
    */
    @Override
    public IPage<Orders> page(QueryRequest queryRequest, Orders orders) {
        LambdaQueryWrapper<Orders> queryWrapper = initQueryWrapper(queryRequest,orders);
        Page<Orders> page = new Page<>(queryRequest.getCurrent(), queryRequest.getPageSize());
        return ordersMapper.selectPage(page, queryWrapper);
    }

    /**
    * 列表的查询参数
    *
    * @author wangjunming
    * @since 2020-11-05 13:29:15
    */
    private LambdaQueryWrapper<Orders> initQueryWrapper(QueryRequest queryRequest, Orders orders) {
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        //--TODO  添加查询条件
        return queryWrapper;
    }

   /**
    * 保存
    *
    * @param orders
    * @author wangjunming
    * @since 2020-11-05 13:29:15
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Orders orders) {
        //--TODO 做一些初始化动作
        return ordersMapper.insert(orders)>0;
    }

   /**
    * 修改
    *
    * @param orders
    * @author wangjunming
    * @since 2020-11-05 13:29:15
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Orders orders) {
        //--TODO 做一些效验动作
        return ordersMapper.updateById(orders)>0;
    }

   /**
    * 获取单个
    *
    * @param orders
    * @author wangjunming
    * @since 2020-11-05 13:29:15
    */
    @Override
    public Orders selOne(Orders orders) {
    LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        //--TODO 初始化查询条件
        return ordersMapper.selectOne(queryWrapper);
    }

    /**
     * 测试分布式事务
     *
     * @param orderFeignEntity 订单信息
     * @return boolean
     */
    @Override
    public boolean testDistributedTransaction(OrderFeignEntity orderFeignEntity) {

        return false;
    }


}
