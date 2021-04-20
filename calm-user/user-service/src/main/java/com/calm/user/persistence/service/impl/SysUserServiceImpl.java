package com.calm.user.persistence.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.calm.common.auth.MenuTree;
import com.calm.common.auth.PasswordService;
import com.calm.common.exception.CalmException;
import com.calm.parent.config.redis.RedisHelper;
import com.calm.sequence.api.enums.SequenceType;
import com.calm.sequence.api.feign.SequenceFeignService;
import com.calm.user.api.dto.SysUserDto;
import com.calm.user.api.entity.SysUser;
import com.calm.user.api.entity.SysUserRole;
import com.calm.user.api.enums.UserStatus;
import com.calm.user.api.exception.HaveBeenforzenException;
import com.calm.user.api.exception.ToauditException;
import com.calm.user.api.vo.SysUserVo;
import com.calm.user.config.delredis.DelRedis;
import com.calm.user.persistence.mapper.SysUserMapper;
import com.calm.user.persistence.service.SysMenuService;
import com.calm.user.persistence.service.SysUserRoleService;
import com.calm.user.persistence.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @Autowired
    private SysMenuService menuService;

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
        if(StringUtils.isBlank(sequenceNum)){
            throw new CalmException("用户编号需要启动“序列号服务”");
        }
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
    @DelRedis(RedisHelper.USER_DEL_KEY)
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
    @DelRedis(RedisHelper.USER_DEL_KEY)
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
     * 通过用户账号查询用户信息
     *
     * @param account 用户账号
     * @return com.calm.user.api.vo.SysUserVo
     * @author wangjunming
     * @since 2021/4/13 14:27
     */
    @Override
    public SysUserVo queryByAccountNps(String account) {
        SysUserDto sysUserDto = new SysUserDto();
        sysUserDto.setAccount(account);
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
    @DelRedis(RedisHelper.USER_DEL_KEY)
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

    /**
     * 授权服务调用，效验密码是否正确
     *
     * @param account  登录账号
     * @param password 用户输入的密码
     * @return com.calm.user.api.vo.SysUserVo
     * @author wangjunming
     * @since 2021/4/16 10:28
     */
    @Override
    public SysUserVo validatePassword(String account, String password) {
        SysUserVo sysUserVo = mapper.queryByAccount(account);
        if (null == sysUserVo) {
            throw new UsernameNotFoundException("用户名不存在。");
        }
        PasswordService.validatePassword(password,sysUserVo.getSalt(),sysUserVo.getPassword());
        if (UserStatus.TO_AUDIT.getCode().equals(sysUserVo.getStatus())) {
            throw new ToauditException("账号待审核，请联系管理员处理。");
        }
        if (UserStatus.DISABLE.getCode().equals(sysUserVo.getStatus())) {
            throw new HaveBeenforzenException("账号已停用，请联系管理员处理。");
        }
        sysUserVo.noPwd();
        List<MenuTree> menuTrees = menuService.selectMenuTreeByRoleCodes(sysUserVo.getRoleCode());
        sysUserVo.setMenuTree(menuTrees);
        return sysUserVo;
    }

    /**
     * 通过用户编码查询当前登录用户信息
     *
     * @param code 用户编码
     * @return com.calm.user.api.vo.SysUserVo
     * @author wangjunming
     * @since 2021/4/17 21:38
     */
    @Override
    public SysUserVo selectByCode(String code) {
        SysUserVo sysUserVo =  mapper.selectByCode(code);
        List<MenuTree> menuTrees = menuService.selectMenuTreeByRoleCodes(sysUserVo.getRoleCode());
        sysUserVo.setMenuTree(menuTrees);
        return sysUserVo;
    }

    /**
     * 更新密码
     *
     * @param sysUserDto 前端传参
     * @return java.lang.Boolean
     * @author wangjunming
     * @since 2021/4/18 17:38
     */
    @Override
    @DelRedis(RedisHelper.USER_DEL_KEY)
    @Transactional(rollbackFor = Exception.class)
    public Boolean changePwd(SysUserDto sysUserDto) {
        SysUserVo sysUserVo = mapper.queryByAccount(sysUserDto.getAccount());
        PasswordService.validatePassword(sysUserDto.getPassword(),sysUserVo.getSalt(),sysUserVo.getPassword());
        String salt = PasswordService.getSalt();
        String confirmPassword = PasswordService.encode(sysUserDto.getConfirmPassword() + salt, salt);
        SysUser sysUser = sysUserDto.getUpdateSysUser();
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getCode, sysUserDto.getCode());
        sysUser.setPassword(confirmPassword);
        sysUser.setSalt(salt);
        return mapper.update(sysUser, queryWrapper) > 0;
    }


}