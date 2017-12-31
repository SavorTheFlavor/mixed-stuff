package com.me.homework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.me.bean.Message;

public class IMessageInvoHandler implements InvocationHandler {

	/**
	 * 最终调用select...的方法
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		System.out.println("select ....... from ...........................");
		
		List<Message> list = new ArrayList<>();
		list.add(new Message("3", "sdsd", "sdfdsfd", "dsfsfsd"));
		list.add(new Message("3", "sdsd", "sdfdsfd", "dsfsfsd"));
		list.add(new Message("3", "sdsd", "sdfdsfd", "dsfsfsd"));
		list.add(new Message("3", "sdsd", "sdfdsfd", "dsfsfsd"));
		return list;
	}


}
