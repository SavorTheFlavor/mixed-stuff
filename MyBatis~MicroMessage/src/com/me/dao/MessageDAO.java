package com.me.dao;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.me.bean.Message;
import com.me.db.DBAccess;

/**
 *��message����ص����ݿ����
 *
 */

public class MessageDAO {
	
	/**
	 * by mybatis
	 * @param command
	 * @param description
	 * @return
	 */
	private static DBAccess dbAccess = new DBAccess();//�����ļ�����Ҫÿһ�ε���DAO�����¼���
	
	public List<Message> queryMessageList(Map<String, Object> parameter){
		long start = System.currentTimeMillis();//��������
		List<Message> messageList = new ArrayList<>();
//		DBAccess dbAccess = new DBAccess();
		SqlSession sqlSession = null;
		try {
			sqlSession = dbAccess.getSqlSession();
			//��ȡIMessage�ӿڵ�ʵ���࣡
			IMessage iMessage = sqlSession.getMapper(IMessage.class);
//			messageList = sqlSession.selectList("Message.queryMessageList",message);
			messageList = iMessage.queryMessageList(parameter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(sqlSession!=null)
				sqlSession.close();
		}
		long end = System.currentTimeMillis();
		System.out.println("cost time:"+(end-start)+"ms");
		return messageList;
	}
	/**
	 * ���ʹ��������ʵ�ַ�ҳ
	 * @param parameter
	 * @return
	 */
	public List<Message> queryMessageListByPage(Map<String, Object> parameter){
		long start = System.currentTimeMillis();//��������
		List<Message> messageList = new ArrayList<>();
//		DBAccess dbAccess = new DBAccess();
		SqlSession sqlSession = null;
		try {
			sqlSession = dbAccess.getSqlSession();
			//��ȡIMessage�ӿڵ�ʵ���࣡
			IMessage iMessage = sqlSession.getMapper(IMessage.class);
//			messageList = sqlSession.selectList("Message.queryMessageList",message);
			messageList = iMessage.queryMessageListByPage(parameter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(sqlSession!=null)
				sqlSession.close();
		}
		long end = System.currentTimeMillis();
		System.out.println("cost time:"+(end-start)+"ms");
		return messageList;
	}
	
	/**
	 * ���ݲ�ѯ������ѯ��Ϣ�б������
	 */
	public int count(Message message){
		SqlSession sqlSession = null;
		int result = 0;
		try{
			sqlSession = dbAccess.getSqlSession();
			IMessage iMessage = sqlSession.getMapper(IMessage.class);
			result = iMessage.count(message);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(sqlSession != null){
				sqlSession.close();
			}
		}
		
		return result;
	}
	
	/**
	 * ����ɾ��
	 * @param id
	 */
	public void deleteOne(int id){

		SqlSession sqlSession = null;
		try {
			sqlSession = dbAccess.getSqlSession();
			sqlSession.delete("Message.deleteOne",id);
			sqlSession.commit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(sqlSession!=null)
				sqlSession.close();
		}
	}
	/**
	 * ����ɾ��
	 * @param idList
	 */
	public void deleteBatch(List<Integer> idList){

		SqlSession sqlSession = null;
		try {
			sqlSession = dbAccess.getSqlSession();
			sqlSession.delete("Message.deleteBatch",idList);
			sqlSession.commit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(sqlSession!=null)
				sqlSession.close();
		}
	}
	/**
	 * ��������
	 */
	public void insertOne(Message message){

		SqlSession sqlSession = null;
		try {
			sqlSession = dbAccess.getSqlSession();
			sqlSession.insert("insertOne",message);
			sqlSession.commit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(sqlSession!=null)
				sqlSession.close();
		}
	}
	
	
	
	///*by JDBC*/
//	public List<Message> queryMessageList(String command, String description){
//		List<Message> messageList = new ArrayList<>();
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			Connection conn =	DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/micro_message","root","23456");
//			StringBuilder sql = new StringBuilder("select ID,COMMAND,DESCRIPTION,CONTENT from MESSAGE where 1=1");
//			List<String> paramList = new ArrayList<>();
//			if(command != null && !"".equals(command)){
//				sql.append(" and COMMAND=?");
//				paramList.add(command);
//			}
//			if(description != null && !"".equals(description)){
//				sql.append("  and DESCRIPTION like '%' ? '%'");
//				paramList.add(description);
//			}
//			PreparedStatement statement = conn.prepareStatement(sql.toString());
//			for (int i = 0; i < paramList.size(); i++) {
//				statement.setString(i+1, paramList.get(i));
//			}
//			ResultSet rs = statement.executeQuery();
//			
//			while(rs.next()){
//				Message message = new Message();
//				messageList.add(message);
//				message.setId(rs.getString("ID"));
//				message.setCommand(rs.getString("COMMAND"));
//				message.setDescription(rs.getString("DESCRIPTION"));
//				message.setContent(rs.getString("CONTENT"));
//			}
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return messageList;
//	}
}
