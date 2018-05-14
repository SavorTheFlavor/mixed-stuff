package com.me.nio.channeldemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileUtil {
	
	/**
	 * @param src  the file to be copied
	 * @param dest  the destination location of file to copy to
	 * @throws IOException
	 */
	public static void transfer(File src, File dest) throws IOException {
		FileInputStream fis = new FileInputStream(src);
		FileOutputStream fos = new FileOutputStream(dest);
		
		FileChannel inChannel = fis.getChannel();
		FileChannel outChannel = fos.getChannel();
		
		/*transfer between channels.... */ 
		//outChannel.transferFrom(inChannel, 0, inChannel.size());
		
		/* scattering  and  gathering */
	//		inChannel.read(ByteBuffer[] dests);
	//		outChannel.write(ByteBuffer[] srcs);
		
		ByteBuffer buf = ByteBuffer.allocate(1024);
		while((inChannel.read(buf)) != -1) {
			buf.flip();
			outChannel.write(buf);
			buf.clear();
		}
		
		fis.close();
		fos.close();
		
	}
	
	/**
	 * use direct memory to transfer the src file to dest
	 * @param src
	 * @param dest
	 * @throws IOException
	 */
	public static void transferByDirectMemory(String srcFile, String destFile) throws IOException {
		FileChannel inChannel = FileChannel.open(Paths.get(srcFile), StandardOpenOption.READ);
		FileChannel outChannel = FileChannel.open(Paths.get(destFile), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
		
		MappedByteBuffer inMappedByteBuffer = inChannel.map(MapMode.READ_ONLY, 0, inChannel.size());
		MappedByteBuffer outMappedByteBuffer = outChannel.map(MapMode.READ_WRITE, 0, inChannel.size());
		
		byte[] buf = new byte[inMappedByteBuffer.limit()];
		inMappedByteBuffer.get(buf);
		outMappedByteBuffer.put(buf);
	}
	
	public static void main(String[] args) throws IOException {
		//transfer(new File("E:\\92.jpg"), new File("E:\\93.jpg"));
		transferByDirectMemory("E:\\92.jpg", "E:\\92222.jpg");
	}
}
