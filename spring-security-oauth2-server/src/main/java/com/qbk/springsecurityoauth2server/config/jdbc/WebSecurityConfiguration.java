package com.qbk.springsecurityoauth2server.config.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Security配置（TODO 基于jdbc）
 */
@Configuration
//开启 Security 服务
@EnableWebSecurity
/**
 * 开启启全局 Securtiy 注解
 *
 * Spring Security默认是禁用注解的，要想开启注解，
 * 需要在继承WebSecurityConfigurerAdapter的类上加@EnableGlobalMethodSecurity注解，
 * 来判断用户对某个控制层的方法是否具有访问权限
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * 装载BCrypt密码编码器
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // 设置默认的加密方式
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置AuthenticationManagerBuilder
     *
     * AuthenticationManagerBuilder用来配置全局的认证相关的信息，
     * 其实就是AuthenticationProvider和UserDetailsService，前者是认证服务提供者，后者是认证用户（及其权限）。
     *
     * AuthenticationManagerBuilder 用于创建一个 AuthenticationManager（身份验证管理器），
     * 让我能够轻松的实现内存验证、LADP验证、基于JDBC的验证、添加UserDetailsService、添加AuthenticationProvider。
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth
                 // 在内存中启用基于用户名的身份验证
                .inMemoryAuthentication()
                .withUser("user").password(passwordEncoder().encode("123456")).roles("USER").and()
                .withUser("admin").password(passwordEncoder().encode("123456")).roles("USER", "ADMIN");
    }

    /**
     * 配置HttpSecurity
     *
     * HttpSecurity 基于Web的安全性允许为特定的http请求进行配置。
     * HttpSecurity 具体的权限控制规则配置。一个这个配置相当于xml配置中的一个标签。
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
    }

    /**
     * 配置WebSecurity
     * WebSecurity 全局请求忽略规则配置（比如说静态文件，比如说注册页面）、
     * 全局HttpFirewall配置、是否debug配置、全局SecurityFilterChain配置、
     * privilegeEvaluator、expressionHandler、securityInterceptor。
     *
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }
}
