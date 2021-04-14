package com.calm.user.api.vo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * explain: 菜单和角色的返回树结构
 * </p>
 *
 * @author wangjunming
 * @since 2021/3/25 11:50
 */
@Data
public class DTreeVo implements Serializable {
    private static final ArrayList<HashMap<String, String>> CHECK_ARR_LIST = CollUtil.newArrayList();
    private static final HashMap<String, String> CHECK_ARR_MAP = CollUtil.newHashMap();

    static {
        CHECK_ARR_MAP.put("type", "0");
        CHECK_ARR_MAP.put("checked", "0");
        CHECK_ARR_LIST.add(CHECK_ARR_MAP);
    }

    /**菜单code*/
    private String id;
    /**菜单名称*/
    private String title;
    /**父级code*/
    private String parentId;
    /**复选框必填项用来返回前段展示复选框*/
    private String checkArr;
    /**其下的子级*/
    List<DTreeVo> children;

    public String getCheckArr() {
        return JSONUtil.toJsonStr(CHECK_ARR_LIST);
    }

}
