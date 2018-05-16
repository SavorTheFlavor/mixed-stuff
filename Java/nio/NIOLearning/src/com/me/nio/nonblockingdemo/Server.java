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
		//��׼������accept�ͻ���ʱ�����������ȴ���ֱ�Ӿ���accept����ʱ�򣩣�
		//�ͻ�����һ��selectionkey�ŵ�selector��
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
					//���������Զ��ͻ�����Ϣ�¼���ʱ��
					//�ͻ�����һ��selectionkey�ŵ�selector��
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
