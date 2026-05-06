package com.qqsystem.serve;

import com.qqsystem.serve.config.AppConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.qqsystem.serve.mapper")
@EnableScheduling
@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class ServeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServeApplication.class, args);
    }
}