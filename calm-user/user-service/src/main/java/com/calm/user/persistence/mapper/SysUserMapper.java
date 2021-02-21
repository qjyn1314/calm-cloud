package com.calm.user.persistence.mapper;

import com.calm.user.api.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.calm.user.api.vo.SysUserVo;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 用户表(SysUser)表数据库访问层
 *
 * @author wangjunming
 * @since 2021-02-15 17:17:55
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser>{


    /**
     * 通过账号查询用户信息
     *
     * @author wangjunming
     * @since 2021/2/15 17:33
     */
    SysUserVo queryByAccount(String account);
}