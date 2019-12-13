package com.qbk.browser.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：quboka
 * @description：
 * @date ：2019/12/6 15:38
 */
@RestController
public class BrowserSecurityController {

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/get")
    public String get() {
        return "s";
    }

    /**
     * hasRole 带默认前缀 ROLE_
     */
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/get2")
    public String get2() {
        return "s";
    }

    @GetMapping("/index")
    public Object index(Authentication authentication) {
        // return SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }
}
