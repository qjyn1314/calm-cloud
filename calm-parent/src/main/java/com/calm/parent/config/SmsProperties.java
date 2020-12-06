package com.calm.parent.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * 短信配置文件
 * 
 * @author wangjunming
 * @since 2020/12/6 16:30
 */
@Configuration
@ConfigurationProperties(prefix = "sms")
public class SmsProperties {
    
    private String smsType;

    /**
     * Gets sms type.
     *
     * @return the sms type
     */
    public String getSmsType() {
        return smsType;
    }


    /**
     * Sets sms type.
     *
     * @param smsType the sms type
     */
    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }
}
