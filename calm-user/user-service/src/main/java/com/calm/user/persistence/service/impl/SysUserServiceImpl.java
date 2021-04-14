package com.calm.user.persistence.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.calm.sequence.api.enums.SequenceType;
import com.calm.sequence.api.feign.SequenceFeignService;
import com.calm.user.api.dto.SysUserDto;
import com.calm.user.api.entity.SysUser;
import com.calm.user.api.entity.SysUserRole;
import com.calm.user.api.vo.SysUserVo;
import com.calm.user.persistence.mapper.SysUserMapper;
import com.calm.user.persistence.service.SysUserRoleService;
import com.calm.user.persistence.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


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

    @Autowired
    private SysUserRoleService userRoleService;


    /**
     * 通过账号查询用户信息，包含密码和盐值
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
        String sequenceNum = sequenceFeignService.getSequenceNum(SequenceType.USER.getCode(), -1);
        sysUser.setCode(sequenceNum);
        sysUser.initPassword();
        int insert = mapper.insert(sysUser);
        return sysUser.getUserId();
    }

    /**
     * 更新用户信息
     *
     * @param sysUserDto 前端传参
     * @return java.lang.Long
     * @author wangjunming
     * @since 2021/4/12 17:04
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean update(SysUserDto sysUserDto) {
        SysUser sysUser = sysUserDto.getUpdateSysUser();
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getCode, sysUserDto.getCode());
        return mapper.update(sysUser, queryWrapper) > 0;
    }

    /**
     * 更新用户的状态
     *
     * @param sysUserDto 前端传参
     * @return java.lang.Long
     * @author wangjunming
     * @since 2021/4/13 12:07
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateStatus(SysUserDto sysUserDto) {
        SysUser sysUser = sysUserDto.getUpdateSysUser();
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getCode, sysUserDto.getCode());
        return mapper.update(sysUser, queryWrapper) > 0;
    }

    /**
     * 通过用户编码查询用户信息
     *
     * @param code 用户编码
     * @return com.calm.user.api.vo.SysUserVo
     * @author wangjunming
     * @since 2021/4/13 14:27
     */
    @Override
    public SysUserVo queryByCode(String code) {
        SysUserDto sysUserDto = new SysUserDto();
        sysUserDto.setCode(code);
        return mapper.queryByParams(sysUserDto);
    }

    /**
     * 用户分配角色
     *
     * @param sysUserDto 前端传参
     * @return java.lang.Boolean
     * @author wangjunming
     * @since 2021/4/14 15:33
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean userDistributionRole(SysUserDto sysUserDto) {
        //将原有关联的角色删除掉
        String code = sysUserDto.getCode();
        userRoleService.deleteByUserCode(code);
        //增加新的角色信息
        List<SysUserRole> userRoleList = Arrays.stream(sysUserDto.getRoleCodes()).map(roleCode ->{
            SysUserRole userRole = new SysUserRole();
            userRole.setUserCode(code);
            userRole.setRoleCode(roleCode);
            return userRole;
        }).collect(Collectors.toList());
        return userRoleService.insertByList(userRoleList);
    }


}