package com.me.homework;

import java.lang.reflect.Proxy;

public class SqlSession {

	public <T> T getMapper(Class<T> paramClass){
		return (T) Proxy.newProxyInstance(paramClass.getClassLoader(), new Class[]{paramClass},new IMessageInvoHandler());
	}
}
