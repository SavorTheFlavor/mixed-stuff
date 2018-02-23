package com.me.classLoader;

public class Test {
	public static void main(String[] args) {
		new Thread(new MsgHandler()).start();
	}
}
