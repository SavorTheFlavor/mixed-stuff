package com.me.classLoader;
/**
 * 每隔一秒检测一次是否
 * @author Administrator
 *
 */
public class MsgHandler implements Runnable {

	@Override
	public void run() {
		while(true){
			BaseManager manager = ManagerFactory.getManager(ManagerFactory.MY_MANAGER);
			manager.logic();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
