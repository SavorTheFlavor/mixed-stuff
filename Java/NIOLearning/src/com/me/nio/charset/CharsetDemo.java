package com.me.nio.charset;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class CharsetDemo {
	public static void main(String[] args) throws CharacterCodingException {
		Charset charset = Charset.forName("GBK");
		CharsetEncoder encoder = charset.newEncoder();
		CharsetDecoder decoder = charset.newDecoder();
		
		CharBuffer charBuffer = CharBuffer.allocate(1024);
		charBuffer.put("«·…˘√‹”Ô");
		
		charBuffer.flip();
		ByteBuffer byteBuffer = encoder.encode(charBuffer);
		for(int i=0; i<8; i++) {
			System.out.println(byteBuffer.get());
		}
		
		byteBuffer.flip();
		CharBuffer charBuffer2 = decoder.decode(byteBuffer);
		System.out.println(charBuffer2.toString());
	}
}
