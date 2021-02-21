package com.calm.parent.config;

/**
 * <p>
 * explain:
 * 1、SpringCloud确保服务只能通过gateway转发访问，禁止直接调用接口访问
 *   参考： https://blog.csdn.net/Hpsyche/article/details/102926010
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/21 19:10
 */
public interface ForwardAccessService {

    String HEADER_KEY = "Calm_GateWay_Header_key";
    String HEADER_VALUE = "Calm_GateWay_Header_value";

}
