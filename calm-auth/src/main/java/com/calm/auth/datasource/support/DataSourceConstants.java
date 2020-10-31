package com.calm.auth.datasource.support;

/**
 * 数据源初始化常量
 *
 * @author wangjunming
 * @since 2020/10/31 20:10
 */
public interface DataSourceConstants {

	/**
	 * 数据源名称
	 */
	String DS_NAME = "name";

	/**
	 * 默认驱动
	 */
	String DS_DRIVER = "com.mysql.cj.jdbc.Driver";

	/**
	 * 默认数据源（master）
	 */
	String DS_MASTER = "master";

	/**
	 * jdbcurl
	 */
	String DS_JDBC_URL = "url";

	/**
	 * 用户名
	 */
	String DS_USER_NAME = "username";

	/**
	 * 密码
	 */
	String DS_USER_PWD = "password";

}
