package com.me.classLoader;
/**
 *  加载信息，记录加载时间
 * @author Administrator
 *
 */
public class LoadInfo {
	private MyClassLoader myClassLoader;
	private long loadTime; //the timestamp when the class was loaded
	private BaseManager manager;//要热加载的类
	
	public LoadInfo(MyClassLoader myClassLoader, long loadTime) {
		this.myClassLoader = myClassLoader;
		this.loadTime = loadTime;
	}

	public void setManager(BaseManager manager) {
		this.manager = manager;
	}

	public long getLoadTime() {
		return loadTime;
	}

	public BaseManager getManager() {
		return manager;
	}
}
