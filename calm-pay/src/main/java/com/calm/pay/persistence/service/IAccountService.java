package com.calm.pay.persistence.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.calm.parent.base.QueryRequest;
import com.calm.pay.persistence.entity.Account;

/**
 *  Service接口
 *
 * @author wangjunming
 * @since 2020-11-07 11:04:10
 */
public interface IAccountService {

    /**
     * 分页列表
     *
     * @author wangjunming
     * @since 2020-11-07 11:04:10
     */
    IPage<Account> page(QueryRequest queryRequest, Account account);

    /**
     * 保存
     *
     * @author wangjunming
     * @since 2020-11-07 11:04:10
     */
     boolean save(Account account);

    /**
     * 修改
     *
     * @author wangjunming
     * @since 2020-11-07 11:04:10
     */
     boolean update(Account account);


    /**
     * 获取单个
     *
     * @author wangjunming
     * @since 2020-11-07 11:04:10
     */
    Account selOne(Account account);


}
