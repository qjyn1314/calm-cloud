package com.calm.user.persistence.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.calm.user.api.dto.SysUserDto;
import com.calm.user.api.entity.SysUser;
import com.calm.user.api.vo.SysUserVo;
import com.calm.user.persistence.mapper.SysUserMapper;
import com.calm.user.persistence.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


/**
 * 用户表(SysUser)表服务实现类
 *
 * @author wangjunming
 * @since 2021-02-15 17:17:55
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper mapper;

    /**
     * 通过账号查询用户信息
     *
     * @param account
     * @author wangjunming
     * @since 2021/2/15 17:28
     */
    @Override
    public SysUserVo queryByAccount(String account) {
        return mapper.queryByAccount(account);
    }




}