package com.qbk.browser.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BrowserSecurityController {

	@GetMapping("/get")
	public String get(HttpServletRequest request) {
		return "s";
	}

}
