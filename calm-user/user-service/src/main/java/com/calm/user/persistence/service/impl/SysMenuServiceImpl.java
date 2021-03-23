package com.calm.user.persistence.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.calm.user.api.dto.SysMenuDto;
import com.calm.user.api.vo.SysMenuVo;
import com.calm.user.persistence.mapper.SysMenuMapper;
import com.calm.user.persistence.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 菜单表(SysMenu)表服务实现类
 *
 * @author wangjunming
 * @since 2021-03-13 21:55:51
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper mapper;


    /**
     * 分页列表
     *
     * @param sysMenuDto 查询对象
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.calm.user.api.vo.SysMenuVo>
     * @author wangjunming
     * @since 2021/3/14 14:17
     */
    @Override
    public IPage<SysMenuVo> page(SysMenuDto sysMenuDto) {
        Page<SysMenuVo> page = new Page<>(sysMenuDto.getCurrent(), sysMenuDto.getPageSize());
        return mapper.page(page, sysMenuDto);
    }
}