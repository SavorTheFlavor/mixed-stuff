package com.me.util.db;

import java.io.File;
import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.DataSources;

public class JDBCUtils {
    
    private static String url = null;
    
    private static String username = null;
    
    private static String pwd = null;
    
    private static DataSource ds_pooled;
    /** 
     *  加载数据库连接的配置文件和驱动
     */
    static{
        FileInputStream fis = null;
        
        Properties env = new Properties();
        try {
        	InputStream configIS = JDBCUtils.class.getClassLoader().getResourceAsStream("dbconfig.properties");
            //加载属性文件中的数据库配置信息
            //以=左边作为key值，右边作为value值
            env.load(configIS); 
            
            //1. 加载驱动类
            Class.forName(env.getProperty("jdbc.driver"));
            
            url = env.getProperty("jdbc.url");
            username = env.getProperty("jdbc.username");
            pwd = env.getProperty("jdbc.pwd");
            
            //设置连接数据库的配置信息
            DataSource ds_unpooled = DataSources
                    .unpooledDataSource(url, username, pwd);
            
            
            
            Map<String, Object> pool_conf = new HashMap<String, Object>();
            //设置最大连接数
            pool_conf.put("maxPoolSize", 20);
            //连接池应该保有的最小连接的数量
            pool_conf.put("minPoolSize", 2);
            //初始化连接池时，获取的连接个数
            pool_conf.put("initialPoolSize", 10);
            //当连接池中已经没有连接时，连接池自动获取连接时一次获取的连接个数
            pool_conf.put("acquireIncrement", 3);
            ds_pooled = DataSources.pooledDataSource(ds_unpooled,
                    pool_conf);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     *  获取连接对象
     */
    public static Connection getConnection()  {
        // 2. 设置连接的url,username,pwd
        Connection connection = null;
        try {
            connection = ds_pooled.getConnection();
            //connection.prepareStatement("set names utf8mb4").executeQuery();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }
    
    public static DataSource getDataSource() {
		return ds_pooled;
	}
    
    /**
     * 释放连接池资源
     */
    public static void clearup(){
        if(ds_pooled != null){
            try {
                DataSources.destroy(ds_pooled);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    /**
     * 资源关闭
     * 
     * @param rs
     * @param stmt
     * @param conn
     */
    public static void close(ResultSet rs, Statement stmt
            , Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}