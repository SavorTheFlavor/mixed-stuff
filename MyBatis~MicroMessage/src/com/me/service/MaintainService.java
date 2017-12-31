package com.me.service;

import java.util.ArrayList;
import java.util.List;

import com.me.bean.Message;
import com.me.dao.MessageDAO;

/**
 * 维护相关业务功能
 *
 */
public class MaintainService {
	/**
	 * 单条删除
	 * @param id
	 */
	public void deleteOne(String id){
		if(id != null && !"".equals(id.trim())){
			MessageDAO messageDAO = new MessageDAO();
			messageDAO.deleteOne(Integer.valueOf(id));
		}
	}
	/**
	 * 批量删除
	 * @param messageList
	 */
	public void deleteBatch(String[] ids){
		
		List<Integer> idList = new ArrayList<>();
		for (String id : ids) {
			idList.add(Integer.valueOf(id));
		}
		
		MessageDAO messageDAO = new MessageDAO();
		messageDAO.deleteBatch(idList);
	}
	
	/**
	 * 单条添加
	 */
	public void insertOne(String message){
		
		if(message != null && !"".equals(message)){
			
			String[] s = message.split(" ");
			Message m = new Message();
			switch(s.length){
				case 1:
					m.setCommand(s[0]);
					break;
				case 2:			
					m.setCommand(s[0]);
					m.setDescription(s[1]);
					break;
				default:
					m.setCommand(s[0]);
					m.setDescription(s[1]);
					m.setContent(s[2]);
			}
			MessageDAO messageDAO = new MessageDAO();
			messageDAO.insertOne(m);
		}
	}
}
