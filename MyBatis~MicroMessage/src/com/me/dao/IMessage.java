package com.me.dao;

import java.util.List;
import java.util.Map;

import com.me.bean.Message;

/**
 * ��Message�����ļ����Ӧ�Ľӿ�
 */
public interface IMessage {
	//����queryMessageList��sql���
	public List<Message> queryMessageList(Map<String, Object> parameter);
	//����������ѯ��Ϣ����
	public int count(Message message);
	
	public List<Message> queryMessageListByPage(Map<String, Object> parameter);
}
