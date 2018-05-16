package com.me.nio.nonblockingdemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Server {
	
	public static void main(String[] args) throws IOException {
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		serverChannel.bind(new InetSocketAddress(7722));
		
		serverChannel.configureBlocking(false);
		
		Selector selector = Selector.open();
		//当准备可以accept客户端时（不用阻塞等待，直接就能accept到的时候），
		//就会生成一个selectionkey放到selector中
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		
		ByteBuffer buf = ByteBuffer.allocate(2048);
		
		while(selector.select() > 0) {
			 Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
			while(iterator.hasNext()) {
				SelectionKey selectionKey = iterator.next();
				if(selectionKey.isAcceptable()) {
					SocketChannel client = serverChannel.accept();
					if(client == null) {
						continue;
					}
					client.configureBlocking(false);
					//当产生可以读客户端消息事件的时候
					//就会生成一个selectionkey放到selector中
					client.register(selector, SelectionKey.OP_READ);
				}else if(selectionKey.isReadable()) {
					SocketChannel client = (SocketChannel)selectionKey.channel();
					int len = 0;
					while((len = client.read(buf)) > 0) {
						buf.flip();
						System.out.println("message from client: "+new String(buf.array(), 0, len));
						buf.clear();
					}
				}
			}
			iterator.remove();
		}
	}
}
