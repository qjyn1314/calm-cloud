package com.calm.sequence.provider;

import com.calm.common.exception.CalmException;
import com.calm.sequence.persistence.service.SysSequenceService;
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
public class SysSequenceProvider {

    @Autowired
    private SysSequenceService sysSequenceService;

    /**
     * 获取对应类型的序列号
     *
     * @param sequenceType 类型
     * @param length 长度
     * @author wangjunming
     * @since 2021/4/10 14:06
     * @return java.lang.String
     */
    @GetMapping("/getSequenceNum/{sequenceType}/{length}")
    public String getSequenceNum(@PathVariable String sequenceType,@PathVariable Integer length){
        if (null != length && Arrays.asList(4, 6, 8).contains(length)) {
            throw new CalmException("超出了长度限制。");
        }
        return sysSequenceService.getSequenceNum(sequenceType,length);
    }


}
