package com.me.nio.nonblockingdemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;

public class Client {
	public static void main(String[] args) throws IOException {
		SocketChannel clientChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 7722));
		
		clientChannel.configureBlocking(false);
		
		ByteBuffer buf = ByteBuffer.allocate(2048);
		buf.put((LocalDateTime.now()+":  hey!!!").getBytes());
		buf.flip();
		clientChannel.write(buf);
		buf.clear();
		
		clientChannel.close();
	}
}
