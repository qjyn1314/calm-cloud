package com.calm.user.persistence.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.calm.common.auth.PasswordService;
import com.calm.sequence.api.enums.SequenceType;
import com.calm.sequence.api.feign.SequenceFeignService;
import com.calm.user.api.dto.SysUserDto;
import com.calm.user.api.entity.SysUser;
import com.calm.user.api.vo.SysUserVo;
import com.calm.user.persistence.mapper.SysUserMapper;
import com.calm.user.persistence.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

    @Autowired
    private SequenceFeignService sequenceFeignService;

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

    /**
     * 用户分页列表
     *
     * @param sysUserDto 前端传参
     * @return java.lang.Object
     * @author wangjunming
     * @since 2021/4/8 14:10
     */
    @Override
    public IPage<SysUserVo> page(SysUserDto sysUserDto) {
        Page<SysUserVo> page = new Page<>(sysUserDto.getCurrent(), sysUserDto.getPageSize());
        return mapper.page(page, sysUserDto);
    }

    /**
     * 保存用户信息
     *
     * @param sysUserDto 前端传参
     * @return java.lang.Long
     * @author wangjunming
     * @since 2021/4/9 15:09
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(SysUserDto sysUserDto) {
        SysUser sysUser = sysUserDto.getSysUser();
        String sequenceNum = sequenceFeignService.getSequenceNum(SequenceType.USER.getCode(), null);
        sysUser.setCode(sequenceNum);
        String salt = PasswordService.getSalt();
        sysUser.setSalt(salt);
        String encodePassword = PasswordService.encode(sysUser.getPassword(), salt);
        sysUser.setPassword(encodePassword);
        int insert = mapper.insert(sysUser);
        return sysUser.getUserId();
    }


}