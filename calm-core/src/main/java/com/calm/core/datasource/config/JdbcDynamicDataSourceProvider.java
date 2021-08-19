package com.calm.core.datasource.config;

import com.baomidou.dynamic.datasource.provider.AbstractJdbcDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.calm.core.datasource.support.DataSourceConstants;
import com.calm.parent.utils.JasyptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;


/**
 * 手动配置动态数据源，从数据库中读取数据源
 *
 * @author wangjunming
 * @since 2020/10/31 20:17
 */
@Slf4j
@Configuration
public class JdbcDynamicDataSourceProvider extends AbstractJdbcDataSourceProvider {

    private final DynamicDataSourceProperties properties;

    public JdbcDynamicDataSourceProvider(DynamicDataSourceProperties dynamicDataSourceProperties) {
        super(dynamicDataSourceProperties.getDriverClassName(), dynamicDataSourceProperties.getUrl(), dynamicDataSourceProperties.getUsername(), JasyptUtil.decyptPwd(dynamicDataSourceProperties.getPassword()));
        this.properties = dynamicDataSourceProperties;
    }

    @Autowired
    private DruidPoolConfig druidPoolConfig;

    /**
     * 解决druid 日志报错：discard long time none received connection:xxx
     */
    @PostConstruct
    public void setProperties(){
        System.setProperty("druid.mysql.usePingMethod","false");
    }

    /**
     * 执行语句获得数据源参数
     *
     * @param statement 语句
     * @return 数据源参数
     * @throws SQLException sql异常
     */
    @Override
    protected Map<String, DataSourceProperty> executeStmt(Statement statement) throws SQLException {
        log.info("配置的druid参数是：{}",druidPoolConfig);
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(properties.getQueryDsSql());
        } catch (Exception e) {
            log.error("初始化动态数据库表时，Table 'gen_datasource_conf' doesn't exist，需要执行以下SQL可解决异常信息。");
            log.info(
                    "\n\nSET NAMES utf8mb4;\n" +
                    "SET FOREIGN_KEY_CHECKS = 0;\n" +
                    "-- ----------------------------\n" +
                    "-- Table structure for gen_datasource_conf\n" +
                    "-- ----------------------------\n" +
                    "DROP TABLE IF EXISTS `gen_datasource_conf`;\n" +
                    "CREATE TABLE `gen_datasource_conf`\n" +
                    "(\n" +
                    "    `id`          int(11)                                                       NOT NULL AUTO_INCREMENT COMMENT '主键',\n" +
                    "    `name`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '数据库名',\n" +
                    "    `url`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据库连接URL',\n" +
                    "    `username`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '用户名',\n" +
                    "    `password`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '密码',\n" +
                    "    `create_date` datetime(0)                                                   NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',\n" +
                    "    `update_date` datetime(0)                                                   NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',\n" +
                    "    `del_flag`    char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      NULL DEFAULT '0',\n" +
                    "    PRIMARY KEY (`id`) USING BTREE\n" +
                    ") ENGINE = InnoDB\n" +
                    "  AUTO_INCREMENT = 1\n" +
                    "  CHARACTER SET = utf8mb4\n" +
                    "  COLLATE = utf8mb4_general_ci COMMENT = '数据源表'\n" +
                    "  ROW_FORMAT = Dynamic;\n" +
                    "SET FOREIGN_KEY_CHECKS = 1;\n"
            );
        }
        Map<String, DataSourceProperty> map = new HashMap<>(8);
        while (rs != null && rs.next()) {
            String name = rs.getString(DataSourceConstants.DS_NAME);
            String username = rs.getString(DataSourceConstants.DS_USER_NAME);
            String password = rs.getString(DataSourceConstants.DS_USER_PWD);
            String url = rs.getString(DataSourceConstants.DS_JDBC_URL);
            DataSourceProperty property = new DataSourceProperty();
            property.setDriverClassName(DataSourceConstants.DS_DRIVER);
            property.setUsername(username);
            property.setPassword(JasyptUtil.decyptPwd(password));
            property.setUrl(url);
            property.setDruid(druidPoolConfig);
            map.put(name, property);
        }

        // 添加默认主数据源
        DataSourceProperty property = new DataSourceProperty();
        property.setDriverClassName(DataSourceConstants.DS_DRIVER);
        property.setUsername(properties.getUsername());
        property.setPassword(JasyptUtil.decyptPwd(properties.getPassword()));
        property.setUrl(properties.getUrl());
        property.setDruid(druidPoolConfig);
        map.put(DataSourceConstants.DS_MASTER, property);
        return map;
    }

}
