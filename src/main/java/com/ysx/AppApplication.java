package com.ysx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppApplication {
	public static void main(String[] args) {
		System.out.println(args);
		SpringApplication.run(AppApplication.class, args);
		System.out.println("启动成功");
		System.out.println(args);
	}

}
