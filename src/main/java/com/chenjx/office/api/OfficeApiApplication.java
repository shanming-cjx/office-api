package com.chenjx.office.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.chenjx"})//扫描指定包路劲
@MapperScan({"com.chenjx.office.api.mapper"})
public class OfficeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OfficeApiApplication.class, args);
	}

}
