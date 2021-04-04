package com.calm.user.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.calm.user.api.dto.SysMenuDto;
import com.calm.user.api.entity.SysMenu;
import com.calm.user.api.vo.MenuTreeVo;
import com.calm.user.api.vo.SysMenuVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜单表(SysMenu)表数据库访问层
 *
 * @author wangjunming
 * @since 2021-03-13 21:55:51
 */
@Repository
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    IPage<SysMenuVo> page(Page<SysMenuVo> page, @Param("qo") SysMenuDto sysMenuDto);

    List<MenuTreeVo> selectByCode(@Param("code") String code);

    SysMenuVo selectMenuByCode(@Param("code") String code);

}