package com.qbk.springsecurityoauth2server.config.memory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * 配置认证服务器（TODO 基于内存）
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
 */
//TODO @Configuration
//开启认证服务
//TODO @EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     *     配置认证服务器
     *     配置客户端信息：ClientDetailsServiceConfigurer
     *          inMemory：内存配置
     *          withClient：客户端标识
     *          secret：客户端安全码
     *          authorizedGrantTypes：客户端授权类型
     *          scopes：客户端授权范围
     *          redirectUris：注册回调地址
     *     配置 Web 安全
     *     通过 GET 请求访问认证服务器获取授权码
     *          端点：/oauth/authorize
     *     通过 POST 请求利用授权码访问认证服务器获取令牌
     *          端点：/oauth/token
     *
     *     附：默认的端点 URL
     *     /oauth/authorize：授权端点
     *     /oauth/token：令牌端点
     *     /oauth/confirm_access：用户确认授权提交端点
     *     /oauth/error：授权服务错误信息端点
     *     /oauth/check_token：用于资源服务访问的令牌解析端点
     *     /oauth/token_key：提供公有密匙的端点，如果你使用 JWT 令牌的话
     *
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 配置客户端
        clients
                // 使用内存设置
                .inMemory()
                // client_id
                .withClient("client")
                // client_secret
                .secret(passwordEncoder.encode("secret"))
                // 授权类型：授权码
                .authorizedGrantTypes("authorization_code")
                // 授权范围
                .scopes("app")
                // 注册回调地址
                .redirectUris("https://www.quboka.cn");
    }
}
