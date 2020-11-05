package com.example.exam;

import com.example.exam.config.SystemConfig;
import com.example.exam.dao.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan("com.example.exam.dao")
public class ExamApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ExamApplication.class, args);
    }
}
