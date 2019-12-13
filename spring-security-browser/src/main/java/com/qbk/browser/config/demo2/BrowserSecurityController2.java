package com.qbk.browser.config.demo2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sun.security.util.SecurityConstants;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class BrowserSecurityController2 {

	/**
	 * 把请求缓存在session中
	 * 实现“已保存的请求”逻辑，允许检索单个请求
	 */
	private RequestCache requestCache = new HttpSessionRequestCache();

	/**
	 * 重定向策略
	 * 封装框架中执行的所有类的重定向逻辑
	 */
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	/**
	 * 当需要身份认证时，跳转到这里
	 *
	 *  不重定向就返回 状态吗 401
	 */
	@RequestMapping("/authentication/require")
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public Map<String,Object> requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//获取seesion 的中 缓存 请求
		SavedRequest savedRequest = requestCache.getRequest(request , response);
		if (savedRequest != null) {
			//请求的url
			String targetUrl = savedRequest.getRedirectUrl();
			log.info("引发跳转的请求是:" + targetUrl);
			if (StringUtils.endsWithIgnoreCase(targetUrl, "login")) {
				//URL的重定向  指定的登录页
				redirectStrategy.sendRedirect(request, response, "/html/login.html");
			}
		}
		Map<String,Object> result = new HashMap<>();
		result.put("msg","访问的服务需要身份认证，请引导用户到登录页");
		return result;
	}

}
