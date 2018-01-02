package com.me.tx;

import java.sql.Connection;
import java.sql.SQLException;

import com.me.util.db.JDBCUtils;

public class TransactionManager {
	//线程局部变量(只属于当前线程的变量....
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
	
	public static Connection getConnection(){
		Connection connection = threadLocal.get();
		if(connection == null){
			connection = JDBCUtils.getConnection();
			threadLocal.set(connection);
		}
		return connection;
	}
	
	public static void startTransaction(){
		Connection connection = getConnection();
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void commit(){
		Connection connection = getConnection();
		try {
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void rollback(){
		Connection connection = getConnection();
		try {
			connection.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void release(){
		Connection connection = getConnection();
		try {
			connection.close();
			threadLocal.remove();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
