package com.calm.execute.sequence;

import com.calm.sequence.api.enums.SequenceType;
import com.calm.sequence.api.feign.SequenceFeignService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 * explain: 序号服务对应的
 * </p>
 *
 * @author wangjunming
 * @since 2021/7/18 10:14
 */
@Component
public class SequenceHandle extends IJobHandler {

    @Autowired
    private SequenceFeignService sequenceFeignService;

    @Override
    public void execute() throws Exception {
        XxlJobHelper.log("XXL-JOB,使用 SequenceFeignService 生成序列号......");
        Integer length = 5;
        String sequenceNum = sequenceFeignService.getSequenceNum(SequenceType.XXL_JOB.getCode(), length);
        XxlJobHelper.log("XXL-JOB,使用 SequenceFeignService 生成序列号...::...{}",sequenceNum);
    }
}
