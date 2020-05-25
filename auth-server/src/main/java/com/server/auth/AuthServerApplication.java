package com.server.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 程龙[chenglonghy]
 * @date 2020/5/15 - 16:20
 * @history 2020/5/15 - 16:20 chenglonghy  create.
 */
@MapperScan(basePackages = {"com.server.auth.dao"})
@SpringBootApplication
public class AuthServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }
}
