package com.me.classLoader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ManagerFactory {
	//根据文件的lastModified是否改变来判断是否要重新加载
	private static Map<String, LoadInfo> loadTimeMap = 
			new HashMap<String, LoadInfo>(); 
	public static final String CLASSPATH = "E:/workplace/classLoader/bin/";
	public static final String MY_MANAGER = "com.me.classLoader.MyManager";
	
	public static BaseManager getManager(String className) {
		File file = new File(CLASSPATH+className.replaceAll("\\.", "/")+".class");
		long lastModified = file.lastModified();
		if(loadTimeMap.get(className) == null){
			load(className, lastModified);
		}else if(loadTimeMap.get(className).getLoadTime() != lastModified){
			load(className, lastModified);
		}
		return loadTimeMap.get(className).getManager();
	}

	private static void load(String className, long lastModified) {
		MyClassLoader myClassLoader = new MyClassLoader(CLASSPATH);
		Class<?> loadClass = null;
		try {
			loadClass = myClassLoader.findClass(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		BaseManager manager = newInstance(loadClass);
		LoadInfo loadInfo = new LoadInfo(myClassLoader, lastModified);
		loadInfo.setManager(manager);
		loadTimeMap.put(className, loadInfo);
	}

	private static BaseManager newInstance(Class<?> loadClass) {
		try {
			return (BaseManager) loadClass.getConstructor(new Class[]{})
					.newInstance(new Object[]{});
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
