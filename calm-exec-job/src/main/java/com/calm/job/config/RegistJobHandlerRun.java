package com.calm.job.config;

import com.calm.job.jobhandle.SequenceXxlJob;
import com.xxl.job.core.executor.XxlJobExecutor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author wangjunming
 * @since 2025-01-13 15:09
 */
@Component
public class RegistJobHandlerRun implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        XxlJobExecutor.registJobHandler("sequenceXxlJob", new SequenceXxlJob());
    }
}
