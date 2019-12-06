package com.qbk.browser.config.demo1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *  过滤器链：
 *  UsernamePasswordAuthenticationFilter 登陆的表单提交 过滤器
 *  ExceptionTranslationFilter 异常处理过滤器
 *  FilterSecurityInterceptor 最终过滤器
 *
 *  处理用户信息获取逻辑 UserDetailsService
 *  处理用户校验逻辑 UserDetails
 *  处理密码加密解密 PasswordEncoder
 *
 *
 * 1 认证管理器配置方法
 * void configure(AuthenticationManagerBuilder auth) 用来配置认证管理器AuthenticationManager。
 * 说白了就是所有 UserDetails 相关的它都管，包含 PasswordEncoder。
 *
 * 2 核心过滤器配置方法
 * void configure(WebSecurity web) 用来配置 WebSecurity 。
 * 而 WebSecurity 是基于 Servlet Filter 用来配置 springSecurityFilterChain 。
 * 而 springSecurityFilterChain 又被委托给了 Spring Security 核心过滤器 Bean DelegatingFilterProxy 。
 * 相关逻辑你可以在 WebSecurityConfiguration 中找到。
 * 我们一般不会过多来自定义 WebSecurity , 使用较多的使其ignoring() 方法用来忽略 Spring Security 对静态资源的控制。
 *
 * 3 安全过滤器链配置方法
 * void configure(HttpSecurity http) 这个是我们使用最多的，用来配置 HttpSecurity 。
 * HttpSecurity 用于构建一个安全过滤器链 SecurityFilterChain 。SecurityFilterChain 最终被注入核心过滤器 。
 * HttpSecurity 有许多我们需要的配置。我们可以通过它来进行自定义安全访问策略.
 *
 */
//TODO @Configuration
public class BrowserSecurityConfig1 extends WebSecurityConfigurerAdapter {

	/**
	 * 装载BCrypt密码编码器
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		// 设置默认的加密方式
		return new BCryptPasswordEncoder();
	}

	/**
	 * 注入 UserDetailsService
	 */
	@Autowired
	@Qualifier("userDetailsServiceImpl1")
	private UserDetailsService userDetailsServiceImpl1;

    /**
     * 用来配置拦截保护的请求，可配置什么请求放行、什么请求验证
	 * @param http 安全请求对象
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http.httpBasic()//basic 验证
		http.formLogin()//表单验证
				.loginPage("/html/login.html")//自定义登录页
				.loginProcessingUrl("/authentication/form")//登陆界面发起登陆请求的URL 使用UsernamePasswordAuthenticationFilter过滤器
				.and()//连接词
				.authorizeRequests()//授权请求
				.antMatchers("/html/login.html").permitAll()//匹配器 所有放行 未登陆用户允许的请求
				.anyRequest()//所有请求
				.authenticated()//认证
				.and()
				.csrf().disable();//禁用csrf（跨站请求伪造)
	}

	/**
	 * 用来配置用户签名服务，主要是user-details机制，还可以给用户赋予角色
	 * @param auth 签名管理构造器，用于构建用户具体权限控制
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//配置UserDetailsService
		auth.userDetailsService(userDetailsServiceImpl1)
				//配置密码编码器
				.passwordEncoder(passwordEncoder());
	}

	/**
     * 用来配置filter链
	 * @param web spring web Security对象
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}
}
