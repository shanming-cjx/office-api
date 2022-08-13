package com.chenjx.office.api;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan({"com.chenjx"})//扫描指定包路劲
@MapperScan({"com.chenjx.office.api.mapper"})
@ServletComponentScan
@Slf4j
@EnableAsync//开启多线程
public class OfficeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OfficeApiApplication.class, args);
	}

}
