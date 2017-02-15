package com.yc.mybatis;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisUtils {
    private static SqlSessionFactory factory;
    public static SqlSessionFactory getFactory(){
        if(factory == null){
            String config = "mybatis-config.xml";
            //流的操作（读取）
            InputStream inputStream = null;
            try {
                inputStream = Resources.getResourceAsStream(config);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //使用了xml的解析，解析出mybatis-config.xml中的 数据库的配置项，创建数据连接池
            factory = new SqlSessionFactoryBuilder().build(inputStream);
        }
        return factory;
    }
    
    public static SqlSession getSqlSession(){
        return getFactory().openSession();
    }
    
    public static SqlSession getSqlSession( boolean isAutoCommit){
        return getFactory().openSession(isAutoCommit);
    }
}
