package com.qbk.browser.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 *  过滤器链：
	 *  UsernamePasswordAuthenticationFilter 登陆的表单提交 过滤器
	 *  ExceptionTranslationFilter 异常处理过滤器
	 *  FilterSecurityInterceptor 最终过滤器
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http.httpBasic()//basic 验证
		http.formLogin()//表单验证
				.and()
				.authorizeRequests()//授权请求
				.anyRequest()//任何请求
				.authenticated();//认证

	}
}
