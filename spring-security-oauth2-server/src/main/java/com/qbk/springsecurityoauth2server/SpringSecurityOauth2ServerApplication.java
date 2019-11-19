package com.qbk.springsecurityoauth2server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 认证服务
 */
@SpringBootApplication()
@MapperScan(basePackages = "com.qbk.springsecurityoauth2server.mapper")
public class SpringSecurityOauth2ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityOauth2ServerApplication.class, args);
    }

}
