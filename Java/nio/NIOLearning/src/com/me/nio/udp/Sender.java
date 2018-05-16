package com.me.nio.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Sender {
	public static void main(String[] args) throws IOException {
		DatagramChannel dChannel = DatagramChannel.open();
		
		dChannel.configureBlocking(false);
		
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()) {
			String msg = scanner.next();
			ByteBuffer buf = ByteBuffer.allocate(1024);
			buf.put((LocalDateTime.now()+": "+msg).getBytes());
			buf.flip();
			dChannel.send(buf, new InetSocketAddress("127.0.0.1", 7744));
			buf.clear();
		}
		dChannel.close();
	}
}
