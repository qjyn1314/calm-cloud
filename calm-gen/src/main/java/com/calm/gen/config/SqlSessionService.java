package com.calm.gen.config;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class SqlSessionService {

    private SqlSessionService() {
    }

    public static SqlSessionService me() {
        return new SqlSessionService();
    }

    @SafeVarargs
    public final <T> SqlSession handleSession(DbMessageInfo dbMessageInfo, Class<T>... classList) {
        MybatisGenConfig mybatisGenConfig = new MybatisGenConfig(dbMessageInfo);
        SqlSessionFactory sqlSessionFactory = mybatisGenConfig.initMybatisSqlSessionFactory(classList);
        return sqlSessionFactory.openSession();
    }

}
