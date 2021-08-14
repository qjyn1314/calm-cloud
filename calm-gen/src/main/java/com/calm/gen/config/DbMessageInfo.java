package com.calm.gen.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * explain:
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/9 20:59
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class DbMessageInfo implements Serializable {
    String driverClassName = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://127.0.0.1:3306/calm?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&serverTimezone=UTC";
    String username = "root";
    String password = "123456";
}
