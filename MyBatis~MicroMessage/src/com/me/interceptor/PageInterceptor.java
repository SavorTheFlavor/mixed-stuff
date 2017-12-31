package com.me.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import com.me.entity.Page;

/**
 * 分页拦截器
 *拦截mybatis源码里的preparestatement语句所在的方法，改造原来的sql语句，换成具有分页功能的语句
 *
 *假如有一个车票代购公司，它需要拦下每一个需要去车票的人，自己买好票，最后再交回主权
 */
@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class})})
public class PageInterceptor implements Interceptor{

	/**
	 * 经过此方法的人都是需要买票的人
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		//需要检查方法的id...不然容易拦错
		StatementHandler statementHandler = 
				(StatementHandler) invocation.getTarget();
		//mybatis提供的....通过反射获取StatementHandler里的protected对象
		MetaObject metaObject = 
				MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY);
		//配置文件里各个属性封装在这个类里
		MappedStatement 	mappedStatement =
				(MappedStatement)metaObject.getValue("delegate.mappedStatement");//先拦到RoutingStatementHandler,再通过delegate到BaseStatementHandler
		
		String id = mappedStatement.getId();//配置文件里的方法id
		
		if(id.matches(".+ByPage$")){//约定以ByPage结尾的需要分页功能
			//改造sql
			BoundSql boundSql = statementHandler.getBoundSql();//包括传给配置文件的参数
			String sql = boundSql.getSql();//原始的sql语句
			
			//查询总条数的SQL语句
			String countSql = "select count(*) from ("+sql+")a";//子查询

			Connection conn = (Connection) invocation.getArgs()[0];//获取拦截方法的参数
			PreparedStatement countStatement = (PreparedStatement) conn.prepareStatement(countSql);
			//设置sql参数?
			ParameterHandler parameterHandler = (ParameterHandler)metaObject.getValue("delegate.parameterHandler");
			parameterHandler.setParameters(countStatement);//为countStatement设置参数
			ResultSet rs = countStatement.executeQuery();
			
			@SuppressWarnings("unchecked")
			Map<String, Object> parameter = 
					(Map<String, Object>)boundSql.getParameterObject();//获取参数
			Page page = (Page) parameter.get("page");//获取page对象
			if(rs.next()){
				page.setTotalNumber(rs.getInt(1));//终于拿到了总条数！射到page里面！
			}
			
			String pageSql = sql+" limit "+page.getDbIndex()+","+page.getDbNumber();//带分页功能的sql语句
			metaObject.setValue("delegate.boundSql.sql", pageSql);//ognl表达式

		}
		
		return invocation.proceed();//交回主权，继续执行
		
	}

	
	@Override
	public Object plugin(Object target) {
		//在里面根据注解检查符合代购条件的哥们，不是的话让他们溜走
		return Plugin.wrap(target, this);//this是这个代购公司的人，在没有进去wrap方法前没有代购权利
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
		
	}

}
