package com.me.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.me.bean.Command;
import com.me.bean.CommandContent;
import com.me.bean.Message;
import com.me.dao.CommandDAO;
import com.me.dao.MessageDAO;
import com.me.entity.Page;
import com.me.util.Iconst;

/**
 * 查询相关的业务功能
 */
public class QueryService {
	public List<Message> queryMessageList(String command,String description, Page page) {
		
		//组织消息对象
		Message message = new Message();
		message.setCommand(command);
		message.setDescription(description);
		
		MessageDAO messageDAO = new MessageDAO();
		//根据条件查询条数
		int totalNumber = messageDAO.count(message);
		//组织分页查询参数
		page.setTotalNumber(totalNumber);
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("message", message);
		parameter.put("page", page);
	
		// 分页查询并返回结果
		return messageDAO.queryMessageList(parameter);
	}
	
	public List<Message> queryMessageListByPage(String command,String description, Page page) {
		
		//组织消息对象
		Message message = new Message();
		message.setCommand(command);
		message.setDescription(description);
		
		MessageDAO messageDAO = new MessageDAO();
		//根据条件查询条数
//		int totalNumber = messageDAO.count(message);
		//组织分页查询参数
//		page.setTotalNumber(totalNumber);
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("message", message);
		parameter.put("page", page);
	
		// 分页查询并返回结果
		return messageDAO.queryMessageListByPage(parameter);
	}
	
	/**
	 * 通过指令查询自动回复的内容
	 * @param command 指令
	 * @return 自动回复的内容
	 */
	public String queryByCommand(String command) {
		CommandDAO commandDAO = new CommandDAO();
		List<Command> commandList;
		if(Iconst.HELP_COMMAND.equals(command)) {
			commandList = commandDAO.queryCommandList(null, null);
			StringBuilder result = new StringBuilder();
			for(int i = 0; i < commandList.size(); i++) {
				if(i != 0) {
					result.append("<br/>");
				}
				result.append("回复[" + commandList.get(i).getName() + "]可以查看" + commandList.get(i).getDescription());
			}
			return result.toString();
		}
		commandList = commandDAO.queryCommandList(command, null);
		if(commandList.size() > 0) {
			List<CommandContent> contentList = commandList.get(0).getContentList();
			int i = new Random().nextInt(contentList.size());
			return contentList.get(i).getContent();
		}
		return Iconst.NO_MATCHING_CONTENT;
	}
}
