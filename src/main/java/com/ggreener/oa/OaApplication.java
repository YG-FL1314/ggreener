package com.ggreener.oa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = {"com.ggreener.oa"})
@MapperScan("com.ggreener.oa.mapper")
public class OaApplication {

	public static void main(String[] args) {
		SpringApplication.run(OaApplication.class, args);
	}
}
