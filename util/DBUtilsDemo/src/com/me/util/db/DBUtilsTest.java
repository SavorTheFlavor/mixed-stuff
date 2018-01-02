package com.me.util.db;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.me.bean.User;

public class DBUtilsTest {
	public void queryTest() throws SQLException{
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from user where user_id = ?";
		ResultSetHandler<User> resultSetHandler = new BeanHandler<>(User.class);
		User user = queryRunner.query(sql, resultSetHandler,"5");
		System.err.println(user);
	}
	
	public void queryTest2() throws SQLException{
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from user";
		List<User> users = queryRunner.query(sql,  new BeanListHandler<User>(User.class));
		for (User user2 : users) {
			System.err.println(user2);
		}
	}
	
	public void queryTestMapList() throws SQLException{
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from user";
		List<Map<String, Object>> maps = queryRunner.query(sql,  new MapListHandler());
		for (Map<String, Object> map : maps) {
			System.out.println(map);
		}
	}
	
	public void queryTestColumnList() throws SQLException{
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select user_name from user";
		List<Object> cols = queryRunner.query(sql,  new ColumnListHandler<>());
		System.out.println(cols);
	}
	
	public void queryTestScalar() throws SQLException{
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select count(distinct user_name) from user";
		//±Í¡ø...
		Number number = (Number)queryRunner.query(sql,  new ScalarHandler());
		System.out.println(number.intValue());
	}
	
	public void updateTest() throws SQLException{
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "update user set password=?, phone = ?, vip_id = ? where user_id = ?";
		//String sql = "delete from user where user_id = ?";
		queryRunner.update(sql, "2222","1111111","6","1");
	}
	
	public static void main(String[] args) throws SQLException {
		DBUtilsTest test = new DBUtilsTest();
		test.updateTest();
	}
}
