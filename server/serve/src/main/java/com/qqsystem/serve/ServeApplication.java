package com.qqsystem.serve;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.qqsystem.serve.mapper")
@SpringBootApplication
public class ServeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServeApplication.class, args);
    }
}