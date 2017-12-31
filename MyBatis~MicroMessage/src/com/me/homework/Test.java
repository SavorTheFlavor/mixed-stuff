package com.me.homework;

import java.util.List;

import com.me.bean.Message;

public class Test {
	public static void main(String[] args) {
		
		SqlSession sqlSession = new SqlSession();
		IMessage iMessage = sqlSession.getMapper(IMessage.class);
		List<Message> list = iMessage.queryMessageList();
		System.out.println(list);
	}
}
