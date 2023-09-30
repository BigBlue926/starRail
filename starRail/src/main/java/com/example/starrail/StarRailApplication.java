package com.example.starrail;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@MapperScan("com.example.starrail.mapper")
@SpringBootApplication
@Transactional
public class StarRailApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarRailApplication.class, args);
    }

}
