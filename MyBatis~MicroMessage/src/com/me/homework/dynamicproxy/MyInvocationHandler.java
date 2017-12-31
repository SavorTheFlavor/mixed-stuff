package com.me.homework.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyInvocationHandler implements InvocationHandler {

	private Object target;//��Ҫ������ļһ�
	
	public MyInvocationHandler(Object target) {
		this.target = target;
	}
	
	/** 
     * ִ��Ŀ�����ķ��� 
     */ 
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // ��Ŀ�����ķ���ִ��֮ǰ�򵥵Ĵ�ӡһ��  
        System.out.println("------------------before------------------");  
          
        // ִ��Ŀ�����ķ���  
        Object result = method.invoke(target, args);  
          
        // ��Ŀ�����ķ���ִ��֮��򵥵Ĵ�ӡһ��  
        System.out.println("-------------------after------------------");  
          
        return result;
	}
	
	/**
	 * ��ȡ��æ����ĸ���
	 */
	public Object getProxy(){
		return Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces() , this);
	}

}
