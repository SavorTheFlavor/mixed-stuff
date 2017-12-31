package com.me.dao;

import java.util.List;
import java.util.Map;

import com.me.bean.Message;

/**
 * 与Message配置文件相对应的接口
 */
public interface IMessage {
	//代言queryMessageList的sql语句
	public List<Message> queryMessageList(Map<String, Object> parameter);
	//根据条件查询消息条数
	public int count(Message message);
	
	public List<Message> queryMessageListByPage(Map<String, Object> parameter);
}
