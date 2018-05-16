package com.me.nio.udp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Scanner;

public class Receiver {
	public static void main(String[] args) throws IOException {
		DatagramChannel dChannel = DatagramChannel.open();
		
		dChannel.configureBlocking(false);
		dChannel.bind(new InetSocketAddress(7744));
		
		ByteBuffer buf = ByteBuffer.allocate(1024);
		Selector sel = Selector.open();
		
		dChannel.register(sel, SelectionKey.OP_READ);
		while(sel.select() > 0) {
			Iterator<SelectionKey> iterator = sel.selectedKeys().iterator();
			while(iterator.hasNext()) {
				SelectionKey selectionKey = iterator.next();
				if(selectionKey.isReadable()) {
					DatagramChannel dc = (DatagramChannel)selectionKey.channel();
					buf.clear();
					dc.receive(buf);
					buf.flip();
					System.out.println("Receive message:"+new String(buf.array(), 0, buf.limit())+" from "+dc.getLocalAddress());
				}
			}
			iterator.remove();
		}
		dChannel.close();
	}
}
