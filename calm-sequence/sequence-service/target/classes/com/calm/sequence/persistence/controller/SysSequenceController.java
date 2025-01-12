package com.calm.sequence.persistence.controller;

import com.calm.parent.base.JsonResult;
import com.calm.sequence.api.dto.SysSequenceDto;
import com.calm.sequence.api.entity.SysSequence;
import com.calm.sequence.persistence.service.SysSequenceService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * explain:
 * </p>
 *
 * @author wangjunming
 * @since 2021/4/10 14:00
 */
@RestController
@RequestMapping
@Tag(name = "序列号控制层")
public class SysSequenceController {

    @Autowired
    private SysSequenceService sysSequenceService;

    /**
     * 获取对应类型的序列号
     *
     * @return java.lang.String
     * @author wangjunming
     * @since 2021/4/10 14:06
     */
    @Operation(description = "获取对应类型的序列号")
    @PostMapping("/sequencePage")
    public JsonResult<PageInfo<SysSequence>> sequencePage(@RequestBody SysSequenceDto sysSequence) {
        PageInfo<SysSequence> pageInfo = sysSequenceService.sequencePage(sysSequence);
        return JsonResult.success(pageInfo);
    }


}
