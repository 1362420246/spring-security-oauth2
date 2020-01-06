package com.qbk.sso.client.config;

import com.qbk.sso.client.exception.RestAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
/*
访问 http://localhost:8882/
点击 login，跳转到 securedPage 页面，页面调用资源服务器的受保护接口 /user ，会跳转到认证服务器的登录界面，要求进行登录认证。
从 http://localhost:8882/ 发出的请求登录成功之后返回8882的安全保护页面。

点击 Login，重定向到了 http://localhost:8882/securedPage，而 securedPage 是受保护的页面。
所以就重定向到了 8882 的登录URL: http://localhost:8882/login, 要求首先进行登录认证。

因为客户端配置了单点登录（@EnableOAuth2Sso），所以单点登录拦截器会读取授权服务器的配置，
发起形如： http://localhost:8881/auth/oauth/authorize?client_id=SampleClientId&redirect_uri=http://localhost:8882/ui/login&response_type=code&state=xtDCY2 的授权请求获取授权码。
然后因为上面访问的是认证服务器的资源，所以又重定向到了认证服务器的登录URL: http://localhost:8881/auth/login，也就是我们自定义的统一认证登录平台页面，要求先进行登录认证，然后才能继续发送获取授权码的请求。
 */
//支持单点登录
@EnableOAuth2Sso
@Configuration
//开启Security注解
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class OAuthClientSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     *  用来解决认证过的用户访问无权限资源时的异常
     */
    @Autowired
    private RestAccessDeniedHandler restAccessDeniedHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
    	http.csrf().disable()//禁用csrf（跨站请求伪造)
    		.antMatcher("/**")
            .authorizeRequests()
            .antMatchers("/", "/login**")
            .permitAll()
            .anyRequest()
            .authenticated();

        //配置自定义异常
        http.exceptionHandling().accessDeniedHandler(restAccessDeniedHandler);
    }

}
