package com.wzw.shirospringboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wzw.shirospringboot.dao")
public class ShirospringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShirospringbootApplication.class, args);
	}

}
