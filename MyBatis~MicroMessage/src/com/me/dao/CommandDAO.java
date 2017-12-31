package com.me.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.me.bean.Command;
import com.me.bean.Message;
import com.me.db.DBAccess;

/**
 * 与指令表对应的数据库操作
 * @author Administrator
 *
 */
public class CommandDAO {

	private static DBAccess dbAccess = new DBAccess();
	
	public List<Command> queryCommandList(String command, String description){
		List<Command> commandList = new ArrayList<>();
		
		Command commandObj = new Command();
		commandObj.setName(command);
		commandObj.setDescription(description);
		
		SqlSession sqlSession = null;
		try {
			sqlSession = dbAccess.getSqlSession();
			commandList = sqlSession.selectList("Command.queryCommandList",commandObj);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(sqlSession!=null)
				sqlSession.close();
		}
		return commandList;
	}
	
}
