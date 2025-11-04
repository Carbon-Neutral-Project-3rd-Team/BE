package com.example.testsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class LoginController {
    @GetMapping("/login")
    public String loginP(){
        return "login";//  mustache 이름과 동일하게 작성해야함
    }
}
