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
 * ��ҳ������
 *����mybatisԴ�����preparestatement������ڵķ���������ԭ����sql��䣬���ɾ��з�ҳ���ܵ����
 *
 *������һ����Ʊ������˾������Ҫ����ÿһ����Ҫȥ��Ʊ���ˣ��Լ����Ʊ������ٽ�����Ȩ
 */
@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class})})
public class PageInterceptor implements Interceptor{

	/**
	 * �����˷������˶�����Ҫ��Ʊ����
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		//��Ҫ��鷽����id...��Ȼ��������
		StatementHandler statementHandler = 
				(StatementHandler) invocation.getTarget();
		//mybatis�ṩ��....ͨ�������ȡStatementHandler���protected����
		MetaObject metaObject = 
				MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY);
		//�����ļ���������Է�װ���������
		MappedStatement 	mappedStatement =
				(MappedStatement)metaObject.getValue("delegate.mappedStatement");//������RoutingStatementHandler,��ͨ��delegate��BaseStatementHandler
		
		String id = mappedStatement.getId();//�����ļ���ķ���id
		
		if(id.matches(".+ByPage$")){//Լ����ByPage��β����Ҫ��ҳ����
			//����sql
			BoundSql boundSql = statementHandler.getBoundSql();//�������������ļ��Ĳ���
			String sql = boundSql.getSql();//ԭʼ��sql���
			
			//��ѯ��������SQL���
			String countSql = "select count(*) from ("+sql+")a";//�Ӳ�ѯ

			Connection conn = (Connection) invocation.getArgs()[0];//��ȡ���ط����Ĳ���
			PreparedStatement countStatement = (PreparedStatement) conn.prepareStatement(countSql);
			//����sql����?
			ParameterHandler parameterHandler = (ParameterHandler)metaObject.getValue("delegate.parameterHandler");
			parameterHandler.setParameters(countStatement);//ΪcountStatement���ò���
			ResultSet rs = countStatement.executeQuery();
			
			@SuppressWarnings("unchecked")
			Map<String, Object> parameter = 
					(Map<String, Object>)boundSql.getParameterObject();//��ȡ����
			Page page = (Page) parameter.get("page");//��ȡpage����
			if(rs.next()){
				page.setTotalNumber(rs.getInt(1));//�����õ������������䵽page���棡
			}
			
			String pageSql = sql+" limit "+page.getDbIndex()+","+page.getDbNumber();//����ҳ���ܵ�sql���
			metaObject.setValue("delegate.boundSql.sql", pageSql);//ognl���ʽ

		}
		
		return invocation.proceed();//������Ȩ������ִ��
		
	}

	
	@Override
	public Object plugin(Object target) {
		//���������ע������ϴ��������ĸ��ǣ����ǵĻ�����������
		return Plugin.wrap(target, this);//this�����������˾���ˣ���û�н�ȥwrap����ǰû�д���Ȩ��
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
		
	}

}
