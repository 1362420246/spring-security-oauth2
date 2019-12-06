package com.qbk.browser.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：quboka
 * @description：
 * @date ：2019/12/6 15:38
 */
@RestController
public class BrowserSecurityController {

    @GetMapping("/get")
    public String get() {
        return "s";
    }

}
