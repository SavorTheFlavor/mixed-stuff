package com.me.classLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MyClassLoader extends ClassLoader{
	// the path where the classes want to be loaded 
	private String classpath;

	public MyClassLoader(String classpath) {
		//init parent loader
		super(ClassLoader.getSystemClassLoader());
		this.classpath = classpath;
	}

	/**
	 * 自定义的类加载方法
	 */
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] data = this.loadClassData(name);
		return this.defineClass(name,data, 0, data.length);
	}

	/**
	 *  加载class文件
	 * @param name
	 * @return byte[]
	 */
	private byte[] loadClassData(String name) {
		name = name.replace(".", "/");//qualified name to path
		
		//try resouce....if it is closable..
		try(FileInputStream fileInputStream = 
					new FileInputStream(new File(classpath+name+".class"))){
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int b = 0;
			while ((b=fileInputStream.read())!=-1) {
				baos.write(b);
			}
			return baos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
