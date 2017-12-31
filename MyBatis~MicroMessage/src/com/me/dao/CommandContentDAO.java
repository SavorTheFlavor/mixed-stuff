package com.me.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.me.bean.Command;
import com.me.bean.CommandContent;
import com.me.bean.Message;
import com.me.db.DBAccess;

/**
 * 与指令表对应的数据库操作
 * @author Administrator
 *
 */
public class CommandContentDAO {

	private static DBAccess dbAccess = new DBAccess();
	
	public void insertBatch(List<CommandContent> contentList){
		SqlSession sqlSession = null;
		try {
			sqlSession = dbAccess.getSqlSession();
			ICommandContent iCommandContent = 
					sqlSession.getMapper(ICommandContent.class);
			iCommandContent.insertBatch(contentList);
			sqlSession.commit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(sqlSession!=null)
				sqlSession.close();
		}
	}
	
}
