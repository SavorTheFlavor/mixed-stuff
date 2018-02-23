package com.me;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//已嵌入tomcat，所以可以直接run....
@SpringBootApplication
public class HotdeploydemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotdeploydemoApplication.class, args);
	}
}
