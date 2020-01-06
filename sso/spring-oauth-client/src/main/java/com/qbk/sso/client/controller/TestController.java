package com.qbk.sso.client.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    /**
     * hasRole 带默认前缀 ROLE_
     */
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get")
    public String get() {
        return "s";
    }

    /**
     * hasRole 带默认前缀 ROLE_
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get2")
    public String get2() {
        return "s";
    }


}
