package com.zero.jwt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zero.jwt.mapper")
public class ZeroJwtBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZeroJwtBootApplication.class, args);
    }

}
