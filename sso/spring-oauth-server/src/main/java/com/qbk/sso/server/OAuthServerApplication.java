package com.qbk.sso.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//https://www.cnblogs.com/xifengxiaoma/p/10043173.html
@SpringBootApplication
//资源服务器
@EnableResourceServer
public class OAuthServerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(OAuthServerApplication.class, args);
    }

}