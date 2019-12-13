package com.qbk.browser.config.demo2;
import com.qbk.browser.config.exception.EntryPointUnauthorizedHandler;
import com.qbk.browser.config.exception.RestAccessDeniedHandler;
import com.qbk.browser.config.handler.MyAuthenticationFailureHandler;
import com.qbk.browser.config.handler.MyAuthenticationSucessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
@Configuration
/**
 * 开启启全局 Securtiy 注解
 *
 * Spring Security默认是禁用注解的，要想开启注解，
 * 需要在继承WebSecurityConfigurerAdapter的类上加@EnableGlobalMethodSecurity注解，
 * 来判断用户对某个控制层的方法是否具有访问权限
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class BrowserSecurityConfig2 extends WebSecurityConfigurerAdapter {

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
	@Qualifier("userDetailsServiceImpl2")
	private UserDetailsService userDetailsServiceImpl2;

    /**
     * successHandler
     */
    @Autowired
    private MyAuthenticationSucessHandler authenticationSucessHandler;

    /**
     * failureHandler
     */
    @Autowired
    private MyAuthenticationFailureHandler authenticationFailureHandler;


    /**
     *  用来解决匿名用户访问无权限资源时的异常
     */
    @Autowired
    private EntryPointUnauthorizedHandler entryPointUnauthorizedHandler;

    /**
     *  用来解决认证过的用户访问无权限资源时的异常
     */
    @Autowired
    private RestAccessDeniedHandler restAccessDeniedHandler;


    /**
     * 用来配置拦截保护的请求，可配置什么请求放行、什么请求验证
	 * @param http 安全请求对象
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http.httpBasic()//basic 验证
		http.formLogin()//表单验证
				.loginPage("/authentication/require")//自定义登录页的controller
				.loginProcessingUrl("/authentication/form")//登陆界面发起登陆请求的URL
				.failureUrl("/html/login.html")//登陆失败的页面跳转URL
//                .successHandler(authenticationSucessHandler) // 处理登录成功
//                .failureHandler(authenticationFailureHandler) // 处理登录失败
				.and()//连接词
				.authorizeRequests()//授权请求 启用基于 HttpServletRequest 的访问限制，开始配置哪些URL需要被保护、哪些不需要被保护
				.antMatchers("/html/login.html","/authentication/require").permitAll()//匹配器 所有放行 未登陆用户允许的请求
				.anyRequest().authenticated() //其他所有路径都需要权限校验
				.and()
				.csrf().disable();//禁用csrf（跨站请求伪造)

        //配置自定义异常
        //http.exceptionHandling().authenticationEntryPoint(entryPointUnauthorizedHandler).accessDeniedHandler(restAccessDeniedHandler);
        http.exceptionHandling().accessDeniedHandler(restAccessDeniedHandler);

		/*
		http
				.authorizeRequests()
				.antMatchers("/index.html").permitAll()//访问index.html不要权限验证
				.anyRequest().authenticated()//其他所有路径都需要权限校验
				.and()
				.csrf().disable()//默认开启，这里先显式关闭
				.formLogin()  //内部注册 UsernamePasswordAuthenticationFilter
				.loginPage("/login.html") //表单登录页面地址
				.loginProcessingUrl("/login")//form表单POST请求url提交地址，默认为/login
				.passwordParameter("password")//form表单用户名参数名
				.usernameParameter("username") //form表单密码参数名
				.successForwardUrl("/success.html")  //登录成功跳转地址
				.failureForwardUrl("/error.html") //登录失败跳转地址
				//.defaultSuccessUrl()//如果用户没有访问受保护的页面，默认跳转到页面
				//.failureHandler(AuthenticationFailureHandler)//自定义失败处理器
				//.successHandler(AuthenticationSuccessHandler)//自定义成功处理器
				//.failureUrl("/login?error") //登陆失败的页面跳转URL
				.permitAll();//允许所有用户都有权限访问登录页面
		 */

	}

	/**
	 * 用来配置用户签名服务，主要是user-details机制，还可以给用户赋予角色
	 * @param auth 签名管理构造器，用于构建用户具体权限控制
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//配置UserDetailsService
		auth.userDetailsService(userDetailsServiceImpl2)
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
