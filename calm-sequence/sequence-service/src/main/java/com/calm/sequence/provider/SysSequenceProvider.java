package com.calm.sequence.provider;

import com.calm.core.exception.CalmException;
import com.calm.core.log.ReqLog;
import com.calm.sequence.persistence.service.SysSequenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * <p>
 * explain: 暴露的接口
 * </p>
 *
 * @author wangjunming
 * @since 2021/4/10 14:01
 */
@RestController
@RequestMapping("/p")
@Tag(name = "对外暴露的feign接口")
public class SysSequenceProvider {

    @Autowired
    private SysSequenceService sysSequenceService;

    /**
     * 获取对应类型的序列号
     *
     * @param sequenceType 类型
     * @param length       长度
     * @return java.lang.String
     * @author wangjunming
     * @since 2021/4/10 14:06
     */
    @ReqLog
    @Operation(description = "获取对应类型的序列号")
    @GetMapping("/getSequenceNum/{sequenceType}/{length}")
    public String getSequenceNum(@PathVariable String sequenceType, @PathVariable Integer length) {
        if (null != length && Arrays.asList(4, 6, 8).contains(length)) {
            throw new CalmException("超出了长度限制。");
        }
        return sysSequenceService.getSequenceNum(sequenceType, length);
    }


}
