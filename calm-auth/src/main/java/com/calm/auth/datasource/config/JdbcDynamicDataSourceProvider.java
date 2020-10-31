package com.calm.auth.datasource.config;

import com.baomidou.dynamic.datasource.provider.AbstractJdbcDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.calm.auth.datasource.support.DataSourceConstants;
import com.calm.auth.util.JasyptUtil;
import org.springframework.context.annotation.Configuration;

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
@Configuration
public class JdbcDynamicDataSourceProvider extends AbstractJdbcDataSourceProvider {

    private final DynamicDataSourceProperties properties;

    public JdbcDynamicDataSourceProvider(DynamicDataSourceProperties properties) {
        super(properties.getDriverClassName(), properties.getUrl(), properties.getUsername(), JasyptUtil.decyptPwd(properties.getPassword()));
        this.properties = properties;
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
        ResultSet rs = statement.executeQuery(properties.getQueryDsSql());
        Map<String, DataSourceProperty> map = new HashMap<>(8);
        while (rs.next()) {
            String name = rs.getString(DataSourceConstants.DS_NAME);
            String username = rs.getString(DataSourceConstants.DS_USER_NAME);
            String password = rs.getString(DataSourceConstants.DS_USER_PWD);
            String url = rs.getString(DataSourceConstants.DS_JDBC_URL);
            DataSourceProperty property = new DataSourceProperty();
            property.setDriverClassName(DataSourceConstants.DS_DRIVER);
            property.setUsername(username);
            property.setPassword(JasyptUtil.decyptPwd(password));
            property.setUrl(url);
            map.put(name, property);
        }

        // 添加默认主数据源
        DataSourceProperty property = new DataSourceProperty();
        property.setUsername(properties.getUsername());
        property.setPassword(JasyptUtil.decyptPwd(properties.getPassword()));
        property.setUrl(properties.getUrl());
        property.setDriverClassName(DataSourceConstants.DS_DRIVER);
        map.put(DataSourceConstants.DS_MASTER, property);
        return map;
    }

}
