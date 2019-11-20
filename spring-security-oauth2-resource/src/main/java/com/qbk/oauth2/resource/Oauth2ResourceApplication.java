package com.qbk.oauth2.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 资源服务
 */
@SpringBootApplication
//扫描mapper类 注意使用tk.mybatis
@MapperScan(basePackages = "com.qbk.oauth2.resource.mapper")
public class Oauth2ResourceApplication {
    public static void main(String[] args) {
        SpringApplication.run(Oauth2ResourceApplication.class,args);
    }
}
