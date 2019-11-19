package com.qbk.springsecurityoauth2server.config.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 配置认证服务器（基于jdbc）
 *
 * 前提：
 * 1.连接数据库并创建相关表
 * 2.在oauth_client_details表中插入客户端配置
 * INSERT INTO `oauth_client_details` VALUES ('client', NULL, '$2a$10$zDVX2dhp6Ye6PjXwK5Y2TuSQcs81vRO/vdH/sKCqB1BakbCXE789G', 'app', 'authorization_code', 'https://www.quboka.cn', NULL, NULL, NULL, NULL, NULL);
 *
 * 验证：
 * 1. 访问获取授权码：http://localhost:8088/oauth/authorize?client_id=client&response_type=code
 * 2. 选择授权后会跳转到回调地址，浏览器地址上还会包含一个授权码（code=A4vIys）如：https://www.quboka.cn/?code=A4vIys
 * 3. 通过授权码向服务器申请令牌，通过 CURL 或是 Postman 请求：
 *    url:http://client:secret@localhost:8088/oauth/token
 *    POST -H "Content-Type: application/x-www-form-urlencoded" -d 'grant_type=authorization_code&code=A4vIys'
 *
 * 返回json：
 * {
 *     "access_token": "ea5069cc-49be-4f19-b920-87c00d6e2bea",
 *     "token_type": "bearer",
 *     "expires_in": 43199,
 *     "scope": "app"
 * }
 *
 * oauth_access_token 表中会多一条token数据
 */
@Configuration
//开启认证服务
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    /**
     * 配置Token
     */
    @Bean
    public TokenStore tokenStore() {
        // 基于 JDBC 实现，令牌保存到数据
        return new JdbcTokenStore(dataSource);
    }

    /**
     * 配置jdbc
     */
    @Bean
    public ClientDetailsService jdbcClientDetails() {
        // 基于 JDBC 实现，需要事先在数据库配置客户端信息
        return new JdbcClientDetailsService(dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 设置令牌
        endpoints.tokenStore(tokenStore());
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //从数据库中 读取客户端配置
        clients.withClientDetails(jdbcClientDetails());
    }
}
