package com.iogogogo.security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by tao.zeng on 2019-03-25.
 */
@SpringBootApplication
@MapperScan("com.iogogogo.security.mapper")
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }
}
