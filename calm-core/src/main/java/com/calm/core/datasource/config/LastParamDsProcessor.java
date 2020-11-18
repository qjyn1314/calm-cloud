package com.calm.core.datasource.config;

import com.baomidou.dynamic.datasource.processor.DsProcessor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.annotation.Configuration;

/**
 * 参数数据源解析 @DS("#last)
 *
 * @author wangjunming
 * @since 2020/10/31 20:16
 */
@Configuration
public class LastParamDsProcessor extends DsProcessor {

	private static final String LAST_PREFIX = "#last";

	/**
	 * 抽象匹配条件 匹配才会走当前执行器否则走下一级执行器
	 * @param key DS注解里的内容
	 * @return 是否匹配
	 */
	@Override
	public boolean matches(String key) {
		return key.startsWith(LAST_PREFIX);
	}

	/**
	 * 抽象最终决定数据源
	 * @param invocation 方法执行信息
	 * @param key DS注解里的内容
	 * @return 数据源名称
	 */
	@Override
	public String doDetermineDatasource(MethodInvocation invocation, String key) {
		Object[] arguments = invocation.getArguments();
		return String.valueOf(arguments[arguments.length - 1]);
	}

}
