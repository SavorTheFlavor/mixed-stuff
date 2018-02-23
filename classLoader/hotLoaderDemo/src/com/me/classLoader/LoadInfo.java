package com.me.classLoader;
/**
 *  ������Ϣ����¼����ʱ��
 * @author Administrator
 *
 */
public class LoadInfo {
	private MyClassLoader myClassLoader;
	private long loadTime; //the timestamp when the class was loaded
	private BaseManager manager;//Ҫ�ȼ��ص���
	
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
