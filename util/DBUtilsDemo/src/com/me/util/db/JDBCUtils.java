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
     *  �������ݿ����ӵ������ļ�������
     */
    static{
        FileInputStream fis = null;
        
        Properties env = new Properties();
        try {
        	InputStream configIS = JDBCUtils.class.getClassLoader().getResourceAsStream("dbconfig.properties");
            //���������ļ��е����ݿ�������Ϣ
            //��=�����Ϊkeyֵ���ұ���Ϊvalueֵ
            env.load(configIS); 
            
            //1. ����������
            Class.forName(env.getProperty("jdbc.driver"));
            
            url = env.getProperty("jdbc.url");
            username = env.getProperty("jdbc.username");
            pwd = env.getProperty("jdbc.pwd");
            
            //�����������ݿ��������Ϣ
            DataSource ds_unpooled = DataSources
                    .unpooledDataSource(url, username, pwd);
            
            
            
            Map<String, Object> pool_conf = new HashMap<String, Object>();
            //�������������
            pool_conf.put("maxPoolSize", 20);
            //���ӳ�Ӧ�ñ��е���С���ӵ�����
            pool_conf.put("minPoolSize", 2);
            //��ʼ�����ӳ�ʱ����ȡ�����Ӹ���
            pool_conf.put("initialPoolSize", 10);
            //�����ӳ����Ѿ�û������ʱ�����ӳ��Զ���ȡ����ʱһ�λ�ȡ�����Ӹ���
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
     *  ��ȡ���Ӷ���
     */
    public static Connection getConnection()  {
        // 2. �������ӵ�url,username,pwd
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
     * �ͷ����ӳ���Դ
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
     * ��Դ�ر�
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