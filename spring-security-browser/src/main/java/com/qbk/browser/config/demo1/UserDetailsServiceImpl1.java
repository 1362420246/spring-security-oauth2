package com.qbk.browser.config.demo1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 用户及其权限配置
 * 加载特定用户数据的核心接口
 */
@Slf4j
//TODO @Component("userDetailsServiceImpl1")
public class UserDetailsServiceImpl1 implements UserDetailsService {

    /**
     * 密码编译器
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 加载用户及其权限
     * @param username 用户名
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("登陆的用户名:{}",username);

        /**  写死账号密码和权限
         *  User 实现了 UserDetails
         *  @param username 账号
         *  @param password 密码
         *  @param authorities 权限集合
         */
        return new User(username,
                passwordEncoder.encode("123456"),
                //用逗号分隔的字符串创建授予的权限对象数组
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,user"));
    }
}
